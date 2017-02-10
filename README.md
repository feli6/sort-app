# Introduction

A demo app showing sorting of random numeric values provided in CSV format. It uses
Dropwizard framework, Google guice, hibernate,  h2 DB and Java 8.

# Running The Application

To test the sort-app, run the following commands.

* To package the application, run the following from the root directory.

        mvn package

* To setup the h2 database run.

        java -jar sort-app-web/target/sort-app-web-1.0-SNAPSHOT.jar db migrate sort-app.yaml

* To run the server run.

        java -jar sort-app-web/target/sort-app-web-1.0-SNAPSHOT.jar server sort-app.yaml


* It accepts list of numbers to be sorted in CSV format e.g "10,2,3,90,40"
   To post data into the application.

	curl -H "Content-Type: text/plain" -X POST -d '5,8,1,2,0' http://localhost:8080/sortNumbers
	
* To see the details of the already sorted input numbers, open http://localhost:8080/sortNumbers
    or 
    curl -X GET http://localhost:8080/sortNumbers
    
* Dropwizard provides nice metrics on
     http://localhost:8081  

# Configuring Application
You can add or change configurations in sort-app.yaml file in the root directory.

# TODO
*  UI for providing numbers to sort and displaying all the previous result.
*  More test coverage
*  Pagination support
*  Validations
*  Logging