# 🔔 Event Notification System

<div align="center">

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/gajendra-ingle/Event-Notification-System/actions)
[![Dockerized](https://img.shields.io/badge/docker-ready-blue)](https://github.com/gajendra-ingle/Event-Notification-System)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/gajendra-ingle/Event-Notification-System/blob/main/LICENSE)
![Lines of Code](https://tokei.rs/b1/github/gajendra-ingle/Event-Notification-System?category=code)
![Visitor Badge](https://visitor-badge.laobi.icu/badge?page_id=gajendra-ingle.Event-Notification-System) 
[![Last Commit](https://img.shields.io/github/last-commit/gajendra-ingle/Event-Notification-System)](https://github.com/gajendra-ingle/Event-Notification-System/commits/main)


</div>





A **Java-based REST API** system that accepts and processes **EMAIL**, **SMS**, and **PUSH** notification events asynchronously. Events are handled in **separate FIFO queues** and callbacks are sent to clients on completion.



## 🎯 Objective

To build a simple, scalable notification system with asynchronous processing using Java and Spring Boot, complete with Docker support, callback integration, graceful shutdown, and unit testing.



## ✅ Features

- Accepts notification events via REST API (`/api/events`)
- Processes EMAIL, SMS, and PUSH events in separate FIFO queues
- Simulated processing delays (EMAIL: 5s, SMS: 3s, PUSH: 2s)
- Simulates random failure (10% of events)
- Sends HTTP POST callback on success/failure
- Graceful shutdown (finishes in-progress tasks before exit)
- Fully Dockerized
- JUnit-based unit testing


## 🛠 Tech Stack

| Technology       | Purpose                                 |
|------------------|------------------------------------------|
| Java 17          | Programming language                     |
| Spring Boot 3    | REST API and background task management  |
| Maven            | Project and dependency management        |
| WebClient        | Async HTTP client for callbacks          |
| Docker           | Containerization                         |
| Docker Compose   | Local multi-service environment          |
| JUnit 5          | Unit testing                             |


## 🔁 Supported Events

### 1. EMAIL Event

- 📧 Sends email
- ⏱ 5 seconds simulated processing time

**Payload Example**
```json
{
  "eventType": "EMAIL",
  "payload": {
    "recipient": "user@example.com",
    "message": "Welcome to our service!"
  },
  "callbackUrl": "http://client.com/callback"
}
```

### 2. SMS Event

- 📱 Sends SMS
- ⏱ 3 seconds simulated processing time

**Payload Example**
```json
{
  "eventType": "SMS",
  "payload": {
    "phoneNumber": "+919876543210",
    "message": "Your OTP is 123456"
  },
  "callbackUrl": "http://client.com/callback"
}
```

### 3. PUSH Event

- 🔔 Sends push notification to device
- ⏱ 2 seconds simulated processing time

**Payload Example**
```json
{
  "eventType": "PUSH",
  "payload": {
    "deviceId": "xyz-abc-123",
    "message": "Your order is out for delivery"
  },
  "callbackUrl": "http://client.com/callback"
}
```



## 📡 Callback Status

Once processing completes, your system will receive an HTTP POST request to the callback URL.

### ✅ Success Callback
```json
{
  "eventId": "e123",
  "status": "COMPLETED",
  "eventType": "EMAIL",
  "processedAt": "2025-07-01T12:34:56Z"
}
```

### ❌ Failure Callback
```json
{
  "eventId": "e123",
  "status": "FAILED",
  "eventType": "SMS",
  "errorMessage": "Simulated processing failure",
  "processedAt": "2025-07-01T12:34:56Z"
}
```



## 📦 API Endpoint

### `POST /api/events`

Submits a new notification event for asynchronous processing and delivery.

#### 📤 Request Format
```json
{
  "eventType": "EMAIL",
  "payload": {
    "recipient": "user@example.com",
    "message": "Welcome to the service!"
  },
  "callbackUrl": "http://client.com/callback"
}
```
> 📝 **Note**  
> - **eventType**: `"EMAIL"`, `"SMS"`, `"PUSH"`  
> - **payload**: Varies based on `eventType`  
> - **callbackUrl**: For receiving async delivery status updates  


#### 📥 Response Format
```json
{
  "eventId": "e123",
  "message": "Event accepted for processing."
}
```



## 🚀 How to Run

### 🖥 Run Locally
```bash
./mvnw clean install -DskipTests
java -jar target/event-notification-system-1.0.0.jar
```

Visit: [http://localhost:8080/api/events](http://localhost:8080/api/events)

### 🐳 Run with Docker
```bash
docker compose up --build
```


## 🧪 Run Tests

```bash
./mvnw test
```

Test coverage includes:
- Event creation & routing
- FIFO queueing per event type
- Callback delivery
- Graceful shutdown
- Simulated failures



## 📁 Project Structure

```text
Event-Notification-System/
├── src/
│   ├── main/
│   │   ├── java/com/sprih/eventnotificationsystem/
│   │   │   ├── controller/               # REST endpoints
│   │   │   ├── dto/                      # Request/response data transfer objects
│   │   │   ├── enums/                    # Enum definitions
│   │   │   ├── service/                  # Service interfaces and implementations
│   │   │   ├── util/                     # Utility classes
│   │   │   ├── config/                   # Configuration 
│   │   │   └── EventNotificationApplication.java  # Main Spring Boot application
│   │   └── resources/
│   │       └── application.yml          # Spring Boot app configuration
│   │
│   └── test/java/com/sprih/eventnotificationsystem/
│       ├── controller/                  # Unit tests for controllers
│       ├── service/                     # Unit tests for services
│       └── ...                          # Other tests 
│
├── Dockerfile                           # Docker build definition
├── docker-compose.yml                   # Docker multi-container setup
├── pom.xml                              # Maven build and dependencies config
└── README.md                            # Project documentation
```



## 🔑 Advantages

- FIFO processing per event type
- Clear separation of concerns
- Fault-tolerant with retry-ready design
- Lightweight, Docker-ready deployment
- Easily testable with JUnit


## 🧪 Sample Test Scenarios

| Test Case | Expected Outcome |
|-----------|------------------|
| Valid EMAIL event | Added to EMAIL queue, processed in FIFO |
| Invalid event type | Returns 400 Bad Request |
| Missing fields | Returns 400 Bad Request |
| Simulated failure | 10% events return `FAILED` |
| Callback triggered | POST sent to callback URL |
| Graceful shutdown | New requests rejected, queues drained |
| Thread cleanup | All threads shutdown cleanly |


## 🤝 Contributing

We welcome contributions! To get started:

1. Fork the repository  
2. Create a new branch:
```bash
git checkout -b feature/your-feature-name
```
3. Make your changes  
4. Commit your code:
```bash
git commit -m "Add new feature"
```
5. Push to your branch:
```bash
git push origin feature/your-feature-name
```
6. Submit a pull request ✅



## 📜 License

This project is licensed under the **MIT License**.


## 🙋‍♂️ Author

**Gajendra Ingle**  
Software Engineer | Pune, India  
📧 **Email:** [gajendraingle01@gmail.com](mailto:gajendraingle01@gmail.com)  
🔗 **GitHub:** [github.com/gajendra-ingle](https://github.com/gajendra-ingle)  
💼 **LinkedIn:** [linkedin.com/in/gajendraingle](https://www.linkedin.com/in/gajendra-ingle/)


