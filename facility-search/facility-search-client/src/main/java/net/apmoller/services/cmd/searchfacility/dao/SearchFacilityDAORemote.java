package net.apmoller.services.cmd.searchfacility.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.ejb.Remote;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;

@Remote
public interface SearchFacilityDAORemote {
	
	List<SearchFacilityVO> searchFacility(
			SearchFacilityVO searchFacilityRequest) throws SQLException;
	
	RetrieveFacilityVO retrieveFacility(
			RetrieveFacilityVO retrieveFacilityRequest)throws SQLException, DatatypeConfigurationException;

	String hBeat() throws NamingException;
	Properties getBusinessProperties();

	Properties getEnvironmentProperties();
}
