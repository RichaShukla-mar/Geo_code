
package net.apmoller.services.cmd.definitions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.hazelcast.util.ExceptionUtil;
import com.jamonapi.proxy.MonProxyFactory;
import com.sun.xml.ws.developer.SchemaValidation;

import net.apmoller.services.cmd.retrievefacility.exceptions.RetrieveFacilityException;
import net.apmoller.services.cmd.schemas.AuditType;
import net.apmoller.services.cmd.schemas.CityDetails;
import net.apmoller.services.cmd.schemas.CityDetailsForSearch;
import net.apmoller.services.cmd.schemas.CommercialFacilityDetailsType;
import net.apmoller.services.cmd.schemas.CountryDetails;
import net.apmoller.services.cmd.schemas.CountryDetailsForSearch;
import net.apmoller.services.cmd.schemas.DaySchedule;
import net.apmoller.services.cmd.schemas.DupFacilitiesType;
import net.apmoller.services.cmd.schemas.DupFacilityType;
import net.apmoller.services.cmd.schemas.FCTAddressType;
import net.apmoller.services.cmd.schemas.FacilitiesType;
import net.apmoller.services.cmd.schemas.FacilityAddressForSearchType;
import net.apmoller.services.cmd.schemas.FacilityAddressType;
import net.apmoller.services.cmd.schemas.FacilityCategoryEnum;
import net.apmoller.services.cmd.schemas.FacilityCategoryOperationalType;
import net.apmoller.services.cmd.schemas.FacilityCategoryType;
import net.apmoller.services.cmd.schemas.FacilityCategoryTypeForSearch;
import net.apmoller.services.cmd.schemas.FacilityDetailsType;
import net.apmoller.services.cmd.schemas.FacilityIDsType;
import net.apmoller.services.cmd.schemas.FacilityLifeCycleStatusEnum;
import net.apmoller.services.cmd.schemas.FacilityOfferingGroupType;
import net.apmoller.services.cmd.schemas.FacilityOfferingGroups;
import net.apmoller.services.cmd.schemas.FacilityOfferingType;
import net.apmoller.services.cmd.schemas.FacilityOfferingTypeForSearch;
import net.apmoller.services.cmd.schemas.FacilityOfferings;
import net.apmoller.services.cmd.schemas.FacilityType;
import net.apmoller.services.cmd.schemas.Offerings;
import net.apmoller.services.cmd.schemas.OpeningHoursType;
import net.apmoller.services.cmd.schemas.OperationalFacilityDetailsType;
import net.apmoller.services.cmd.schemas.RegionDetails;
import net.apmoller.services.cmd.schemas.RetrieveFacilityRequest;
import net.apmoller.services.cmd.schemas.RetrieveFacilityResponse;
import net.apmoller.services.cmd.schemas.SearchDuplicateFacilityRequest;
import net.apmoller.services.cmd.schemas.SearchDuplicateFacilityResponse;
import net.apmoller.services.cmd.schemas.SearchFacilityRequest;
import net.apmoller.services.cmd.schemas.SearchFacilityResponse;
import net.apmoller.services.cmd.schemas.ServiceFaultType;
import net.apmoller.services.cmd.schemas.StatusResponseType;
import net.apmoller.services.cmd.schemas.TeleCommunicationType;
import net.apmoller.services.cmd.schemas.TransportModeDetails;
import net.apmoller.services.cmd.schemas.TransportModes;
import net.apmoller.services.cmd.schemas.ValidDatePeriodType;
import net.apmoller.services.cmd.searchfacility.dao.DayScheduleVO;
import net.apmoller.services.cmd.searchfacility.dao.FacilityTypeVO;
import net.apmoller.services.cmd.searchfacility.dao.OfferingVO;
import net.apmoller.services.cmd.searchfacility.dao.RetrieveFacilityVO;
import net.apmoller.services.cmd.searchfacility.dao.SearchDuplicateFacilityDAOLocal;
import net.apmoller.services.cmd.searchfacility.dao.SearchDuplicateFacilityVO;
import net.apmoller.services.cmd.searchfacility.dao.SearchFacilityDAOLocal;
import net.apmoller.services.cmd.searchfacility.dao.SearchFacilityVO;
import net.apmoller.services.cmd.searchfacility.dao.TransportModesVO;


/**
 * Webservice implementation class for Facility Search functionality
 *
 */
@WebService(portName = "SearchFacilityPort", serviceName = "FacilitySearchService", targetNamespace = "http://services.apmoller.net/cmd/definitions", wsdlLocation = "/wsdls/SearchFacilityService.wsdl", endpointInterface = "net.apmoller.services.cmd.definitions.SearchFacility")
@SchemaValidation(handler = net.apmoller.services.cmd.definitions.ErrorHandler.class)
@BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
public class FacilitySearchService_SearchFacilityPortImpl implements SearchFacility {
	/** The LOGGER. */
	private static Logger			LOGGER	= Logger.getLogger(FacilitySearchService_SearchFacilityPortImpl.class.getName());
	/** The search dao local. */
	@EJB(name = "SearchFacilityDAO")
	private SearchFacilityDAOLocal	jSearchDaoLocal;

	/** The search duplicate dao local. */
	@EJB
	private SearchDuplicateFacilityDAOLocal	searchDuplicateDAOLocal;

	@Resource
	private WebServiceContext		ctx;

	/**
	 * Instantiates a new customer search service_ search facility port impl.
	 */


	public FacilitySearchService_SearchFacilityPortImpl() {
		System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace", "false");
	}

	/**
	 * Webservice implementation for searchFacility operation.
	 *
	 * @param parameters
	 *            the parameters
	 * @return returns net.apmoller.services.cmd.schemas.SearchFacilityResponse
	 * @throws SearchFacilityFault
	 *             the search facility fault
	 */
	public SearchFacilityResponse searchFacility(SearchFacilityRequest parameters) throws SearchFacilityFault {
		SearchFacilityResponse searchResponse = new SearchFacilityResponse();
		if(parameters.getCityDetails()==null){
			parameters.setCityDetails(new CityDetailsForSearch());
		}
		if(parameters.getCountryDetails()==null){
			parameters.setCountryDetails(new CountryDetailsForSearch());
		}
		if(parameters.getRegionDetails()==null){
			parameters.setRegionDetails(new RegionDetails());
		}
		Boolean tooBig=false;

		String limit = jSearchDaoLocal.getBusinessProperties().getProperty("record.limit");

		if ((!isProvided(parameters.getLatitude()) && isProvided(parameters.getLongitude())) || (isProvided(parameters.getLatitude()) && !isProvided(parameters.getLongitude()))|| (!isProvided(parameters.getLatitude()) && !isProvided(parameters.getLongitude()))){
			if(!(isProvided(parameters.getCountryDetails().getISOCountryCode())||isProvided(parameters.getCountryDetails().getCountryGeoID()))) {
				//throwServiceFault("Either Latitude&Longitude OR Country with anyother field is mandatory for search!", "CMDWS-503"); //$NON-NLS-2$
				StatusResponseType facilityStatus = new StatusResponseType();
				facilityStatus.setCode("CMDWS-401");
				facilityStatus.setStatus("Either Latitude&Longitude OR Country with anyother field is mandatory for search!");
				searchResponse.setSearchFacilityStatus(facilityStatus);
				return searchResponse;
			}

		}


		if(!isProvided(parameters.getFacilityCategory()))
		{

			if (parameters.getFacilityCategory() != FacilityCategoryEnum.CUST
					|| parameters.getFacilityCategory() != FacilityCategoryEnum.OPS || parameters.getFacilityCategory() != FacilityCategoryEnum.COMM)
			{

				if (isProvided(parameters.getBrand()) || isProvided(parameters.getCommercialFacilityType()) ||
						isProvided(parameters.getFunction())) {


					if (parameters.getFacilityTypes() != null) {


						if (parameters.getFacilityTypes().getFacilityType() != null) {


							if (isProvided(parameters.getFacilityTypes().getFacilityType().get(0))) {


								StatusResponseType facilityStatus = new StatusResponseType();
								facilityStatus.setCode("CMDWS-501");
								facilityStatus.setStatus("No results found!");
								searchResponse.setSearchFacilityStatus(facilityStatus);
								return searchResponse;
							}
						}

					}

				}

			}
		}

		if (parameters.getFacilityCategory()!=null) {
			if (parameters.getFacilityCategory()==FacilityCategoryEnum.CUST || parameters.getFacilityCategory()==FacilityCategoryEnum.OPS) {
				if (isProvided(parameters.getBrand()) || isProvided(parameters.getCommercialFacilityType()) ||
						isProvided(parameters.getFunction() ))
				{
					StatusResponseType facilityStatus = new StatusResponseType();
					facilityStatus.setCode("CMDWS-501");
					facilityStatus.setStatus("No results found!");
					searchResponse.setSearchFacilityStatus(facilityStatus);
					return searchResponse;


				}
			}




			if (parameters.getFacilityCategory()==FacilityCategoryEnum.CUST ||
					parameters.getFacilityCategory()==FacilityCategoryEnum.COMM) {


				if (parameters.getFacilityTypes()!=null)
				{
					if(parameters.getFacilityTypes().getFacilityType()!=null)
					{
						if(isProvided(parameters.getFacilityTypes().getFacilityType().get(0)))
						{
							StatusResponseType facilityStatus = new StatusResponseType();
							facilityStatus.setCode("CMDWS-501");
							facilityStatus.setStatus("No results found!");
							searchResponse.setSearchFacilityStatus(facilityStatus);
							return searchResponse;
						}
					}

				}

			}

			if (parameters.getFacilityCategory()==FacilityCategoryEnum.CUST ) {
				if (parameters.getOfferings()!=null)
				{
					if(parameters.getOfferings().getOfferingName()!=null)
					{
						if(isProvided(parameters.getOfferings().getOfferingName().get(0)))
						{
							StatusResponseType facilityStatus = new StatusResponseType();
							facilityStatus.setCode("CMDWS-501");
							facilityStatus.setStatus("No results found!");
							searchResponse.setSearchFacilityStatus(facilityStatus);
							return searchResponse;
						}
					}

				}
			}


			if (parameters.getFacilityCategory()!=null) {

				if (parameters.getFacilityCategory() == FacilityCategoryEnum.CUST ||
						parameters.getFacilityCategory() == FacilityCategoryEnum.OPS ||
				parameters.getFacilityCategory() == FacilityCategoryEnum.COMM) {

					if (isProvided(parameters.getBrand()) || isProvided(parameters.getCommercialFacilityType()) ||
							isProvided(parameters.getFunction())) {

						if (parameters.getFacilityTypes() != null) {

							if (parameters.getFacilityTypes().getFacilityType() != null) {

								if (isProvided(parameters.getFacilityTypes().getFacilityType().get(0))) {

									StatusResponseType facilityStatus = new StatusResponseType();
									facilityStatus.setCode("CMDWS-501");
									facilityStatus.setStatus("No results found!");
									searchResponse.setSearchFacilityStatus(facilityStatus);
									return searchResponse;
								}
							}

						}

					}
				}
			}






















		}





			if((isProvided(parameters.getCountryDetails().getISOCountryCode())||isProvided(parameters.getCountryDetails().getCountryGeoID()))&& !isAnyotherProvided(parameters)) {

					//throwServiceFault("Country with anyother field is mandatory for search!", "CMDWS-503"); //$NON-NLS-2$
					StatusResponseType facilityStatus = new StatusResponseType();
					facilityStatus.setCode("CMDWS-401");
					facilityStatus.setStatus("Country with anyother field is mandatory for search!");
					searchResponse.setSearchFacilityStatus(facilityStatus);
					return searchResponse;

				}



				if(!(isProvided(parameters.getCountryDetails().getISOCountryCode())||isProvided(parameters.getCountryDetails().getCountryGeoID()))&& isAnyotherProvided(parameters)) {
					if(!(isProvided(parameters.getLatitude()) && isProvided(parameters.getLongitude())))
					{
					//throwServiceFault("Country with anyother field is mandatory for search!", "CMDWS-503"); //$NON-NLS-2$
					StatusResponseType facilityStatus = new StatusResponseType();
					facilityStatus.setCode("CMDWS-401");
					facilityStatus.setStatus("Country with anyother field is mandatory for search!");
					searchResponse.setSearchFacilityStatus(facilityStatus);
					return searchResponse;
					}
				}
				System.currentTimeMillis();
				try {
					SearchFacilityVO input = getSearchFacilityVO(parameters);
					//SearchFacilityDAOLocal jSearchDaoLocal = (SearchFacilityDAOLocal) MonProxyFactory.monitor(searchDaoLocal);
					java.util.List<SearchFacilityVO> searchFacilityVOResponsesearchDaoLocal = jSearchDaoLocal.searchFacility(input);

					if(searchFacilityVOResponsesearchDaoLocal.size()>Integer.valueOf(limit)){
						List<SearchFacilityVO> newList=searchFacilityVOResponsesearchDaoLocal.subList(0, Integer.valueOf(limit));
						searchFacilityVOResponsesearchDaoLocal=newList;
						tooBig=true;
					}

					FacilitiesType response = new FacilitiesType();
					List<FacilityType> facilities= new ArrayList<FacilityType>();
					for (SearchFacilityVO rs : searchFacilityVOResponsesearchDaoLocal) {
						FacilityType facility = new FacilityType();
						FacilityCategoryTypeForSearch facilityCategoryType = new FacilityCategoryTypeForSearch();
						FacilityOfferings facilityOfferings = null;
						List<FacilityOfferingTypeForSearch> facilityOffering=null;
						FacilityAddressForSearchType facilityAddressType= new FacilityAddressForSearchType();
						CountryDetails countryDetails = new CountryDetails();
						CityDetails	cityDetails= new CityDetails();
						RegionDetails regionDetails= new RegionDetails();
						if(rs.getFacilityGEOId()!= null || rs.getFacilityRKSTCode()!= null||rs.getBusinessUnitId()!=null)
						{
							FacilityIDsType facilityIDs = new FacilityIDsType();
							if(rs.getFacilityGEOId()!= null &&!rs.getFacilityGEOId().isEmpty())
							{
								facilityIDs.setFacilityGEOId(rs.getFacilityGEOId());
							}
							if(rs.getFacilityRKSTCode()!= null &&!rs.getFacilityRKSTCode().isEmpty())
							{
								facilityIDs.setFacilityRKSTCode(rs.getFacilityRKSTCode());
							}

							if(rs.getBusinessUnitId()!= null &&!rs.getBusinessUnitId().isEmpty())
							{
								facilityIDs.setFacilityBusinessUnitId(rs.getBusinessUnitId());
							}
							facility.setFacilityIDs(facilityIDs);
						}
						if(rs.getFacilityName()!=null && !rs.getFacilityName().isEmpty())
						{
							facility.setFacilityName(rs.getFacilityName());
						}

						if(rs.getBrand()!=null && !rs.getBrand().isEmpty())
						{
							facility.setBrand(rs.getBrand());
						}
						if(rs.getFunction()!=null && !rs.getFunction().isEmpty())
						{
							facility.setFunction(rs.getFunction());
						}
						if(rs.getCommType()!=null && !rs.getCommType().isEmpty())
						{
							facility.setCommercialFacilityType(rs.getCommType());
						}
						if(rs.getFacilityCategory()!= null && !rs.getFacilityCategory().isEmpty())
						{
						facility.setFacilityCategory(FacilityCategoryEnum.valueOf(rs.getFacilityCategory()));
						}

						if(rs.getFacilityTypes()!= null && !rs.getFacilityTypes().isEmpty())
						{
						facilityCategoryType.getFacilityType().addAll(rs.getFacilityTypes());
						}
						if(facilityCategoryType.getFacilityType()!=null && facilityCategoryType.getFacilityType().size()>0&&facilityCategoryType.getFacilityType().get(0)!=null)
						{
							facility.setFacilityTypes(facilityCategoryType);
						}
						if(rs.getFacilityOffering()!=null)
						{

							facilityOffering= new ArrayList<FacilityOfferingTypeForSearch>();
							for (OfferingVO vo  : rs.getFacilityOffering()) {
								FacilityOfferingTypeForSearch offer= new FacilityOfferingTypeForSearch();
								offer.setOfferingCode(vo.getOffCode());
								offer.setOfferingName(vo.getOffName());
								facilityOffering.add(offer);
						   }
							facilityOfferings=new FacilityOfferings();
							facilityOfferings.getFacilityOffering().addAll(facilityOffering);
						}
						if(facilityOfferings!=null)
						{
							facility.setFacilityOfferings(facilityOfferings);
						}
						if(rs.getFacilityLifecycleStatus()!=null && !rs.getFacilityLifecycleStatus().isEmpty())
						{
							facility.setFacilityLifecycleStatus(FacilityLifeCycleStatusEnum.valueOf(rs.getFacilityLifecycleStatus()));
						}
						if(rs.getCity()!=null &&!rs.getCity().isEmpty())
						{
							cityDetails.setCity(rs.getCity());
							facilityAddressType.setCityDetails(cityDetails);
						}
						if(rs.getCityGeoID()!=null &&!rs.getCityGeoID().isEmpty())
						{
							cityDetails.setCityGeoID(rs.getCityGeoID());
							facilityAddressType.setCityDetails(cityDetails);
						}
						if(rs.getHouseNo()!=null &&!rs.getHouseNo().isEmpty())
						{
						facilityAddressType.setHouseNo(rs.getHouseNo());
						}
						if(rs.getIsoCountryCode()!=null &&!rs.getIsoCountryCode().isEmpty())
						{
							countryDetails.setISOCountryCode(rs.getIsoCountryCode());
							facilityAddressType.setCountryDetails(countryDetails);
						}
						if(rs.getCountryGeoID()!=null &&!rs.getCountryGeoID().isEmpty())
						{
							countryDetails.setCountryGeoID(rs.getCountryGeoID());
							facilityAddressType.setCountryDetails(countryDetails);
						}
						if(rs.getLatitude()!=null)
						{
						facilityAddressType.setLatitude(new BigDecimal(rs.getLatitude()));
						}
						if(rs.getLongitude()!=null)
						{
						facilityAddressType.setLongitude(new BigDecimal(rs.getLongitude()));
						}
						if(rs.getPostalCode()!=null &&!rs.getPostalCode().isEmpty())
						{
						facilityAddressType.setPostalCode(rs.getPostalCode());
						}
						if(rs.getRegion()!=null &&!rs.getRegion().isEmpty())
						{
							regionDetails.setRegion(rs.getRegion());
							facilityAddressType.setRegionDetails(regionDetails);
						}
						if(rs.getRegionGeoID()!=null &&!rs.getRegionGeoID().isEmpty())
						{
							regionDetails.setRegionGeoID(rs.getRegionGeoID());
							facilityAddressType.setRegionDetails(regionDetails);
						}

						if(rs.getStreetName()!=null &&!rs.getStreetName().isEmpty())
						{
						facilityAddressType.setStreetName(rs.getStreetName());
						}
						if(facilityAddressType!= null)
						{
						facility.setFacilityAddress(facilityAddressType);
						}
						if(facility!=null)
						{
						facilities.add(facility);
						}
					}

					response.getFacility().addAll(facilities);




					searchResponse.setFacilities(response);


				}catch(Exception  e)
				{
					Throwable rootException = getCause(e);


					if (rootException instanceof IllegalArgumentException) {
						searchResponse=new SearchFacilityResponse();
						StatusResponseType facilityStatus = new StatusResponseType();
						facilityStatus.setCode("CMDWS-305");
						facilityStatus.setStatus("The data found was incorrect!"+ExceptionUtils.getStackTrace(e));
						searchResponse.setSearchFacilityStatus(facilityStatus);
						return searchResponse;

					} else {

						LOGGER.warn(rootException.getLocalizedMessage());
				throwServiceFault(
						"Unexpected error has occurred" + " : "
								+ stackTraceToString(rootException),
						"CMDWS-903");
					}

				}
				//SearchFacilityDAOLocal jSearchDaoLocal = (SearchFacilityDAOLocal) MonProxyFactory.monitor(searchDaoLocal);

				if (null != searchResponse && null != searchResponse.getFacilities()) {
					if (searchResponse.getFacilities().getFacility().size() == 0) {
						StatusResponseType facilityStatus = new StatusResponseType();
						facilityStatus.setCode("CMDWS-501");
						facilityStatus.setStatus("No results found!");
						searchResponse.setSearchFacilityStatus(facilityStatus);

					} else if (tooBig) {


						StatusResponseType facilityStatus = new StatusResponseType();
						facilityStatus.setCode("CMDWS-401");
						facilityStatus.setStatus("Too many search results found. Please be more specific.");
						searchResponse.setSearchFacilityStatus(facilityStatus);

					}
					else {
						StatusResponseType facilityStatus = new StatusResponseType();
						facilityStatus.setCode("CMDWS-200");
						facilityStatus.setStatus("SUCCESS");
						searchResponse.setSearchFacilityStatus(facilityStatus);
					}
				}
				JAXBContext jaxbContext;
				try {
					jaxbContext = JAXBContext.newInstance(SearchFacilityResponse.class);
					Marshaller jaxbMarshaller;
					jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					StringWriter stringwriter = new StringWriter();
					jaxbMarshaller.marshal(searchResponse, stringwriter);
					String message = stringwriter.toString();
					LOGGER.info(message);

				}catch (PropertyException e) {
					LOGGER.info(e);
				}
				catch (JAXBException e) {
					LOGGER.info(e);
				}



			return 	searchResponse;
	}

	/**
	 * Gets the search customer VO.
	 *
	 * @param parameters
	 *            the parameters
	 * @return the search customer vo
	 */
	private SearchFacilityVO getSearchFacilityVO(SearchFacilityRequest parameters) {
		SearchFacilityVO input = new SearchFacilityVO();
		if (null != parameters.getCityDetails())
		{
		input.setCity(parameters.getCityDetails().getCity());
		if (null != parameters.getCityDetails().getCityGeoID()) {
			input.setCityGeoID(parameters.getCityDetails().getCityGeoID());
		}
		}
		if (null != parameters.getCountryDetails())
		{
		input.setIsoCountryCode(parameters.getCountryDetails().getISOCountryCode());
		if (null != parameters.getCountryDetails().getCountryGeoID()) {
			input.setCountryGeoID(parameters.getCountryDetails().getCountryGeoID());
		}
		}
		if (null != parameters.getFacilityCategory()) {
			input.setFacilityCategory(parameters.getFacilityCategory().value());

		}
		if (null != parameters.getFacilityLifecycleStatus()) {
			input.setFacilityLifecycleStatus(parameters.getFacilityLifecycleStatus().value());

		}
		if (null != parameters.getFacilityName()) {
			input.setFacilityName(parameters.getFacilityName());

		}

		if (null != parameters.getHouseNo()) {
			input.setHouseNo(parameters.getHouseNo());

		}
		//here we are getting this exception.
		if (null != parameters.getLatitude()) {
			input.setLatitude(parameters.getLatitude().toString());

		}
		if (null != parameters.getLongitude()) {
			input.setLongitude(parameters.getLongitude().toString());

		}
		if (null != parameters.getOfferings()) {
			Offerings offeringCodes=parameters.getOfferings();
			input.setOfferingCodes(offeringCodes.getOfferingName());

		}
		if (null != parameters.getFacilityTypes()) {
			FacilityCategoryTypeForSearch facilityTypes=(FacilityCategoryTypeForSearch)parameters.getFacilityTypes();
			input.setFacilityTypes(facilityTypes.getFacilityType());
		}
		if (null != parameters.getPostalCode()) {
			input.setPostalCode(parameters.getPostalCode());

		}
		if(null!=parameters.getRegionDetails())
		{
		if (null != parameters.getRegionDetails().getRegion()) {
			input.setRegion(parameters.getRegionDetails().getRegion());

		}


		if (null != parameters.getRegionDetails().getRegionGeoID()) {
			input.setRegionGeoID(parameters.getRegionDetails().getRegionGeoID());

		}
		}
		if (null != parameters.getStreetName()) {
			input.setStreetName(parameters.getStreetName());

		}

		if (null != parameters.getBrand()) {
			input.setBrand(parameters.getBrand());

		}
		if (null != parameters.getFunction()) {
			input.setFunction(parameters.getFunction());

		}
		if (null != parameters.getCommercialFacilityType()) {
			input.setCommType(parameters.getCommercialFacilityType());

		}




		return input;
	}
	/**
	 * Gets the retrieve facility VO.
	 *
	 * @param parameters
	 *            the parameters
	 * @return the retrieve facility Vo
	 */
	private RetrieveFacilityVO getRetrieveFacilityVO(RetrieveFacilityRequest parameters) {
		RetrieveFacilityVO input = new RetrieveFacilityVO();
		if (null != parameters.getFacilityIDs()&&null != parameters.getFacilityIDs().getFacilityRKSTCode()) {
			input.setRkstCode(parameters.getFacilityIDs().getFacilityRKSTCode());
		}
		if (null != parameters.getFacilityIDs()&&null != parameters.getFacilityIDs().getFacilityGEOId()) {
			input.setGeoID(parameters.getFacilityIDs().getFacilityGEOId());
		}

		if(StringUtils.isNotBlank(parameters.getFacilityIDs().getFacilityBusinessUnitId())){
			input.setBusniessUnitId(parameters.getFacilityIDs().getFacilityBusinessUnitId());
		}

		return input;
		}

	/**
	 * Checks if is provided.
	 *
	 * @param input
	 *            the input
	 * @return true, if is provided
	 */
	private boolean isProvided(Object input) {

		if (null == input || input.toString().trim().isEmpty()) {
			return false;
		} else {
			return true;
		}

	}


	private boolean isAnyotherProvided( SearchFacilityRequest parameters) {
		boolean facilityTypeFlag = false;
		boolean offeringCodeFlag = false;
		boolean flag=isProvided(parameters.getCityDetails().getCity())||isProvided(parameters.getFacilityCategory())||isProvided(parameters.getFacilityLifecycleStatus())
			||isProvided(parameters.getFacilityName())||isProvided(parameters.getCityDetails().getCityGeoID())
			||isProvided(parameters.getHouseNo())||isProvided(parameters.getRegionDetails().getRegionGeoID())
			||isProvided(parameters.getLatitude())||isProvided(parameters.getLongitude())
			||isProvided(parameters.getPostalCode())
			||isProvided(parameters.getRegionDetails().getRegion())||isProvided(parameters.getStreetName())||isProvided(parameters.getBrand())||isProvided(parameters.getFunction())||isProvided(parameters.getCommercialFacilityType());
		if (parameters.getFacilityTypes()!=null)
		{
			if(parameters.getFacilityTypes().getFacilityType()!=null)
			{
				facilityTypeFlag=isProvided(parameters.getFacilityTypes().getFacilityType().get(0));
			}

		}
		if (parameters.getOfferings()!=null)
		{
			if(parameters.getOfferings().getOfferingName()!=null)
			{
				offeringCodeFlag=isProvided(parameters.getOfferings().getOfferingName().get(0));
			}

		}

		return flag||facilityTypeFlag||offeringCodeFlag;
	}
	/**
	 * Creates and throws service fault.
	 *
	 * @param message
	 *            the message
	 * @param code
	 *            the code
	 * @throws SearchFacilityFault
	 *             the search facility  fault
	 */
	private void throwServiceFault(String message, String code) throws net.apmoller.services.cmd.definitions.SearchFacilityFault {
		throw new net.apmoller.services.cmd.definitions.SearchFacilityFault(message, getServiceFaultInstance(message, code));
	}
	/**
	 * Creates and throws service fault.
	 *
	 * @param message
	 *            the message
	 * @param code
	 *            the code
	 * @throwsSearchCustomerFault
	 *             the search customer fault
	 */
	private void throwRetrieveServiceFault(String message, String code) throws net.apmoller.services.cmd.definitions.RetrieveFacilityFault {
		throw new net.apmoller.services.cmd.definitions.RetrieveFacilityFault(message, getServiceFaultInstance(message, code));
	}
	/**
	 * Gets the service fault instance.
	 *
	 * @param message
	 *            the message
	 * @param faultCode
	 *            the fault code
	 * @return the service fault instance
	 */
	private ServiceFaultType getServiceFaultInstance(String message, String faultCode) {
		ServiceFaultType serviceFault = new ServiceFaultType();
		serviceFault.setCode(faultCode);
		serviceFault.setReason(message);
		return serviceFault;
	}
	private String stackTraceToString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
	/**
	 * Gets the cause.
	 *
	 * @param e
	 *            the e
	 * @return the cause
	 */
	private Throwable getCause(Throwable e) {
		Throwable cause = null;
		Throwable result = e;

		while (null != (cause = result.getCause()) && result != cause) {
			result = cause;
		}
		return result;
	}

	/**
	 * @paramduplicateCustomerList
	 * @paramduplicateCustomer
	 * @throwsSearchDuplicateCustomerFault
	 */
	private ArrayList<DupFacilityType> convertVOToResponse(List<SearchDuplicateFacilityVO> duplicateFacilityList) throws SearchDuplicateFacilityFault {
		ArrayList<DupFacilityType> duplicateFacility = new ArrayList<DupFacilityType>();
		for (SearchDuplicateFacilityVO facility : duplicateFacilityList) {
			DupFacilityType facilityResponse = new DupFacilityType();
			FacilityIDsType facilityIDsType= new FacilityIDsType();
			FacilityCategoryTypeForSearch facilityCategoryType = new FacilityCategoryTypeForSearch();
			/*OfferingCodes offeringCodes= new OfferingCodes();*/
			FacilityAddressType facilityAddressType= new FacilityAddressType();
			if(facility.getFacilityName()!=null && !facility.getFacilityName().isEmpty())
			{
			facilityResponse.setFacilityName(facility.getFacilityName());
			}
			if(facility.getFacilityCategory()!=null && !facility.getFacilityCategory().isEmpty())
			{
			facilityResponse.setFacilityCategory(FacilityCategoryEnum.fromValue(facility.getFacilityCategory()));
			}
			if(facility.getFacilityGEOId()!=null && !facility.getFacilityGEOId().isEmpty())
			{
			facilityIDsType.setFacilityGEOId(facility.getFacilityGEOId());
			}
			if(facility.getFacilityRKSTCode()!=null && !facility.getFacilityRKSTCode().isEmpty())
			{
			facilityIDsType.setFacilityRKSTCode(facility.getFacilityRKSTCode());
			}
			if(facility.getFacilityTypes()!=null && !facility.getFacilityTypes().isEmpty())
			{
			facilityCategoryType.getFacilityType().addAll(facility.getFacilityTypes());
			}
			if(facility.getCity()!=null&& !facility.getCity().isEmpty())
			{
			facilityAddressType.setCity(facility.getCity());
			}
			if(facility.getHouseNo()!=null&& !facility.getHouseNo().isEmpty())
			{
			facilityAddressType.setHouseNo(facility.getHouseNo());
			}
			if(facility.getIsoCountryCode()!=null&& !facility.getIsoCountryCode().isEmpty())
			{
			facilityAddressType.setISOCountryCode(facility.getIsoCountryCode());
			}
			if(facility.getLatitude()!= null)
			{
			facilityAddressType.setLatitude(new BigDecimal(facility.getLatitude()));
			}
			if (facility.getLongitude()!=null)
			{
			facilityAddressType.setLongitude(new BigDecimal(facility.getLongitude()));
			}
			if(facility.getPostalCode()!=null&& !facility.getPostalCode().isEmpty())
			{
			facilityAddressType.setPostalCode(facility.getPostalCode());
			}
			if(facility.getStreetName()!=null&& !facility.getStreetName().isEmpty())
			{
			facilityAddressType.setStreetName(facility.getStreetName());
			}
			if(facility.getRegion()!=null&& !facility.getRegion().isEmpty())
			{
			facilityAddressType.setRegion(facility.getRegion());
			}
			if (null != facility.getFacilityLifecycleStatus()) {
				facilityResponse.setFacilityLifecycleStatus(FacilityLifeCycleStatusEnum.fromValue(facility.getFacilityLifecycleStatus()));
			} else {
				throwSearchDuplicateServiceFault("Invalid Data", "503");
			}
			if(facilityAddressType!=null)
			{
			facilityResponse.setFacilityAddress(facilityAddressType);
			}
			if(facilityIDsType!=null)
			{
			facilityResponse.setFacilityIDs(facilityIDsType);
			}
			//facilityResponse.setOfferingCodes(offeringCodes);
			//facilityResponse.setFacilityTypes(facilityCategoryType);
			duplicateFacility.add(facilityResponse);
		}
		return duplicateFacility;
	}
	/**
	 *
	 * @param parameters
	 * @return returns
	 *         net.apmoller.services.cmd.schemas.SearchDuplicateFacilityResponse
	 * @throws SearchDuplicateFacilityFault
	 */
	public SearchDuplicateFacilityResponse searchDuplicateFacility(SearchDuplicateFacilityRequest parameters)
			throws SearchDuplicateFacilityFault {
		//***********************mohan************************************//
		SearchDuplicateFacilityResponse resp = new SearchDuplicateFacilityResponse();
		SearchDuplicateFacilityVO inputParameter = new SearchDuplicateFacilityVO();
		StatusResponseType searchDuplicateStatus = new StatusResponseType();
		if(null!=parameters.getCity())
		{
			inputParameter.setCity(parameters.getCity());
		}
		if(null!=parameters.getFacilityCategory())
		{
			if(parameters.getFacilityCategory().equals(FacilityCategoryEnum.CUST))
			{
			inputParameter.setFacilityCategory(parameters.getFacilityCategory().value());
			}
			else{
				searchDuplicateStatus.setCode("CMDWS-503");
				searchDuplicateStatus.setStatus("Only Customer facility is allowed!");
				resp.setDuplicateFacilityStatus(searchDuplicateStatus);
				return resp;
			}
		}
		if(null!=parameters.getFacilityLifecycleStatus())
		{
			inputParameter.setFacilityLifecycleStatus(parameters.getFacilityLifecycleStatus().value());
		}
		if(null!=parameters.getFacilityName())
		{
			inputParameter.setFacilityName(parameters.getFacilityName());
		}
		/*if(null!=parameters.getFacilityTypes())
		{
			inputParameter.setFacilityTypes(parameters.getFacilityTypes());
		}*/
		if(null!=parameters.getISOCountryCode())
		{
			inputParameter.setIsoCountryCode(parameters.getISOCountryCode());
		}
		if(null!=parameters.getRegion())
		{
			inputParameter.setRegion(parameters.getRegion());
		}
		if(null!=parameters.getStreetName())
		{
			inputParameter.setStreetName(parameters.getStreetName());
		}

		long startTime = System.currentTimeMillis();
		List<SearchDuplicateFacilityVO> duplicateFailityList = null;
		try {
			SearchDuplicateFacilityDAOLocal jSearchDuplicateDAOLocal = searchDuplicateDAOLocal;
			duplicateFailityList = jSearchDuplicateDAOLocal.performSearchMatchRequest(inputParameter);
			} catch (Exception e) {
			LOGGER.info(e.getLocalizedMessage(), e);
			throwSearchDuplicateServiceFault(stackTraceToString(e), "503");
			}
		long endTime = System.currentTimeMillis();
		LOGGER.info("The number of matched records found are :"
		       // + duplicateFailityList.size()
		        + " took" + (endTime - startTime) + "  seconds");

		if (null != duplicateFailityList) {
			ArrayList<DupFacilityType> duplicatefacility = convertVOToResponse(duplicateFailityList);
			DupFacilitiesType facilitiesType = new DupFacilitiesType();
			facilitiesType.getFacility().addAll(duplicatefacility);
			resp.setFacilities(facilitiesType);
			//SearchFacilityDAOLocal jSearchDaoLocal = (SearchFacilityDAOLocal) MonProxyFactory.monitor(searchDaoLocal);
			String limit=jSearchDaoLocal.getBusinessProperties().getProperty("record.limit");
			if (duplicatefacility.size() >= Integer.valueOf(limit)) {
				searchDuplicateStatus.setCode("CMDWS-401");
				searchDuplicateStatus.setStatus("Too many search results found. Please be more specific.");
			} else if (duplicatefacility.isEmpty() ) {
				searchDuplicateStatus.setCode("CMDWS-503");
				searchDuplicateStatus.setStatus("No Records found!");
			} else {
				searchDuplicateStatus.setCode("CMDWS-200");
				searchDuplicateStatus.setStatus("SUCCESS");
			}
		} else {
			searchDuplicateStatus.setCode("CMDWS-503");
			searchDuplicateStatus.setStatus("No Records found!");

		}
		resp.setDuplicateFacilityStatus(searchDuplicateStatus);
		return resp;
		//***********************mohan************************************//
	}

	/**
	 *
	 * @param parameters
	 * @return returns
	 *         net.apmoller.services.cmd.schemas.RetrieveFacilityResponse
	 * @throws RetrieveFacilityFault
	 */
	public RetrieveFacilityResponse retrieveFacility(RetrieveFacilityRequest parameters) throws RetrieveFacilityFault {
		RetrieveFacilityResponse response = new RetrieveFacilityResponse();
		if (!isProvided(parameters.getFacilityIDs().getFacilityGEOId()) && !isProvided(parameters.getFacilityIDs().getFacilityRKSTCode())&&!isProvided(parameters.getFacilityIDs().getFacilityBusinessUnitId())) {
			throwRetrieveServiceFault("Either GEO ID or RKST code or Business Unit ID is mandatory to retrieve facility.", "CMDWS-503");
		}

		System.currentTimeMillis();
		try {
			RetrieveFacilityVO input = getRetrieveFacilityVO(parameters);
			//SearchFacilityDAOLocal jSearchDaoLocal = (SearchFacilityDAOLocal) MonProxyFactory.monitor(searchDaoLocal);
			RetrieveFacilityVO rs = jSearchDaoLocal.retrieveFacility(input);
			if(rs!=null && (rs.getGeoID()!=null || rs.getRkstCode()!=null))
			{
			OpeningHoursType openingHoursType=new OpeningHoursType();
			FacilityDetailsType facilityDetailsType=new FacilityDetailsType();;
			OperationalFacilityDetailsType operationalFacilityDetailsType=new OperationalFacilityDetailsType();
			CommercialFacilityDetailsType commercialFacilityDetailsType= new CommercialFacilityDetailsType();

			List<DaySchedule> dayScheduleList = new ArrayList<DaySchedule>();
			FacilityOfferingGroups facilityOfferingGroups = new FacilityOfferingGroups();//null if no data...
			List<FacilityOfferingGroupType> facilityOfferingGroupList = new ArrayList<FacilityOfferingGroupType>();
			List<FacilityOfferingType> facilityOfferingTypeList= new ArrayList<FacilityOfferingType>();
			FCTAddressType addressType = new FCTAddressType();
			AuditType at=new AuditType();
			AuditType auditType = new AuditType();
			if((rs.getGeoID()!=null&&!rs.getGeoID().isEmpty()) ||(rs.getRkstCode()!=null&&!rs.getRkstCode().isEmpty()))
			{
			FacilityIDsType facilityIDsType=new FacilityIDsType();
			if(rs.getGeoID()!=null &&!rs.getGeoID().isEmpty())
				{
					facilityIDsType.setFacilityGEOId(rs.getGeoID());
				}
			if(rs.getRkstCode()!=null &&!rs.getRkstCode().isEmpty())
				{
					facilityIDsType.setFacilityRKSTCode(rs.getRkstCode());
				}

			if(StringUtils.isNotBlank(rs.getBusniessUnitId())){
				facilityIDsType.setFacilityBusinessUnitId(rs.getBusniessUnitId());
			}


				facilityDetailsType.setFacilityIDs(facilityIDsType);
			}

			if(rs.getFacilityName()!=null &&!rs.getFacilityName().isEmpty())
			{
				facilityDetailsType.setFacilityName(rs.getFacilityName());
			}


			if (rs.getFacilityCategory()!=null &&!rs.getFacilityCategory().isEmpty())
			{
			facilityDetailsType.setFacilityCategory(FacilityCategoryEnum.fromValue(rs.getFacilityCategory()));
			}
			if(rs.getFacilityLifecycleStatus()!=null&&!rs.getFacilityLifecycleStatus().isEmpty())
			{
			facilityDetailsType.setFacilityLifecycleStatus(FacilityLifeCycleStatusEnum.fromValue(rs.getFacilityLifecycleStatus()));
			}
			if(rs.getUrl()!=null&&!rs.getUrl().isEmpty())
			{
			facilityDetailsType.setURL(rs.getUrl());
			}
			if(rs.getDodaac()!=null&&!rs.getDodaac().isEmpty())
			{
			facilityDetailsType.setDODAAC(rs.getDodaac());
			}

			FacilityCategoryType fctype=null;
			if(rs.getFacilityTypes()!=null)
			{
				fctype=new FacilityCategoryType();
				List<FacilityCategoryOperationalType> fcotList = new ArrayList<FacilityCategoryOperationalType>();
				for (FacilityTypeVO facilityTypeVO : rs.getFacilityTypes()) {
					FacilityCategoryOperationalType fcot= new FacilityCategoryOperationalType();
					ValidDatePeriodType vpt=null;
					fcot.setFacilityType(facilityTypeVO.getFacilityType());
					if(facilityTypeVO.getValidDatePeriodVO().getValidFrom()!=null||facilityTypeVO.getValidDatePeriodVO().getValidTo()!=null)
					{
					vpt=new ValidDatePeriodType();
					vpt.setValidFrom(facilityTypeVO.getValidDatePeriodVO().getValidFrom());
					vpt.setValidTo(facilityTypeVO.getValidDatePeriodVO().getValidTo());
					}
					fcot.setValidDatePeriod(vpt);

					fcotList.add(fcot);
				}
				if(fcotList!=null  )
				{
					//if(fcotList.get(0).getFacilityType()!=null &&fcotList.get(0).getValidDatePeriod()!=null)
					//{
					fctype.getFacilityTypeDetails().addAll(fcotList);
					//}
				}
			}
			if(fctype!=null)
			{
			facilityDetailsType.setFacilityTypes(fctype);
			}
			if(rs.getExternallyExposed()!=null &&!rs.getExternallyExposed().isEmpty())
			{
				if(rs.getExternallyExposed().equalsIgnoreCase("Y"))
				{
					operationalFacilityDetailsType.setExternallyExposed(true);
					commercialFacilityDetailsType.setExternallyExposed(true);

				}
				else if(rs.getExternallyExposed().equalsIgnoreCase("N"))
				{
					operationalFacilityDetailsType.setExternallyExposed(false);
					commercialFacilityDetailsType.setExternallyExposed(false);
				}
			}
			if(rs.getExternallyOwned()!=null&&!rs.getExternallyOwned().isEmpty())
			{
				if(rs.getExternallyOwned().equalsIgnoreCase("Y"))
				{
					operationalFacilityDetailsType.setExternallyOwned(true);
					commercialFacilityDetailsType.setExternallyOwned(true);
				}
				else if(rs.getExternallyOwned().equalsIgnoreCase("N"))
				{
					operationalFacilityDetailsType.setExternallyOwned(false);
					commercialFacilityDetailsType.setExternallyOwned(false);
				}
			}
			if(rs.getFctCreationDate()!=null ||rs.getFctCreationUser()!=null ||rs.getFctLastUpdateDate()!=null ||rs.getFctLastUpdateUser()!=null)
			{
			at.setCreationDate(rs.getFctCreationDate());
			at.setCreationUser(rs.getFctCreationUser());
			at.setLastUpdateDate(rs.getFctLastUpdateDate());
			at.setLastUpdateUser(rs.getFctLastUpdateUser());
			facilityDetailsType.setFacilityAuditData(at);
			}
			if(rs.getGpsFlag()!=null&&!rs.getGpsFlag().isEmpty())
			{
				if(rs.getGpsFlag().equalsIgnoreCase("Y"))
				{
					operationalFacilityDetailsType.setGPSFlag(true);
				}
				else if(rs.getGpsFlag().equalsIgnoreCase("N"))
				{
					operationalFacilityDetailsType.setGPSFlag(false);
				}
			}
			if(rs.getGsmFlag()!=null&&!rs.getGsmFlag().isEmpty())
			{
				if(rs.getGsmFlag().equalsIgnoreCase("Y"))
				{
					operationalFacilityDetailsType.setGSMFlag(true);
				}
				else if(rs.getGsmFlag().equalsIgnoreCase("N"))
				{
					operationalFacilityDetailsType.setGSMFlag(false);
				}
			}
			if(rs.getWeightLimitInYard()!=null&&!rs.getWeightLimitInYard().isEmpty())
			{
			operationalFacilityDetailsType.setWeightLimitInYard(rs.getWeightLimitInYard());
			}
			if(rs.getWeightLimitOnCranes()!=null&&!rs.getWeightLimitOnCranes().isEmpty())
			{
			operationalFacilityDetailsType.setWeightLimitOnCranes(rs.getWeightLimitOnCranes());
			}
			if(rs.getVesselAgent()!=null&&!rs.getVesselAgent().isEmpty())
			{
				if(rs.getVesselAgent().equalsIgnoreCase("Y"))
					{
						operationalFacilityDetailsType.setVesselAgent(true);
					}
				else if(rs.getVesselAgent().equalsIgnoreCase("N"))
					{
						operationalFacilityDetailsType.setVesselAgent(false);
					}
			}
			if(rs.getOceanFreightPricing()!=null && !rs.getOceanFreightPricing().isEmpty())
			{
				if(rs.getOceanFreightPricing().equalsIgnoreCase("Y"))
					{
						operationalFacilityDetailsType.setOceanFreightPricing(true);
					}
				else if(rs.getOceanFreightPricing().equalsIgnoreCase("N"))
					{
						operationalFacilityDetailsType.setOceanFreightPricing(false);
					}
			}
			if(rs.getFacilityOfferingGroupMap()!=null &&!rs.getFacilityOfferingGroupMap().isEmpty())
			{
			for (String key : rs.getFacilityOfferingGroupMap().keySet()) {
				FacilityOfferingGroupType facilityOfferingGroupType= new FacilityOfferingGroupType();
				facilityOfferingGroupType.setFacilityOfferingGroupCode(key);
				facilityOfferingTypeList =  new ArrayList<FacilityOfferingType>();
				List<OfferingVO> offList =rs.getFacilityOfferingGroupMap().get(key);
				for(OfferingVO offeringVO : offList) {
				FacilityOfferingType facilityOfferingType = new  FacilityOfferingType();
				if(offeringVO.getOffCode()!=null)
				{
				facilityOfferingType.setOfferingCode(offeringVO.getOffCode());
				}
				if(offeringVO.getOffName()!=null)
				{
				facilityOfferingType.setOfferingName(offeringVO.getOffName());
				}
				if(offeringVO.getValidDatePeriodVO()!=null)
				{
					ValidDatePeriodType vp= null;
					if(offeringVO.getValidDatePeriodVO().getValidFrom()!=null ||offeringVO.getValidDatePeriodVO().getValidTo()!=null)
					{
						 vp= new ValidDatePeriodType();
						 if(offeringVO.getValidDatePeriodVO().getValidFrom()!=null)
						 {
							 vp.setValidFrom(offeringVO.getValidDatePeriodVO().getValidFrom());
						 }
						 if(offeringVO.getValidDatePeriodVO().getValidTo()!=null)
						 {
							 vp.setValidTo(offeringVO.getValidDatePeriodVO().getValidTo());
						 }
					}
					facilityOfferingType.setValidDatePeriod(vp);
				}
				facilityOfferingTypeList.add(facilityOfferingType);
				}
				facilityOfferingGroupType.getFacilityOfferings().addAll(facilityOfferingTypeList);
				facilityOfferingGroupList.add(facilityOfferingGroupType);
			}
			}

			if(rs.getDayScheduleMap()!=null)
			{
			for (String key : rs.getDayScheduleMap().keySet()) {
				DaySchedule daySchedule =new DaySchedule();
				if(StringUtils.isNotBlank(key)){
				daySchedule.setOfficeWorkingDay(key);
				}
				List<DayScheduleVO> lst =rs.getDayScheduleMap().get(key);
				List<XMLGregorianCalendar> dayopn= new ArrayList<XMLGregorianCalendar>();
				List<XMLGregorianCalendar> daycls= new ArrayList<XMLGregorianCalendar>();
				for(DayScheduleVO dayScheduleVO : lst) {
					dayopn.add(XMLGregorianCalendarConversionUtil.convertStringTimeToXmlGregorian(dayScheduleVO.getOfficeOpeningHours()+":00"));
					daycls.add(XMLGregorianCalendarConversionUtil.convertStringTimeToXmlGregorian(dayScheduleVO.getOfficeClosingHours()+":00"));
				}
				daySchedule.getOfficeOpeningHours().addAll(dayopn);
				daySchedule.getOfficeClosingHours().addAll(daycls);
				if(StringUtils.isNotBlank(daySchedule.getOfficeWorkingDay())){
				dayScheduleList.add(daySchedule);
				}
			}
			}

			if(dayScheduleList!= null );
			{
				if (!(dayScheduleList.size()==1 && dayScheduleList.get(0).getOfficeWorkingDay().equals("")&&(dayScheduleList.get(0).getOfficeClosingHours().get(0)==null&&dayScheduleList.get(0).getOfficeOpeningHours().get(0)==null)))
				{
					openingHoursType.getDaySchedule().addAll(dayScheduleList);
				}
				else{
					openingHoursType=null;
				}
			}
			if(openingHoursType!=null&&!openingHoursType.getDaySchedule().isEmpty())
			{
			operationalFacilityDetailsType.setOpeningHours(openingHoursType);
			commercialFacilityDetailsType.setOpeningHours(openingHoursType);
			}
			if(facilityOfferingGroupList!= null );
			{

				if (!(facilityOfferingGroupList.size()==1 && facilityOfferingGroupList.get(0).getFacilityOfferingGroupCode().equals("")&&facilityOfferingGroupList.get(0).getFacilityOfferings().get(0).getOfferingCode()==null))
				{
				facilityOfferingGroups.getFacilityOfferingGroup().addAll(facilityOfferingGroupList);
				}
				else{
					facilityOfferingGroups=null;
				}

			}
			//CountryDetails
			//CityDetails
			//RegionDetails
			if(rs.getRegion()!= null ||rs.getRegionGeoID()!= null)
			{
				RegionDetails rd= new RegionDetails();
				if(rs.getRegion()!=null&&!rs.getRegion().isEmpty())
				{
				rd.setRegion(rs.getRegion());
				}
				if(rs.getRegionGeoID()!=null&&!rs.getRegionGeoID().isEmpty())
				{
				rd.setRegionGeoID(rs.getRegionGeoID());
				}
				addressType.setRegionDetails(rd);
			}
			if(rs.getCountryGeoID()!= null ||rs.getIsoCountryCode()!=null)
			{
			CountryDetails cd= new CountryDetails();
			cd.setCountryGeoID(rs.getCountryGeoID());
			cd.setISOCountryCode(rs.getIsoCountryCode());
			addressType.setCountryDetails(cd);
			}
			if(rs.getCityGeoID()!= null ||rs.getCity()!=null)
			{
			CityDetails cid= new CityDetails();
			cid.setCity(rs.getCity());
			cid.setCityGeoID(rs.getCityGeoID());
			addressType.setCityDetails(cid);
			}
			if(rs.getAddCreationDate()!=null ||rs.getCreationUser()!=null ||rs.getLastUpdateDate()!=null ||rs.getLastUpdateUser()!=null)
			{
				auditType.setCreationDate(rs.getAddCreationDate());
				auditType.setCreationUser(rs.getCreationUser());
				auditType.setLastUpdateDate(rs.getLastUpdateDate());
				auditType.setLastUpdateUser(rs.getLastUpdateUser());
				addressType.setAddressAuditData(auditType);
			}
			if(rs.getLatitude()!=null &&!rs.getLatitude().isEmpty())
			{
				addressType.setLatitude(new BigDecimal(rs.getLatitude()));
			}
			if(rs.getLongitude()!=null&&!rs.getLongitude().isEmpty() )
			{
				addressType.setLongitude(new BigDecimal(rs.getLongitude()));
			}

			if(rs.getValidFrom()!=null || rs.getValidTo()!=null)
			{
				ValidDatePeriodType validDatePeriodType= new ValidDatePeriodType();
				if(rs.getValidFrom()!=null)
					{
						validDatePeriodType.setValidFrom(rs.getValidFrom());
					}
				if(rs.getValidFrom()!=null)
					{
						validDatePeriodType.setValidTo(rs.getValidTo());
					}
				addressType.setValidDatePeriod(validDatePeriodType);
			}
			if(rs.getBuilding()!=null && !rs.getBuilding().isEmpty())
			{
			addressType.setBuilding(rs.getBuilding());
			}
			if(rs.getDistrict()!=null && !rs.getDistrict().isEmpty())
			{
			addressType.setDistrict(rs.getDistrict());
			}
			if(rs.getHouseNo()!=null && !rs.getHouseNo().isEmpty())
			{
			addressType.setHouseNo(rs.getHouseNo());
			}
			if(rs.getPostalCode()!=null && !rs.getPostalCode().isEmpty())
			{
			addressType.setPostalCode(rs.getPostalCode());
			}
			if(rs.getStreetName()!=null && !rs.getStreetName().isEmpty())
			{
			addressType.setStreetName(rs.getStreetName());
			}
			if(rs.getSuburb()!=null && !rs.getSuburb().isEmpty())
			{
			addressType.setSuburb(rs.getSuburb());
			}

			response.setFacilityAddress(addressType);
			if(facilityDetailsType!=null)
			response.setFacility(facilityDetailsType);
			TransportModes transportModes = null;
			if (rs.getTransportModes()!=null && !rs.getTransportModes().isEmpty() )
			{
				transportModes = new TransportModes();
				List<TransportModeDetails> tpList = new ArrayList<TransportModeDetails>();
				for (TransportModesVO transportModesVO : rs.getTransportModes()) {
					TransportModeDetails transportModeDetails = new TransportModeDetails();
					if(transportModesVO.getTransportMode()!=null && !transportModesVO.getTransportMode().isEmpty())
					{
						transportModeDetails.setTransportMode(transportModesVO.getTransportMode());
					}
					if(transportModesVO.getValidDatePeriodVO().getValidFrom()!=null || transportModesVO.getValidDatePeriodVO().getValidTo()!=null)
					{
					ValidDatePeriodType validDatePeriodType= new ValidDatePeriodType();
					if(transportModesVO.getValidDatePeriodVO()!=null&&transportModesVO.getValidDatePeriodVO().getValidFrom()!=null)
					{
					validDatePeriodType.setValidFrom(transportModesVO.getValidDatePeriodVO().getValidFrom());
					}
					if(transportModesVO.getValidDatePeriodVO()!=null&&transportModesVO.getValidDatePeriodVO().getValidTo()!=null)
					{
					validDatePeriodType.setValidTo(transportModesVO.getValidDatePeriodVO().getValidTo());
					}
					transportModeDetails.setValidDatePeriod(validDatePeriodType);
					}
					tpList.add(transportModeDetails);
				}
				transportModes.getTransportModeDetails().addAll(tpList);
			}

			if(transportModes!= null)
			{
			operationalFacilityDetailsType.setTransportModes(transportModes);
			}

			if(facilityOfferingGroups!= null&&!facilityOfferingGroups.getFacilityOfferingGroup().isEmpty())
			{
				operationalFacilityDetailsType.setFacilityOfferingGroups(facilityOfferingGroups);
				commercialFacilityDetailsType.setFacilityOfferingGroups(facilityOfferingGroups);
			}
			commercialFacilityDetailsType.setBrand(rs.getBrandCode());
			commercialFacilityDetailsType.setExportMail(rs.getExpMail());
			commercialFacilityDetailsType.setFacilityType(rs.getCommercialFctType());
			commercialFacilityDetailsType.setFunction(rs.getFunctionCode());
			commercialFacilityDetailsType.setImportMail(rs.getImpMail());
			TeleCommunicationType telecomm= new TeleCommunicationType();
			telecomm.setInternationalDialingCode(rs.getDailingCode());
			telecomm.setNumber(rs.getTelecomNumber());
			if(null!=telecomm&&null!=telecomm.getNumber()){
			commercialFacilityDetailsType.setTelephoneDetails(telecomm);
			}

			if(rs.getFacilityCategory().equalsIgnoreCase("OPS"))
			{
				response.setFacilityAttributes(operationalFacilityDetailsType);
			}
			if(rs.getFacilityCategory().equalsIgnoreCase("COMM"))
			{
				response.setCommercialFacilityAttributes(commercialFacilityDetailsType);
			}
			if(rs.getFacilityCategory().equalsIgnoreCase("CUST"))
			{
				response.setFacilityAttributes(operationalFacilityDetailsType);
			}


			}
			else
			{
				StatusResponseType facilityStatus = new StatusResponseType();
				facilityStatus.setCode("No results found!");
				facilityStatus.setStatus("CMDWS-501");
				response.setRetrieveFacilityStatus(facilityStatus);

			}
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(RetrieveFacilityResponse.class);
				Marshaller jaxbMarshaller;
				jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				StringWriter stringwriter = new StringWriter();
				jaxbMarshaller.marshal(response, stringwriter);
				String message = stringwriter.toString();
				LOGGER.info(message);

			}catch (PropertyException e) {
				LOGGER.info(e);
			}
			catch (JAXBException e) {
				LOGGER.info(e);
			}

		}catch(Exception e)
		{
			Throwable rootException = getCause(e);
			if (rootException instanceof IllegalArgumentException) {
				response=new RetrieveFacilityResponse();
				StatusResponseType facilityStatus = new StatusResponseType();
				facilityStatus.setCode("CMDWS-305");
				facilityStatus.setStatus("The data found was incorrect!");
				response.setRetrieveFacilityStatus(facilityStatus);
				return response;

			} else if(rootException instanceof RetrieveFacilityException){
				response=new RetrieveFacilityResponse();
				StatusResponseType facilityStatus = new StatusResponseType();
				facilityStatus.setCode("CMDWS-305");
				facilityStatus.setStatus("GeoID/RKST/Business Unit Id entered was incorrect!");
				response.setRetrieveFacilityStatus(facilityStatus);
				return response;
			}
			else{

				LOGGER.warn(rootException.getLocalizedMessage());
				throwRetrieveServiceFault(
						"Unexpected error has occurred" + " : "
						+ stackTraceToString(rootException),
				"CMDWS-903");
			}
		}
		String commFlag = jSearchDaoLocal.getBusinessProperties().getProperty("fct.commercial.switch");
		if (null != response && null != response.getFacility()) {
			if ((response.getFacility().getFacilityIDs().getFacilityGEOId()==null &&response.getFacility().getFacilityIDs().getFacilityRKSTCode()==null))
			{
				StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode("CMDWS-501");
				facilityStatus.setStatus("Facility ID's are mandatory in the response.");
				response.setRetrieveFacilityStatus(facilityStatus);
			}else if(commFlag.equalsIgnoreCase("off")&&response.getFacility().getFacilityCategory().equals(FacilityCategoryEnum.COMM)){
				StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode("CMDWS-501");
				facilityStatus.setStatus("Commercial facility is not part of this release.");
				response.setRetrieveFacilityStatus(facilityStatus);
			}else if(response.getFacility().getFacilityName()==null){
				StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode("CMDWS-501");
				facilityStatus.setStatus("Facility Name is mandatory in the response.");
				response.setRetrieveFacilityStatus(facilityStatus);
			}else if(response.getFacility().getFacilityCategory()==null){
				StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode("CMDWS-501");
				facilityStatus.setStatus("Facility Category is mandatory in the response.");
				response.setRetrieveFacilityStatus(facilityStatus);
			}
			else if(response.getFacilityAddress().getCityDetails().getCity()==null){
				StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode("CMDWS-501");
				facilityStatus.setStatus("City is mandatory in the response.");
				response.setRetrieveFacilityStatus(facilityStatus);
			}
			else if(response.getFacilityAddress().getCountryDetails().getISOCountryCode()==null){
				StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode("CMDWS-501");
				facilityStatus.setStatus("Country is mandatory in the response.");
				response.setRetrieveFacilityStatus(facilityStatus);
			}
			/*else if(response.getFacilityAddress().getLatitude()!=null){
				if(response.getFacilityAddress().getLatitude().compareTo(new BigDecimal("-90.0"))<0 && response.getFacilityAddress().getLatitude().compareTo(new BigDecimal("90.0"))>0)
				{
					StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode(BusinessPropertiesLoader.getString("FacilitySearchService_SearchFacilityPortImpl.12"));
				facilityStatus.setStatus("Invalid Latitude found in response.");
				response.setRetrieveFacilityStatus(facilityStatus);
				}
			}
			else if(response.getFacilityAddress().getLongitude()!=null){
				if(response.getFacilityAddress().getLongitude().compareTo(new BigDecimal("-180.0"))<0 && response.getFacilityAddress().getLongitude().compareTo(new BigDecimal("180.0"))>0)
				{
				StatusResponseType facilityStatus = new StatusResponseType();
				response=new RetrieveFacilityResponse();
				facilityStatus.setCode(BusinessPropertiesLoader.getString("FacilitySearchService_SearchFacilityPortImpl.12"));
				facilityStatus.setStatus("Invalid Longitude found in response.");
				response.setRetrieveFacilityStatus(facilityStatus);
				}
			}*/
			else {
				StatusResponseType facilityStatus = new StatusResponseType();
				facilityStatus.setCode("CMDWS-200");
				facilityStatus.setStatus("SUCCESS");
				response.setRetrieveFacilityStatus(facilityStatus);
			}
		}
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(RetrieveFacilityResponse.class);
			Marshaller jaxbMarshaller;
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringwriter = new StringWriter();
			jaxbMarshaller.marshal(response, stringwriter);
			String message = stringwriter.toString();
			LOGGER.info(message);

		}catch (  Exception  e) {
			LOGGER.info(e);
		}

		return response;
	}


	private void throwSearchDuplicateServiceFault(String message, String code) throws SearchDuplicateFacilityFault {
		throw new SearchDuplicateFacilityFault(message, getServiceFaultInstance(message, code));
	}

}
