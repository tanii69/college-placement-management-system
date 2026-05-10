# College Placement Management System

## Overview

The College Placement Management System is a web-based application developed to simplify and automate the placement process in colleges. The system helps administrators manage student records, company details, placement drives, interviews, and applications efficiently.

It provides separate modules for students and administrators, making the placement process organized, transparent, and easy to manage.

---

# Features

## Admin Module

* Admin login authentication
* Manage student records
* Add and manage companies
* Schedule interviews
* View placement applications
* Manage notifications
* Track placed students

## Student Module

* Student registration and login
* View available companies
* Apply for jobs
* Upload resumes
* View interview schedules
* Receive placement notifications

---

# Technologies Used

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java            | Backend Programming Language   |
| Spring Boot     | Backend Framework              |
| Spring Security | Authentication & Authorization |
| Hibernate / JPA | Database Operations            |
| MySQL           | Database Management            |
| HTML            | Frontend Structure             |
| CSS             | Styling                        |
| JavaScript      | Frontend Functionality         |
| Bootstrap       | Responsive UI Design           |
| Maven           | Dependency Management          |
| Git & GitHub    | Version Control                |

---

# Project Structure

```text
src/main/java/com/tanisha/placement
│
├── config
├── controller
├── entity
├── repository
├── service
└── model

src/main/resources
│
├── static
├── templates
└── application.properties
```

---

# Modules Explanation

## Authentication Module

Handles login and authentication for students and administrators using Spring Security.

## Student Management Module

Stores and manages student details, including branch, CGPA, resume, and placement status.

## Company Management Module

Allows the admin to add and manage companies visiting for campus placements.

## Application Module

Enables students to apply to companies and tracks application records.

## Interview Management Module

Schedules and manages interview rounds and interview details.

## Notification Module

Provides placement-related notifications to students.

---

# Database Used

* MySQL Database
* Hibernate ORM for database interaction
* JPA Repository for CRUD operations

---

# How to Run the Project

## Prerequisites

* Java JDK 17+
* MySQL Server
* Maven
* IntelliJ IDEA / VS Code / Eclipse

## Steps

1. Clone the repository

```bash
git clone https://github.com/tanii69/college-placement-management-system.git
```

2. Open the project in your IDE

3. Configure MySQL database in `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/placement_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Install dependencies

```bash
mvn clean install
```

5. Run the application

```bash
mvn spring-boot:run
```

6. Open browser

```text
http://localhost:8080
```

---

# Screenshots

## Login Page

<img width="814" height="473" alt="image" src="https://github.com/user-attachments/assets/c72603ff-1ae8-4a51-b2c1-7d12f2e4637c" />



## Admin Dashboard

<img width="1071" height="515" alt="image" src="https://github.com/user-attachments/assets/e9ef6a6c-f654-441d-a44f-5c7d2915f81f" />


## Student Dashboard

<img width="1029" height="515" alt="image" src="https://github.com/user-attachments/assets/b8293a9a-b0ed-4ab9-bff9-50d3d8631c62" />


## Company Management

<img width="1075" height="516" alt="image" src="https://github.com/user-attachments/assets/c5c78bb2-4adb-4acf-aa87-688430f2a5bd" />


---

# Future Enhancements

* Email notifications
* AI-based job recommendations
* Online aptitude tests
* Resume analysis system
* Company analytics dashboard
* Mobile application support

---

# Advantages of the System

* Reduces manual work
* Faster placement management
* Centralized student records
* Better communication between students and admin
* Secure authentication system

---

# Conclusion

The College Placement Management System helps streamline the campus placement process by automating student management, company management, applications, and interview scheduling. The project improves efficiency, reduces paperwork, and provides a user-friendly platform for both students and administrators.

---

# Author

Tanisha Singh

---

# GitHub Repository

[https://github.com/tanii69/college-placement-management-system](https://github.com/tanii69/college-placement-management-system)
