# experienced-integration-labs

Contains the core application.

Instructions to deploy and run the application

1. Get the environment ready:

 - Install SOAPUI
 
 - Download and configure jboss-fuse-karaf-6.3.0.redhat-187.zip from access.redhat.com
    - Open a Terminal or Command and locate the jboss-fuse-karaf-6.3.0.redhat-187.zip
	  - Extract unzing the command below:
	
			$ unzip jboss-fuse-karaf-6.3.0.redhat-187.zip -d .
      
    - Go into the bin folder inside the folder created (e.g. jboss-fuse-6.3.0.redhat-187)
		
		  	$ cd jboss-fuse-6.3.0.redhat-187/bin
		
	  - Run fuse
	
			$ ./fuse    

2. Build the project

	- Download the project zip or clone the git repo
	
	- The project root is at code/06_homework-assignment/core

  	- Open a Terminal and locate the parent folder (code/06_homework-assignment/parent)
	  	- Run the command bellow
		
		  mvn clean install -DskipTests
    
	- Open a Terminal and locate the core folder (code/06_homework-assignment/core)
		- Run the command bellow (with skip tests since the webservice is not running yet)
		
		  mvn clean install -DskipTests
		  
	- Now that we have the necessary artifacts in the maven repo, we can deploy the support applications and test the important applications. In the terminal oppened to run fuse, run the following commands:
	
		osgi:install mvn:com.customer.app/artifacts/1.0-SNAPSHOT
		
		osgi:install mvn:com.customer.app/mq-service/1.0-SNAPSHOT
		
		osgi:install mvn:com.customer.app/integration-test-server/1.0-SNAPSHOT
		
	- Start the deployed applications with:
	
		osgi:start {deployed bundle id}
		
	- Open a different terminal, locate the core folder (code/06_homework-assignment/core) and run:
	
		mvn clean install
		
	Every test should have passed.
		
		
3. Deploy on karaf - return to the terminal running fuse
	- Run the command to add the local repository with the features
	
		features:addurl mvn:com.customer.app/customer-features/1.0/xml/features
		
	- Run the command to install the features
	
		features:install customer-app-01
		
	- Make sure the bundles are running
	
		osgi:list

Ignore this last topic:
(
- deploy the individual projects (I was not able to make the features work - problem with the local maven repo)
		
		JBossFuse:karaf@root> osgi:install mvn:com.customer.app/artifacts/1.0-SNAPSHOT
		JBossFuse:karaf@root> osgi:install mvn:com.customer.app/mq-service/1.0-SNAPSHOT
		JBossFuse:karaf@root> osgi:install mvn:com.customer.app/integration-test-server/1.0-SNAPSHOT
		JBossFuse:karaf@root> osgi:install mvn:com.customer.app/inbound/1.0-SNAPSHOT
		JBossFuse:karaf@root> osgi:install mvn:com.customer.app/outbound/1.0-SNAPSHOT
		JBossFuse:karaf@root> osgi:install mvn:com.customer.app/xlate/1.0-SNAPSHOT
	
	- Check the IDs of the deployed artifacts
	
		JBossFuse:karaf@root> osgi:list
	
	- Start the projects with the id of the deployed artifact:
	
		JBossFuse:karaf@root> osgi:start 346
)	
    
4. Test - return to the terminal connected to fuse

  - Use the following commands to confirm the services are working
  		
		log:set INFO
  		log:tail

  - Go to http://localhost:8181/cxf to make sure the REST and SOAP services are running
	
	- The Rest service will be listening at http://localhost:9098/cxf/demos
	
	- The SOAP service will be listening at http://localhost:8181/cxf/PersonEJBService/PersonEJB
	
  - Open SOAPUI and import the projects at /code/06_homework-assignment/tooling
  
  ALTERNATIVE:
	- Open a terminal able to do curl and run the following command
  
    curl -k http://localhost:9098/cxf/demos/persons -X POST  -d '<?xml version="1.0" encoding="UTF-8"?>
    <p:Person xmlns:p="http://www.app.customer.com"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.app.customer.com PatientDemographics.xsd ">
      <p:age>30</p:age>
      <p:legalname>
        <p:given>First</p:given>
        <p:family>Last</p:family>
      </p:legalname>
      <p:fathername>Dad</p:fathername>
      <p:mothername>Mom</p:mothername>
      <p:gender xsi:type="p:Code">
        <p:code>Male</p:code>
      </p:gender>
    </p:Person>' -H 'content-type: application/xml'
    
      
5. Conclusions

- As a different approach, we could have used different routes and output queues  in the INBOUND application for the different input objects (Person, Address,...) so that we can use different routes in XLATE and OUTBOUND applications. This way actual business objects and their operations are separated. 
We can go further and have separate applications (at least XLATE) for each business object.



		
