package net.apmoller.services.cmd.definitions;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingType;
import net.apmoller.maersk.cmd.geo.bo.CityBO;
import net.apmoller.maersk.cmd.geo.bo.ZipCodeBO;
import net.apmoller.maersk.cmd.geo.ejb.GeoServiceEJBLocal;
import net.apmoller.services.cmd.schemas.DataElementArray;

import net.apmoller.services.cmd.schemas.DataPayLoad;
import net.apmoller.services.cmd.schemas.GeoServiceRequest;
import net.apmoller.services.cmd.schemas.GeoServiceResponse;
import net.apmoller.services.cmd.schemas.ObjectFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService(portName="GeoServicePort", serviceName="GeoService", targetNamespace="http://services.apmoller.net/cmd/definitions", wsdlLocation="/wsdls/GeoService.wsdl", endpointInterface="net.apmoller.services.cmd.definitions.GeoService")
@BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
public class GeoService_GeoServicePortImpl
		implements GeoService
{
	@EJB(beanName="GeoServiceEJB")
	private GeoServiceEJBLocal geoServiceEJBLocal;
	private static final Logger logger = LogManager.getLogger(GeoService_GeoServicePortImpl.class);

	public GeoService_GeoServicePortImpl()
	{
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
	}

	public GeoServiceResponse processGeoData(GeoServiceRequest parameters)
			throws GeoFault {
		DataPayLoad payload = parameters.getDataPayLoad();
		String operation = payload.getOperation();
		String entity = payload.getEntity();
		String source = payload.getSourceSystem();
		String user = payload.getUser();
		ZipCodeBO bo = null;
		CityBO cityBo = null;

		System.out.println(" operation - " + operation);
		System.out.println(" entity - " + entity);
		System.out.println(" source - " + source);
		System.out.println(" user - " + user);

		GeoServiceResponse response = new GeoServiceResponse();

		List<DataElementArray.DataElement> elements = payload.getDataElementArray().getDataElement();

		System.out.println(" elements - " + elements);

		Map<String, String> responsemap = null;
		if ("Postal Code".equals(entity))
		{
			bo = new ZipCodeBO();

			bo.setEntity(entity);
			bo.setOperation(operation);
			bo.setSourcesystem(source);
			bo.setUsername(user);
			for (int i = 0; i < elements.size(); i++)
			{
				DataElementArray.DataElement element = (DataElementArray.DataElement)elements.get(i);

				logger.info(" element.getFieldName() - " + element.getFieldName());
				logger.info(" element.getFieldValue() - " + element.getFieldValue());
				if ("GEO_FIELD_POSTALNAME".equals(element.getFieldName())) {
					bo.setPostalname(element.getFieldValue());
				}
				if ("GEO_FIELD_POSTALCODE".equals(element.getFieldName())) {
					bo.setPostalcode(element.getFieldValue());
				}
				if ("GEO_FIELD_DESCRIPTION".equals(element.getFieldName())) {
					bo.setDescription(element.getFieldValue());
				}
				if ("GEO_FIELD_GDAROWID".equals(element.getFieldName())) {
					bo.setGDARowid(element.getFieldValue());
				}
				if ("GEO_FIELD_PARENTROWID".equals(element.getFieldName())) {
					bo.setParentRowid(element.getFieldValue());
				}
				if ("GEO_FIELD_VALIDTHRU".equals(element.getFieldName())) {
					bo.setValid_thru(element.getFieldValue());
				}
				if ("GEO_FIELD_VALIDFROM".equals(element.getFieldName())) {
					bo.setValid_from(element.getFieldValue());
				}
				if ("GEO_FIELD_TYPTYPECD".equals(element.getFieldName())) {
					bo.setRelType(element.getFieldValue());
				}
			}
			responsemap = this.geoServiceEJBLocal.upsertGEOData(bo);
		}
		else if ("CITY".equals(entity))
		{
			cityBo = new CityBO();

			cityBo.setEntity(entity);
			cityBo.setOperation(operation);
			cityBo.setSourcesystem(source);
			cityBo.setUsername(user);
			for (int i = 0; i < elements.size(); i++)
			{
				DataElementArray.DataElement element = (DataElementArray.DataElement)elements.get(i);

				System.out.println(" element.getFieldName() - " + element.getFieldName());
				System.out.println(" element.getFieldValue() - " + element.getFieldValue());
				if ("CITY_NAME".equals(element.getFieldName())) {
					cityBo.setCityname(element.getFieldValue());
				}
				if ("DESCRIPTION".equals(element.getFieldName())) {
					cityBo.setDescription(element.getFieldValue());
				}
				if ("MAERSK_CITY".equals(element.getFieldName())) {
					cityBo.setmaerskcityind(element.getFieldValue());
				}
				if ("HSUD_NAME".equals(element.getFieldName())) {
					cityBo.sethsudname(element.getFieldValue());
				}
				if ("LATITUDE".equals(element.getFieldName())) {
					cityBo.setLatitude(element.getFieldValue());
				}
				if ("LONGITUDE".equals(element.getFieldName())) {
					cityBo.setLongitude(element.getFieldValue());
				}
				if ("DAYLIGHTSAVING".equals(element.getFieldName())) {
					cityBo.setDaylightsaving(element.getFieldValue());
				}
				if ("OLSON_TIMEZONE".equals(element.getFieldName())) {
					cityBo.setOztimezone(element.getFieldValue());
				}
				if ("PORT_FLAG".equals(element.getFieldName())) {
					cityBo.setPortExists(element.getFieldValue());
				}
				if ("STATUS".equals(element.getFieldName())) {
					cityBo.setStatus(element.getFieldValue());
				}
				if ("TIMEZONE".equals(element.getFieldName())) {
					cityBo.setTimezone(element.getFieldValue());
				}
				if ("TYP_TYPE_CD".equals(element.getFieldName())) {
					cityBo.setType(element.getFieldValue());
				}
				if ("WORKRND_REASON".equals(element.getFieldName())) {
					cityBo.setWorkaround_type(element.getFieldValue());
				}
				if ("WORKRND_REASON_ROWID".equals(element.getFieldName())) {
					cityBo.setWorkaround_type_rowid(element.getFieldValue());
				}
				if ("GDA_VALID_FROM".equals(element.getFieldName())) {
					cityBo.setCity_Valid_from(element.getFieldValue());
				}
				if ("GDA_VALID_TO".equals(element.getFieldName())) {
					cityBo.setCity_Valid_thru(element.getFieldValue());
				}
				if ("CITYREL_VALID_FROM".equals(element.getFieldName())) {
					cityBo.setCity_rel_valid_from(element.getFieldValue());
				}
				if ("CITYREL_VALID_TO".equals(element.getFieldName())) {
					cityBo.setCity_rel_valid_thru(element.getFieldValue());
				}
				if ("GDA_DFND_AREA_PRNT_ROWID".equals(element.getFieldName())) {
					cityBo.setParentRowid(element.getFieldValue());
				}
				if ("GDA_DFND_AREA_REL_ROWID".equals(element.getFieldName())) {
					cityBo.setRelRowid(element.getFieldValue());
				}
				if ("ALTCODE_RKTS".equals(element.getFieldName())) {
					cityBo.setAltCode_rkts(element.getFieldValue());
				}
				if ("ALTNAME_RKTS".equals(element.getFieldName())) {
					cityBo.setAltName_rkts(element.getFieldValue());
				}
				if ("ALTDDESC_RKTS".equals(element.getFieldName())) {
					cityBo.setAltDesc_rkts(element.getFieldValue());
				}
				if ("ALTSTATUS_RKTS".equals(element.getFieldName())) {
					cityBo.setAltStatus_rkts(element.getFieldValue());
				}
				if ("ALTTYPE_RKTS".equals(element.getFieldName())) {
					cityBo.setAltType_rkts(element.getFieldValue());
				}
				if ("ALTCODE_RKST".equals(element.getFieldName())) {
					cityBo.setAltCode_rkst(element.getFieldValue());
				}
				if ("ALTNAME_RKST".equals(element.getFieldName())) {
					cityBo.setAltName_rkst(element.getFieldValue());
				}
				if ("ALTDDESC_RKST".equals(element.getFieldName())) {
					cityBo.setAltDesc_rkst(element.getFieldValue());
				}
				if ("ALTSTATUS_RKST".equals(element.getFieldName())) {
					cityBo.setAltStatus_rkst(element.getFieldValue());
				}
				if ("ALTTYPE_RKST".equals(element.getFieldName())) {
					cityBo.setAltType_rkst(element.getFieldValue());
				}
				if ("ALTCODE_MODEL".equals(element.getFieldName())) {
					cityBo.setAltCode_model(element.getFieldValue());
				}
				if ("ALTNAME_MODEL".equals(element.getFieldName())) {
					cityBo.setAltName_model(element.getFieldValue());
				}
				if ("ALTDDESC_MODEL".equals(element.getFieldName())) {
					cityBo.setAltDesc_model(element.getFieldValue());
				}
				if ("ALTSTATUS_MODEL".equals(element.getFieldName())) {
					cityBo.setAltStatus_model(element.getFieldValue());
				}
				if ("ALTTYPE_MODEL".equals(element.getFieldName())) {
					cityBo.setAltType_model(element.getFieldValue());
				}
				if ("CITY_BDA".equals(element.getFieldName())) {
					cityBo.setBda(element.getFieldValue());
				}
				if ("CITY_BDA_HUB_IND".equals(element.getFieldName())) {
					cityBo.setBda_hub_ind(element.getFieldValue());
				}
				if ("CITY_REL_BDA_ROWID".equals(element.getFieldName())) {
					cityBo.setRelBdaRowid(element.getFieldValue());
				}
				if ("GEO_FIELD_GDAROWID".equals(element.getFieldName())) {
					cityBo.setGDARowid(element.getFieldValue());
				}
			}
			responsemap = this.geoServiceEJBLocal.upsertGEOData(cityBo);

			String status = responsemap.get("STATUS");

			logger.info("RameshCheck1:"+status);

			if (status.equalsIgnoreCase("SUCCESS"))
			{
				String action = responsemap.get("ACTION");
				String gdaRowid = responsemap.get("GDAROWID");
				logger.info("Action:"+action);
				logger.info("gdaRowid:"+gdaRowid);
				try {
					cityEMPResponse(gdaRowid,action);
					cityMQResponse(gdaRowid);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}

		}
		else
		{
			responsemap = new HashMap();
			responsemap.put("INTERACTIONID", "No Such Entity - " + entity);
			responsemap.put("STATUS", "FAIL");
		}
		ObjectFactory factory = new ObjectFactory();
		JAXBElement<Object> interactionid = factory.createGeoServiceResponseInteractionid(responsemap.get("INTERACTIONID"));
		JAXBElement<Object> status = factory.createGeoServiceResponseStatus(responsemap.get("STATUS"));

		List<JAXBElement<Object>> responselist = response.getStatusAndInteractionid();

		responselist.add(0, interactionid);
		responselist.add(1, status);

		logger.info("processGeoData Ends");

		return response;
	}

	public void cityEMPResponse(String newRowid,String Action) throws FileNotFoundException {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestBuilder reqbuilder = RequestBuilder.post().setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

		///opt/weblogic/mdmpprd/config/applications/smds_pprd/servers/WSServer/GeoEMPBBU/BBUPublish.properties
		///opt/weblogic/mdmsit/config/applications/smds_sit/servers/WSServer/GeoEMPBBU/BBUPublish.properties
		File file = new File("/opt/weblogic/mdmprod/config/applications/smds_prod/servers/WSServer/GeoEMPBBU/BBUPublish.properties");
		if(!file.exists())
		{
			System.out.println("Property File Does Not Exists in Location");
		}
		FileInputStream fileInput = new FileInputStream(file);

		Properties prop = new Properties();

		//InputStream is = SaveHandler.class.getResourceAsStream("/com/maersk/mdm/taskdata/userexit/EMPGEO.properties");     ///to change savehandler

		try {
			prop.load(fileInput);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String str = prop.getProperty("endpoint.geoemp.service.url");

		logger.info("Property str:" + str);

		String rowidSpace = StringUtils.rightPad(newRowid,14," ");

		String rowidUtfFormat = rowidSpace.replaceAll(" ","%20");

		String newStr = str+Action+"&geoRowID="+rowidUtfFormat;

		RequestBuilder reqbuilder1 = reqbuilder.setUri(newStr);

		logger.info("URI=" + reqbuilder1.getUri());


		HttpUriRequest httppost = reqbuilder1.build();

		HttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(httppost);
			logger.info(EntityUtils.toString(httpresponse.getEntity()));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		logger.info(httpresponse.getStatusLine());
	}


	public void cityMQResponse(String newRowid) throws FileNotFoundException {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestBuilder reqbuilder = RequestBuilder.post().setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            ///opt/weblogic/mdmpprd/config/applications/smds_pprd/servers/WSServer/GeoEMPBBU/BBUPublish.properties
		///opt/weblogic/mdmsit/config/applications/smds_sit/servers/WSServer/GeoEMPBBU/BBUPublish.properties
		File file = new File("/opt/weblogic/mdmprod/config/applications/smds_prod/servers/WSServer/GeoEMPBBU/BBUPublish.properties");
		if(!file.exists())
		{
			System.out.println("Property File Does Not Exists in Location");
		}
		FileInputStream fileInput = new FileInputStream(file);

		Properties prop = new Properties();

		//InputStream is = SaveHandler.class.getResourceAsStream("/com/maersk/mdm/taskdata/userexit/EMPGEO.properties");     ///to change savehandler

		try {
			prop.load(fileInput);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String str = prop.getProperty("endpoint.geomq.service.url");

		logger.info("Property str:" + str);

		String rowidSpace = StringUtils.rightPad(newRowid,14," ");

		String rowidUtfFormat = rowidSpace.replaceAll(" ","%20");

		String newStr = str+rowidUtfFormat;

		RequestBuilder reqbuilder1 = reqbuilder.setUri(newStr);

		logger.info("URI=" + reqbuilder1.getUri());


		HttpUriRequest httppost = reqbuilder1.build();

		HttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(httppost);
			logger.info(EntityUtils.toString(httpresponse.getEntity()));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		logger.info(httpresponse.getStatusLine());
	}
}
