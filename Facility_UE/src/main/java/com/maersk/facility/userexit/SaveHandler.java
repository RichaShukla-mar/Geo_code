/*
' * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.siperian.bdd.userexits.datamodel.BDDObject
 *  com.siperian.bdd.userexits.operations.AbstractBaseOperationPlugin
 *  com.siperian.bdd.userexits.operations.ISaveOperationPlugin
 *  com.siperian.bdd.userexits.operations.OperationContext
 *  com.siperian.bdd.userexits.operations.OperationExecutionError
 *  com.siperian.bdd.userexits.operations.OperationResult
 *  com.siperian.bdd.userexits.operations.OperationType
 *  com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate
 *  com.siperian.sif.client.SiperianClient
 *  com.siperian.sif.message.Field
 *  com.siperian.sif.message.Password
 *  com.siperian.sif.message.Record
 *  com.siperian.sif.message.RecordKey
 *  com.siperian.sif.message.SiperianObjectType
 *  com.siperian.sif.message.SiperianRequest
 *  com.siperian.sif.message.SiperianResponse
 *  com.siperian.sif.message.mrm.CleanseRequest
 *  com.siperian.sif.message.mrm.CleanseResponse
 *  com.siperian.sif.message.mrm.PutRequest
 *  com.siperian.sif.message.mrm.PutResponse
 *  org.apache.commons.validator.UrlValidator
 *  org.apache.log4j.Logger
 */
package com.maersk.facility.userexit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.validator.UrlValidator;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.maersk.facility.userexit.bean.SMDSFacilityEvent;
import com.maersk.facility.userexit.data.FacilityDataBinding;
import com.maersk.facility.userexit.util.FacilityJaxBTranslator;
import com.maersk.facility.userexit.util.PublishService;
import com.maersk.facility.userexit.utils.Utils;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.AbstractBaseOperationPlugin;
import com.siperian.bdd.userexits.operations.ISaveOperationPlugin;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.operations.OperationExecutionError;
import com.siperian.bdd.userexits.operations.OperationResult;
import com.siperian.bdd.userexits.operations.OperationType;
import com.siperian.sif.client.SiperianClient;
import com.siperian.sif.message.Field;
import com.siperian.sif.message.Password;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.RecordKey;
import com.siperian.sif.message.SiperianObjectType;
import com.siperian.sif.message.SiperianRequest;
import com.siperian.sif.message.mrm.CleanseRequest;
import com.siperian.sif.message.mrm.CleanseResponse;
import com.siperian.sif.message.mrm.PutRequest;
import com.siperian.sif.message.mrm.PutResponse;

import net.apmoller.services.cmd.definitions.UpsertFacilityService;
import net.apmoller.services.cmd.schemas.FacilityIDsType;
import net.apmoller.services.cmd.schemas.PublishFacilityCodeRequest;
import net.apmoller.services.cmd.schemas.PublishFacilityCodeResponse;
import net.apmoller.services.cmd.schemas.UpsertEnum;

public class SaveHandler extends AbstractBaseOperationPlugin implements ISaveOperationPlugin {
	private static final Logger log = Logger.getLogger((String) SaveHandler.class.getName());
	SiperianClient sipClient = null;
	Connection connection = null;
	String test = "Inititated";
	private FacilityDataBinding factDataBinding;
	
	private String username = "";
	private String ORSID;
	private SMDSFacilityEvent facilityEvent;
	private FacilityJaxBTranslator mqPublisher = new FacilityJaxBTranslator();
	private Properties properties;
	private static String geoIDforPublish="";
	private ArrayList<String> iataCodesValues=new ArrayList<String>();
	Collection<String> collection=new ArrayList<>();  
	public OperationType getOperationType() {
		return OperationType.SAVE_OPERATION;
	}

	/*
	 * WARNING - Removed try catching itself - possible behaviour change.
	 */
	public OperationResult afterEverything(BDDObject bddObject) {
				
		log.info("MDM 2 code started");
		int geoTypTypeRowidCount = 0;
		String mdmORSid = this.getOperationContext().getValue("ors id").toString();
		log.info((Object) ("The MDM ORS is::" + mdmORSid));
		Connection conn = this.getDatabaseConnection(mdmORSid);
		try {
			if ("Operational".equalsIgnoreCase(bddObject.getObjectName().toString())) {

				this.sipClient = (SiperianClient) this.getOperationContext().getValue("sifClient");
				log.info((Object) ("The Siperian Client is::" + (Object) this.sipClient));

				String factRowid = bddObject.getRowId();
				log.info((Object) ("The Fact Rowid is::" + factRowid));
				String typTypeRowid = this.getTypTypeRowid(conn);
				log.info((Object) ("The Typ Type Rowid is::" + typTypeRowid));
				log.info((Object) "before calling getGeoTypTypeRowidCount ");
				geoTypTypeRowidCount = this.getGeoTypTypeRowidCount(conn, typTypeRowid, factRowid);
				log.info((Object) "after calling getGeoTypTypeRowidCount ");
				String geoID = null;
				//String BuiId=null;
				String BUIDRowid = this.getBUIDRowid(conn);
				if (geoTypTypeRowidCount == 0 ) {
					log.info((Object) "before geoid");
					//geoID = this.generateId();
					
					geoID = this.geoIDforPublish;
					
					log.info((Object) ("The geoID is::" + geoID));
					PutRequest putRequest = new PutRequest();

					try {
						putRequest.setUsername((String) this.getOperationContext().getValue(OperationContext.USERNAME));
						log.info((Object) ("The IDD  is::"
								+ this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
						putRequest.setPassword(
								new Password((String) this.getOperationContext().getValue(OperationContext.PASSWORD)));
						log.info((Object) ("The MDM password is::"
								+ this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
						log.info("GetOperation Context has been sent afterEverything GEOID");

					} catch (Exception e) {
						log.info("Security Payload started afterEverything GEOID");
						byte[] securityPayload = (byte[]) getOperationContext().getValue("security payload");
						putRequest.setSecurityPayload(securityPayload);
						log.info("Security Payload has been sent afterEverything GEOID" + e);
					}

					
					putRequest.setOrsId((String) this.getOperationContext().getValue("ors id"));
					log.info((Object) ("The MDM ORS is::" + this.getOperationContext().getValue("ors id").toString()));
					Record putRecord = new Record();
					putRecord.setField(new Field("FCT_ROWID", factRowid));
					putRecord.setField(new Field("TYP_TYPE_ROWID", typTypeRowid));
					putRecord.setField(new Field("CODE", geoID));
					RecordKey fctAltCodesKey = new RecordKey();
					fctAltCodesKey.setSystemName("Admin");
					putRequest.setRecordKey(fctAltCodesKey);
					putRequest.setGenerateSourceKey(true);
					putRecord.setSiperianObjectUid(SiperianObjectType.BASE_OBJECT.makeUid("C_FCT_ALT_CODES"));
					putRequest.setRecord(putRecord);
					log.info((Object) "=========== About to execute SIF PUT for C_FCT_ALT_CODES ===========");
					log.info((Object) ("The value of Code to be set is :: = " + putRecord.getField("CODE").toString()));
					PutResponse putResponse = (PutResponse) this.sipClient.process((SiperianRequest) putRequest);
					log.info((Object) ("Put Response for Fact Alt code is :: " + putResponse.getMessage()));

					
					List<BDDObject> facilityAddres = bddObject.getChildren("Facility_Address");
					log.info((Object) "Inside AfterEv");
					if (this.getObjectCount(facilityAddres) > 0 && facilityAddres.size() > 0) {
						for (BDDObject facility_Address : facilityAddres) {

							Date dt = new Date("12/31/3999");
							String sys_date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dt);
							log.info(sys_date);
							String ADDR_DCTR_IND = (String) facility_Address.getValue("C_FCT_ADDR_REL|ADDR_DCTR_IND");
							log.info("ADDR_DCTR_IND IS::" + ADDR_DCTR_IND);

							String Fct_Addr_Rowid = getAddrrowid(getDatabaseConnection(mdmORSid), factRowid);
							PutRequest putRequest_addr = new PutRequest();

							try {
								putRequest_addr.setUsername(
										(String) this.getOperationContext().getValue(OperationContext.USERNAME));
								log.info((Object) ("The IDD  is::"
										+ this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
								putRequest_addr.setPassword(new Password(
										(String) this.getOperationContext().getValue(OperationContext.PASSWORD)));
								log.info((Object) ("The MDM password is::"
										+ this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
								log.info("GetOperation Context has been sent afterEverything Addr");

							} catch (Exception e) {
								log.info("Security Payload started afterEverything Addr");
								byte[] securityPayload = (byte[]) getOperationContext().getValue("security payload");
								putRequest_addr.setSecurityPayload(securityPayload);
								log.info("Security Payload has been sent afterEverything Addr" + e);
							}

							/*
							 * log.info("Security Payload started afterEverything Addr"); byte[]
							 * securityPayload_addr = (byte[]
							 * )getOperationContext().getValue("security payload");
							 * putRequest_addr.setSecurityPayload(securityPayload_addr);
							 * log.info("Security Payload has been sent afterEverything Addr");
							 */

							putRequest_addr.setOrsId((String) this.getOperationContext().getValue("ors id"));
							log.info((Object) ("The MDM ORS is::"
									+ this.getOperationContext().getValue("ors id").toString()));
							Record putRecord_addr = new Record();
							putRecord_addr.setField(new Field("VALID_THRU_DT", sys_date));
							putRecord_addr.setField(new Field("ADDR_DCTR_IND", ADDR_DCTR_IND));
							RecordKey fctAltCodesKey_addr = new RecordKey();
							fctAltCodesKey_addr.setSystemName("Admin");
							fctAltCodesKey_addr.setRowid(Fct_Addr_Rowid);
							putRequest_addr.setRecordKey(fctAltCodesKey_addr);
							putRequest_addr.setGenerateSourceKey(true);
							putRecord_addr
									.setSiperianObjectUid(SiperianObjectType.BASE_OBJECT.makeUid("C_FCT_ADDR_REL"));
							putRequest_addr.setRecord(putRecord_addr);
							log.info((Object) "=========== About to execute SIF PUT for C_FCT_ADDR_REL ===========");

							PutResponse putResponse_addr = (PutResponse) this.sipClient
									.process((SiperianRequest) putRequest_addr);
							log.info((Object) ("Put Response for C_FCT_ADDR_RELe is :: "
									+ putResponse_addr.getMessage()));
						}
					}

					try {
						log.info((Object) ("Inside afterEverything() Publish Facility Geo Code: "
								+ this.sendFacilityCode(geoID, "CREATE")));
					} catch (Exception e) {
						log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
					}

				}

				else {

					log.info((Object) "The geo id is already present");
					String geoCode = null;
					List<BDDObject> facilityAltCodes = bddObject.getChildren("Alternate_Codes");
					if (this.getObjectCount(facilityAltCodes) > 0 || facilityAltCodes.size() > 0) {
						for (BDDObject fctAltCodes : facilityAltCodes) {
							if (null == fctAltCodes.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID")
									|| !typTypeRowid.equals(fctAltCodes.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID")))
								continue;
							geoCode = fctAltCodes.getValue("C_FCT_ALT_CODES|CODE").toString();
							log.info((Object) ("Inside afterEverything() Facility FCT Code: " + geoCode));
							try {
								log.info((Object) ("Inside afterEverything() Publish Facility FCT Code: "
										+ this.sendFacilityCode(geoCode, "UPDATE")));

							} catch (Exception e) {
								log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
							}

						}
					}
				}

			}
			
			if ("Commercial".equalsIgnoreCase(bddObject.getObjectName().toString())) {

				this.sipClient = (SiperianClient) this.getOperationContext().getValue("sifClient");
				log.info((Object) ("The Siperian Client is::" + (Object) this.sipClient));

				String factRowid = bddObject.getRowId();
				log.info((Object) ("The Fact Rowid is::" + factRowid));
				String typTypeRowid = this.getTypTypeRowid(conn);
				log.info((Object) ("The Typ Type Rowid is::" + typTypeRowid));
				log.info((Object) "before calling getGeoTypTypeRowidCount ");
				geoTypTypeRowidCount = this.getGeoTypTypeRowidCount(conn, typTypeRowid, factRowid);
				log.info((Object) "after calling getGeoTypTypeRowidCount ");
				String geoID = null;
				 String BuiId=null;
				String BUIDRowid = this.getBUIDRowid(conn);
				if (geoTypTypeRowidCount == 0 ) {
					log.info((Object) "before geoid");
					geoID = this.generateId();
					
					log.info((Object) ("The geoID is::" + geoID));
					PutRequest putRequest = new PutRequest();

					try {
						putRequest.setUsername((String) this.getOperationContext().getValue(OperationContext.USERNAME));
						log.info((Object) ("The IDD  is::"
								+ this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
						putRequest.setPassword(
								new Password((String) this.getOperationContext().getValue(OperationContext.PASSWORD)));
						log.info((Object) ("The MDM password is::"
								+ this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
						log.info("GetOperation Context has been sent afterEverything GEOID");

					} catch (Exception e) {
						log.info("Security Payload started afterEverything GEOID");
						byte[] securityPayload = (byte[]) getOperationContext().getValue("security payload");
						putRequest.setSecurityPayload(securityPayload);
						log.info("Security Payload has been sent afterEverything GEOID" + e);
					}

					
					putRequest.setOrsId((String) this.getOperationContext().getValue("ors id"));
					log.info((Object) ("The MDM ORS is::" + this.getOperationContext().getValue("ors id").toString()));
					Record putRecord = new Record();
					putRecord.setField(new Field("FCT_ROWID", factRowid));
					putRecord.setField(new Field("TYP_TYPE_ROWID", typTypeRowid));
					putRecord.setField(new Field("CODE", geoID));
					RecordKey fctAltCodesKey = new RecordKey();
					fctAltCodesKey.setSystemName("Admin");
					putRequest.setRecordKey(fctAltCodesKey);
					putRequest.setGenerateSourceKey(true);
					putRecord.setSiperianObjectUid(SiperianObjectType.BASE_OBJECT.makeUid("C_FCT_ALT_CODES"));
					putRequest.setRecord(putRecord);
					log.info((Object) "=========== About to execute SIF PUT for C_FCT_ALT_CODES ===========");
					log.info((Object) ("The value of Code to be set is :: = " + putRecord.getField("CODE").toString()));
					PutResponse putResponse = (PutResponse) this.sipClient.process((SiperianRequest) putRequest);
					log.info((Object) ("Put Response for Fact Alt code is :: " + putResponse.getMessage()));

					 /*----------------------------Start of Put Call for Business Unit Id----------------------------*/
					
				log.info((Object)"before buid");
					
		                BuiId = this.getBuiId(conn);
		                log.info((Object)("The buid is::" + BuiId));
		                PutRequest putRequest_buid = new PutRequest();
		               
		                
		                
		                try{
		                	putRequest_buid.setUsername((String)this.getOperationContext().getValue(OperationContext.USERNAME));
		                    log.info((Object)("The IDD  is::" + this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
		                    putRequest_buid.setPassword(new Password((String)this.getOperationContext().getValue(OperationContext.PASSWORD)));
		                    log.info((Object)("The MDM password is::" + this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
		                    log.info("GetOperation Context has been sent afterEverything buid");
		                	
		                }
		                catch(Exception e){
		                	log.info("Security Payload started afterEverything buid");
		                    byte[]  securityPayload = (byte[] )getOperationContext().getValue("security payload");
		        			putRequest_buid.setSecurityPayload(securityPayload);
		        			log.info("Security Payload has been sent afterEverything buid" + e);
		                }
		                
		                               
		                
		                putRequest_buid.setOrsId((String)this.getOperationContext().getValue("ors id"));
		                log.info((Object)("The MDM ORS is::" + this.getOperationContext().getValue("ors id").toString()));
		                Record putRecord_buid = new Record();
		                putRecord_buid.setField(new Field("FCT_ROWID", factRowid));
		                putRecord_buid.setField(new Field("TYP_TYPE_ROWID", BUIDRowid));
		                putRecord_buid.setField(new Field("CODE", BuiId));
		                RecordKey fctAltCodesKey_buid = new RecordKey();
		                fctAltCodesKey_buid.setSystemName("Admin");
		                putRequest_buid.setRecordKey(fctAltCodesKey_buid);
		                putRequest_buid.setGenerateSourceKey(true);
		                putRecord_buid.setSiperianObjectUid(SiperianObjectType.BASE_OBJECT.makeUid("C_FCT_ALT_CODES"));
		                putRequest_buid.setRecord(putRecord_buid);
		                log.info((Object)"=========== About to execute SIF PUT for C_FCT_ALT_CODES for Buid ===========");
		                log.info((Object)("The value of Code for Buid  to be set is :: = " + putRecord_buid.getField("CODE").toString()));
		                PutResponse putResponse_buid = (PutResponse)this.sipClient.process((SiperianRequest)putRequest_buid);
		                log.info((Object)("Put Response for Fact Alt code is for Buid :: " + putResponse_buid.getMessage()));
		                
					
					 /*----------------------------End of Put Call for Business Unit Id----------------------------*/
					
					
					
					List<BDDObject> facilityAddres = bddObject.getChildren("Facility_Address");
					log.info((Object) "Inside AfterEv");
					if (this.getObjectCount(facilityAddres) > 0 && facilityAddres.size() > 0) {
						for (BDDObject facility_Address : facilityAddres) {

							Date dt = new Date("12/31/3999");
							String sys_date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dt);
							log.info(sys_date);
							String ADDR_DCTR_IND = (String) facility_Address.getValue("C_FCT_ADDR_REL|ADDR_DCTR_IND");
							log.info("ADDR_DCTR_IND IS::" + ADDR_DCTR_IND);

							String Fct_Addr_Rowid = getAddrrowid(getDatabaseConnection(mdmORSid), factRowid);
							PutRequest putRequest_addr = new PutRequest();

							try {
								putRequest_addr.setUsername(
										(String) this.getOperationContext().getValue(OperationContext.USERNAME));
								log.info((Object) ("The IDD  is::"
										+ this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
								putRequest_addr.setPassword(new Password(
										(String) this.getOperationContext().getValue(OperationContext.PASSWORD)));
								log.info((Object) ("The MDM password is::"
										+ this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
								log.info("GetOperation Context has been sent afterEverything Addr");

							} catch (Exception e) {
								log.info("Security Payload started afterEverything Addr");
								byte[] securityPayload = (byte[]) getOperationContext().getValue("security payload");
								putRequest_addr.setSecurityPayload(securityPayload);
								log.info("Security Payload has been sent afterEverything Addr" + e);
							}

							

							putRequest_addr.setOrsId((String) this.getOperationContext().getValue("ors id"));
							log.info((Object) ("The MDM ORS is::"
									+ this.getOperationContext().getValue("ors id").toString()));
							Record putRecord_addr = new Record();
							putRecord_addr.setField(new Field("VALID_THRU_DT", sys_date));
							putRecord_addr.setField(new Field("ADDR_DCTR_IND", ADDR_DCTR_IND));
							RecordKey fctAltCodesKey_addr = new RecordKey();
							fctAltCodesKey_addr.setSystemName("Admin");
							fctAltCodesKey_addr.setRowid(Fct_Addr_Rowid);
							putRequest_addr.setRecordKey(fctAltCodesKey_addr);
							putRequest_addr.setGenerateSourceKey(true);
							putRecord_addr
									.setSiperianObjectUid(SiperianObjectType.BASE_OBJECT.makeUid("C_FCT_ADDR_REL"));
							putRequest_addr.setRecord(putRecord_addr);
							log.info((Object) "=========== About to execute SIF PUT for C_FCT_ADDR_REL ===========");

							PutResponse putResponse_addr = (PutResponse) this.sipClient
									.process((SiperianRequest) putRequest_addr);
							log.info((Object) ("Put Response for C_FCT_ADDR_RELe is :: "
									+ putResponse_addr.getMessage()));
						}
					}

					try {
						log.info((Object) ("Inside afterEverything() Publish Facility Geo Code: "
								+ this.sendFacilityCode(geoID, "CREATE")));
					} catch (Exception e) {
						log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
					}

				}

				else {

					log.info((Object) "The geo id is already present");
					String geoCode = null;
					List<BDDObject> facilityAltCodes = bddObject.getChildren("Alternate_Codes");
					if (this.getObjectCount(facilityAltCodes) > 0 || facilityAltCodes.size() > 0) {
						for (BDDObject fctAltCodes : facilityAltCodes) {
							if (null == fctAltCodes.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID")
									|| !typTypeRowid.equals(fctAltCodes.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID")))
								continue;
							geoCode = fctAltCodes.getValue("C_FCT_ALT_CODES|CODE").toString();
							log.info((Object) ("Inside afterEverything() Facility FCT Code: " + geoCode));
							try {
								log.info((Object) ("Inside afterEverything() Publish Facility FCT Code: "
										+ this.sendFacilityCode(geoCode, "UPDATE")));

							} catch (Exception e) {
								log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
							}

						}
					}
				}

			}

		} catch (Exception e) {
			log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
		} finally {
			try {
				conn.close();
				log.info((Object) "--------Connection closed After Everything");
				connection.close();
				log.info((Object) "--------Main Connection closed");
			} catch (Exception e) {
			}
		}
		
		log.info("MDM 1 code started");
		if (bddObject.getObjectName().equalsIgnoreCase("Operational") || bddObject.getObjectName().equalsIgnoreCase("Customer")
				|| bddObject.getObjectName().equalsIgnoreCase("Commercial")) {
			log.info("MDM 11 code started");
		String Action = geoAction(bddObject);
		log.info("Geo Transaction Case:" + Action);
		
		String fctRowID =	bddObject.getRowId();
		String fctRowIDFinal = fctRowID.replaceAll(" ", "%20");
		log.info("geoRowId value:" + fctRowIDFinal);
		
	  
	  CloseableHttpClient httpclient = HttpClients.createDefault();
		
		RequestBuilder reqbuilder = RequestBuilder.post().setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		
		Properties prop = new Properties();
		
		InputStream is = SaveHandler.class.getResourceAsStream("/com/maersk/facility/userexit/EMP.properties");
	  
		try {
			prop.load(is);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String str = prop.getProperty("endpoint.facilityemp.service.url");
		
		log.info("Property str:" + str);			
		
		RequestBuilder reqbuilder1 = reqbuilder.setUri(str+Action+"&fctRowID="+fctRowIDFinal);
		
		log.info("URI=" + reqbuilder1.getUri());
		
		
     HttpUriRequest httppost = reqbuilder1.build();
		
 		HttpResponse httpresponse = null;
		try {
			httpresponse = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			log.info(EntityUtils.toString(httpresponse.getEntity()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		log.info(httpresponse.getStatusLine());
	
		
		//*************Ending EMP CODE***************//*
		}
		
		
		return OperationResult.OK;
	}

	public OperationResult afterSave(BDDObject SavingObject) {

		/*----------------------------------------------------------------*/
	
		return OperationResult.OK;
	}
	
	public String geoAction(BDDObject bddObject) {
		if (bddObject.isCreated()) {
			return "Create";
		} else {
			return "Update";
		}
	
	}

	public OperationResult beforeEverything(BDDObject arg0) {

		String mdmORSid = this.getOperationContext().getValue("ors id").toString();
		Connection conn1 = this.getDatabaseConnection(mdmORSid);

		try {
			/*******393831: Adding Comm facility opening Hrs madatory*******/
			if ("Commercial".equalsIgnoreCase(arg0.getObjectName().toString())) {
				int count = 0;
				int rmvcount = 0;
				List<BDDObject> openingHrs = arg0.getChildren("Opening_Hours");
				log.info("openingHrs1" + openingHrs.size() + (openingHrs == null));
				log.info(openingHrs);
				if(openingHrs.size() <1 || openingHrs.isEmpty()){
					log.info("openingHrs2");
					return new OperationResult(new OperationExecutionError("SIP-70009", getLocalizationGate()));
				}
				for(BDDObject openingHr : openingHrs){
					log.info("openingHr" + openingHr.isRemoved() +openingHr.isChanged() +" Test  " +openingHr );
				    count++;
				    log.info("counts" + count);
				
					if(openingHr.isRemoved()){
						rmvcount++;
						 log.info("in remv" + count  + rmvcount);
						if (count == rmvcount){
					return new OperationResult(new OperationExecutionError("SIP-70009", getLocalizationGate()));
						}
					}
				
				}
				}
			
				

			if ("Operational".equalsIgnoreCase(arg0.getObjectName().toString())) {
//Richa Added code in Beforeeverthing for SMDG & BIC
				//int count = 0;
				
				/******************    184026: Validation for GEO DELETE START ***********************/
				// 1. Allowed ALT CODE DETETION : UN CODE, UN CODE(Lookup Only), UN CODE(Return Only), Schedule D, Schedule K,SMDG,BIC. THROW ERRORS FOR OTHERS
				List<BDDObject> altCodes = arg0.getChildren("Alternate_Codes");
				GenerateNotAllowedList();
				log.info("GenerateListDone");
				
					
				boolean isUNCodeDeleted=false;
				boolean isUNLookupDeleted=true;
				for(BDDObject altcode : altCodes) {
					String AltCodeRowid = altcode.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
					log.info(AltCodeRowid);
					if (collection.contains(getAltCodetypeName(AltCodeRowid,mdmORSid,altcode)) && altcode.isRemoved()) {
						//Anil Change RKTS Decom
						log.info("RKST& GEOID is not allowed to delete" );
						return new OperationResult(new OperationExecutionError("SIP-500301", getLocalizationGate()));
					}
					if ((AltCodeRowid.equalsIgnoreCase(this.getUncodeRowid(conn1).trim()) && altcode.isRemoved())) {
						log.info("UN CODE IS REMOVED");
						isUNCodeDeleted=true;
					}
					if (AltCodeRowid.equalsIgnoreCase(this.getUncodeLookupRowid(conn1).trim()) && !(altcode.isRemoved())) {
						log.info("UN lookup code is not removed.");
						isUNLookupDeleted=false;
					}
			    	}
				log.info("isUNCodeDeleted:" + isUNCodeDeleted);
				log.info("isUNLookupDeleted" + isUNLookupDeleted);
				
				     if (isUNCodeDeleted && !isUNLookupDeleted) {
				    	 log.info("UNCODE CANNOT BE DELETED IF UNLOOKUP IS PRESENT. Please delete both or delete UNLOOKUP first." );
						return new OperationResult(new OperationExecutionError("SIP-500302", getLocalizationGate()));
				     }
						/******************    IATA Should be duplicate for one Facility Starts- Anil***********************/
				     log.info("Before IATA Codes check");
				    if(iataCodesValues!=null) {
				    	if(iataCodesValues.size()>0 && !iataCodesValues.isEmpty()) {
				    	log.info("IATA Codes list isn't 0, size is :: " +iataCodesValues.size());
				        Set inputSet = new HashSet(iataCodesValues);
				        if(inputSet.size()< iataCodesValues.size()) {
				        	if(!(inputSet.size() == iataCodesValues.size())) {
				        	log.info("IATA Codes list found duplicates");
								return new OperationResult(new OperationExecutionError("SIP-70001", getLocalizationGate()));
				        		}
				        	}
				    	}
				    }
						/******************    IATA Should be duplicate for one Facility Ends- Anil***********************/
				        
				/******************    184026: Validation for GEO DELETE ENDS ***********************/
				     
				List<BDDObject> facilityBICOperationaltcode = arg0.getChildren("Alternate_Codes");
				log.info("value of child adding altcode object r : " + facilityBICOperationaltcode);
				if (facilityBICOperationaltcode != null && facilityBICOperationaltcode.size() > 0) {
					int count = 0;
					log.info("size of child adding altcode object r : " + facilityBICOperationaltcode.size());
					for (BDDObject alttypeobject : facilityBICOperationaltcode) {
					String AltCodeValue = alttypeobject.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("AltCodeValue in BIC CODE  " + AltCodeValue);
				log.info("get BIC Rowid from code  " + getBICRowid(conn1).trim() );
				String comaprerowid = getBICRowid(conn1).trim();
				
					if (AltCodeValue.equalsIgnoreCase(comaprerowid)){
					count++;					
					log.info("Inside BIC count1 '" + AltCodeValue +"' = " + count );
					
					}
				}
					if (count>1){
						log.info("get count BIC Code check" + count );
						return new OperationResult(new OperationExecutionError("SIP-5566998", getLocalizationGate()));
					}
				}
				
				List<BDDObject> facilitySMDGOperationaltcode = arg0.getChildren("Alternate_Codes");
				log.info("value of child adding altcode object r : " + facilitySMDGOperationaltcode);
				if (facilitySMDGOperationaltcode != null && facilitySMDGOperationaltcode.size() > 0) {
					int count = 0;
					log.info("size of child adding altcode object r : " + facilitySMDGOperationaltcode.size());
					for (BDDObject alttypeobject : facilitySMDGOperationaltcode) {
					String AltCodeValue = alttypeobject.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("AltCodeValue in SMDG CODE  " + AltCodeValue );
				log.info("get SMDG Rowid from code  " + getSMDGRowid(conn1).trim() );
				String comaprerowid = getSMDGRowid(conn1).trim();
					if (AltCodeValue.equalsIgnoreCase(comaprerowid)){
					count++;					
					log.info("Inside SMDG count1 '" + AltCodeValue +"' = " + count );
					
					}
				}
					if (count>1){
						log.info("get count SMDG Code check" + count );
						return new OperationResult(new OperationExecutionError("SIP-5566999", getLocalizationGate()));
					}
				}
				    
	               
	            	   List<BDDObject> Uncodesite = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite);
	   				if (Uncodesite != null && Uncodesite.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite);
	   		  if ((getUncodeCount(Uncodesite,conn1)>0) && (getUncodeReturnCount(Uncodesite,conn1)>0)){
	   			log.info("getinto city adding both altcode check object Uncodesite : " + Uncodesite);
	   			return new OperationResult(new OperationExecutionError("SIP-500290", this.getLocalizationGate()));
	   				} }
	   				
	   			
	            	   List<BDDObject> Uncodesite1 = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite1);
	   				if (Uncodesite1 != null && Uncodesite1.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite1);
	   		     if ((getUncodeLookupCount(Uncodesite1,conn1)>0) && (getUncodeReturnCount(Uncodesite1,conn1)>0)){
	   			log.info("getinto city adding both altcode check object Uncodesite1 : " + Uncodesite1);
	   			return new OperationResult(new OperationExecutionError("SIP-500291", this.getLocalizationGate()));
	   				} }
	               
	   			  List<BDDObject> Uncodesite2 = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite2);
	   				if (Uncodesite2 != null && Uncodesite2.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite2);
	   		     if (getUncodeReturnCount(Uncodesite2,conn1)>1){
	   			log.info("getinto city adding both altcode check object Uncodesite2 : " + Uncodesite2);
	   			return new OperationResult(new OperationExecutionError("SIP-500292", this.getLocalizationGate()));
	   				} }
	   				
	   			 List<BDDObject> Uncodesite3 = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite3);
	   				if (Uncodesite3 != null && Uncodesite3.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite3);
	   		     if (getUncodeCount(Uncodesite3,conn1)>1){
	   			log.info("getinto city adding both altcode check object Uncodesite3 : " + Uncodesite3);
	   			return new OperationResult(new OperationExecutionError("SIP-500293", this.getLocalizationGate()));
	   				} }
	   				
	   			 List<BDDObject> Uncodesite4 = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite4);
	   				if (Uncodesite4 != null && Uncodesite4.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite4);
	   		  if ((getUncodeLookupCount(Uncodesite4,conn1)>0) && (getUncodeCount(Uncodesite4,conn1)==0)){
	   			log.info("getinto city adding both altcode check object Uncodesite : " + Uncodesite4);
	   			return new OperationResult(new OperationExecutionError("SIP-500294", this.getLocalizationGate()));
	   				} }
	               
	   		//		schduleD Check Richa
	   				List<BDDObject> schduleD = arg0.getChildren("Alternate_Codes");
	   				List<BDDObject> getCountry = arg0.getChildren("Facility_Address");
	   			    log.info("value of child adding both schduleD object r : " + schduleD);
		   			log.info("value of child adding both schduleD object r : " + getCountry);
		   			if (schduleD != null && schduleD.size() > 0) {
		   				for (BDDObject schduleDcheck : schduleD){
		   					if(!schduleDcheck.isRemoved()){
		   				String AltCodeValue = schduleDcheck.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
		   				String tocomapreDKrowid = getScheduleDRowid(conn1).trim();
		   			 if(AltCodeValue.equalsIgnoreCase(tocomapreDKrowid)) {
		   			 for (BDDObject facility_Country : getCountry) {
		   			  String ADDR_CNTRYROWID = facility_Country.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString().trim();
		   			  List<String> US_CNTRYROWID = getUScountryrowid(conn1);
   					  log.info("get Value from IDD ADDR_CNTRYROWID # " + ADDR_CNTRYROWID);
   					  log.info("get Value from IDD US_CNTRYROWID # " + US_CNTRYROWID);
   					  log.info("Get All If Values # " + (getScheduleDCount(schduleD,conn1)>1) + " Get another Values # " + !US_CNTRYROWID.contains(ADDR_CNTRYROWID)  + "Check final" + ((getScheduleDCount(schduleD,conn1)>1) && !US_CNTRYROWID.contains(ADDR_CNTRYROWID)) );
   					  //if (!ADDR_CNTRYROWID.contains(US_CNTRYROWID))
   						if (!US_CNTRYROWID.contains(ADDR_CNTRYROWID)){
   						log.info("Get All If Values Inside If # " + (getScheduleDCount(schduleD,conn1)>1) + " Get another Values # " + US_CNTRYROWID.contains(ADDR_CNTRYROWID) +
   	   							"Final check # " + ((getScheduleDCount(schduleD,conn1)>1) && !US_CNTRYROWID.contains(ADDR_CNTRYROWID)));
   						return new OperationResult(new OperationExecutionError("SIP-500295", this.getLocalizationGate()));   						
   					}
   					if (getScheduleDCount(schduleD,conn1)>1){
   						log.info(" get count for Schdeule D # " + getScheduleDCount(schduleD,conn1));
   						return new OperationResult(new OperationExecutionError("SIP-500298", this.getLocalizationGate()));   						
   					}
		   			  }
		   			}
			}}
		   			}
		   			
		   			
                  //schduleK Check Richa
	   				
		   			List<BDDObject> schduleK = arg0.getChildren("Alternate_Codes");
	   				List<BDDObject> get_Country = arg0.getChildren("Facility_Address");
	   			    log.info("value of child adding both schduleK object r : " + schduleD);
		   			log.info("value of child adding both schduleK object r : " + getCountry);
		   			if (schduleK != null && schduleK.size() > 0) {
		   				for (BDDObject schduleKcheck : schduleK){
		   					if(!schduleKcheck.isRemoved()){
		   				String AltCodeValue = schduleKcheck.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
		   				String tocomapreKrowid = getScheduleKRowid(conn1).trim();
		   			 if(AltCodeValue.equalsIgnoreCase(tocomapreKrowid)) {
		   			 for (BDDObject facility_CountryK : get_Country) {
		   			  String ADDR_CNTRYROWIK = facility_CountryK.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString().trim();
		   			  List<String> US_CNTRYROWIDK = getUScountryrowid(conn1);
   					  log.info("get Value from IDD ADDR_CNTRYROWID for K # " + ADDR_CNTRYROWIK);
   					  log.info("get Value from IDD US_CNTRYROWID  for K # " + US_CNTRYROWIDK);
   					 log.info("Get All If Values K # " + (getScheduleKCount(schduleK,conn1)>1) + " Get another Values # " + US_CNTRYROWIDK.contains(ADDR_CNTRYROWIK)  + "Check final" + 
   					  ((getScheduleKCount(schduleK,conn1)>1) || US_CNTRYROWIDK.contains(ADDR_CNTRYROWIK)) );
   					  // if (ADDR_CNTRYROWIK.equalsIgnoreCase(US_CNTRYROWIDK))
   						if (US_CNTRYROWIDK.contains(ADDR_CNTRYROWIK)){
   						 log.info("Get All If Values K Inside IF # " + (getScheduleDCount(schduleD,conn1)>1) + " Get another Values # " + US_CNTRYROWIDK.contains(ADDR_CNTRYROWIK)  + "Check final" +
   								((getScheduleKCount(schduleK,conn1)>1) || US_CNTRYROWIDK.contains(ADDR_CNTRYROWIK)) );
   						   return new OperationResult(new OperationExecutionError("SIP-500296", this.getLocalizationGate()));   						
   					}
   					   if (getScheduleKCount(schduleK,conn1)>1){
     						 log.info(" get count for Schdeule K # " + getScheduleKCount(schduleK,conn1));
        						   return new OperationResult(new OperationExecutionError("SIP-500297", this.getLocalizationGate()));
   					   }
   					   
		   			  }
		   			}
			}}
		   			}
		   			
	   				/*****************ICD & PCC FCTTYP start**********/
		   			
		   			List<BDDObject> facilityDetaitls = arg0.getChildren("Facility_Details");
		   			for (BDDObject fctdtls : facilityDetaitls) {
		   				List<BDDObject> fctTypeAll = fctdtls.getChildren("Facility_Type");
		   				if (fctTypeAll != null && fctTypeAll. size() > 0){
		   				List<String> fcttyplst = new ArrayList<String>();
		   				for (BDDObject fctType : fctTypeAll) {
		   					if(!fctType.isRemoved()){
		   				log.info("Get into ICD AND PCC FCT_TYPE CHECK");
		   				String fcttypevalue = fctType.getValue("C_TYP_TYPE|CODE").toString().trim();
		   				   log.info("FCTTYPE3" + fcttypevalue );
		   				    if(fcttypevalue.contains("FCT_OPS_TYPE.ICD") || fcttypevalue.contains("FCT_TYPE.PCC_DEP")){
		   				    	fcttyplst.add(fcttypevalue);
		   				    }
		   				    log.info("final fcttyplst" + fcttyplst);
		   				   }}
		   					log.info("final fcttyplst1" + fcttyplst);
		   				
		   			 if (fcttyplst.contains("FCT_TYPE.PCC_DEP") && fcttyplst.contains("FCT_OPS_TYPE.ICD")){
	   						return new OperationResult(new OperationExecutionError("SIP-500303", this.getLocalizationGate()));
	   					//ICD and PCC both cant be present
	   				    	
	   						}
		   						}
		   						}
				
					/*****************ICD & PCC FCTTYP start**********/
		   			
		   			/*****************HSUD and LNS code start************/
		   			List<BDDObject> hsudCode = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding hsudCode object r : " + hsudCode);
	   				if (hsudCode != null && hsudCode.size() > 0) {
	   					log.info("getinto city adding hsudCode object r : " + hsudCode);
	   		     if (getHsudCodeCount(hsudCode,conn1)>1){
	   			log.info("getinto city adding altcode check object hsudCode : " + hsudCode);
	   			return new OperationResult(new OperationExecutionError("SIP-70002", this.getLocalizationGate()));
	   				} }
		   			
	   				List<BDDObject> hsudNum = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding hsudNum object r : " + hsudNum);
	   				if (hsudNum != null && hsudNum.size() > 0) {
	   					log.info("getinto city adding hsudNum object r : " + hsudNum);
	   		     if (getHsudNumCount(hsudNum,conn1)>1){
	   			log.info("getinto city adding altcode check object hsudNum : " + hsudNum);
	   			return new OperationResult(new OperationExecutionError("SIP-70003", this.getLocalizationGate()));
	   				}
	   		     }
	   				
	   				List<BDDObject> LnsCode = arg0.getChildren("Alternate_Codes");
	   				log.info("value of child adding hsudNum object r : " + LnsCode);
	   				if (LnsCode != null && LnsCode.size() > 0) {
	   					log.info("getinto city adding hsudNum object r : " + LnsCode);
	   		     if (getLnsCount(LnsCode,conn1)>1){
	   			log.info("getinto city adding altcode check object hsudNum : " + LnsCode);
	   			return new OperationResult(new OperationExecutionError("SIP-70005", this.getLocalizationGate()));
	   				}
	   		     }
	   				
	   				List<BDDObject> allAltCodes = arg0.getChildren("Alternate_Codes");
	   				List<BDDObject> getCountryRowid = arg0.getChildren("Facility_Address");
	   				if (allAltCodes != null && allAltCodes.size() > 0) {
	   					for (BDDObject allAltCode : allAltCodes){
	   					 String AltCodeValue = allAltCode.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
	   					String tocomapreHusdrowid = getHsudCodeRowid(conn1).trim();
	   					 if(AltCodeValue.equalsIgnoreCase(tocomapreHusdrowid)) {
                        	String AltCode = allAltCode.getValue("C_FCT_ALT_CODES|CODE").toString().trim();
                        	 for (BDDObject facilityCountry : getCountryRowid) {
                        	 String ADDR_CNTRYROWID = facilityCountry.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString().trim();
                        	  String FirstTwoCharOfHsudCd = AltCode.substring(0, 2);
                        	  String getCountryCd = FetchCountryCode(conn1, ADDR_CNTRYROWID);
                        	  log.info("FirstTwoCharOfHsudCd" + FirstTwoCharOfHsudCd  + "Full Code" + AltCode);
                        	  log.info("getCountryCd" + getCountryCd );
                        	  if(!FirstTwoCharOfHsudCd.equalsIgnoreCase(getCountryCd)){
                        		  return new OperationResult(new OperationExecutionError("SIP-70004", this.getLocalizationGate()));
                        	  }
                        	 }
                         }
	   					
	   					 
	   					}
	   					
	   				}
	   				
	   				
	   				
	   				if (allAltCodes != null && allAltCodes.size() > 0) {
	   					for (BDDObject allAltCode : allAltCodes){
	   					 String AltCodeValue = allAltCode.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
	   					 String tocomapreLnsrowid = getLnsRowid(conn1).trim();
                         if(AltCodeValue.equalsIgnoreCase(tocomapreLnsrowid)) {
                        	String AltCode = allAltCode.getValue("C_FCT_ALT_CODES|CODE").toString().trim();
                        	 for (BDDObject facilityCountry : getCountryRowid) {
                        	 String ADDR_CNTRYROWID = facilityCountry.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString().trim();
                        	  String FirstTwoCharOfLnsCd = AltCode.substring(0, 2);
                        	  String getCountryCd = FetchCountryCode(conn1, ADDR_CNTRYROWID);
                        	  log.info("FirstTwoCharOfLnsCd" + FirstTwoCharOfLnsCd  + "Full Code" + AltCode);
                        	  log.info("getCountryCd" + getCountryCd );
                        	  if(!FirstTwoCharOfLnsCd.equalsIgnoreCase(getCountryCd)){
                        		  return new OperationResult(new OperationExecutionError("SIP-70006", this.getLocalizationGate()));
                        	  }
                        	 }
                         }
	   					
	   					 
	   					}
	   					
	   				}
	   				
	   				
	   				/*******Active and inactive Ind check for HSUD and LNS********/
	   				

	   				List<BDDObject> allOpsAltCodes = arg0.getChildren("Alternate_Codes");
	   				if (allAltCodes != null && allAltCodes.size() > 0) {
	   					for (BDDObject allAltCode : allAltCodes){
	   					String OpsStatus = arg0.getValue("C_FCT_FACILITY|STATUS_CD").toString().trim();
	   					String AltCodeValue = allAltCode.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
	   					String tocomapreHusdrowid = getHsudCodeRowid(conn1).trim();
	   					String tocomapreHusdNumrowid = getHsudNumRowid(conn1).trim();
	   					String tocomapreLnsrowid = getLnsRowid(conn1).trim();
	   					 if(OpsStatus.equalsIgnoreCase("I") && (AltCodeValue.equalsIgnoreCase(tocomapreHusdrowid) || AltCodeValue.equalsIgnoreCase(tocomapreHusdNumrowid) || 
	   							AltCodeValue.equalsIgnoreCase(tocomapreLnsrowid)) &&  !allAltCode.isRemoved()) {
                        	 return new OperationResult(new OperationExecutionError("SIP-70008", this.getLocalizationGate()));
                        	  }
                        	 }
                         }
	   					 
	   			   				
		   			/*****************HSUD and LNS code End************/
//Richa End Code				
				

				String Fct_Rowid = arg0.getSystemValue("C_FCT_FACILITY|ROWID_OBJECT").toString();
				String Fct_Status = arg0.getValue("C_FCT_FACILITY|STATUS_CD").toString();

				if ("P".equalsIgnoreCase(Fct_Status)) {

					return new OperationResult(new OperationExecutionError("SIP-500164", this.getLocalizationGate()));
				}
				log.info((Object) ("Facility Rowid Is ::" + Fct_Rowid));
				List<BDDObject> facilityDetails = arg0.getChildren("Facility_Details");
				log.info((Object) "Inside facility details count");
				if (this.getObjectCount(facilityDetails) > 1 || facilityDetails.size() > 1) {
					log.info((Object) "facility details more than one");
					return new OperationResult(new OperationExecutionError("SIP-500114", this.getLocalizationGate()));
				}

				log.info((Object) ("Count is " + this.getOffDetails(conn1, Fct_Rowid)));
				if (this.getOffDetails(conn1, Fct_Rowid) > 0) {
					log.info((Object) "inside getOffDetails ::");
					return new OperationResult(new OperationExecutionError("SIP-500125", this.getLocalizationGate()));
				}
				List<BDDObject> facilityAddress = arg0.getChildren("Facility_Address");
				log.info((Object) "Inside facility address count");
				if (this.getObjectCount(facilityAddress) > 1 || facilityAddress.size() > 1) {
					log.info((Object) "facility address more than one");
					return new OperationResult(new OperationExecutionError("SIP-500115", this.getLocalizationGate()));

				}

				List<BDDObject> AlternateCodes = arg0.getChildren("Alternate_Codes");
				log.info((Object) "Inside Alternate Codes count");

				List<BDDObject> facilityAddres = arg0.getChildren("Facility_Address");
				log.info((Object) "Inside AdressDoctorCall");
				if (this.getObjectCount(facilityAddres) > 0 && facilityAddres.size() > 0) {
					for (BDDObject facility_Address : facilityAddres) {
						String ADDR_DCTR_IND = (String) facility_Address.getValue("C_FCT_ADDR_REL|ADDR_DCTR_IND");
						log.info("ADDR_DCTR_IND IS::" + ADDR_DCTR_IND);
						String AddressDctorCheck_Output = null;

						if ("Do not Check".equalsIgnoreCase(ADDR_DCTR_IND)) {
							log.info("Do not Check");
							return new OperationResult(
									new OperationExecutionError("SIP-500154", this.getLocalizationGate()));
						}

						/* if("Check".equalsIgnoreCase(ADDR_DCTR_IND)) */else {

							log.info("Check");
							String STREET = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|STREET");
							log.info("Inside AdressDoctorCall 1");
							String HOUSE_NUM = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|HOUSE_NUM");
							log.info("Inside AdressDoctorCall 2");
							String CITY = facility_Address.getValue("C_CTM_PSTL_ADDR|CITY_ROWID").toString();
							log.info("Inside AdressDoctorCall 3");
							String PSTCD = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|PSTCD");
							log.info("Inside AdressDoctorCall 4");
							String TRTY_ROWID = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|TRTY_ROWID");
							log.info("Inside AdressDoctorCall 5");
							String CTRY_ROWID = facility_Address.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString();
							log.info("Inside AdressDoctorCall 6");
							String ADDR_LN_2 = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|ADDR_LN_2");
							log.info("Inside AdressDoctorCall 7");
							String ADDR_LN_3 = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|ADDR_LN_3");
							log.info("Inside AdressDoctorCall 8");
							String LAT_GEOSPTL = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL");
							log.info("Inside AdressDoctorCall 9");
							String LNG_GEOSPTL = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL");
							log.info("Inside AdressDoctorCall 10");

							AddressDctorCheck_Output = AddressDctorCheck(STREET, HOUSE_NUM, CITY, PSTCD, TRTY_ROWID,
									CTRY_ROWID, ADDR_LN_2, ADDR_LN_3, LAT_GEOSPTL, LNG_GEOSPTL,
									FetchCountryCode(conn1, CTRY_ROWID));
							log.info("AddressDctorCheck_Output isssss" + AddressDctorCheck_Output);

							if ("Country Not Available".equalsIgnoreCase(AddressDctorCheck_Output)) {
								return new OperationResult(
										new OperationExecutionError("SIP-500168", this.getLocalizationGate()));

							}

							if ("Region Not Available".equalsIgnoreCase(AddressDctorCheck_Output)) {
								return new OperationResult(
										new OperationExecutionError("SIP-500169", this.getLocalizationGate()));

							}

							if ("Check City_country".equalsIgnoreCase(AddressDctorCheck_Output)) {
								return new OperationResult(
										new OperationExecutionError("SIP-500151", this.getLocalizationGate()));

							}
							if ("BadAddress".equalsIgnoreCase(AddressDctorCheck_Output)) {
								return new OperationResult(
										new OperationExecutionError("SIP-500150", this.getLocalizationGate()));
							}
							if (AddressDctorCheck_Output.contains("CLEANSE_0")) {

								log.info("modified");
								test = AddressDctorCheck_Output;
								return new OperationResult(
										new OperationExecutionError("SIP-500152", this.getLocalizationGate()));
							} else {
								String STREET_OUTPUT = AddressDctorCheck_Output.substring(0,
										AddressDctorCheck_Output.indexOf("CLEANSE_1"));
								String HOUSE_NUM_OUTPUT = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_1") + "CLEANSE_1".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_2"));
								String PSTCD_OUTPUT = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_2") + "CLEANSE_2".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_3"));
								String ADDR_LN_2_OUTPUT = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_3") + "CLEANSE_3".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_4"));
								String ADDR_LN_3_OUTPUT = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_4") + "CLEANSE_4".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_5"));
								String LAT_GEOSPTL_OUTPUT = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_5") + "CLEANSE_5".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_6"));
								String LNG_GEOSPTL_OUTPUT = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_6") + "CLEANSE_6".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_7"));
								String Country_Rowid = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_7") + "CLEANSE_7".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_8"));
								String Region_Rowid = AddressDctorCheck_Output.substring(
										AddressDctorCheck_Output.indexOf("CLEANSE_8") + "CLEANSE_8".length(),
										AddressDctorCheck_Output.indexOf("CLEANSE_9"));
								log.info("STREET_OUTPUT::" + STREET_OUTPUT);
								facility_Address.setValue("C_CTM_PSTL_ADDR|STREET", STREET_OUTPUT);
								log.info("HOUSE_NUM_OUTPUT::" + HOUSE_NUM_OUTPUT);
								facility_Address.setValue("C_CTM_PSTL_ADDR|HOUSE_NUM", HOUSE_NUM_OUTPUT);
								log.info("PSTCD_OUTPUT::" + PSTCD_OUTPUT);
								facility_Address.setValue("C_CTM_PSTL_ADDR|PSTCD", PSTCD_OUTPUT);
								log.info("ADDR_LN_2_OUTPUT::" + ADDR_LN_2_OUTPUT);
								facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_2", ADDR_LN_2_OUTPUT);
								log.info("ADDR_LN_3_OUTPUT::" + ADDR_LN_3_OUTPUT);
								facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_3", ADDR_LN_3_OUTPUT);
								log.info("LAT_GEOSPTL_OUTPUT::" + LAT_GEOSPTL_OUTPUT);
								facility_Address.setValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL", LAT_GEOSPTL_OUTPUT);
								log.info("LNG_GEOSPTL_OUTPUT::" + LNG_GEOSPTL_OUTPUT);
								facility_Address.setValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL", LNG_GEOSPTL_OUTPUT);
								log.info("Country::" + Country_Rowid);
								facility_Address.setValue("C_CTM_PSTL_ADDR|CTRY_ROWID", Country_Rowid);
								log.info("Region ::" + Region_Rowid);
								facility_Address.setValue("C_CTM_PSTL_ADDR|TRTY_ROWID", Region_Rowid);
								log.info("Setting has completed");

							}

						}

					}
				}

			}
			if ("Services".equalsIgnoreCase(arg0.getObjectName().toString())) {
				String isActive = arg0.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString();
				log.info((Object) ("Is Active Ind ::" + isActive));
				if (null == arg0.getOldValue("C_FCT_OFF|IS_ACTIVE_IND") && "N".equalsIgnoreCase(isActive)) {
					log.info((Object) "Is Active Ind is being created inactive");
					return new OperationResult(new OperationExecutionError("SIP-500116", this.getLocalizationGate()));
				}
				if (null != arg0.getOldValue("C_FCT_OFF|IS_ACTIVE_IND") && "N".equalsIgnoreCase(isActive)) {
					log.info((Object) "Is Active Ind is being updated to inactive");
					return new OperationResult(new OperationExecutionError("SIP-500105", this.getLocalizationGate()));
				}
			}
		} catch (Exception e) {
			log.info(e);
		} finally {
			log.info("We are inside finally method");
			try {
				conn1.close();
				log.info((Object) "--------Connection closed BeforeEverything");
			} catch (Exception e) {
			}
			{

			}
		}

		return OperationResult.OK;
	}
	
public String getBuiId(Connection conn) {
    	
    	try {
    	
    	Statement stmt= conn.createStatement();
    	Integer Bu_id=null;

    	ResultSet rs = stmt.executeQuery("SELECT FCT_BUID_SEQ.NEXTVAL FROM dual");

    	if ( rs!=null && rs.next() ) {
    		Bu_id = rs.getInt(1);
    	 
    	
    	 	rs.close();
    	}

    		stmt.close(); 
    		return Bu_id.toString();
    	}
    	
    	catch(Exception e){
    		log.info("BUID Exception"+e);
    		return null;
    	}
    	
    }

	public String generateId() {

		String mdmORSid = this.getOperationContext().getValue("ors id").toString();
		log.info((Object) ("The MDM ORS is::" + mdmORSid));
		Connection conn = this.getDatabaseConnection(mdmORSid);

		try {

			String typTypeRowid = this.getTypTypeRowid(conn);
			log.info((Object) ("Typ Type Rowid ::" + typTypeRowid));
			this.sipClient = (SiperianClient) this.getOperationContext().getValue("sifClient");
			log.info((Object) ("The Siperian Client is::" + (Object) this.sipClient));
			CleanseRequest request = new CleanseRequest();
			request.setCleanseFunctionName("Facility Cleanse Function|GenerateAltGeoCode");
			Record record = new Record();
			Field field = new Field();
			field.setName("Typ Type Code");
			field.setValue((Object) typTypeRowid);
			record.setField(field);

			try {
				request.setUsername((String) this.getOperationContext().getValue(OperationContext.USERNAME));
				log.info((Object) ("The IDD  is::"
						+ this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
				request.setPassword(
						new Password((String) this.getOperationContext().getValue(OperationContext.PASSWORD)));
				log.info((Object) ("The MDM password is::"
						+ this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
				log.info("getOperationContext has been set for generateId");

			} catch (Exception e) {
				log.info("Security Payload started generateId");
				byte[] securityPayload = (byte[]) getOperationContext().getValue("security payload");
				request.setSecurityPayload(securityPayload);
				log.info("Security Payload has been sent generateId" + e);
			}

			/*
			 * log.info("Security Payload started generateId"); byte[] securityPayload =
			 * (byte[] )getOperationContext().getValue("security payload");
			 * request.setSecurityPayload(securityPayload);
			 * log.info("Security Payload has been sent generateId");
			 */

			request.setOrsId((String) this.getOperationContext().getValue("ors id"));
			log.info((Object) ("The MDM ORS is::" + this.getOperationContext().getValue("ors id").toString()));
			request.setRecord(record);
			CleanseResponse response = (CleanseResponse) this.sipClient.process((SiperianRequest) request);
			log.info((Object) "before getting response");
			log.info((Object) ("Geo ID is ::" + response.getRecord().getField("Code").getStringValue()));
			return response.getRecord().getField("Code").getStringValue();

		} catch (Exception e) {
			log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
			return null;
		} finally {
			try {
				conn.close();
				log.info((Object) "--------Connection closed generateId");
			} catch (Exception e) {

			}
		}
	}

	public Connection getDatabaseConnection(String mdmORSid) {
		try {
			String dataSourceName = "jdbc/siperian-" + mdmORSid.toLowerCase() + "-ds";
			log.info((Object) ("The MDM DataSource name is ::" + dataSourceName));
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dataSourceName);
			connection = ds.getConnection();
			log.info((Object) "getConnection end - success");
			return connection;
		} catch (Exception e) {
			log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
			return null;
		}

	}

	public boolean GetCountOff(List<BDDObject> Service_Details) {

		if (getObjectCount(Service_Details) > 0 && Service_Details.size() > 0) {

			List<String> NewList = new ArrayList<String>();

			for (BDDObject Service_Detail : Service_Details) {
				String Off_Name = Service_Detail.getValue("C_FCT_OFF|OFF_NAME").toString();
				NewList.add(Off_Name);
			}
			Set<String> set = new HashSet<String>(NewList);
			if (set.size() < NewList.size())
				return true;
		}
		return false;
	}

	public boolean GetCountTrnsp(List<BDDObject> Trnsp_Details) {

		if (getObjectCount(Trnsp_Details) > 0 && Trnsp_Details.size() > 0) {

			List<String> NewList = new ArrayList<String>();

			for (BDDObject Trnsp_Detail : Trnsp_Details) {
				String Trnsp_Name = Trnsp_Detail.getValue("C_FCT_TRNSP_MODE|TRNSP_NAME").toString();

				NewList.add(Trnsp_Name);
			}
			Set<String> set = new HashSet<String>(NewList);
			log.info("NewList.size()" + set.size());
			log.info("set.size()" + Trnsp_Details.size());
			if (set.size() < NewList.size())
				return true;
		}
		return false;
	}

	public boolean GetCountType(List<BDDObject> Facility_Details) {

		if (getObjectCount(Facility_Details) > 0 && Facility_Details.size() > 0) {
			for (BDDObject Facility_Detail : Facility_Details) {

				List<BDDObject> Facility_types = Facility_Detail.getChildren("Facility_Type");

				if (getObjectCount(Facility_types) > 0 && Facility_types.size() > 0) {

					List<String> NewList = new ArrayList<String>();

					for (BDDObject Facility_type : Facility_types) {

						String Facility_type_name = (String) Facility_type.getValue("C_TYP_TYPE|CODE");
						NewList.add(Facility_type_name);

					}
					Set<String> set = new HashSet<String>(NewList);
					log.info("NewList.size()" + set.size());
					log.info("set.size()" + NewList.size());
					if (set.size() < NewList.size())
						return true;

				}

			}
		}
		return false;
	}

	public String getRKSTRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.RKST' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}

	public String getRKTSRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.RKTS' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
			}
			log.info("RKTS rowid is ::"+partyRoleName);
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
	
	//Richa Start
	
	
	public String getBICRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.BIC' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
				log.info("Get BIC rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
	
	public String getSMDGRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.SMDG' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
	
	
	private int getUncodeCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("getUncodeCount loop");
				if (AltCodeValue.equalsIgnoreCase(this.getUncodeRowid(conn)) && !object.isRemoved())
				count++;
				log.info("getUncodeCount after count loop1 " + AltCodeValue.equalsIgnoreCase(this.getUncodeRowid(conn)) +" " +  count );
			}
		}
		return count;
	}
    
	private int getUncodeLookupCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("getUncodeCount1 loop");
				if (AltCodeValue.equalsIgnoreCase(getUncodeLookupRowid(conn)) && !object.isRemoved())
				++count;
				log.info("getUncodeLookupCount after count loop1 " + AltCodeValue.equalsIgnoreCase(this.getUncodeLookupRowid(conn)) +" " +  count );
			}
		}
		return count;
	}
    
	
	private int getUncodeReturnCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("getUncodeCount2 loop");
				if (AltCodeValue.equalsIgnoreCase(getUncodeReturnRowid(conn)) && !object.isRemoved())
				count++;
				log.info("getUncodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(this.getUncodeReturnRowid(conn)) +" " + count );
			}
		}
		return count;
	}
    
	private int getScheduleKCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("getScheduleDCount2 loop");
				if (AltCodeValue.equalsIgnoreCase(getScheduleKRowid(conn)) && !object.isRemoved())
				count++;
				log.info("getUncodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(this.getUncodeReturnRowid(conn)) +" " + count );
			}
		}
		return count;
	}
	
	private int getScheduleDCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("getSchedeuleKCount2 loop");
				if (AltCodeValue.equalsIgnoreCase(getScheduleDRowid(conn)) && !object.isRemoved() )
				count++;
				log.info("getUncodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(this.getUncodeReturnRowid(conn)) +" " + count );
			}
		}
		return count;
	}
    
	
	/*****************HUSD & LNS********/
	
		
	private int getHsudCodeCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				String getHsudCodeRowid1 = getHsudCodeRowid(conn).toString().trim();
				log.info("getHsudCodeCount loop1 # " + AltCodeValue + "getHsudCodeRowid#  " +getHsudCodeRowid1 + getHsudCodeRowid(conn) +  !object.isRemoved()  );
				if (AltCodeValue.equalsIgnoreCase(getHsudCodeRowid1) && !object.isRemoved())
				count++;
				log.info("getHsudCodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(getHsudCodeRowid(conn)) +" " + count );
			}
		}
		return count;
	}
	
	private int getHsudNumCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("getHsudNumCount loop1");
				String getHsudNumRowid1 = getHsudNumRowid(conn).toString().trim();
				if (AltCodeValue.equalsIgnoreCase(getHsudNumRowid1) && !object.isRemoved() )
				count++;
				log.info("getHsudNumCount after count loop2 " + AltCodeValue.equalsIgnoreCase(getHsudNumRowid(conn)) +" " + count );
			}
		}
		return count;
	}
	
	private int getLnsCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("getLnsCount loop1");
				String getLnsRowid1 = getLnsRowid(conn).toString().trim();
				if (AltCodeValue.equalsIgnoreCase(getLnsRowid1) && !object.isRemoved() )
				count++;
				log.info("getLNSCount after count loop2 " + AltCodeValue.equalsIgnoreCase(getHsudNumRowid(conn)) +" " + count );
			}
		}
		return count;
	}
	/*****************HUSD & LNS********/
	
	/*public String getcountryRkstrowid(Connection conn){
		  String usCountryrowid = null;
		  StringBuilder sb = new StringBuilder();
		  sb.append("select ROWID_OBJECT as CountryName from C_GDA_DFND_AREA where TYP_TYPE_CD ='GDA.COUNTRY' and NAME ='United States'");
		  try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					usCountryrowid = rs.getString("CountryName").trim();
					log.info("Get usCountryrowid rowid # " + usCountryrowid+ " & " + usCountryrowid );
				}
				return usCountryrowid;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
	  }*/
	
  public List<String> getUScountryrowid(Connection conn){
	  //String usCountryrowid = null;
	  //ArrayList<String> usCountryrowid = new ArrayList<>();
	  List<String> usCountryrowid = new ArrayList<>();
	  StringBuilder sb = new StringBuilder();
	  sb.append("select ROWID_OBJECT as CountryName from C_GDA_DFND_AREA where TYP_TYPE_CD ='GDA.COUNTRY' and NAME in ('United States','Guam','Virgin Islands (US)','Puerto Rico')");
	  try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				//usCountryrowid = rs.getString("CountryName").trim();
				usCountryrowid.add(rs.getString("CountryName").trim());
				log.info("Get usCountryrowid rowid # " + usCountryrowid+ " & " + usCountryrowid );
			}
			return usCountryrowid;
		} catch (Exception ex) {
			log.info((Object) ("getTypTypeRowid Exception ::" + ex.getLocalizedMessage()));
			log.info("Error in getExistingUnCode method ::"+ex.getCause());
			log.info("Error in getExistingUnCode method ::"+ex.getMessage());
			log.info("Error in getExistingUnCode method ::"+ex.getLocalizedMessage());
			log.info("Error in getExistingUnCode method ::"+ex.getStackTrace());
			ex.printStackTrace();
			
		}
	return usCountryrowid;
  }
  
  /********************HSUD AND LNS change start***********/
  
  public String getLnsRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.LNS_CODE' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
				log.info("Get HSUD_CODE rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
  
	public String getHsudCodeRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.HSUD_CODE' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
				log.info("Get HSUD_CODE rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
	
	
	public String getHsudNumRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.HSUD_NUM' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
				log.info("Get HSUDNUM rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
	
	/***************HSUD & LNS Change END***********/
    
    public String getUncodeRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.UN_CODE' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				log.info("Get UNCODE rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public String getUncodeLookupRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.UN_CODE1' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				log.info("Get UNCODELOOKUP rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public String getUncodeReturnRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.UN_CODE2' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				log.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public String getScheduleKRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.Schedule_K' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				log.info("Get ALT_CODE.Schedule_K rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public String getScheduleDRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.Schedule_D' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				log.info("Get ALT_CODE.Schedule_D rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
  //Richa code end

	public String getTypTypeRowid(Connection conn) {
		String partyRoleName = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.GEOID' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
			}
			return partyRoleName;
		} catch (Exception e) {
			log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}

	
	public String getBUIDRowid(Connection conn) {
        String partyRoleName = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
        sb.append("FROM ");
        sb.append("( ");
        sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
        sb.append("FROM ");
        sb.append("( ");
        sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
        sb.append("FROM    C_TYP_TYPE ");
        sb.append("WHERE   HUB_STATE_IND = 1 ");
        sb.append("AND     CODE = 'ALT_CODE_BUID' ");
        sb.append("UNION ALL ");
        sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
        sb.append(") ");
        sb.append(") ");
        sb.append("WHERE D_RANK = 1");
        try {
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sb.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                partyRoleName = rs.getString("ROWID_OBJECT");
            }
            return partyRoleName;
        }
        catch (Exception e) {
            log.info((Object)("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
            return "NOT AVAILABLE";
        }
    }
	
	public Integer getOffDetails(Connection conn, String Facility_Rowid) {
		Integer Off_Rowid = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(1) AS CNT FROM C_FCT_OFF_REL ");
		sb.append("P WHERE P.FCT_ROWID= ");
		sb.append("'" + Facility_Rowid + "'");
		sb.append(" AND EXISTS (SELECT 1 FROM C_FCT_OFF WHERE IS_ACTIVE_IND='N' AND ROWID_OBJECT=P.OFFERING_ROWID)");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Off_Rowid = rs.getInt("CNT");
				log.info((Object) ("Count is ::" + Off_Rowid));
			}
			return Off_Rowid;
		} catch (Exception e) {
			log.info((Object) ("getOffDetails Exception ::" + e.getLocalizedMessage()));
			return 0;
		}
	}

	public String FetchCountryCode(Connection conn, String CNTRY_ROWID) {
		String CNTRY_CD = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SUBSTR(CODE,0,2) AS CNTRY_CD FROM C_ALT_CODE WHERE TYP_TYPE_ROWID= ");
		sb.append("(SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE='ALT_CODE.RKST') ");
		sb.append("AND GDA_DFND_AREA_ROWID= ");
		sb.append("'" + CNTRY_ROWID + "'");
		log.info((Object) sb);
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CNTRY_CD = rs.getString("CNTRY_CD");
				log.info((Object) ("CNTRY_CD is ::" + CNTRY_CD));
			}
			return CNTRY_CD;
		} catch (Exception e) {
			log.info((Object) ("FetchCountryCode Exception ::" + e.getLocalizedMessage()));
			return "Error in Fetching the Code";
		}
	}

	public boolean TagInactiveOff(BDDObject arg0) {
		List<BDDObject> Service_Details = arg0.getChildren("Facility_Services");
		if (this.getObjectCount(Service_Details) > 0 && Service_Details.size() > 0) {
			for (BDDObject Service_Detail : Service_Details) {
				String ServiceOldValue = (String) Service_Detail.getOldValue("C_FCT_OFF|IS_ACTIVE_IND");
				if (ServiceOldValue != null
						|| !"N".equalsIgnoreCase(Service_Detail.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString()))
					continue;
				return true;
			}
		}
		return false;
	}

	public boolean ExistInactiveOff(BDDObject arg0) {
		List<BDDObject> Service_Details = arg0.getChildren("Facility_Services");
		if (this.getObjectCount(Service_Details) > 0 && Service_Details.size() > 0) {
			for (BDDObject Service_Detail : Service_Details) {
				String ServiceOldValue = (String) Service_Detail.getOldValue("C_FCT_OFF|IS_ACTIVE_IND");
				log.info((Object) ("ServiceOldValue=" + ServiceOldValue));
				if (ServiceOldValue == null
						|| !"N".equalsIgnoreCase(Service_Detail.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString()))
					continue;
				log.info((Object) ("ServiceValue=" + Service_Detail.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString()));
				return true;
			}
		}
		return false;
	}

	public boolean URLCheck(BDDObject arg0) {
		org.apache.commons.validator.UrlValidator urlValidator = new UrlValidator();
		log.info("1");
		String URL = (String) arg0.getValue("C_FCT_FACILITY|URL");
		log.info("2:= " + URL);

		if (URL != null) {
			log.info("3");
			if (!urlValidator.isValid(URL)) {
				log.info("4");
				return true;
			}
		}
		return false;
	}

	public boolean emailCheck(BDDObject arg0) {

		Pattern pattern;
		Matcher matcher;

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);

		List<BDDObject> Contact_Details = arg0.getChildren("Facility_Contact_Details");

		if (getObjectCount(Contact_Details) > 0 && Contact_Details.size() > 0)
			for (BDDObject Contact_Detail : Contact_Details) {

				String Email = (String) Contact_Detail.getValue("C_FCT_CONT|EMAIL");

				if (Email != null) {
					log.info("3");
					matcher = pattern.matcher(Email);
					return matcher.matches();
				}

			}
		return true;
	}

	private int getRKSTCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				if (!AltCodeValue.equalsIgnoreCase(this.getRKSTRowid(conn)) || object.isRemoved())
					continue;
				++count;
			}
		}
		return count;
	}

	private boolean getRKSTLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn)) && AltCodeValue.length() > 8) {
					return true;

				}

			}
		}
		return false;
	}

	private boolean getMINRKSTLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn)) && AltCodeValue.length() < 7) {
					return true;

				}

			}
		}
		return false;
	}

	private boolean getMINRKTSLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if(AltCodeValue!=null && !AltCodeValue.equalsIgnoreCase("")) {
					if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn)) && AltCodeValue.length() < 5) {
						return true;

					}
				}

			}
		}
		return false;
	}

	private String getFacilityRKST(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();

				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn))) {
					return AltCode;

				}

			}
		}
		return null;
	}

	private String getFacilityRKTS(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		log.info("Inside getFacilityRKTS before loop");
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if(AltCode!=null && !AltCode.equalsIgnoreCase("")) {
				log.info("Inside getFacilityRKTS");
				if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn))) {
					log.info("Inside getFacilityRKTS inside check");
							return AltCode;

					}
				}
			}
		}
		return null;
	}

	public Integer getAltDetails(Connection conn, String Facility_Rowid, List<BDDObject> objects) {
		Integer CNT = 0;

		if (objects != null) {
			for (BDDObject object : objects) {

				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();

				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn))
						|| AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn))) {
					StringBuilder sb = new StringBuilder();
					sb.append("SELECT COUNT(1) AS CNT FROM C_FCT_ALT_CODES ");
					sb.append("P WHERE P.CODE= ");
					sb.append("'" + AltCodeValue + "'");
					sb.append(
							" AND EXISTS (SELECT 1 FROM C_FCT_FACILITY WHERE CLASS_CD='OPS' AND ROWID_OBJECT=P.FCT_ROWID)");
					try {
						PreparedStatement stmt = null;
						stmt = conn.prepareStatement(sb.toString());
						ResultSet rs = stmt.executeQuery();
						while (rs.next()) {
							CNT = rs.getInt("CNT");
							log.info((Object) ("Count is ::" + CNT));
						}
						return CNT;
					} catch (Exception e) {
						log.info((Object) ("getOffDetails Exception ::" + e.getLocalizedMessage()));
						return 0;
					}
				}
			}
		}
		return 0;
	}

	private boolean getRKTSLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if(AltCodeValue!=null && !AltCodeValue.equalsIgnoreCase("")) {
				if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn)) && AltCodeValue.length() > 5) {
					return true;

				}
			}

			}
		}
		return false;
	}

	private int getRKTSCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				if(AltCodeValue!=null && !AltCodeValue.equalsIgnoreCase("")){
				if (!AltCodeValue.equalsIgnoreCase(this.getRKTSRowid(conn)) || object.isRemoved())
					continue;
				++count;
			}
			}
		}
		return count;
	}

	private int getContactCount(List<BDDObject> objects) throws ParseException {
		Date dNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				Date Vlid_Thru_Dt = (Date) formatter
						.parseObject(object.getValue("C_FCT_CONT_REL|VALID_THRU_DT").toString());
				if (dNow.compareTo(Vlid_Thru_Dt) > 0 || object.isRemoved())
					continue;
				++count;
			}
		}
		return count;
	}

	private String getFacilityRKSTCount(List<BDDObject> objects, Connection conn) {

		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {

				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();

				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn))) {
					count = getFCTRKSTCount(conn, AltCode);
					if (count > 0) {
						return "YES";
					}
				}
			}
		}
		return null;
	}

	private String getFacilityRKTSCount(List<BDDObject> objects, Connection conn) {

		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {

				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if(AltCode!=null && !AltCode.equalsIgnoreCase("")){
				if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn))) {
					count = getFCTRKTSCount(conn, AltCode);
					if (count > 0) {
						return "YES";
					}
				}
			}
			}
		}
		return null;
	}

	private boolean getAltCodeCount(List<BDDObject> objects, String typTypeRowid) {
		log.info((Object) "getAltCodeCount called 1");
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltcodeBeforeSave = (String) object.getOldValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID");
				log.info((Object) ("getAltCodeCount AltcodeBeforeSave" + AltcodeBeforeSave));
				
				String AltcodeAfterSave = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				log.info((Object) ("getAltCodeCount AltcodeAfterSave" + AltcodeAfterSave));
				if (AltcodeBeforeSave == null || !AltcodeBeforeSave.equalsIgnoreCase(typTypeRowid)
						|| AltcodeBeforeSave == AltcodeAfterSave)
					continue;
				log.info((Object) "getAltCodeCount called 2");
				return true;
			}
		}
		return false;
	}

	public int getGeoTypTypeRowidCount(Connection conn, String typTypeRowid, String fctRowid) {
		int geoTyptypeRowidCount = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(" select count ");
		sb.append(" ( ");
		sb.append(" * ");
		sb.append(" ) ");
		sb.append(" from C_FCT_ALT_CODES ");
		sb.append(" where ");
		sb.append(" TYP_TYPE_ROWID = (?) ");
		sb.append(" and ");
		sb.append(" FCT_ROWID = (?) ");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, typTypeRowid);
			stmt.setString(2, fctRowid);
			log.info((Object) ("Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				geoTyptypeRowidCount = rs.getInt(1);
			}
			return geoTyptypeRowidCount;
		} catch (Exception e) {
			log.info((Object) ("getGeoTypTypeRowidCount Exception ::" + e.getLocalizedMessage()));
			return 0;
		}
	}

	public String getAddrrowid(Connection conn, String fctRowid) {
		String Addr_Rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" select ROWID_OBJECT ");
		sb.append(" from C_FCT_ADDR_REL ");
		sb.append(" where ");
		sb.append(" FCT_ROWID = (?) ");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, fctRowid);
			log.info((Object) ("Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Addr_Rowid = rs.getString(1);
			}
			return Addr_Rowid;
		} catch (Exception e) {
			log.info((Object) ("getGeoTypTypeRowidCount Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	public int getFCTRKSTCount(Connection conn, String RKST_Val) {
		int RKST_Val1 = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT COUNT(1) AS CNT FROM C_FCT_ALT_CODES ");
		sb.append(" WHERE CODE=(?) AND TYP_TYPE_ROWID= ");
		sb.append(" (SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE='ALT_CODE.RKST') ");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, RKST_Val);
			log.info((Object) ("Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				RKST_Val1 = rs.getInt(1);
			}
			return RKST_Val1;
		} catch (Exception e) {
			log.info((Object) ("RKST_Val1 Exception ::" + e.getLocalizedMessage()));
			return 0;
		}
	}

	public int getFCTRKTSCount(Connection conn, String RKTS_Val) {
		int RKTS_Val1 = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT COUNT(1) AS CNT FROM C_FCT_ALT_CODES ");
		sb.append(" WHERE CODE=(?) AND TYP_TYPE_ROWID= ");
		sb.append(" (SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE='ALT_CODE.RKTS' AND HUB_STATE_IND ='1') ");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, RKTS_Val);
			log.info((Object) ("Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				RKTS_Val1 = rs.getInt(1);
			}
			return RKTS_Val1;
		} catch (Exception e) {
			log.info((Object) ("RKST_Val1 Exception ::" + e.getLocalizedMessage()));
			return 0;
		}
	}

	private String getCntryRKSTVal(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String CTRY_ROWID = object.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString();

				return getCntryRKST(conn, CTRY_ROWID);

			}
		}
		return null;
	}

	private String getCityRKSTVal(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String CITY_ROWID = object.getValue("C_CTM_PSTL_ADDR|CITY_ROWID").toString();

				return getCityRKST(conn, CITY_ROWID);

			}
		}
		return null;
	}

	private String getCityRKTSVal(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String CITY_ROWID = object.getValue("C_CTM_PSTL_ADDR|CITY_ROWID").toString();

				return getCityRKTS(conn, CITY_ROWID);

			}
		}
		return null;
	}

	public String getCntryRKST(Connection conn, String CntryRowid) {
		String CntryRKST = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT CODE FROM C_ALT_CODE WHERE ");
		sb.append(" GDA_DFND_AREA_ROWID=(?) ");
		sb.append(" AND TYP_TYPE_ROWID=(SELECT ROWID_OBJECT FROM C_TYP_TYPE ");
		sb.append(" WHERE CODE='ALT_CODE.RKST') ");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, CntryRowid);
			log.info((Object) ("Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CntryRKST = rs.getString(1);
			}
			return CntryRKST;
		} catch (Exception e) {
			log.info((Object) ("CntryRKST Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	public String getCityRKST(Connection conn, String CityRowid) {
		String CityRKST = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT CODE FROM C_ALT_CODE WHERE ");
		sb.append(" GDA_DFND_AREA_ROWID=(?) ");
		sb.append(" AND TYP_TYPE_ROWID=(SELECT ROWID_OBJECT FROM C_TYP_TYPE ");
		sb.append(" WHERE CODE='ALT_CODE.RKST') ");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, CityRowid);
			log.info((Object) ("Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CityRKST = rs.getString(1);
			}
			return CityRKST;
		} catch (Exception e) {
			log.info((Object) ("CityRKST Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	public String getCityRKTS(Connection conn, String CityRowid) {
		String CityRKTS = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT CODE FROM C_ALT_CODE WHERE ");
		sb.append(" GDA_DFND_AREA_ROWID=(?) ");
		sb.append(" AND TYP_TYPE_ROWID=(SELECT ROWID_OBJECT FROM C_TYP_TYPE ");
		sb.append(" WHERE CODE='ALT_CODE.RKTS') ");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, CityRowid);
			log.info((Object) ("Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CityRKTS = rs.getString(1);
			}
			log.info("City ID to return ::"+CityRKTS);
			return CityRKTS;
		} catch (Exception e) {
			log.info((Object) ("CityRKTS Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	private int getObjectCount(List<BDDObject> objects) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				if (object.isRemoved())
					continue;
				log.info((Object) ("Count::" + ++count));
			}
		}
		return count;
	}

	public String AddressDctorCheck(String Street, String House_Num, String City, String Pstl_Cd, String Trty_Rowid,
			String Cntry_Rowid, String Addr_Ln_2, String Addr_Ln_3, String LAT_GEOSPTL, String LNG_GEOSPTL,
			String CNTRY_CD) {
		String mdmORSid = (String) this.getOperationContext().getValue("ors id");
		Connection conn6 = getDatabaseConnection(mdmORSid);

		log.info((Object) "Inside AddressDctorCheck::");
		this.sipClient = (SiperianClient) this.getOperationContext().getValue("sifClient");
		log.info((Object) ("The Siperian Client is::" + (Object) this.sipClient));
		CleanseRequest request = new CleanseRequest();
		request.setCleanseFunctionName("Facility Cleanse Function|FacilityAdressIDD");
		Record record = new Record();
		Field field = new Field();
		field.setName("STREET");
		field.setValue((Object) Street);
		record.setField(field);
		field = new Field();
		field.setName("HOUSE_NUM");
		field.setValue((Object) House_Num);
		record.setField(field);
		field = new Field();
		field.setName("CITY");
		field.setValue((Object) City);
		record.setField(field);
		field = new Field();
		field.setName("PSTCD");
		field.setValue((Object) Pstl_Cd);
		record.setField(field);
		field = new Field();
		field.setName("TRTY_ROWID");
		field.setValue((Object) Trty_Rowid);
		record.setField(field);
		field = new Field();
		field.setName("CTRY_ROWID");
		field.setValue((Object) Cntry_Rowid);
		record.setField(field);
		field = new Field();
		field.setName("ADDR_LN_2");
		field.setValue((Object) Addr_Ln_2);
		record.setField(field);
		field = new Field();
		field.setName("ADDR_LN_3");
		field.setValue((Object) Addr_Ln_3);
		record.setField(field);
		field = new Field();
		field.setName("LAT_GEOSPTL");
		field.setValue((Object) LAT_GEOSPTL);
		record.setField(field);
		field = new Field();
		field.setName("LNG_GEOSPTL");
		field.setValue((Object) LNG_GEOSPTL);
		record.setField(field);
		field = new Field();
		field.setName("CNTRY_CD");
		field.setValue((Object) CNTRY_CD);
		record.setField(field);
		log.info("input completed");

		try {
			request.setUsername((String) this.getOperationContext().getValue(OperationContext.USERNAME));
			log.info((Object) ("The IDD  is(Adress)::"
					+ this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
			request.setPassword(new Password((String) this.getOperationContext().getValue(OperationContext.PASSWORD)));
			log.info((Object) ("The MDM password is(Adress)::"
					+ this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
			log.info("getOperationContext has been sent for AddressDctorCheck");

		} catch (Exception e) {
			log.info("Security Payload has been started AddressDctorCheck");
			byte[] securityPayload = (byte[]) getOperationContext().getValue("security payload");
			request.setSecurityPayload(securityPayload);
			log.info("Security Payload has been sent AddressDctorCheck" + e);
		}

		/*
		 * log.info("Security Payload has been started AddressDctorCheck"); byte[]
		 * securityPayload = (byte[]
		 * )getOperationContext().getValue("security payload");
		 * request.setSecurityPayload(securityPayload);
		 * log.info("Security Payload has been sent AddressDctorCheck");
		 */

		request.setOrsId((String) this.getOperationContext().getValue("ors id"));
		log.info((Object) ("The MDM ORS is(Adress)::" + this.getOperationContext().getValue("ors id").toString()));
		request.setRecord(record);
		CleanseResponse response = (CleanseResponse) this.sipClient.process((SiperianRequest) request);
		log.info((Object) "before getting response(Adress)");
		log.info((Object) ("StatusInfoMatchCode is ::"
				+ response.getRecord().getField("StatusInfoMatchCode").getStringValue()));
		String StatusInfoMatchCode = response.getRecord().getField("StatusInfoMatchCode").getStringValue();
		try {

			if (StatusInfoMatchCode.contains("V")) {

				log.info("V_ADDR_0");
				String Country_Name = response.getRecord().getField("Country").getStringValue();
				log.info("V_ADDR_1 Modified");

				log.info("V_Territory is ::" + response.getRecord().getField("Territory"));
				String Region_Code = null;
				String Get_Region = null;
				log.info("AV_DDR_3");
				String Get_country = GetCountry(conn6, Country_Name);
				log.info("V_Country Rowid is::" + Get_country);
				if (response.getRecord().getField("ADDR_LN_2") == null) {

					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("V_Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222_1");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				} else {
					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222_2");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("V_Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222_3");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				}

			} else if (StatusInfoMatchCode.contains("C")) {

				log.info("ADDR_0");
				String Country_Name = response.getRecord().getField("Country").getStringValue();
				log.info("ADDR_1 Modified");

				log.info("Territory is ::" + response.getRecord().getField("Territory"));
				String Region_Code = null;
				String Get_Region = null;
				log.info("ADDR_3");
				String Get_country = GetCountry(conn6, Country_Name);
				log.info("Country Rowid is::" + Get_country);
				if (response.getRecord().getField("ADDR_LN_2") == null) {

					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222_1");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				} else {
					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222_2");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222_3");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				}

			} else

				return "BadAddress";

		} catch (NumberFormatException e) {
			return "Check City_country";
		}

		finally {
			try {
				conn6.close();
				log.info((Object) "--------Connection closed");
			} catch (Exception e) {
			}
		}
	}

	public OperationResult beforeSave(BDDObject SavingObject) {
		
		String mdmORSid = this.getOperationContext().getValue("ors id").toString();
		Connection conn3 = this.getDatabaseConnection(mdmORSid);

		String Var = "No";
		try {

					
			if ("Operational".equalsIgnoreCase(SavingObject.getObjectName().toString())) {

				String Fct_Rowid = SavingObject.getSystemValue("C_FCT_FACILITY|ROWID_OBJECT").toString();
				log.info((Object) ("Facility Rowid Is ::" + Fct_Rowid));
				int geoTypTypeRowidCount = 0;
				List<BDDObject> Service_Details = SavingObject.getChildren("Facility_Services");
				List<BDDObject> Trnsp_Details = SavingObject.getChildren("Transport_Details");
				List<BDDObject> Facility_Details = SavingObject.getChildren("Facility_Details");
				List<BDDObject> Alternate_Codes = SavingObject.getChildren("Alternate_Codes");
				String typTypeRowid = this.getTypTypeRowid(conn3);
				log.info("typTypeRowid in beforeSave is : "+typTypeRowid);
				geoTypTypeRowidCount = this.getGeoTypTypeRowidCount(conn3, typTypeRowid, Fct_Rowid);
				log.info("Before 1");
				if (this.getRKSTCount(Alternate_Codes, conn3) > 1) {
					log.info((Object) "getRKSTCount being called");
					Var = "1";
					return new OperationResult(new OperationExecutionError("SIP-500156", this.getLocalizationGate()));
				}
				log.info("Before 2");
				if (this.getRKTSCount(Alternate_Codes, conn3) > 1) {
					log.info((Object) "getRKTSCount being called");
					Var = "2";
					return new OperationResult(new OperationExecutionError("SIP-500157", this.getLocalizationGate()));
				}
				log.info("Before 3");
				if (this.getAltCodeCount(Alternate_Codes, this.getTypTypeRowid(conn3))) {
					log.info((Object) "getAltCodeCount being called");
					Var = "3";
					return new OperationResult(new OperationExecutionError("SIP-500155", this.getLocalizationGate()));
				}
				log.info("Before 4");
				if (this.GetCountOff(Service_Details)) {
					Var = "4";
					return new OperationResult(new OperationExecutionError("SIP-500153", this.getLocalizationGate()));
				}
				log.info("Before 5");
				if (this.GetCountTrnsp(Trnsp_Details)) {
					Var = "5";
					return new OperationResult(new OperationExecutionError("SIP-500153", this.getLocalizationGate()));
				}
				log.info("Before 6");
				if (this.GetCountType(Facility_Details)) {
					Var = "6";
					return new OperationResult(new OperationExecutionError("SIP-500153", this.getLocalizationGate()));
				}

				try {
					log.info("Before 7");
					if (!(Float.valueOf(this.FacltyLatitudeCheck(SavingObject)).floatValue() <= 90.0f
							&& Float.valueOf(this.FacltyLatitudeCheck(SavingObject)).floatValue() >= -90.0f
							|| Float.valueOf(this.FacltyLongitudeCheck(SavingObject)).floatValue() <= 180.0f && Float
									.valueOf(this.FacltyLongitudeCheck(SavingObject)).floatValue() >= -180.0f)) {
						Var = "7";
						return new OperationResult(
								new OperationExecutionError("SIP-500122", this.getLocalizationGate()));
					}
					log.info("Before 8");
					if (Float.valueOf(this.FacltyLatitudeCheck(SavingObject)).floatValue() > 90.0f
							|| Float.valueOf(this.FacltyLatitudeCheck(SavingObject)).floatValue() < -90.0f) {
						Var = "8";
						return new OperationResult(
								new OperationExecutionError("SIP-500123", this.getLocalizationGate()));
					}
					log.info("Before 9");
					if (Float.valueOf(this.FacltyLongitudeCheck(SavingObject)).floatValue() > 180.0f
							|| Float.valueOf(this.FacltyLongitudeCheck(SavingObject)).floatValue() < -180.0f) {
						Var = "9";
						return new OperationResult(
								new OperationExecutionError("SIP-500124", this.getLocalizationGate()));
					}
				} catch (NumberFormatException e) {
					Var = "56";
					return new OperationResult(new OperationExecutionError("SIP-500415", this.getLocalizationGate()));
				}
				log.info("Before 10");
				if (this.TagInactiveOff(SavingObject)) {
					Var = "10";
					return new OperationResult(new OperationExecutionError("SIP-500106", this.getLocalizationGate()));
				}
				try {
					log.info("Before 11");
					if (this.getContactCount(SavingObject.getChildren("Facility_Contact_Details")) > 1) {
						Var = "11";
						return new OperationResult(
								new OperationExecutionError("SIP-500107", this.getLocalizationGate()));
					}
				} catch (ParseException e) {
					Var = "12";
					e.printStackTrace();
				}
				log.info("Before 13");
				if (URLCheck(SavingObject)) {
					Var = "13";
					return new OperationResult(new OperationExecutionError("SIP-500110", this.getLocalizationGate()));
				}
				log.info("Before 14");
				if (!emailCheck(SavingObject)) {
					Var = "14";
					return new OperationResult(new OperationExecutionError("SIP-500163", this.getLocalizationGate()));
				}
				/*
				 * if (this.FacltyOpsCheck(SavingObject) &&
				 * this.FacltyAddressCheck(SavingObject)) { log.info((
				 * Object)"Facility Details,Facility Type and Facility Address not given");
				 * return new OperationResult(new OperationExecutionError("SIP-500117",
				 * this.getLocalizationGate())); } if (this.FacltyTypeCheck(SavingObject) &&
				 * this.FacltyAddressCheck(SavingObject)) {
				 * log.info((Object)"Facility Type and Facility Address not given"); return new
				 * OperationResult(new OperationExecutionError("SIP-500118",
				 * this.getLocalizationGate())); } if (this.FacltyOpsCheck(SavingObject)) {
				 * log.info((Object)"Facility Details,Facility Type not given"); return new
				 * OperationResult(new OperationExecutionError("SIP-500119",
				 * this.getLocalizationGate())); } if (this.FacltyTypeCheck(SavingObject)) {
				 * log.info((Object)"Facility Type and Facility Address"); return new
				 * OperationResult(new OperationExecutionError("SIP-500120",
				 * this.getLocalizationGate())); } if (this.FacltyAddressCheck(SavingObject)) {
				 * log.info((Object)"Facility Address not given"); return new
				 * OperationResult(new OperationExecutionError("SIP-500121",
				 * this.getLocalizationGate())); } if(!this.FacltyAddressCheck(SavingObject) &&
				 * !this.FacltyTypeCheck(SavingObject) && !this.FacltyOpsCheck(SavingObject) &&
				 * this.FacltyServiceCheck(SavingObject)){
				 * log.info((Object)"Facility Service not given"); return new
				 * OperationResult(new OperationExecutionError("SIP-500170",
				 * this.getLocalizationGate())); }
				 */
				log.info("Before 15");
				if (this.FacltyAddressCheck(SavingObject)) {
					Var = "15";
					log.info((Object) "Facility Address not given");
					return new OperationResult(new OperationExecutionError("SIP-500121", this.getLocalizationGate()));
				}
				log.info("Before 16");
				if (!this.FacltyAddressCheck(SavingObject) && this.FacltyOpsCheck(SavingObject)
						&& this.FacltyServiceCheck(SavingObject)) {
					log.info((Object) "Facility details, Type and services not given");
					Var = "16";
					return new OperationResult(new OperationExecutionError("SIP-500172", this.getLocalizationGate()));
				}
				log.info("Before 17");
				if (!this.FacltyAddressCheck(SavingObject) && this.FacltyOpsCheck(SavingObject)) {
					log.info((Object) "Facility details, Type and services not given");
					Var = "17";
					return new OperationResult(new OperationExecutionError("SIP-500183", this.getLocalizationGate()));
				}
				log.info("Before 17 second");
				if (!this.FacltyAddressCheck(SavingObject) && this.FacltyTypeCheck(SavingObject)
						&& this.FacltyServiceCheck(SavingObject)) {
					log.info((Object) "Facility  Type and services not given");
					Var = "17";
					return new OperationResult(new OperationExecutionError("SIP-500173", this.getLocalizationGate()));
				}
				log.info("Before 18");
				if (!this.FacltyAddressCheck(SavingObject) && this.FacltyTypeCheck(SavingObject)) {
					log.info((Object) "Facility Type not given");
					Var = "18";
					return new OperationResult(new OperationExecutionError("SIP-500171", this.getLocalizationGate()));
				}
				log.info("Before 19");
				if (!this.FacltyAddressCheck(SavingObject) && this.FacltyServiceCheck(SavingObject)) {
					log.info((Object) "Facility Service not given");
					Var = "19";
					return new OperationResult(new OperationExecutionError("SIP-500170", this.getLocalizationGate()));
				}
				//RKTS - Remove mandatory RKTS- Anil
				log.info("Before 20");
				if (this.getRKSTCount(Alternate_Codes,
						conn3) == 0 /*&& this.getRKTSCount(Alternate_Codes, conn3) == 0*/ ) {
					log.info((Object) "getRKSTCount and getRKSTCount being called for exist or not");
					Var = "20";
					return new OperationResult(new OperationExecutionError("SIP-500176", this.getLocalizationGate()));
				}
				log.info("Before 21");
				if (this.getRKSTCount(Alternate_Codes, conn3) == 0) {
					log.info((Object) "getRKSTCount being called for exist or not");
					Var = "21";
					return new OperationResult(new OperationExecutionError("SIP-500174", this.getLocalizationGate()));
				}
				//RKTS - Remove mandatory RKTS- Anil
				log.info("Logger to check Code Execution ::");
				log.info("Before 22");
			//	  if (this.getRKTSCount(Alternate_Codes, conn3) == 0) {
				 // log.info((Object) "getRKTSCount being called for exist or not"); 
				  //Var = "22";
				  	//return new OperationResult(new OperationExecutionError("SIP-500175",this.getLocalizationGate())); 
			//	  }
				 
				log.info("Before 23");
				if (getMINRKSTLength(Alternate_Codes, conn3)) {
					log.info((Object) "RKST can not be less  than 7");
					Var = "23";
					return new OperationResult(new OperationExecutionError("SIP-500188", this.getLocalizationGate()));

				}
				log.info("Before 24");
				if (getMINRKTSLength(Alternate_Codes, conn3)) {
					log.info((Object) "RKTS can not be less  than 5");
					Var = "24";
					return new OperationResult(new OperationExecutionError("SIP-500189", this.getLocalizationGate()));

				}
				log.info("Before 25");
				if (getRKSTLength(Alternate_Codes, conn3)) {
					log.info((Object) "RKST can not be more than 8");
					Var = "25";
					return new OperationResult(new OperationExecutionError("SIP-500165", this.getLocalizationGate()));

				}
				log.info("Before 26");
				if (getRKTSLength(Alternate_Codes, conn3)) {
					log.info((Object) "RKTS can not be more than 5");
					Var = "26";
					return new OperationResult(new OperationExecutionError("SIP-500166", this.getLocalizationGate()));

				}

				/*************** This is pending ************/

				/*
				 * if(getAltDetails(getDatabaseConnection(mdmORSid),Fct_Rowid,AlternateCodes)>0)
				 * { log.info((Object)"RKST or RKTS is not unique"); return new
				 * OperationResult(new OperationExecutionError("SIP-500167",
				 * this.getLocalizationGate())); }
				 */

				/*************** This is pending ************/
				log.info("Line 2634");
				List<BDDObject> facilityAddres = SavingObject.getChildren("Facility_Address");
				String FacilityRKST = getFacilityRKST(Alternate_Codes, conn3);
				log.info(FacilityRKST);
				String FacilityRKTS = getFacilityRKTS(Alternate_Codes, conn3);
				log.info("Line 2643 :: RKTS string FacilityRKTS is"+FacilityRKTS);
				log.info(FacilityRKTS);
				log.info("Line 2640");
				String FacilityRKST_Cntry = FacilityRKST.substring(0, 2);
				log.info(FacilityRKST_Cntry);
				String FacilityRKST_City = FacilityRKST.substring(0, 5);
				log.info(FacilityRKST_City);
				log.info("Line 2650");
				String FacilityRKTS_City ="";
				if(FacilityRKTS!=null) {
					FacilityRKTS.substring(0, 3);
				}
				log.info(FacilityRKTS_City);
				log.info("Line 2652");
				String CntryRKSTVal = getCntryRKSTVal(facilityAddres, conn3);
				log.info(CntryRKSTVal);
				String CityRKSTVal = getCityRKSTVal(facilityAddres, conn3);
				log.info(CityRKSTVal);
				String CityRKTSVal = getCityRKTSVal(facilityAddres, conn3);
				log.info(CityRKTSVal);

				/*
				 * if(FacilityRKST_Cntry.equalsIgnoreCase(CntryRKSTVal) &&
				 * FacilityRKST_City.equalsIgnoreCase(CityRKSTVal) &&
				 * FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)){
				 * log.info((Object)"RKST and RKTS is not sync with Geo"); return new
				 * OperationResult(new OperationExecutionError("SIP-500185",
				 * this.getLocalizationGate())); }
				 */
				log.info("Line 2663");
				if (!FacilityRKST_Cntry.equalsIgnoreCase(CntryRKSTVal)) {
					log.info((Object) "RKST and RKTS is not sync with Geo 1");
					if (!FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 2");
						if(FacilityRKTS_City!="") {
							if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
						
							log.info((Object) "RKST and RKTS is not sync with Geo 3");
							Var = "27";
							return new OperationResult(
									new OperationExecutionError("SIP-500185", this.getLocalizationGate()));
						} else if (FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
							log.info((Object) "RKST and RKTS is not sync with Geo 3");
							Var = "28";
							return new OperationResult(
									new OperationExecutionError("SIP-500191", this.getLocalizationGate()));
						}
						}
					} else if (FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 8");
						if(FacilityRKTS_City!="") {
							if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
							log.info((Object) "RKST and RKTS is not sync with Geo 9");
							Var = "29";
							return new OperationResult(
									new OperationExecutionError("SIP-500413", this.getLocalizationGate()));
						} else if (FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
							log.info((Object) "RKST and RKTS is not sync with Geo 10");
							Var = "30";
							return new OperationResult(
									new OperationExecutionError("SIP-500414", this.getLocalizationGate()));
						}
						}
						
					}
				} else if (!FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)) {
					log.info((Object) "RKST and RKTS is not sync with Geo 4");
					if(FacilityRKTS_City!="") {
						if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 5");
						Var = "30";
						return new OperationResult(
								new OperationExecutionError("SIP-500186", this.getLocalizationGate()));
					} else if (FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 7");
						Var = "30";
						return new OperationResult(
								new OperationExecutionError("SIP-500190", this.getLocalizationGate()));
					}
				}
				} else if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
					if(FacilityRKTS_City!="") {
					log.info((Object) "RKST and RKTS is not sync with Geo 6");
					Var = "30";
					return new OperationResult(new OperationExecutionError("SIP-500187", this.getLocalizationGate()));
					}
				}
				

				/*
				 * else if(!FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)){
				 * log.info((Object)"RKST and RKTS is not sync with Geo 7"); return new
				 * OperationResult(new OperationExecutionError("SIP-500190",
				 * this.getLocalizationGate())); }
				 */if (geoTypTypeRowidCount == 0) {
					if (("YES".equalsIgnoreCase(getFacilityRKSTCount(Alternate_Codes, conn3)))
							&& ("YES".equalsIgnoreCase(getFacilityRKTSCount(Alternate_Codes, conn3)))) {
						log.info((Object) "RKST and RKTS Duplicate");
						Var = "30";
						return new OperationResult(
								new OperationExecutionError("SIP-500213", this.getLocalizationGate()));
					}

					if ("YES".equalsIgnoreCase(getFacilityRKSTCount(Alternate_Codes, conn3))) {
						log.info((Object) "RKST Duplicate");
						Var = "30";
						return new OperationResult(
								new OperationExecutionError("SIP-500211", this.getLocalizationGate()));
					}

					if ("YES".equalsIgnoreCase(getFacilityRKTSCount(Alternate_Codes, conn3))) {
						log.info((Object) "RKTS Duplicate");
						Var = "30";
						return new OperationResult(
								new OperationExecutionError("SIP-500212", this.getLocalizationGate()));
					}
				}

				else {
					log.info((Object) "Ok");
				}

					log.info("Before hitting generateOperationalFacilityData method");
					if(geoTypTypeRowidCount==0 && SavingObject.isCreated()) {
					log.info("Generating Geo ID For Create Case");	
					}
					OperationResult oprRes=generateOperationalFacilityData(SavingObject,mdmORSid);
					log.info("After hitting generateOperationalFacilityData method");
					if(oprRes!=OperationResult.OK) {
					log.info("No Success from generateOperationalFacilityData method");
					return new OperationResult(new OperationExecutionError("SIP-60012", new String[] {"Error in generateOperationalFacilityData method"}, getLocalizationGate()));
					}
					}
		} catch (Exception e) {
			log.info("Exception in generateOperationalFacilityData method ..");
			e.printStackTrace();
			return new OperationResult(new OperationExecutionError("SIP-40013", getLocalizationGate()));
		} finally {
			log.info("We are inside finally method");
			try {

				if ("No".equalsIgnoreCase(Var)) {
					if ("Operational".equalsIgnoreCase(SavingObject.getObjectName().toString())) {

						/*---------------------------------------------------*/

						log.info("in AftreSave");
						log.info(test);
						List<BDDObject> facilityAddress = SavingObject.getChildren("Facility_Address");

						if (this.getObjectCount(facilityAddress) > 0 && facilityAddress.size() > 0) {
							for (BDDObject facility_Address : facilityAddress) {

								log.info("test 1");

								if (test.contains("CLEANSE_0")) {

									String STREET_OUTPUT = test.substring(
											test.indexOf("CLEANSE_0") + "CLEANSE_0".length(),
											test.indexOf("CLEANSE_1"));
									String HOUSE_NUM_OUTPUT = test.substring(
											test.indexOf("CLEANSE_1") + "CLEANSE_1".length(),
											test.indexOf("CLEANSE_2"));
									String PSTCD_OUTPUT = test.substring(
											test.indexOf("CLEANSE_2") + "CLEANSE_2".length(),
											test.indexOf("CLEANSE_3"));
									String ADDR_LN_2_OUTPUT = test.substring(
											test.indexOf("CLEANSE_3") + "CLEANSE_3".length(),
											test.indexOf("CLEANSE_4"));
									String ADDR_LN_3_OUTPUT = test.substring(
											test.indexOf("CLEANSE_4") + "CLEANSE_4".length(),
											test.indexOf("CLEANSE_5"));
									String LAT_GEOSPTL_OUTPUT = test.substring(
											test.indexOf("CLEANSE_5") + "CLEANSE_5".length(),
											test.indexOf("CLEANSE_6"));
									String LNG_GEOSPTL_OUTPUT = test.substring(
											test.indexOf("CLEANSE_6") + "CLEANSE_6".length(),
											test.indexOf("CLEANSE_7"));
									String Country_Rowid = test.substring(
											test.indexOf("CLEANSE_7") + "CLEANSE_7".length(),
											test.indexOf("CLEANSE_8"));
									String Region_Rowid = test.substring(
											test.indexOf("CLEANSE_8") + "CLEANSE_8".length(),
											test.indexOf("CLEANSE_9"));
									log.info("STREET_OUTPUT::" + STREET_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|STREET", STREET_OUTPUT);
									log.info("HOUSE_NUM_OUTPUT::" + HOUSE_NUM_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|HOUSE_NUM", HOUSE_NUM_OUTPUT);
									log.info("PSTCD_OUTPUT::" + PSTCD_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|PSTCD", PSTCD_OUTPUT);
									log.info("ADDR_LN_2_OUTPUT::" + ADDR_LN_2_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_2", ADDR_LN_2_OUTPUT);
									log.info("ADDR_LN_3_OUTPUT::" + ADDR_LN_3_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_3", ADDR_LN_3_OUTPUT);
									log.info("LAT_GEOSPTL_OUTPUT::" + LAT_GEOSPTL_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL", LAT_GEOSPTL_OUTPUT);
									log.info("LNG_GEOSPTL_OUTPUT::" + LNG_GEOSPTL_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL", LNG_GEOSPTL_OUTPUT);
									log.info("Country::" + Country_Rowid);
									facility_Address.setValue("C_CTM_PSTL_ADDR|CTRY_ROWID", Country_Rowid);
									log.info("Region ::" + Region_Rowid);
									facility_Address.setValue("C_CTM_PSTL_ADDR|TRTY_ROWID", Region_Rowid);
									log.info("Setting has completed");

								}

							}
						}
					}
				}
				/*------------------------------------------------------*/
				conn3.close();
				log.info((Object) "--------Connection closed AfterSave");
			} catch (Exception e) {
			}
			{

			}
		}
		return OperationResult.OK;

	}

	/**
     * @param SavingObject
     * @param mdmORSid 
	 * @param geoIDforPublish 
     * @return
     */
    private OperationResult generateOperationalFacilityData(BDDObject SavingObject, String mdmORSid) {
		username = (String) getOperationContext().getValue(OperationContext.USERNAME);
		try {
			geoIDforPublish=this.generateId();
			log.info("GeoID for Create is::" +geoIDforPublish);
			log.info("Before Data Binding of IDD objects");
			facilityEvent= new SMDSFacilityEvent();
			factDataBinding = new FacilityDataBinding(SavingObject, getOperationContext(),
					getLocalizationGate(),mdmORSid,facilityEvent,geoIDforPublish);
			facilityEvent = factDataBinding.generateFacilityData();
			log.info("Before JAXB conversion of IDD objects");
			String isJAXBTransDone = FacilityJaxBTranslator.sendDataToJAXBTranslation(facilityEvent);
			log.info("After JAXB conversion of IDD objects");
			if (isJAXBTransDone!=null) {
				log.info("Inside isJAXBTransDone check.. Before Sending to JMS");
				//HttpResponse response = mqPublisher.publishToJms();
				

				//String res = EntityUtils.toString(response.getEntity());
				PublishService mqInterface = Utils.getMQInterface();
				loadPropertyFile();
		        int napQueueMessage = mqInterface.send(isJAXBTransDone, properties);
				log.info("Inside isJAXBTransDone check.. After Sending to JMS");
				log.info(napQueueMessage);
				if(napQueueMessage==0) {
					log.info("Sucessfully updated ");
				return OperationResult.OK;
				}
				else {
					log.info("Response from MQ Publish isnt successful ::" + napQueueMessage);
					return new OperationResult(new OperationExecutionError("SIP-60010",
					          new String[] {"Error while sending data to JMS"},
					          this.getLocalizationGate()));
				}
			} else {
				log.info("Exception while JAXB conversion of IDD Objects");
				return new OperationResult(new OperationExecutionError("SIP-60011",
				          new String[] {"Error while converting Facility java to JAXB Objects"},
				          this.getLocalizationGate()));
				
				
			}

		} catch (Exception ex) {
			return new OperationResult(new OperationExecutionError("SIP-60012",
			          new String[] {"Error while executing method generateOperationalFacilityData"},
			          this.getLocalizationGate()));			
		}
	}

	public boolean FacltyOpsCheck(BDDObject SavingObject) {
		List<BDDObject> Ops_Details = SavingObject.getChildren("Facility_Details");
		if (this.getObjectCount(Ops_Details) == 0) {
			return true;
		}
		return false;
	}

	public boolean FacltyServiceCheck(BDDObject SavingObject) {
		List<BDDObject> Service_Details = SavingObject.getChildren("Facility_Services");
		if (this.getObjectCount(Service_Details) == 0) {
			return true;
		}
		return false;
	}

	public boolean FacltyTypeCheck(BDDObject SavingObject) {
		List<BDDObject> Ops_Details = SavingObject.getChildren("Facility_Details");
		if (this.getObjectCount(Ops_Details) > 0 && Ops_Details.size() > 0) {
			for (BDDObject Ops_Detail : Ops_Details) {
				if (this.getObjectCount(Ops_Detail.getChildren("Facility_Type")) != 0)
					continue;
				return true;
			}
		}
		return false;
	}

	public boolean FacltyAddressCheck(BDDObject SavingObject) {
		List<BDDObject> Address_Details = SavingObject.getChildren("Facility_Address");
		if (this.getObjectCount(Address_Details) == 0) {
			return true;
		}
		return false;
	}

	public String FacltyLongitudeCheck(BDDObject SavingObject) {
		List<BDDObject> Address_Details = SavingObject.getChildren("Facility_Address");
		if (this.getObjectCount(Address_Details) > 0 && Address_Details.size() > 0) {
			for (BDDObject Address_Detail : Address_Details) {
				String Longitude = (String) Address_Detail.getValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL");
				if (Longitude == null || Longitude.isEmpty())
					continue;
				return Longitude;
			}
		}
		return "0";
	}

	public String FacltyLatitudeCheck(BDDObject SavingObject) {
		List<BDDObject> Address_Details = SavingObject.getChildren("Facility_Address");
		if (this.getObjectCount(Address_Details) > 0 && Address_Details.size() > 0) {
			for (BDDObject Address_Detail : Address_Details) {
				String Latitude = (String) Address_Detail.getValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL");
				if (Latitude == null || Latitude.isEmpty())
					continue;
				return Latitude;
			}
		}
		return "0";
	}

	public String GetCountry(Connection conn, String Country_Name) {
		String Cntry_Rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" with tab as (SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID  ");
		sb.append(" ,TRIM(C_ALT_CODE.CODE) CNTRY_CD,C_GDA_DFND_AREA.NAME CTRY_NAME FROM C_ALT_CODE ,C_GDA_DFND_AREA ");
		sb.append(" WHERE C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append(" AND   C_GDA_DFND_AREA.TYP_TYPE_CD    = 'GDA.COUNTRY' ");
		sb.append(" AND   C_GDA_DFND_AREA.ACTIVE_FLAG    = 'Y' AND   C_ALT_CODE.TYP_TYPE_ROWID      =( ");
		sb.append(" SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID') ");
		sb.append(" AND LOWER(TRIM(C_GDA_DFND_AREA.NAME))    = LOWER('" + Country_Name + "') ");
		sb.append(" union all select 'No','','' from dual) ");
		sb.append(
				" select decode (COUNTRY_MDM_ID ,'No','Country Not Available',COUNTRY_MDM_ID)COUNTRY_MDM_ID from tab WHERE rownum<2 ");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			log.info((Object) ("Country Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Cntry_Rowid = rs.getString("COUNTRY_MDM_ID");
			}
			return Cntry_Rowid;
		} catch (Exception e) {
			log.info((Object) ("COUNTRY_MDM_ID Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	public String GetRegion(Connection conn, String Region_Code, String Country_Name) {
		String Region_Rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" WITH CNTRY_CD_DETAILS AS (  SELECT DISTINCT Y.ROWID_OBJECT COUNTRY_MDM_ID,   ");
		sb.append(
				" TRIM(X.CODE) CNTRY_CD, Y.NAME CTRY_NAME FROM C_ALT_CODE X, C_GDA_DFND_AREA Y WHERE X.GDA_DFND_AREA_ROWID = Y.ROWID_OBJECT ");
		sb.append(" AND Y.TYP_TYPE_CD           = 'GDA.COUNTRY'   AND Y.ACTIVE_FLAG           = 'Y'  ");
		sb.append(
				" AND X.TYP_TYPE_ROWID        = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST' ) ");
		sb.append("  AND  LOWER(TRIM(Y.NAME))    = LOWER('" + Country_Name + "')) ,  ");
		sb.append(" CTRY_REGION_REL AS  ( SELECT DISTINCT  K1.ROWID_OBJECT REGION_MDM_ID,K1.NAME REGION_NAME, ");
		sb.append(" K3.CNTRY_CD FROM C_GDA_DFND_AREA K1,C_GDA_DFND_AREA_REL K2,CNTRY_CD_DETAILS K3  ");
		sb.append(
				" WHERE K2.TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY'  AND K2.GDA_DFND_AREA_PRNT_ROWID = K3.COUNTRY_MDM_ID ");
		sb.append(" AND K2.GDA_DFND_AREA_CHLD_ROWID = K1.ROWID_OBJECT ),  ");
		sb.append(
				" REGION_INFO AS  ( SELECT DISTINCT C_ALT_CODE.CODE, REGION.NAME REGION_NAME,REGION.ROWID_OBJECT REGION_MDM_ID ");
		sb.append(
				"  FROM C_ALT_CODE, C_TYP_TYPE,  C_GDA_DFND_AREA REGION  WHERE C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT  ");
		sb.append(
				" AND C_ALT_CODE.GDA_DFND_AREA_ROWID = REGION.ROWID_OBJECT AND C_TYP_TYPE.CODE IN ('ALT_CODE.ISO_TRTY')  AND C_ALT_CODE.HUB_STATE_IND = 1  ");
		sb.append("  AND REGION.ACTIVE_FLAG  = 'Y'  AND ( LOWER ( C_ALT_CODE.CODE )    = LOWER('" + Region_Code
				+ "'))  ");
		sb.append(" AND REGION.NAME IN  ( SELECT REGION_NAME FROM CTRY_REGION_REL)) ");
		sb.append(" SELECT DECODE ( REGION_MDM_ID,'ZZZZZZZ', 'Region Not Available',REGION_MDM_ID ) REGION_MDM_ID  ");
		sb.append(" FROM  ( SELECT DISTINCT REGION_MDM_ID, DENSE_RANK() OVER (ORDER BY REGION_MDM_ID) D_RANK FROM  ");
		sb.append(" ( SELECT REGION_MDM_ID FROM REGION_INFO WHERE REGION_MDM_ID <> 'XXX' AND ROWNUM  < 2 UNION ALL  ");
		sb.append(" SELECT 'ZZZZZZZ' FROM DUAL )) WHERE D_RANK = 1 ");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			log.info((Object) ("Region Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Region_Rowid = rs.getString("REGION_MDM_ID");
			}
			return Region_Rowid;
		} catch (Exception e) {
			log.info((Object) ("REGION_MDM_ID Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	public boolean sendFacilityCode(String facilityCode, String action) throws Exception {

		
		 //URL wsdlUrl = new URL("http://scrbipcdk001152:37801/FacilityManagement/FacilityService?wsdl");
		 
		
		  //URL wsdlUrl = new
		  //URL("http://scrbsmddk002094:7801/FacilityManagement/FacilityService");
		 
			//SIT
		
		  //URL wsdlUrl = new
		  //URL("http://scrbsmddk002094:7801/FacilityManagement/FacilityService?wsdl");
		  
		  //PROD
		
		URL wsdlUrl = new URL("http://smdsws.apmoller.net/FacilityManagement/FacilityService?wsdl");
		
		
		//SIT
		
		 //URL wsdlUrl = new URL("http://scrbsmddk002094:7801/FacilityManagement/FacilityService?wsdl");
		 	
		//Pre-Prod
		
		//URL wsdlUrl = new URL("http://smdswspp.apmoller.net/FacilityManagement/FacilityService?wsdl");
				
		  
		/*
		 * URL wsdlUrl = new
		 * URL("http://scrbsmddkc02150:7801/FacilityManagement/FacilityService?wsdl");
		 */ 
		//URL wsdlUrl = new URL("http://scrbsmddk002093:7801/FacilityManagement/FacilityService?wsdl");
		
		
		 //URL wsdlUrl = new
		 //URL("http://scrbsmddk002093:7801/FacilityManagement/FacilityService?wsdl");
		

		log.info(wsdlUrl);
		PublishFacilityCodeResponse response = null;
		PublishFacilityCodeRequest request = new PublishFacilityCodeRequest();
		FacilityIDsType type = new FacilityIDsType();
		type.setFacilityGEOId(facilityCode);
		request.setFacilityCode(type);
		if (action.equalsIgnoreCase("CREATE")) {
			request.setAction(UpsertEnum.CREATE);
		} else {
			request.setAction(UpsertEnum.UPDATE);
		}
		QName qname = new QName("http://services.apmoller.net/cmd/definitions", "FacilityService");
		Service service = Service.create(wsdlUrl, qname);
		UpsertFacilityService upsertFacilityService = (UpsertFacilityService) service
				.getPort(UpsertFacilityService.class);
		response = upsertFacilityService.publishFacilityCode(request);
		System.out.println("statusResponseType Code: " + response.getStatus().getCode());
		System.out.println("statusResponseType Message: " + response.getStatus().getStatus());
		return true;
	}

	/*
	 * public Boolean publishSDN(String GEOID,String Fct_Rowid,String FCT_Name)
	 * throws NamingException, JMSException, JAXBException { String message = null;
	 * JAXBContext jaxbContext = JAXBContext.newInstance(VerifyWTCS.class);
	 * Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 * jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 * log.info("facility Header --" + facility.getSdnHeader()); Properties
	 * environmentProperties = new Properties(); // LOGGER.info("Customer Header --"
	 * + // customer.getSdnHeader().getUser()); // LOGGER.info("Customer Header --"
	 * + customer.getSdnHeader()); log.info("facility Header --" +
	 * facility.getSdnHeader().getSourceSystem()); StringWriter stringwriter = new
	 * StringWriter(); jaxbMarshaller.marshal(facility, stringwriter); message =
	 * stringwriter.toString(); VerifyWTCS facility =new VerifyWTCS();
	 * WTCSInformationType facility_type=new WTCSInformationType(); AddressType
	 * Addr_Type=new AddressType(); Addr_Type.setHouseNo(House_num);
	 * Addr_Type.setStreetName(Street_Nm); Addr_Type.setBuilding(Addr_Ln_2);
	 * Addr_Type.setSuburb(Addr_Ln_3); Addr_Type.setCity(City_rowid);
	 * Addr_Type.setRegion(Rgn_rowid) Addr_Type.setISOCountryCode()
	 * Addr_Type.setPostalCode Addr_Type.setTaxJurisdictionCode
	 * facility_type.setEntityName(FCT_Name); facility_type.setAddress(Addr_Type);
	 * facility.setSdnHeader("FCT"); facility.setTechnicalIdentifier(Fct_Rowid);
	 * facility.setCode(GEOID); facility.setWTCSInformation(facility_type);
	 * 
	 * 
	 * 
	 * QueueConnectionFactory qconFactory = null; QueueConnection qcon = null;
	 * QueueSession qsession = null; QueueSender qsender = null; Queue queue = null;
	 * TextMessage msg; try { Hashtable env = new Hashtable();
	 * env.put("java.naming.factory.initial",
	 * environmentProperties.getProperty("java.naming.factory.initial"));
	 * env.put(Context.PROVIDER_URL,constant_remote_provider_url));
	 * env.put(Context.SECURITY_PRINCIPAL,
	 * constant_java_naming_security_principal)); //$NON-NLS-1$
	 * env.put(Context.SECURITY_CREDENTIALS,constant_naming_security_credentials"));
	 * Context initialcontext = null; try { initialcontext = new
	 * InitialContext(env); qconFactory = (QueueConnectionFactory)
	 * initialcontext.lookup(environmentProperties.getProperty(
	 * "SDN_CONNECTION_FACTORY")); qcon = qconFactory.createQueueConnection();
	 * qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); queue =
	 * (Queue) initialcontext.lookup(CONSTANT_QUEUE_SDN_QUEUE_NAME)); qsender =
	 * qsession.createSender(queue); msg = qsession.createTextMessage();
	 * qcon.start(); msg.setText(message); qsender.send(msg); } finally { if
	 * (initialcontext != null) { initialcontext.close(); } }
	 * 
	 * } catch (NamingException | JMSException e) {
	 * LOGGER.fatal(e.getLocalizedMessage()); throw e; } finally { if (null !=
	 * qsender) { qsender.close(); } if (null != qsession) { qsession.close(); } if
	 * (null != qcon) { qcon.close(); } }
	 * 
	 * return Boolean.TRUE; }
	 * 
	 */
	private void loadPropertyFile() {
	    InputStream is = null;
	    try {
	      this.properties = new Properties();
	      is = this.getClass().getResourceAsStream("/NAP.properties");
	      properties.load(is);
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	  }
	/******************    184026: ALTCODE STATIC LIST  START ***********************/
	private void GenerateNotAllowedList() {
		    log.info("Inside GenerateList Started");	       
	        collection.add("ALT_CODE.RKST");  
	        //collection.add("ALT_CODE.RKTS");  
	       // collection.add("ALT_CODE.BIC");  
	       // collection.add("ALT_CODE.SMDG");  
	      //  collection.add("ALT_CODE.UN_CODE"); 
	      //  collection.add("ALT_CODE.UN_CODE1"); 
	      //  collection.add("ALT_CODE.UN_CODE2");
	      //  collection.add("ALT_CODE.Schedule_K"); 
	      //  collection.add("ALT_CODE.Schedule_D"); 
	        collection.add("ALT_CODE.GEOID"); 
	      //  collection.add("ALT_CODE.CUSTOMSLOC"); 	        
	        log.info("Inside GenerateList ended");	 
	    } 
	/******************    184026: ALTCODE STATIC LIST  END ***********************/
	/******************    184026: Get Alt code type name  start 
	 * @param altcode ***********************/
	private String getAltCodetypeName(String AltCodeRowid , String mdmORSid, BDDObject altcode ) {
			String getTypeCdQuery=Utils.getDatabaseLookupValue("C_TYP_TYPE", AltCodeRowid, "CODE", "ROWID_OBJECT");
			String typCdValue=Utils.getValueFromDB1(mdmORSid, getTypeCdQuery);
			log.info("typCdValue: " + typCdValue);
			if(typCdValue!=null) {
				if(typCdValue.trim().equalsIgnoreCase("ALT_CODE.IATA")) {
					log.info("Code is IATA code.. so add in List");
					if(!altcode.isRemoved()) {
					Object iataCodeValue=altcode.getValue("C_FCT_ALT_CODES|CODE");
						if(iataCodeValue!=null)
							{
								log.info("IATA Code is ::"+iataCodeValue.toString());
								iataCodesValues.add(iataCodeValue.toString());
							}
				}
				}
			}
			log.info("IATA Codes list size in getAltCodetypeName method ::"+iataCodesValues);
			return typCdValue;
}
	/******************    184026: Get Alt code type name  ends ***********************/
	
}
