# Architecture Documentation

## System Overview

The database engine is built as a layered architecture with clear separation of concerns. Each layer depends only on layers below it.

```
┌──────────────────────────────────────────────────┐
│              CLI / Main Interface                │
│  - Interactive REPL for executing queries        │
│  - User input parsing and command handling       │
└────────────────────┬─────────────────────────────┘
                     │
┌────────────────────▼─────────────────────────────┐
│          Query Parser Layer                      │
│  - Tokenizer: SQL string → Tokens               │
│  - Parser: Tokens → Abstract Syntax Tree (AST)  │
│  - Validator: Type and syntax validation        │
└────────────────────┬─────────────────────────────┘
                     │
┌────────────────────▼─────────────────────────────┐
│         Query Execution Layer                    │
│  - ExecutionPlan: AST → execution strategy      │
│  - Operators: SeqScan, Filter, Project, etc.   │
│  - Optimizer: Plan optimization                 │
└────────────────────┬─────────────────────────────┘
                     │
┌────────────────────▼─────────────────────────────┐
│      Transaction & Catalog Layer                │
│  - TransactionManager: ACID guarantees          │
│  - Catalog: Table schemas and metadata          │
│  - Lock Manager: Concurrency control            │
└────────────────────┬─────────────────────────────┘
                     │
┌────────────────────▼─────────────────────────────┐
│      Index & Storage Engine Layer                │
│  - BTreeIndex: Efficient data lookup             │
│  - StorageEngine: Page management               │
│  - Data organization on disk                    │
└────────────────────┬─────────────────────────────┘
                     │
┌────────────────────▼─────────────────────────────┐
│    Buffer Pool & File I/O Layer                  │
│  - BufferPool: LRU page caching                 │
│  - FileManager: Disk I/O operations             │
│  - Page: Fixed-size disk blocks (4KB)           │
└──────────────────────────────────────────────────┘
         ↓
    [Disk Storage]
```

## Core Components

### 1. Storage Layer (Foundation)

**Responsibility**: Manage disk I/O and memory caching

**Components**:
- **FileManager**: 
  - Creates/opens database files
  - Reads/writes pages from disk
  - Manages file pointers and offsets
  
- **Page**:
  - Fixed-size data container (4KB by default)
  - Tracks page ID, dirty flag, pin count
  - Serializes/deserializes data to byte arrays
  
- **BufferPool**:
  - LRU (Least Recently Used) cache for pages
  - Implements page replacement policy
  - Tracks page access patterns
  - Configurable pool size (default: 10 pages)
  
- **StorageEngine**:
  - Coordinates FileManager and BufferPool
  - Provides abstraction for reading/writing records
  - Handles page allocation and deallocation

**Key Design Patterns**:
- Singleton: FileManager, BufferPool
- Strategy: Page replacement algorithms
- Iterator: Scanning records

**Data Flow**:
```
Query needs data
    ↓
StorageEngine.readRecord(recordId)
    ↓
BufferPool has page? → Return from cache
    ↓ (No)
FileManager.readPage(pageId) → Load from disk
    ↓
Add to BufferPool cache
    ↓
Return page with record data
```

### 2. Index Layer

**Responsibility**: Fast data lookup and range queries

**Components**:
- **BTreeNode**:
  - Leaf nodes: Store key-value pairs (recordId -> location)
  - Internal nodes: Store keys and child pointers
  - Variable fan-out based on key size
  
- **BTreeIndex**:
  - B-tree data structure (order M, typically M=100)
  - Insert: Maintains balance, handles splits
  - Search: O(log n) lookup time
  - Range scan: Efficient sequential access
  - Delete: Handles merging and rebalancing
  
- **IndexManager**:
  - Manages multiple indexes
  - Creates/drops indexes
  - Coordinates index updates with data changes

**B-Tree Properties**:
- All leaves at same depth
- Minimum degree: M/2 children (except root)
- Maximum M children per node
- Search, insert, delete: O(log n)

**Space Complexity**: O(n) for n records

### 3. Parser Layer

**Responsibility**: Convert SQL strings to executable AST

**Components**:
- **Tokenizer**:
  - Lexical analysis: SQL → tokens
  - Token types: Keywords, identifiers, operators, literals
  - Handles whitespace and comments
  - Error reporting with line numbers
  
- **Token**:
  - Type enum: SELECT, FROM, WHERE, etc.
  - Value: Actual string content
  - Position: For error reporting
  
- **SQLParser**:
  - Recursive descent parser
  - Builds AST from token stream
  - Validates syntax
  - Error recovery
  
- **AST Classes**:
  - SQLStatement: Base class
  - SelectStatement: SELECT with WHERE, ORDER BY
  - InsertStatement: INSERT with values
  - UpdateStatement: UPDATE with SET, WHERE
  - DeleteStatement: DELETE with WHERE

**Supported Grammar**:
```
SELECT column_list FROM table [WHERE condition]
INSERT INTO table VALUES (value_list)
UPDATE table SET assignments [WHERE condition]
DELETE FROM table [WHERE condition]
CREATE TABLE table (column_definitions)
```

### 4. Execution Layer

**Responsibility**: Execute AST and return results

**Components**:
- **ExecutionPlan**:
  - Converts AST to operator tree
  - Estimates cost and resource usage
  - Optimizes query (predicate pushdown, etc.)
  
- **Operator** (Base Interface):
  - Interface for all operators
  - Methods: open(), next(), close()
  - Iterator pattern for result processing
  
- **Concrete Operators**:
  - **SeqScanOperator**: Full table scan
  - **IndexScanOperator**: B-tree indexed lookup
  - **FilterOperator**: WHERE clause evaluation
  - **ProjectOperator**: Column selection
  - **InsertOperator**: Insert records
  - **UpdateOperator**: Modify records
  - **DeleteOperator**: Remove records
  - **JoinOperator**: (Future) Join operations
  
- **QueryExecutor**:
  - Executes operator tree
  - Manages result sets
  - Error handling and reporting

**Query Execution Flow**:
```
SQL: SELECT name FROM users WHERE age > 20
            ↓
Parse → SelectStatement AST
            ↓
Create ExecutionPlan:
    ┌─────────────────┐
    │ ProjectOperator │ (SELECT name)
    └────────┬────────┘
             │
    ┌────────▼────────┐
    │ FilterOperator  │ (WHERE age > 20)
    └────────┬────────┘
             │
    ┌────────▼──────────┐
    │ IndexScanOperator │ (if index on age)
    └────────┬──────────┘
             │
        [age > 20 index]
```

### 5. Catalog Layer

**Responsibility**: Schema management and metadata

**Components**:
- **Column**:
  - Name, type, constraints (NOT NULL, PRIMARY KEY)
  - Serializable for persistence
  
- **Table**:
  - Schema: List of columns
  - Metadata: Record size, row count
  - Index list: Associated indexes
  - Serialized to catalog file
  
- **Catalog**:
  - Registry of all tables
  - Singleton pattern
  - Validates table existence
  - Manages table creation/deletion

**Storage**:
- Catalog persisted to `catalog.db` file
- JSON or binary serialization
- Loaded on database startup

### 6. Transaction Layer

**Responsibility**: ACID transaction guarantees

**Components**:
- **Transaction**:
  - Transaction ID, timestamp
  - State: Active, Committed, Aborted
  - Maintains read/write sets
  - Rollback log for undo operations
  
- **TransactionManager**:
  - Manages transaction lifecycle
  - Assigns transaction IDs
  - Coordinates commit/abort
  - Enforces isolation levels
  
- **Lock**:
  - Read locks (shared)
  - Write locks (exclusive)
  - Lock manager handles conflicts
  - Deadlock detection (simple cycle detection)
  
- **WAL (Write-Ahead Log)**:
  - Logs operations before committing
  - Enables crash recovery
  - Entries: [TxnId, Operation, RecordId, OldValue, NewValue]

**ACID Implementation**:
- **Atomicity**: WAL + commit protocol
- **Consistency**: Transaction validation
- **Isolation**: Locking mechanism
- **Durability**: FSSync after commit

**Transaction States**:
```
START
  ↓
ACTIVE (read/write operations)
  ↓
COMMIT (write WAL, fsync, mark committed)
  ↓
COMMITTED

Or on error:
ACTIVE → ABORT (rollback) → ABORTED
```

### 7. CLI Layer

**Responsibility**: User interface and command execution

**Components**:
- **Main**:
  - Entry point
  - REPL (Read-Eval-Print Loop)
  - Command routing
  
- **Command Parser**:
  - Parses user input
  - Routes to appropriate handlers
  
- **Result Formatter**:
  - Formats query results as tables
  - Pretty-prints output

**User Interface**:
```
Database Engine v1.0
Type 'help' for commands or 'exit' to quit

db> CREATE TABLE users (id INT, name VARCHAR(100), age INT);
OK

db> INSERT INTO users VALUES (1, 'John', 25);
Rows affected: 1

db> SELECT * FROM users;
id | name | age
---+------+----
1  | John | 25

db> exit
```

## Data Layout

### Page Layout (4KB)

```
┌─────────────────────────────────────────┐
│ Page Header (32 bytes)                  │
│ - Page ID (4)                           │
│ - Free space pointer (4)                │
│ - Number of records (2)                 │
│ - Dirty flag (1)                        │
│ - Reserved (21)                         │
├─────────────────────────────────────────┤
│ Slot Directory (10 slots × 4 = 40)      │
│ - Offsets for variable-length records  │
├─────────────────────────────────────────┤
│ Free Space                              │
│ (for new records)                       │
├─────────────────────────────────────────┤
│ Records (variable length)               │
│ - Record 1                              │
│ - Record 2                              │
│ - ...                                   │
└─────────────────────────────────────────┘
```

### File Layout

```
database.db
├── Header Block
│   ├── File version
│   ├── Page size
│   ├── Catalog pointer
│   └── Next free page ID
├── Page 0: Metadata
├── Page 1-N: Data pages
└── Last pages: Index pages
```

## Design Patterns Used

1. **Singleton**: FileManager, BufferPool, Catalog, TransactionManager
2. **Factory**: Creating operators, transactions
3. **Strategy**: Page replacement, query optimization
4. **Iterator**: Scanning records, operator results
5. **Template Method**: Operator base class
6. **Adapter**: Converting between data formats
7. **Proxy**: Lock manager, transaction proxy

## Performance Considerations

### Time Complexity
- Point lookup: O(log n) with B-tree index
- Range scan: O(log n + k) where k = result size
- Full table scan: O(n)
- Insert: O(log n)
- Delete: O(log n)

### Space Complexity
- Storage: O(n) for n records
- B-tree overhead: ~log n height, fan-out M
- Buffer pool: Fixed size (default 10 × 4KB = 40KB)
- Indexes: Additional ~O(n) space per index

### Optimization Strategies
1. **Index selection**: Use B-tree for frequent WHERE clauses
2. **Page caching**: LRU buffer pool reduces disk I/O
3. **Query optimization**: Pushdown predicates to scans
4. **Lazy loading**: Load pages only when needed
5. **Batch operations**: Group small transactions

## Future Enhancements

1. **Query Optimization**:
   - Cost-based optimizer
   - Query plan caching
   - Statistics-driven optimization

2. **Advanced Indexing**:
   - Hash indexes
   - Multi-column indexes
   - Partial indexes

3. **Concurrency**:
   - MVCC (Multi-Version Concurrency Control)
   - Optimistic locking
   - Lock escalation

4. **Advanced Features**:
   - Aggregation functions (SUM, COUNT, AVG)
   - GROUP BY and HAVING
   - Joins (nested loop, hash, sort-merge)
   - Views and triggers
   - Stored procedures

5. **Performance**:
   - Vectorized execution
   - JIT compilation
   - Column-oriented storage

6. **Robustness**:
   - Checkpointing
   - Replication
   - Sharding

---

**Author**: Database Engine Project  
**Last Updated**: 2026-05-26
