## Login and Registration Example Project with Spring Security

### Build and Deploy the Project
```
mvn clean install
```

This is a Spring Boot project, so you can deploy it by simply using the main class: `Application.java`

Once deployed, you can access the app at: 

https://localhost:8081


### Set up MySQL
By default, the project is configured to use the embedded H2 database.
If you want to use the MySQL instead, you need to uncomment relevant section in the [application.properties](src/main/resources/application.properties) and create the db user as shown below:
```


### Set up Email

You need to configure the email by providing your own username and password in application.properties
You also need to use your own host, you can use Amazon or Google for example.

### Feature toggle for Geo IP Lib
The geolocation checks do not work for the IP addresses 127.0.0.1 and 0.0.0.0, 
which can be a problem when running the application locally or in a test environment.
To enable/disable the check on the geolocation, set the property `geo.ip.lib.enabled` to true/false; this is false by default.

![Alt text](arch.PNG?raw=true "ARCH")

