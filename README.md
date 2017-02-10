# Introduction

A demo app showing sorting of random numeric values. It uses
Dropwizard framework, Google guice, hibernate and h2 DB.


# Running The Application

To test the sort-app, run the following commands.

* To package the application, run the following from the root directory.

        mvn package

* To setup the h2 database run.

        java -jar sort-app-web/target/sort-app-web-1.0-SNAPSHOT.jar db migrate sort-app.yaml

* To run the server run.

        java -jar sort-app-web/target/sort-app-web-1.0-SNAPSHOT.jar server sort-app.yaml


* To post data into the application.

	curl -H "Content-Type: text/plain" -X POST -d '5,8,1,2,0' http://localhost:8080/sortNumbers
	
* To see the details of the already sorted input numbers, open http://localhost:8080/sortNumbers