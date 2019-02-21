package com.redhat.usecase.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


import org.apache.activemq.broker.BrokerService;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.customer.app.Person;

public class CamelContextTest extends CamelSpringTestSupport{
	
	// Expected message bodies
	protected Object[] expectedResponse = {
	"<reply id=\"p:localid\">Done!</reply>" };
	
	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;
	
	// Templates to send to input endpoints
	@Produce(uri = "direct:integrateRoute")
	protected ProducerTemplate inputEndpoint;
	BrokerService broker = null;

	
	@Test
	public void testCamelRoute() throws Exception {
		String xmlRequest = readFile("src/test/data/PatientDemographics.xml");
		resultEndpoint.expectedBodiesReceived(expectedResponse);
	    
	    if(xmlRequest != null) {
	    	inputEndpoint.sendBodyAndHeader(xmlRequest,"METHOD","add");
	    }

		resultEndpoint.assertIsSatisfied();
	}
	
	@Override
	protected ClassPathXmlApplicationContext createApplicationContext() {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = null;

		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				new String[] { "bundleContext.xml", "camelTestContext.xml" });

		return classPathXmlApplicationContext;
	}
	
	
	private String readFile(String filePath) throws Exception {
		String content;
		FileInputStream fis = new FileInputStream(filePath);
		try {
			content = createCamelContext().getTypeConverter().convertTo(String.class, fis);
		} finally {
			fis.close();
		}
		return content;
	}
	
    
	  public Person unmarshalPersonXML(String personXML) throws FileNotFoundException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		    StringReader reader = new StringReader(personXML);
		    Person person = (Person) jaxbUnmarshaller.unmarshal(reader);

		    return person;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	  }

}
