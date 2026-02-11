# Token Bucket Rate Limiter (Java)

A thread-safe Rate Limiter Engine implemented in Java using the **Token Bucket algorithm**.  
This project simulates concurrent client requests and enforces per-client rate limits using fine-grained locking and concurrent data structures.

---

## 🚀 Features

- Token Bucket rate limiting algorithm
- Per-client rate limiting
- Thread-safe design
- ConcurrentHashMap for bucket storage
- ReentrantLock for token updates
- Lazy token refill strategy (no scheduler needed)
- Metrics tracking (allowed / rejected / reject %)
- Thread pool based concurrent request simulation
- Interactive console testing UI

---

Metrics Collector


- Each client gets its own TokenBucket
- Buckets are stored in ConcurrentHashMap
- Token consumption is protected by locks
- Metrics are tracked using AtomicLong counters

---

## ⚙️ Tech Stack

- Java
- Concurrency (ExecutorService, Locks)
- ConcurrentHashMap
- AtomicLong
- Token Bucket Algorithm

---


---

## 🧪 What the Simulation Does

- Creates a fixed thread pool
- Multiple threads send repeated requests for the same client
- Demonstrates:
  - concurrency safety
  - correct token refill
  - correct allow/block behavior

---

## 📊 Example Use Cases

- API rate limiting
- Request throttling
- Gateway protection
- Per-user request control

---

## 📌 Future Improvements

- Distributed rate limiting (Redis)
- Sliding window algorithm option
- Bucket expiration cleanup
- Configurable policies
- REST API wrapper

---

## 👤 Author

Suraj Keshri




