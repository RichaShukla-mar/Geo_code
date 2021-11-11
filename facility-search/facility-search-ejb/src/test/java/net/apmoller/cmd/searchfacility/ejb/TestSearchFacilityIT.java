/*
	 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.apmoller.cmd.searchfacility.ejb;



import javax.xml.namespace.QName;

import org.junit.Test;

import net.apmoller.services.cmd.definitions.FacilitySearchService;
import net.apmoller.services.cmd.definitions.SearchFacility;
import net.apmoller.services.cmd.definitions.SearchFacilityFault;
import net.apmoller.services.cmd.schemas.SearchFacilityRequest;
import net.apmoller.services.cmd.schemas.SearchFacilityResponse;



/**
 *		
 * @author MDE133
 */

public class TestSearchFacilityIT {

	
	@Test
	public void testSearchFacility(){
	
		/*FacilitySearchService service;
		try {
			//http://scrbipcdk001152:37801/FacilitySearch/FacilitySearchService?WSDL
			//http://10.255.83.99:7001/FacilitySearch/FacilitySearchService?WSDL     
			System.setProperty("javax.xml.bind.JAXBContext", 
	                   "com.sun.xml.internal.bind.v2.ContextFactory");        
			service = new  FacilitySearchService(new java.net.URL("http://scrbipcdk001152:37801/FacilitySearch/FacilitySearchService?WSDL"), new QName("http://services.apmoller.net/cmd/definitions", "FacilitySearchService"));
			SearchFacility port = service.getSearchFacilityPort();  
			SearchFacilityRequest searchFacilityRequest = new SearchFacilityRequest();
			searchFacilityRequest.setISOCountryCode("ZZ");
			searchFacilityRequest.setCity("City");   
			SearchFacilityResponse searchFacilityResponse	=port.searchFacility(searchFacilityRequest);
			if (searchFacilityResponse.getSearchFacilityStatus().getCode().equalsIgnoreCase("No results found!"))
			assert(true);    
		} catch (SearchFacilityFault e) {
			assert(false);
		}
		catch (Exception e) {
			assert(false);
		}*/
		  
	}
}