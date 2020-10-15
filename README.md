# Smart house monitoring system

**Description**

It is an application for control use of devices in smart houses. 
<br>

All users have an opportunity to login and to view information about rooms in their house, view domestic appliances in the rooms
and description with values of their parameters. If the device has at least one parameter
it can be turned on with the default mode (minimum values of all parameters) or choose the value of device parameter.
Only the owner of the house can add devices and their parameters.
All users can turn off the device which is on. 
Users can add to the database some information about the rooms, namely humidity, temperature, levels of smoke and water.
Also there are some buttons that simulate emergencies in the house, namely fire, flood and open window. If
the system found out that something is not ok in the house, the user would see some messages about emergencies.
<br>

Admin has an opportunity to add users to the system and add to database information about the house, address
and about all rooms in it. Moreover he can view existed data in the system.
If something went wrong, admin could delete the room.
Also he/she cah add standard domestic appliances` names.

## Installation and Running
* JDK 1.8
* Apache Maven
* PostgreSql

**_Running the project:_**
1. Clone this project to your local repository
2. Change _spring.jpa.hibernate.ddl-auto_ in _application.properties_ from _update_ to _create_
3. Update database login and password in _application.properties_
4. Run `mvn spring-boot:run` from project root folder
5. The project will start on 8080 port. Use http://localhost:8080/ to view web application

Pay your attention that this project will be work correct only with the project:
https://github.com/polinaucc/smart-house-sensors, otherwise you will have 500 error when check for emergencies.

## Main Technologies and Instruments
* Spring Boot
* Spring Data, Jpa
* Spring Security
* Spring MVC
* Maven
* Log4J
* Thymeleaf
* PostgreSql