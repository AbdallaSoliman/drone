# drone
links live to check app API and Schema
* https://abdalla-soliman-drone.herokuapp.com/swagger-ui/index.html
* https://abdalla-soliman-drone.herokuapp.com/h2-console/

Note:
to use https://abdalla-soliman-drone.herokuapp.com/h2-console/
for first time set JDBC URL: jdbc:h2:mem:testdb then press connect

### pre-required:
    java 8
    maven 

## build:
>> mvn clean install 
## run:
>> java -jar drones-0.0.1-SNAPSHOT.jar
## test
Note:
file:  drone\src\main\resources\data.sql
has default date for drones and medication 
junit test case depend on them

