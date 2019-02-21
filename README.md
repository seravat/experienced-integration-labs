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

  	- Open a Terminal and locate the parent folder
	  - Run the command bellow
		
		  $ mvn clean install
    
	- Open a Terminal and locate the core folder
	- Run the command bellow
		
		  $ mvn clean install
		
3. Deploy on karaf
		
	- Add the feature URL
	
		JBossFuse:karaf@root> features:addurl mvn:com.customer.app/features/1.0-SNAPSHOT/xml/features
		
	- Install the customer-app-01 feature
	
		JBossFuse:karaf@root> features:install customer-app-01
	
	- Set the logging level of "com.redhat.usecase" to DEBUG
	
		JBossFuse:karaf@root> log:set DEBUG com.redhat.usecase
    
    
4. Test

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


		
