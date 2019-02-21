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
		  
	  - Create a root fabric
	  
	  	fabric:create
	  
	  - Add the camel-soap feature
	  	
		fabric:profile-edit --feature mvn:org.apache/camel-soap jboss-fuse-full
    

2. Build the project

  	- Open a Terminal and locate the parent folder
	  - Run the command bellow
		
		  $ mvn clean install
    
	- Open a Terminal and locate the core folder
	- Run the command bellow (with skip tests since the webservice is not running yet)
		
		  $ mvn clean install -DskipTests
		
3. Deploy on karaf - deploy the individual projects (I was not able to make the features work - problem with the local maven repo)
		
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
	
    
4. Test

  - Use the following command to confirm the services are working
  	JBossFuse:karaf@root> log:tail

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
    
      


		
