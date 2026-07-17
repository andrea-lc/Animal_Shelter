<div align="center">

# 🐾 Animal Shelter Management System

### *A Java application for managing an animal shelter using custom data structures and algorithms.*

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![NetBeans](https://img.shields.io/badge/Apache-NetBeans-blue?style=for-the-badge&logo=apache-netbeans-ide)
![Data Structures](https://img.shields.io/badge/Data%20Structures-Custom-success?style=for-the-badge)
![Algorithms](https://img.shields.io/badge/Algorithms-Implemented-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-Educational-lightgrey?style=for-the-badge)

---

*"Learning Data Structures through a real-world application."*

</div>

---

# 📖 Overview

**Animal Shelter Management System** is a desktop application developed in **Java** that simulates the daily operations of an animal shelter.

The project was designed not only to solve a real-world management problem, but also to demonstrate the practical implementation of **Data Structures**, **Algorithms**, and **Object-Oriented Programming (OOP)**.

Instead of relying solely on Java's built-in collections, several **custom data structures** were implemented to better understand their internal behavior and computational complexity.

---

# ✨ Features

🐶 Register new animals

❤️ Manage adoption processes

👥 Volunteer administration

📋 Shelter information management

🔍 Search records efficiently

✏️ Update and delete information

💾 Persistent storage using text files

🔄 Automatic volunteer rotation

---

# 🛠 Technologies

| Technology | Description |
|------------|-------------|
| ☕ Java | Main programming language |
| 🖥 Apache NetBeans | Development IDE |
| 📂 TXT Files | Data persistence |
| 🎯 OOP | Object-Oriented Programming |

---

# 🧠 Data Structures Implemented

One of the main objectives of this project was to **develop our own data structures**, reinforcing concepts learned during the Data Structures course.

---

## 🔗 Doubly Linked List

A custom **Doubly Linked List** was implemented to efficiently manage volunteers and dynamic records.

### Features

- Bidirectional traversal
- Efficient insertion
- Efficient deletion
- Index-based access
- Dynamic memory allocation
- Optimized traversal from the nearest end

### Time Complexity

| Operation | Complexity |
|------------|------------|
| Insert | **O(1)** |
| Search | **O(n)** |
| Delete | **O(n)** |
| Get by Index | **O(n)** |

---

## ➡️ Singly Linked List

Used to manage sequential collections where backward traversal is unnecessary.

Supports:

- Insertions
- Deletions
- Sequential traversal
- Search operations

---

## 🔄 Circular Linked List

Implemented to automate volunteer assignment.

Instead of repeatedly selecting the same volunteer, the system cycles through the list continuously, ensuring a fair distribution of responsibilities.

### Benefits

✔ Continuous iteration

✔ Fair workload distribution

✔ Efficient rotation

✔ No restart required after reaching the last node

---

# ⚙ Algorithms Used

The project includes several algorithms that interact directly with the implemented data structures.

---

## 🔍 Sequential Search

Used throughout the system to locate:

- Animals
- Volunteers
- Adoptions

**Complexity**

```
O(n)
```

---

## 🔄 Volunteer Rotation Algorithm

Using the Circular Linked List, volunteers are automatically rotated after each assignment.

This algorithm guarantees:

- Equal participation
- Balanced workload
- Continuous scheduling

---

## ✏ CRUD Algorithms

The system includes algorithms for:

- Adding new records
- Updating information
- Deleting entries
- Validating input data
- Managing relationships between entities

---

## 💾 File Persistence Algorithm

Instead of using a database, the application stores information in **text files**.

### Startup

```
TXT Files
      │
      ▼
Read Files
      │
      ▼
Rebuild Data Structures
      │
      ▼
System Ready
```

### Shutdown

```
Memory
      │
      ▼
Write Changes
      │
      ▼
TXT Files
```

---

# 🏗 Project Architecture

```
src/
│
├── 📂 EstructurasDeDatos
│     ├── ListaDoblementeEnlazada
│     ├── ListaEnlazadaSimple
│     ├── ListaCircular
│     └── Nodo
│
├── 📂 Gestores
│
├── 📂 Modelos
│
├── 📂 GUI
│
└── 📂 Archivos
```

---

# 🎯 Why This Project?

This project demonstrates how classical **Data Structures** can solve practical software engineering problems.

Instead of simply storing data, each structure was selected according to its strengths:

| Data Structure | Purpose |
|----------------|---------|
| 🔗 Doubly Linked List | Efficient volunteer management |
| ➡️ Singly Linked List | Simple sequential collections |
| 🔄 Circular Linked List | Automatic volunteer rotation |

---

# 📈 Learning Outcomes

During the development of this project we applied:

- ✅ Object-Oriented Programming
- ✅ Encapsulation
- ✅ Modularity
- ✅ Custom Data Structures
- ✅ Algorithm Design
- ✅ Dynamic Memory Management
- ✅ File Persistence
- ✅ Time Complexity Analysis

---

# 📸 Preview

> *(You can add screenshots here.)*

```
/images
    main-menu.png
    volunteers.png
    adoption.png
```

---

# 🚀 Future Improvements

- 🗄 Database integration (MySQL / PostgreSQL)
- 🌐 Web version
- 📊 Statistics dashboard
- 📅 Appointment scheduling
- 📧 Email notifications
- 🔒 User authentication

---

# 👨‍💻 Authors

Developed by students of **Universidad Tecnológica del Perú (UTP)**

Course:

**Data Structures & Algorithms**

---

<div align="center">

### ⭐ If you like this project, don't forget to leave a star!

🐾 *Every algorithm helps another animal find a home.*

</div>
