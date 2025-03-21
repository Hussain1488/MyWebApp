# MyWebApp (Terminal-Based Application)

MyWebApp is a terminal-based Java application designed to simulate a web application's backend functionality. It includes features like user authentication, product management, and order tracking, all accessible through a command-line interface.

---

## **Features**

- **User Authentication**:
  - Register and log in with a username and password.
  - Securely hashed passwords.
- **Product Management**:
  - Admins can add, update, and delete products.
  - Customers can view product details.
- **Order Management**:
  - Customers can create and view orders.
  - Admins can manage all orders and update their status.
- **Database Integration**:
  - Uses MySQL to store user, product, and order data.

---

## **Technologies Used**

- **Programming Language**: Java.
- **Database**: MySQL.
- **Build Tool**: Maven/Gradle.
- **Version Control**: Git.

---

## **Installation**

### **1. Prerequisites**
Before running the application, ensure you have the following installed:
- **Java Development Kit (JDK)**: Version 11 or higher.
- **MySQL**: A running MySQL server.
- **Maven**: For building the project.

### **2. Clone the Repository**
Clone the repository to your local machine:
```bash
git clone https://github.com/Hussain1488/MyWebApp.git
cd MyWebApp
```
### **3. Set Up the Database**
    a. Create the Database:
      - Open your MySQL client and create a new database:
    b. Run the Schema Script:
      - Execute the project_schemas.sql script to create the necessary tables:
    c. Insert Initial Data:
      - Run the initial_data.sql script to populate the database with initial data for testing:
### **4. Configure Database Connection**
  - Update the database configuration file (src/main/resources/database.properties) with your MySQL credentials:
```
db.url=jdbc:mysql://localhost:3306/your_database_name
db.username=your_username
db.password=your_password
```

### **5. Build the Project**
- Use Maven to build the project:
```
mvn clean install
```

### **6. Run the Project**
- run the Main class to run the project.

