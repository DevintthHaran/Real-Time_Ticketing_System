# Ticket Management System

## Overview

The Ticket Management System is a comprehensive solution for managing tickets, designed with multithreading at its core. It consists of three key components:

1. **CLI Application**: A Java-based command-line interface for simulating and managing the ticketing system.
2. **Backend**: A Spring Boot service that manages ticket data, ensures concurrency safety, and exposes RESTful APIs.
3. **Frontend**: A React-based web application providing an interactive UI to configure, control, and monitor the ticketing system.

---

## Features

### CLI Application
1. **Multithreaded Simulation**:
   - Vendors add tickets to a shared pool at a specified rate.
   - Customers purchase tickets concurrently.
2. **Ticket Pool Management**:
   - Thread-safe operations using synchronization.
   - Configurable ticket pool size and release/retrieval rates.
3. **System Configuration**:
   - Save and load configurations via JSON.
   - Interactive CLI menu for parameter updates.

### Backend
1. **RESTful API**:
   - Manages tickets and configurations through API endpoints.
2. **Thread-Safe Ticket Operations**:
   - Ensures data integrity during concurrent customer and vendor operations.
3. **Configuration Management**:
   - Dynamically update system settings.
4. **Persistence**:
   - Stores ticket data in memory or an optional relational database.

### Frontend
1. **Interactive Configuration Panel**:
   - Set system parameters like ticket capacity, rates, and vendor/customer counts.
2. **Ticket Management**:
   - Add tickets for vendors and customers via an intuitive UI.
3. **System Monitoring**:
   - View the current system state and operation logs.
4. **System Control**:
   - Start and stop the system with a single click.

---

## Prerequisites

### CLI Application
- **Java JDK**: Version 22.
- **Gson Library**: For JSON handling.
- **SLF4J Library**: For logging.

### Backend
- **Java JDK**: Version 22.
- **Maven**: For dependency management.
- **Spring Boot**: Framework for building the application.
- **H2 Database (Optional)**: For data persistence.

### Frontend
- **Node.js**: Version 18 or higher`.
- **npm** or **yarn**: For package management.

---

## Dependencies

The project uses the following Maven dependencies:

### Spring Boot Dependencies
- **Spring Boot Starter Actuator**:
   - Provides production-ready features like monitoring and management.
- **Spring Boot Starter Web**:
   - Used to build RESTful web services and web applications.
- **Spring Boot DevTools**:
   - Adds development-time tools like automatic restarts.
- **Spring Boot Starter Test**:
   - Provides testing support for Spring applications.

### Other Dependencies
- **Lombok**:
   - Reduces boilerplate code by providing annotations for automatic generation of getters, setters, and other utility methods.
- **Gson**:
   - A Java library for converting Java objects into JSON and vice versa.
- **SLF4J API**:
   - A simple facade or abstraction for various logging frameworks.

---

