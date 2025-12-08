# This is a pet project of a stock/crypto exchange engine, just for fun and practice

## Architecture

Render [diagram.plantuml](diagram.plantuml) to see the architecture (but install PlantUML plugin first)

Execution chain:
API -> Business logic -> WAL (Chronicle queue / Kafka) -> Ring buffer -> Matching engine -> ...

### Tradeoffs:
- WAL will decrease latency, but will increase safety
- Chronicle queue: same instance but faster than Kafka. Could be replaced by Kafka later if cross-instance replication needed
- PostgreSQL for balances: ACID, but one instance (no geo distribution required anyway)
- Java API implementation is slower than a Go/Rust implementation, but for now I want to keep the technology stack tight
- No LMAX Disruptor

### Available order books:

    - Array bucket order book: ~50M ops/sec
    - TreeMap order book: ~5M ops/sec

Work in progress