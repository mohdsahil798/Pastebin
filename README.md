# Pastebin Lite 📝

Pastebin Lite is a simple backend-focused web application that allows users to create short text pastes and share them using a unique URL.  
Each paste can optionally expire after a given time (TTL) or after a certain number of views.

This project is built mainly to demonstrate **backend development**, **API design**, **Redis usage**, and **deployment using Docker**.

---

## 🚀 Features

- Create a text paste
- Generate a unique, shareable URL
- Optional time-based expiration (TTL)
- Optional view-based expiration
- Anyone can access the paste using the URL
- Paste automatically disappears after expiry
- Simple frontend using Thymeleaf
- Redis used for fast in-memory storage

---

## 🛠 Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data Redis
- Thymeleaf

### Database
- Redis (Upstash – cloud Redis with SSL)

### Deployment
- Docker
- Render

---

## 📁 Project Structure

