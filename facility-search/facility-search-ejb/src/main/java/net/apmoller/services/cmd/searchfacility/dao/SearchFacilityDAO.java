package net.apmoller.services.cmd.searchfacility.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import net.apmoller.services.cmd.retrievefacility.exceptions.RetrieveFacilityException;
import net.apmoller.services.cmd.searchfacility.util.SearchUtil;





/**
 * Session Bean implementation class SearchCustomerDAO.
 */
@Stateless(name = "SearchFacilityDAO")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class SearchFacilityDAO implements SearchFacilityDAORemote,
		SearchFacilityDAOLocal {

	/**
	 * Default constructor.
	 */
	public SearchFacilityDAO() {

	}

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(SearchFacilityDAO.class.getName());



	/**
	 * Container managed Datasource.
	 * */
	@Resource(name = DaoConstants.DATASOURCE_NAME)
	private DataSource searchDatasource;

	/** The properties. */
	@Autowired
	@Qualifier("springEnvironmentProperties")
	public Properties			properties;
	@Autowired
	@Qualifier("springBusinessProperties")
	public Properties			businessProperties;


	/**
	 * Inits the properties.
	 */
	@PostConstruct
	@PostActivate
	private void initProperties() {
	//	SearchUtil searchUtil = new SearchUtil();

		/*try {
			properties = searchUtil.loadProperties(DaoConstants.SIP_PROPERTIES_FILE_NAME);
			businessProperties = searchUtil.loadProperties(DaoConstants.BUSINESS_PROPERTIES_FILE_NAME);
		} catch (IOException e) {
			LOGGER.warn(e.getLocalizedMessage());
		}*/
		initLogger();

	}

	@Override
	public Properties getBusinessProperties() {
		return this.businessProperties;
	}

	@Override
	public Properties getEnvironmentProperties() {
		return this.properties;
	}
	private void initLogger() {
		org.apache.logging.log4j.core.LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File(properties.getProperty("fct.search.external.log.config.file"));
		if (file.exists()) {
			// this will force a reconfiguration
			context.setConfigLocation(file.toURI());
		}
	}

	/**
	 * Method implementation of searchCustomer functionality.
	 *
	 * @param searchFacilityRequest
	 *            the search customer request
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public List<SearchFacilityVO> searchFacility(
			SearchFacilityVO searchFacilityRequest) throws SQLException {
		LOGGER.info("Invoked DAO class:: " + this.getClass().getCanonicalName());
		try (Connection c = searchDatasource.getConnection();) {
			Map<String, String> inputMap = createInputMap(searchFacilityRequest);
			//return new QueryHelper().runSearchFacilityFecth(c, inputMap,searchFacilityRequest,businessProperties);
			return new QueryHelper().runSearchFacilityFecthWithNamedQuery(c,inputMap,searchFacilityRequest,businessProperties);

		} catch (SQLException e) {
			LOGGER.info( e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public RetrieveFacilityVO retrieveFacility(RetrieveFacilityVO retrieveCustomerRequest) throws SQLException, DatatypeConfigurationException {
		try (Connection c = searchDatasource.getConnection();) {
		String facilityID =null;
		if(retrieveCustomerRequest.getGeoID()!=null && !retrieveCustomerRequest.getGeoID().isEmpty())
		{
			facilityID = retrieveCustomerRequest.getGeoID();
			String id = new QueryHelper().runValidateGeoID(c,facilityID);
			if (id==null)
			{
				throw new RetrieveFacilityException("Invalid GeoID!");
			}

		}else if(StringUtils.isNotBlank(retrieveCustomerRequest.getRkstCode())){
			facilityID=retrieveCustomerRequest.getRkstCode();
			String id = new QueryHelper().runValidateRkst(c,facilityID);
			 if (id==null)
				{
				 throw new RetrieveFacilityException("Invalid RKST Code!");
				}
		}else if (StringUtils.isNotBlank(retrieveCustomerRequest.getBusniessUnitId())){
			facilityID=retrieveCustomerRequest.getBusniessUnitId();
			String id = new QueryHelper().runValidateBuid(c,facilityID);
			 if (id==null)
				{
				 throw new RetrieveFacilityException("Invalid Business Id Code!");
				}

		}


			return new QueryHelper().runRetrieveFacilityFecth(c,  facilityID,businessProperties);
		} catch (SQLException e) {
			LOGGER.info(e.getLocalizedMessage(), e);
			throw e;
		}
	}
	/**
	 * Creates the inputMap which will be used to construct the query.
	 *
	 * @paramsearchCustomerRequest
	 *            the search customer request
	 * @return the map
	 */
	private Map<String, String> createInputMap(
			SearchFacilityVO searchFacilityRequest) {
		Map<String, String> inputMap = new LinkedHashMap<String, String>();

		if (isProvided(searchFacilityRequest.getFacilityName())) {
			inputMap.put(DaoConstants.INPUTMAP_FACILITY_NAME,
					searchFacilityRequest.getFacilityName());
		}
		if (isProvided(searchFacilityRequest.getFacilityCategory())) {
			inputMap.put(DaoConstants.INPUTMAP_FACILITY_CATEGORY,
					searchFacilityRequest.getFacilityCategory());
		}
		if (null != searchFacilityRequest.getIsoCountryCode()
				&& isProvided(searchFacilityRequest.getCountry())) {
			inputMap.put(DaoConstants.INPUTMAP_COUNTRY,
					searchFacilityRequest.getCountry());
		}
		if (null != searchFacilityRequest.getIsoCountryCode()
				&& isProvided(searchFacilityRequest.getIsoCountryCode())) {
			inputMap.put(DaoConstants.INPUTMAP_COUNTRY_CODE,
					searchFacilityRequest.getIsoCountryCode());
		}
		if (null != searchFacilityRequest.getCountryGeoID()
				&& isProvided(searchFacilityRequest.getCountryGeoID())) {
			inputMap.put(DaoConstants.INPUTMAP_COUNTRY_GEOID,
					searchFacilityRequest.getCountryGeoID());
		}
		if (isProvided(searchFacilityRequest.getRegionGeoID())) {
			inputMap.put(DaoConstants.INPUTMAP_REGION_GEOID,
					searchFacilityRequest.getRegionGeoID());
		}
		if (isProvided(searchFacilityRequest.getCityGeoID())) {
			inputMap.put(DaoConstants.INPUTMAP_CITY_GEOID,
					searchFacilityRequest.getCityGeoID());
		}
		if (isProvided(searchFacilityRequest.getCity())) {
			inputMap.put(DaoConstants.INPUTMAP_CITY,
					searchFacilityRequest.getCity());
		}
		if (isProvided(searchFacilityRequest.getHouseNo())) {
			inputMap.put(DaoConstants.INPUTMAP_HOUSE_NO,
					searchFacilityRequest.getHouseNo());
		}
		if (isProvided(searchFacilityRequest.getStreetName())) {
			inputMap.put(DaoConstants.INPUTMAP_STREET_NAME,
					searchFacilityRequest.getStreetName());
		}
		if (isProvided(searchFacilityRequest.getRegion())) {
			inputMap.put(DaoConstants.INPUTMAP_REGION,
					searchFacilityRequest.getRegion());
		}
		if (isProvided(searchFacilityRequest.getPostalCode())) {
			inputMap.put(DaoConstants.INPUTMAP_POSTAL_CODE,
					searchFacilityRequest.getPostalCode());
		}
		if (isProvided(searchFacilityRequest.getLatitude())) {
			inputMap.put(DaoConstants.INPUTMAP_LATITUDE,
					searchFacilityRequest.getLatitude());
		}
		if (isProvided(searchFacilityRequest.getLongitude())) {
			inputMap.put(DaoConstants.INPUTMAP_LONGITUDE,
					searchFacilityRequest.getLongitude());
		}

		if (isProvided(searchFacilityRequest.getFacilityLifecycleStatus())) {
			inputMap.put(DaoConstants.INPUTMAP_LIFY_CYCLE_STATUS,
					searchFacilityRequest.getFacilityLifecycleStatus());
		}

		if (searchFacilityRequest.getOfferingCodes()!=null&&!searchFacilityRequest.getOfferingCodes().isEmpty()) {

			String offerCodes = "";

			for (String offerCode : searchFacilityRequest.getOfferingCodes()) {
				offerCodes=offerCodes+offerCode+":";
			}
			inputMap.put(DaoConstants.INPUTMAP_OFFER_CODES, offerCodes);
		}
		if (searchFacilityRequest.getFacilityTypes()!=null&&!searchFacilityRequest.getFacilityTypes().isEmpty()) {

			String facilityTypes = "";

			for (String facilityType : searchFacilityRequest.getFacilityTypes()) {
				facilityTypes=facilityTypes+facilityType+":";
			}
			inputMap.put(DaoConstants.INPUTMAP_FACILITY_TYPE, facilityTypes);
		}

		return inputMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.apmoller.services.cmd.searchcustomer.dao.SearchCustomerDAORemote#
	 * retrieveCustomer
	 * (net.apmoller.services.cmd.schemas.RetrieveCustomerRequest)
	 */


	/**
	 * Checks if is provided.
	 *
	 * @param input
	 *            the input
	 * @return true, if is provided
	 */
	private boolean isProvided(Object input) {

		if (null == input || input.toString().trim().isEmpty()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}

	}

	@Override
	public String hBeat() throws NamingException {
		// TODO Auto-generated method stub
		return "SUCCESS";
	}

}
