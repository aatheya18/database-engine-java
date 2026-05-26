# Database Engine in Java

A functional SQL-like database engine built from scratch in Java, implementing core database concepts including B-tree indexing, transaction management, query parsing and execution.

## Project Overview

This project implements a complete database engine from first principles, covering:
- **Storage Layer**: File management and buffer pool caching
- **Indexing**: B-tree data structure for efficient lookups
- **Query Parsing**: SQL tokenizer and parser to AST
- **Query Execution**: Query executor with multiple operators
- **Schema Management**: Table creation and catalog management
- **Transactions**: ACID transaction support with WAL
- **CLI**: Interactive command-line interface

## Features

### Current Implementation (Roadmap)
- [x] Project setup with Maven
- [ ] **Phase 1**: Storage layer (Week 1-2)
- [ ] **Phase 2**: B-tree indexing (Week 2-3)
- [ ] **Phase 3**: SQL parser (Week 3-4)
- [ ] **Phase 4**: Query executor (Week 4-5)
- [ ] **Phase 5**: Catalog & schema management (Week 5-6)
- [ ] **Phase 6**: Transaction management (Week 6-7)
- [ ] **Phase 7**: CLI & integration (Week 7-8)

## Technology Stack

- **Language**: Java 17 LTS
- **Build Tool**: Maven 3.9+
- **Testing**: JUnit 5 (Jupiter), Mockito
- **Logging**: SLF4J + Logback
- **IDE**: IntelliJ IDEA / VS Code

## Project Structure

```
database-engine-java/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── LICENSE                          # MIT License
├── .gitignore                       # Git ignore rules
├── docs/
│   ├── ARCHITECTURE.md              # System architecture
│   ├── MODULE_PLAN.md               # Module breakdown
│   └── API.md                       # API documentation
├── src/
│   ├── main/java/com/dbengine/
│   │   ├── Main.java                # Entry point
│   │   ├── storage/                 # Storage layer
│   │   │   ├── StorageEngine.java
│   │   │   ├── Page.java
│   │   │   ├── BufferPool.java
│   │   │   └── FileManager.java
│   │   ├── index/                   # B-tree indexing
│   │   │   ├── BTreeIndex.java
│   │   │   ├── BTreeNode.java
│   │   │   └── IndexManager.java
│   │   ├── parser/                  # SQL parsing
│   │   │   ├── SQLParser.java
│   │   │   ├── Tokenizer.java
│   │   │   ├── Token.java
│   │   │   └── ast/
│   │   │       ├── SQLStatement.java
│   │   │       ├── SelectStatement.java
│   │   │       ├── InsertStatement.java
│   │   │       ├── UpdateStatement.java
│   │   │       └── DeleteStatement.java
│   │   ├── executor/                # Query execution
│   │   │   ├── QueryExecutor.java
│   │   │   ├── ExecutionPlan.java
│   │   │   └── Operator.java
│   │   ├── transaction/             # Transaction support
│   │   │   ├── TransactionManager.java
│   │   │   ├── Transaction.java
│   │   │   └── Lock.java
│   │   ├── catalog/                 # Schema management
│   │   │   ├── Catalog.java
│   │   │   ├── Table.java
│   │   │   └── Column.java
│   │   ├── exception/               # Custom exceptions
│   │   │   ├── DBException.java
│   │   │   ├── ParseException.java
│   │   │   └── ExecutionException.java
│   │   └── util/                    # Utilities
│   │       ├── Constants.java
│   │       ├── SerializationUtil.java
│   │       └── DBLogger.java
│   └── test/java/com/dbengine/      # Unit and integration tests
│       ├── storage/
│       ├── index/
│       ├── parser/
│       └── executor/
└── target/                          # Maven build output (gitignored)
```

## Prerequisites

- **Java 17 LTS** or higher
- **Maven 3.9+**
- **Git**
- **VS Code** (optional but recommended)

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/aatheya18/database-engine-java.git
cd database-engine-java
```

### 2. Build the Project

```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the code
- Run tests
- Build the JAR file

### 3. Run Tests

```bash
mvn test
```

To run a specific test class:

```bash
mvn test -Dtest=BufferPoolTest
```

### 4. Generate Code Coverage Report

```bash
mvn jacoco:report
```

Coverage report will be at: `target/site/jacoco/index.html`

## Usage

### Running the Database Engine

```bash
mvn exec:java -Dexec.mainClass="com.dbengine.Main"
```

Or after building:

```bash
java -jar target/database-engine-*.jar
```

### Example Commands (After CLI Implementation)

```sql
CREATE TABLE users (id INT, name VARCHAR(100), age INT);
INSERT INTO users VALUES (1, 'John', 25);
SELECT * FROM users WHERE age > 20;
UPDATE users SET age = 26 WHERE id = 1;
DELETE FROM users WHERE id = 1;
```

## Development Guide

### Code Style
- Follow Google Java Style Guide
- Use meaningful variable names
- Write JavaDoc for all public methods
- Keep methods small and focused

### Testing
- Write unit tests for all new classes
- Aim for 70%+ code coverage (80%+ ideal)
- Use descriptive test names: `testXxxWithYyyExpectZzz()`
- Mock external dependencies

### Documentation
- Update relevant docs when adding features
- Include comments for complex algorithms
- Document assumptions and limitations

## Architecture Overview

```
┌─────────────────────────────────────────┐
│           CLI Interface                 │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│    Query Parser (SQL → AST)              │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│    Query Executor & Operators            │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│    Transaction Manager & Catalog         │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│    B-tree Index & Storage Engine         │
└────────────────┬────────────────────────┘
                 │
┌─────────────────▼────────────────────────┐
│    Buffer Pool & File Manager            │
└─────────────────────────────────────────┘
```

## Roadmap & Timeline

| Week | Phase | Module | Status |
|------|-------|--------|--------|
| 1-2 | Foundation | Storage Layer | 🔄 In Progress |
| 2-3 | Data Organization | B-Tree Indexing | ⏳ Pending |
| 3-4 | Query Understanding | SQL Parser | ⏳ Pending |
| 4-5 | Query Processing | Query Executor | ⏳ Pending |
| 5-6 | Schema Management | Catalog & Tables | ⏳ Pending |
| 6-7 | ACID Properties | Transactions | ⏳ Pending |
| 7-8 | Integration | CLI & Polish | ⏳ Pending |

## Learning Resources

- [Database Internals](https://www.oreilly.com/library/view/database-internals/9781492040330/) by Alex Petrov
- [SQLite Source Code](https://www.sqlite.org/codeofconduct.html)
- [OSDev.org Database Wiki](https://wiki.osdev.org/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

This is a learning project. Contributions, suggestions, and discussions are welcome!

## Author

**Aatheya** - B.Tech CSE (4th Year)

## Questions & Support

For questions, issues, or suggestions, open an issue on GitHub or reach out directly.

---

**Last Updated**: 2026-05-26  
**Status**: 🚀 Project Initialization Complete
