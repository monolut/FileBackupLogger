# File Backup Logger

## Description

**File Backup Logger** is a JavaFX desktop application that creates backups of folders and files. The application supports normal folder backups, ZIP compression, logging of backup operations, and a graphical user interface.

The project was developed using Java, JavaFX, and Maven.

---

## Features

* Select a source folder
* Select a destination folder
* Copy folders and files
* Create timestamped backup folders
* Optional ZIP compression
* Backup operation logging
* Progress bar
* Graphical User Interface (JavaFX)
* Error handling

---

## Technologies

* Java 21/26
* JavaFX
* Maven
* Jackson (JSON)
* Java NIO
* ZIP API

---

## Project Structure

```text
FileBackupLogger
│
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│
├── logs
├── backups
├── pom.xml
└── README.md
```

---

## How to Run

### Clone the repository

```bash
git clone https://github.com/yourusername/FileBackupLogger.git
```

### Open the project

Open the project in IntelliJ IDEA.

### Run

```bash
mvn clean javafx:run
```

or run the `Main` class from IntelliJ IDEA.

---

## How to Use

1. Click **Browse** and choose the source folder.
2. Click **Browse** and choose the backup destination.
3. (Optional) Enable **ZIP Compression**.
4. Click **Start Backup**.
5. Wait until the backup is completed.

---

## Log File

After each backup, the application creates:

```text
logs/backup.log
```

The log contains:

* Backup time
* Status
* Source folder
* Destination folder
* Number of files
* Backup duration
* ZIP status

---

## Screenshots

### Main Window

<img width="1110" height="1186" alt="8D486D93-1C51-41FB-A3DC-4C136461B4EC" src="https://github.com/user-attachments/assets/77623407-3481-4b8a-9ce0-379b20ff3d2e" />

### Backup Completed

<img width="1810" height="1210" alt="A0762CF1-460F-4082-94C3-B8EDD04FD934" src="https://github.com/user-attachments/assets/b43e523e-5e85-46de-b4ef-e76044ccd2f4" />

### Backup Log

<img width="1810" height="1210" alt="A0762CF1-460F-4082-94C3-B8EDD04FD934" src="https://github.com/user-attachments/assets/fc3226c1-e2cb-4330-8167-756f76c88f01" />


