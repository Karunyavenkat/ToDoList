# ToDoList - Console Application

# To-Do List Application

## 📌 Project Overview
The **To-Do List Application** is a console-based task management system built in Java. It allows users to:

- Add tasks with due dates.
- View all pending and completed tasks.
- Update the status of tasks.
- Delete tasks.
- Receive alerts for tasks that are due today with a sound notification.

This project demonstrates practical Java programming, JDBC database connectivity, scheduled task alerts, and simple console UI design.

---

## 🎯 Purpose
The purpose of this project is to:

- Help users organize and manage daily tasks efficiently.
- Provide experience in building Java applications with database integration.
- Demonstrate handling date-based events and scheduling alerts in Java.
- Showcase integration of audio notifications in a console application.

---

## 🛠️ Technologies & Tools Used
- **Programming Language:** Java 21
- **Build Tool:** Apache Maven 3.9.11
- **Database:** MySQL
- **IDE:** Visual Studio Code or any Java IDE (Eclipse, IntelliJ IDEA)
- **Audio Support:** Java Sound API (for playing WAV files)

---

## 💻 Installed Plugins / Dependencies
- **Maven Dependencies (pom.xml):**
  ```xml
  <dependencies>
      <!-- MySQL Connector -->
      <dependency>
          <groupId>com.mysql</groupId>
          <artifactId>mysql-connector-j</artifactId>
          <version>8.0.33</version>
      </dependency>
      <!-- Exec Maven Plugin for running the application -->
      <dependency>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>exec-maven-plugin</artifactId>
          <version>3.1.0</version>
      </dependency>
  </dependencies>
