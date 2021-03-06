# User management project
This project represents the management of the users of a system.\
Implemented functionalities:
* User authentication
* Confirm user account
* Edit user
* Logout

### Requirements
For building and running the application you need:

* Java 14
* Maven 4
* MySql

### Running the application locally
In order to use the application you must use the sql script from _resources/db_\
We need this to be able to use spring security.\
#### There are several ways to run a Spring Boot application on your local machine.
One way is to execute the main method in the _java.con.users.demo.UserManagementApplication_ class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:\
```mvn spring-boot:run```