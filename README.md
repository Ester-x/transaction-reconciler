# Transaction Reconciler

## About This Project

This is an **internship project** developed to automate reconciliation of financial transactions by downloading CSV files from an SFTP server, comparing them with database records, 
and generating HTML reports of differences.

It demonstrates skills in Java development, SFTP file handling, database connectivity, and basic report generation.

---

## Features

- Connects to an SFTP server to download transaction CSV files
- Compares CSV data with records in a MySQL database
- Generates an HTML report summarizing differences
- Configurable via properties file for easy setup
- Uses Java and JSch library for SFTP operations

---

## Project Requirements

- Java 21 or later
- MySQL database with the required schema (see below)
  
CREATE DATABASE reconciliationdb;

CREATE TABLE Transactions (
    id VARCHAR(50) PRIMARY KEY,
    date DATE,
    amount DECIMAL(10,2)
);

- Access to an SFTP server configured with proper credentials
- Maven for building the project

---

## Setup and Configuration

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/transaction-reconciler.git
   cd transaction-reconciler
Configure application properties

Create a file named application.properties in the root folder (do NOT commit this file to GitHub as it contains sensitive info). Use application-example.properties as a template.

Example properties to configure:

properties
Copy code
sftp.host=localhost
sftp.port=2223
sftp.user=ester
sftp.password=your_sftp_password
sftp.remote.path=/upload/report.csv
sftp.local.path=report.csv

db.url=jdbc:mysql://localhost:3306/Reconciliationdb
db.user=root
db.password=your_db_password

report.output.path=report.html
Set up MySQL database

Make sure you have MySQL installed and running. Create the database Reconciliationdb and import the required tables/schema (you may provide a .sql file or run commands accordingly).

Build the project

Use Maven to build the project:

bash
Copy code
mvn clean install
Run the application

bash
Copy code
java -cp target/transaction-reconciler.jar com.esb.reconciler.CsvUploader
Important Notes
Sensitive information such as passwords is excluded from GitHub via .gitignore.


*You must create and configure your own application.properties file with valid credentials.

*The project assumes you have access to the specified SFTP server and database.

*This project was completed as part of an internship and serves as a demonstration of backend Java development skills.
