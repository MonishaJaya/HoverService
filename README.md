# Robotic Hover Service

## Description
This project provides a service to navigate an imaginary robotic hoover through a room and clean patches of dirt.
The service calculates the final coordinates of the hoover and the number of cleaned patches based on the provided instructions.

##Prerequisites
- Java 11 or higher
- Maven 3.9.9 or higher
- Set up JAVA_HOME and M2_HOME on windows/linux/Mac

##Installation
1.Clone the repository
git clone <repository url>
cd <repository-directory>


## Build Instructions
Build the project using Maven: mvn clean install from the repo directory to install the dependencies and build the project

## Run the application
mvn spring-boot:run

## Service Availability
After successful installation and  running application the Rest service will be available at : https://localhost:8080.


##Endpoint:
- Use the following endpoint to clean the room.
 POST https://localhost:8080/hoover/service/clean

## Requestbody :
 {
  "roomSize" : [5, 5],
  "coords" : [1, 2],
  "patches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "instructions" : "NNESEESWNWW"
}
##Responsebody : 
{
  "coords" : [1, 3],
  "patches" : 1
}

Openapi yaml settings attached 
