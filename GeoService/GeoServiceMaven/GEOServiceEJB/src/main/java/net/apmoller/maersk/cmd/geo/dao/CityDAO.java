package net.apmoller.maersk.cmd.geo.dao;

import com.siperian.sif.client.EjbSiperianClient;
import com.siperian.sif.client.SiperianClient;
import com.siperian.sif.message.Field;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.RecordKey;
import com.siperian.sif.message.mrm.CleansePutRequest;
import com.siperian.sif.message.mrm.CleansePutResponse;
import com.siperian.sif.message.mrm.CleanseRequest;
import com.siperian.sif.message.mrm.CleanseResponse;
import com.siperian.sif.message.mrm.DeleteRequest;
import com.siperian.sif.message.mrm.DeleteResponse;
import com.siperian.sif.message.mrm.PutRequest;
import com.siperian.sif.message.mrm.PutResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

import net.apmoller.maersk.cmd.geo.bo.CityBO;
import net.apmoller.maersk.cmd.geo.util.GEOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import weblogic.utils.collections.DelegatingSet;

import javax.transaction.UserTransaction;


public class CityDAO
		extends GEOUtils
{
	Connection connection;
	SiperianClient siperianClient;
	private static final String SRC_PKEY_OBJECT = "SRC_PKEY_OBJECT";
	private static final Logger logger = LogManager.getLogger(CityDAO.class);

	public Map<String, String> upsertGEOData(CityBO data) throws FileNotFoundException {
		logger.info(" UpsertGEOData DAO Starts ");

		Map<String, String> responseMap = new LinkedHashMap();
		Map<String, CleansePutRequest> requestMap = new LinkedHashMap();

		PutRequest defdareaCityrequest = new PutRequest();
		PutRequest defdarearequest = new PutRequest();
		PutRequest defdarearelrequest = new PutRequest();
		PutRequest altcoderequestrkts = new PutRequest();
		PutRequest altcoderequestrkst = new PutRequest();
		PutRequest altcoderequestmodel = new PutRequest();
		PutRequest altnamerequestrkts = new PutRequest();
		PutRequest altnamerequestrkst = new PutRequest();
		PutRequest altnamerequestmodel = new PutRequest();
		PutRequest altcoderequestGeo = new PutRequest();
		PutRequest wrkaroundreasrequest = new PutRequest();

		String GDARowid = "";

		try
		{
			String interactionid = getInteractionId(getConnection());

			logger.info(" UpsertGEOData interactionid : " + interactionid);
			PutResponse defdarearelresponse;
			if ("BDA".equalsIgnoreCase(data.getBda()))
			{
				if (("1".equals(data.getBda_hub_ind())) && ("".equals(data.getRelBdaRowid())))
				{
					String relfromDate = "";
					String reltoDate = "";
					try
					{
						SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date frdt = fr.parse(data.getCity_rel_valid_from());

						SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						relfromDate = fr1.format(frdt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing From Date" + data.getCity_rel_valid_from(), exp);
						relfromDate = getDatabaseDate(getConnection()).toString();
					}
					try
					{
						SimpleDateFormat to = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date todt = to.parse(data.getCity_rel_valid_thru());

						SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						reltoDate = to1.format(todt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing To Date" + data.getCity_rel_valid_thru(), exp);
						reltoDate = "31-12-9999 12:59:59";
					}
					Record recorddfndArearel = new Record();

					recorddfndArearel.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");

					recorddfndArearel.setField(new Field("HUB_STATE_IND", "1"));
					recorddfndArearel.setField(new Field("TYP_TYPE_CD", data.getType()));
					recorddfndArearel.setField(new Field("GDA_DFND_AREA_PRNT_ROWID", data.getParentRowid()));
					recorddfndArearel.setField(new Field("GDA_DFND_AREA_CHLD_ROWID", data.getGDARowid()));
					recorddfndArearel.setField(new Field("OPN_IND", "I"));
					recorddfndArearel.setField(new Field("VALID_FROM_DT", relfromDate));
					recorddfndArearel.setField(new Field("VALID_THRU_DT", reltoDate));

					RecordKey keydfarearel = new RecordKey();

					keydfarearel.setSystemName("MULTIUPDATE");

					defdarearelrequest.setGenerateSourceKey(true);
					defdarearelrequest.setRecordKey(keydfarearel);

					defdarearelrequest.setRecord(recorddfndArearel);
					defdarearelrequest.setInteractionId(interactionid);
					PutResponse to1 = saveOrUpdate(defdarearelrequest, getSiperianClient());


				}
				else
				{

					if (("1".equalsIgnoreCase(data.getBda_hub_ind())) && (!"".equals(data.getRelBdaRowid())))
					{
						String relfromDate = "";
						String reltoDate = "";
						try
						{
							SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date frdt = fr.parse(data.getCity_rel_valid_from());

							SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							relfromDate = fr1.format(frdt);
						}
						catch (Exception exp)
						{
							logger.error("Error Parsing From Date" + data.getCity_rel_valid_from(), exp);
							relfromDate = getDatabaseDate(getConnection()).toString();
						}
						try
						{
							SimpleDateFormat to = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date todt = to.parse(data.getCity_rel_valid_thru());

							SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							reltoDate = to1.format(todt);
						}
						catch (Exception exp)
						{
							logger.error("Error Parsing To Date" + data.getCity_rel_valid_thru(), exp);
							reltoDate = "31-12-9999 12:59:59";
						}
						Record recorddfndArearel = new Record();

						recorddfndArearel.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");

						recorddfndArearel.setField(new Field("HUB_STATE_IND", "1"));
						recorddfndArearel.setField(new Field("TYP_TYPE_CD", data.getType()));
						recorddfndArearel.setField(new Field("GDA_DFND_AREA_PRNT_ROWID", data.getParentRowid()));
						recorddfndArearel.setField(new Field("GDA_DFND_AREA_CHLD_ROWID", data.getGDARowid()));
						recorddfndArearel.setField(new Field("OPN_IND", "U"));
						recorddfndArearel.setField(new Field("VALID_FROM_DT", relfromDate));
						recorddfndArearel.setField(new Field("VALID_THRU_DT", reltoDate));

						RecordKey keydfarearel = new RecordKey();

						keydfarearel.setSystemName("MULTIUPDATE");

						keydfarearel.setRowid(data.getRelBdaRowid());
						defdarearelrequest.setGenerateSourceKey(true);
						defdarearelrequest.setRecordKey(keydfarearel);

						defdarearelrequest.setRecord(recorddfndArearel);
						defdarearelrequest.setInteractionId(interactionid);
						PutResponse to1 = saveOrUpdate(defdarearelrequest, getSiperianClient());

						GDARowid = data.getGDARowid().trim();
					}
					else if ("-1".equalsIgnoreCase(data.getBda_hub_ind()))
					{
						Record delrecorddfndArearel = new Record();
						DeleteRequest deldefdarearelrequest = new DeleteRequest();

						RecordKey keyreldel = new RecordKey();
						keyreldel.setSystemName("MULTIUPDATE");
						keyreldel.setRowid(data.getRelBdaRowid());

						ArrayList<RecordKey> recordkeys = new ArrayList();
						recordkeys.add(keyreldel);

						deldefdarearelrequest.setRecordKeys(recordkeys);
						deldefdarearelrequest.setInteractionId(interactionid);
						deldefdarearelrequest.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");

						DeleteResponse to1 = saveOrUpdate(deldefdarearelrequest, getSiperianClient());

					}
				}

				GDARowid = data.getGDARowid().trim();
			}

			else
			{
				if ("I".equals(data.getOperation()))
				{
					logger.info(" getCity_Valid_from " + data.getCity_Valid_from());
					logger.info(" getCity_Valid_thru " + data.getCity_Valid_thru());
					logger.info(" getCity_rel_valid_from " + data.getCity_rel_valid_from());
					logger.info(" getCity_rel_valid_thru " + data.getCity_rel_valid_thru());

					logger.info(data.toString());
					String dfareafromDate = "";
					String dfareatoDate = "";
					try
					{
						SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date frdt = fr.parse(data.getCity_Valid_from());

						SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						dfareafromDate = fr1.format(frdt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing From Date" + data.getCity_Valid_from(), exp);
						dfareafromDate = getDatabaseDate(getConnection()).toString();
					}
					try
					{
						SimpleDateFormat to = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date todt = to.parse(data.getCity_Valid_thru());

						SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						dfareatoDate = to1.format(todt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing To Date" + data.getCity_Valid_thru(), exp);
						dfareatoDate = "31-12-9999 12:59:59";
					}
					String relfromDate = "";
					String reltoDate = "";
					try
					{
						SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date frdt = fr.parse(data.getCity_rel_valid_from());

						SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						relfromDate = fr1.format(frdt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing From Date" + data.getCity_rel_valid_from(), exp);
						relfromDate = getDatabaseDate(getConnection()).toString();
					}
					try
					{
						SimpleDateFormat to = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date todt = to.parse(data.getCity_rel_valid_thru());

						SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						reltoDate = to1.format(todt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing To Date" + data.getCity_rel_valid_thru(), exp);
						reltoDate = "31-12-9999 12:59:59";
					}
					String geoid = getGEOID();

					Record recorddfndArea = new Record();

					logger.info(" Name " + data.getCityname());
					logger.info(" TYP_TYPE_CD " + data.getType());
					logger.info(" dfareafromDate " + dfareafromDate);
					logger.info(" dfareatoDate " + dfareatoDate);

					recorddfndArea.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA");
					recorddfndArea.setField(new Field("HUB_STATE_IND", "1"));
					recorddfndArea.setField(new Field("NAME", data.getCityname()));
					recorddfndArea.setField(new Field("DESCRIPTION", data.getDescription()));
					recorddfndArea.setField(new Field("TYP_TYPE_CD", "GDA.CITY"));
					recorddfndArea.setField(new Field("VALID_FROM_DT", dfareafromDate));
					recorddfndArea.setField(new Field("VALID_THRU_DT", dfareatoDate));
					recorddfndArea.setField(new Field("ACTIVE_FLAG", "Y"));
					recorddfndArea.setField(new Field("OPN_IND", "I"));
					recorddfndArea.setField(new Field("TDS_TMZ_ROWID", data.getTimezone()));
					recorddfndArea.setField(new Field("TDS_DST_ROWID", data.getDaylightsaving()));
					recorddfndArea.setField(new Field("LAT_GEOSPTL", data.getLatitude()));
					recorddfndArea.setField(new Field("LNG_GEOSPTL", data.getLongitude()));

					RecordKey keydfarea = new RecordKey();

					keydfarea.setSystemName("MULTIUPDATE");
					keydfarea.setSourceKey(geoid);
					defdarearequest.setGenerateSourceKey(true);
					defdarearequest.setRecordKey(keydfarea);

					defdarearequest.setRecord(recorddfndArea);
					defdarearequest.setInteractionId(interactionid);

					//EjbSiperianClient sifClient = (EjbSiperianClient)getSiperianClient();
					//EjbSiperianClient sifClient = (EjbSiperianClient)this.siperianClient;
					//UserTransaction tx = ((EjbSiperianClient)sifClient).createTX(60);
					//tx.begin();
					PutResponse defdarearesponse = saveOrUpdate(defdarearequest, getSiperianClient());
					//tx.commit();


					Record recorddfndCity = new Record();

					recorddfndCity.setSiperianObjectUid("BASE_OBJECT.C_GDA_CITY");
					recorddfndCity.setField(new Field("HUB_STATE_IND", "1"));
					recorddfndCity.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
					recorddfndCity.setField(new Field("PORT_FLAG", data.getPortExists()));
					recorddfndCity.setField(new Field("OLSON_TZ", data.getOztimezone()));
					recorddfndCity.setField(new Field("MAERSK_CITY", data.getmaerskcityind()));


					RecordKey keyGdaCity = new RecordKey();

					keyGdaCity.setSystemName("MULTIUPDATE");
					keyGdaCity.setSourceKey(geoid);
					defdareaCityrequest.setGenerateSourceKey(true);
					defdareaCityrequest.setRecordKey(keyGdaCity);

					defdareaCityrequest.setRecord(recorddfndCity);
					defdareaCityrequest.setInteractionId(interactionid);

					PutResponse gdacityresponse = saveOrUpdate(defdareaCityrequest, getSiperianClient());

					Record recorddfndArearel = new Record();

					recorddfndArearel.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");

					recorddfndArearel.setField(new Field("HUB_STATE_IND", "1"));
					recorddfndArearel.setField(new Field("TYP_TYPE_CD", data.getType()));
					recorddfndArearel.setField(new Field("GDA_DFND_AREA_PRNT_ROWID", data.getParentRowid()));
					recorddfndArearel.setField(new Field("GDA_DFND_AREA_CHLD_ROWID", defdarearesponse.getRecordKey().getRowid()));
					recorddfndArearel.setField(new Field("OPN_IND", "I"));
					recorddfndArearel.setField(new Field("VALID_FROM_DT", relfromDate));
					recorddfndArearel.setField(new Field("VALID_THRU_DT", reltoDate));

					RecordKey keydfarearel = new RecordKey();

					keydfarearel.setSystemName("MULTIUPDATE");
					keydfarearel.setSourceKey(geoid);
					defdarearelrequest.setGenerateSourceKey(true);
					defdarearelrequest.setRecordKey(keydfarearel);

					defdarearelrequest.setRecord(recorddfndArearel);
					defdarearelrequest.setInteractionId(interactionid);
					defdarearelresponse = saveOrUpdate(defdarearelrequest, getSiperianClient());

					logger.info(" data.getWorkaround_type() - " + data.getWorkaround_type());

					Record recordworkaround = new Record();

					recordworkaround.setSiperianObjectUid("BASE_OBJECT.C_TMP_WRKRND_RSN");

					recordworkaround.setField(new Field("HUB_STATE_IND", "1"));
					recordworkaround.setField(new Field("TYP_TYPE_ROWID", getWrkArndTypTypeRowID(getConnection(), data.getWorkaround_type())));
					recordworkaround.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));

					RecordKey keyworkreason = new RecordKey();

					keyworkreason.setSystemName("MULTIUPDATE");
					keyworkreason.setSourceKey(geoid);
					wrkaroundreasrequest.setGenerateSourceKey(true);
					wrkaroundreasrequest.setRecordKey(keyworkreason);

					wrkaroundreasrequest.setRecord(recordworkaround);
					wrkaroundreasrequest.setInteractionId(interactionid);
					PutResponse wrkaroundreqresponse = saveOrUpdate(wrkaroundreasrequest, getSiperianClient());

					Record recordaltcodeGeo = new Record();

					recordaltcodeGeo.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
					recordaltcodeGeo.setField(new Field("HUB_STATE_IND", "1"));
					recordaltcodeGeo.setField(new Field("TYP_TYPE_ROWID", getTypTypeid(getConnection())));
					recordaltcodeGeo.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
					recordaltcodeGeo.setField(new Field("CODE", geoid));
					recordaltcodeGeo.setField(new Field("OPN_IND", "I"));

					RecordKey keyaltcodeGeo = new RecordKey();
					keyaltcodeGeo.setSystemName("GEO");
					keyaltcodeGeo.setSourceKey(geoid);
					altcoderequestGeo.setGenerateSourceKey(true);
					altcoderequestGeo.setRecordKey(keyaltcodeGeo);

					altcoderequestGeo.setRecord(recordaltcodeGeo);
					altcoderequestGeo.setInteractionId(interactionid);

					PutResponse altcoderesponseGeo = saveOrUpdate(altcoderequestGeo, getSiperianClient());

					Record recordaltcoderkst = new Record();

					recordaltcoderkst.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
					recordaltcoderkst.setField(new Field("HUB_STATE_IND", "1"));
					recordaltcoderkst.setField(new Field("TYP_TYPE_ROWID", getTypTypeRowID(getConnection(), "ALT_CODE.RKST", "RKST")));
					recordaltcoderkst.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
					recordaltcoderkst.setField(new Field("CODE", data.getAltCode_rkst()));
					recordaltcoderkst.setField(new Field("OPN_IND", "I"));

					RecordKey keyaltcoderkst = new RecordKey();
					keyaltcoderkst.setSystemName("MULTIUPDATE");

					altcoderequestrkst.setGenerateSourceKey(true);
					altcoderequestrkst.setRecordKey(keyaltcoderkst);

					altcoderequestrkst.setRecord(recordaltcoderkst);
					altcoderequestrkst.setInteractionId(interactionid);

					PutResponse altcoderesponserkst = saveOrUpdate(altcoderequestrkst, getSiperianClient());

					logger.info(" altcoderesponserkst - " + altcoderesponserkst.getRecordKey().getRowid());

					keyaltcoderkst = null;
					recordaltcoderkst = null;
					altcoderequestrkst = null;
					altcoderesponserkst = null;

					if(data.getAltCode_rkts()!=null && !data.getAltCode_rkts().equalsIgnoreCase("")) {
						Record recordaltcoderkts = new Record();


						recordaltcoderkts.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
						recordaltcoderkts.setField(new Field("HUB_STATE_IND", "1"));
						recordaltcoderkts.setField(new Field("TYP_TYPE_ROWID", getTypTypeRowID(getConnection(), "ALT_CODE.RKTS", "RKTS")));
						recordaltcoderkts.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
						recordaltcoderkts.setField(new Field("CODE", data.getAltCode_rkts()));
						recordaltcoderkts.setField(new Field("OPN_IND", "I"));

						RecordKey keyaltcoderkts = new RecordKey();
						keyaltcoderkts.setSystemName("MULTIUPDATE");

						altcoderequestrkts.setGenerateSourceKey(true);
						altcoderequestrkts.setRecordKey(keyaltcoderkts);

						altcoderequestrkts.setRecord(recordaltcoderkts);
						altcoderequestrkts.setInteractionId(interactionid);

						PutResponse altcoderesponserkts = saveOrUpdate(altcoderequestrkts, getSiperianClient());

						logger.info(" altcoderesponserkts - " + altcoderesponserkts.getRecordKey().getRowid());

						keyaltcoderkts = null;
						recordaltcoderkts = null;
						altcoderesponserkts = null;
						altcoderequestrkts = null;
					}
					Record recordaltcodemodel = new Record();

					recordaltcodemodel.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
					recordaltcodemodel.setField(new Field("HUB_STATE_IND", "1"));
					recordaltcodemodel.setField(new Field("TYP_TYPE_ROWID", getTypTypeRowID(getConnection(), "ALT_CODE.MODEL", "MODEL")));
					recordaltcodemodel.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
					recordaltcodemodel.setField(new Field("CODE", data.getAltCode_model()));
					recordaltcodemodel.setField(new Field("OPN_IND", "I"));

					RecordKey keyaltcodemodel = new RecordKey();
					keyaltcodemodel.setSystemName("MULTIUPDATE");

					altcoderequestmodel.setGenerateSourceKey(true);
					altcoderequestmodel.setRecordKey(keyaltcodemodel);

					altcoderequestmodel.setRecord(recordaltcodemodel);
					altcoderequestmodel.setInteractionId(interactionid);

					PutResponse altcoderesponsemodel = saveOrUpdate(altcoderequestmodel, getSiperianClient());

					logger.info(" altcoderesponsemodel - " + altcoderesponsemodel.getRecordKey().getRowid());

					keyaltcodemodel = null;
					recordaltcodemodel = null;
					altcoderesponsemodel = null;
					altcoderequestmodel = null;
					PutResponse localPutResponse1;
					if ((data.getAltName_rkst() != null) && (!"".equals(data.getAltName_rkst())))
					{
						Record recordaltnamerkst = new Record();

						recordaltnamerkst.setSiperianObjectUid("BASE_OBJECT.C_ALT_NAME");
						recordaltnamerkst.setField(new Field("HUB_STATE_IND", "1"));

						recordaltnamerkst.setField(new Field("ACTIVE_FLAG", "Y"));
						recordaltnamerkst.setField(new Field("TYP_TYPE_CD", "ALT_NAME.GDA_NAME"));
						recordaltnamerkst.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
						recordaltnamerkst.setField(new Field("NAME", data.getAltName_rkst()));
						recordaltnamerkst.setField(new Field("DESCRIPTION", data.getAltDesc_rkst()));

						RecordKey keyaltnamerkst = new RecordKey();
						keyaltnamerkst.setSystemName("MULTIUPDATE");

						altnamerequestrkst.setGenerateSourceKey(true);
						altnamerequestrkst.setRecordKey(keyaltnamerkst);

						altnamerequestrkst.setRecord(recordaltnamerkst);
						altnamerequestrkst.setInteractionId(interactionid);

						localPutResponse1 = saveOrUpdate(altnamerequestrkst, getSiperianClient());
					}
					if ((data.getAltName_rkts() != null) && (!"".equals(data.getAltName_rkts())))
					{
						Record recordaltnamerkts = new Record();

						recordaltnamerkts.setSiperianObjectUid("BASE_OBJECT.C_ALT_NAME");
						recordaltnamerkts.setField(new Field("HUB_STATE_IND", "1"));

						recordaltnamerkts.setField(new Field("ACTIVE_FLAG", "Y"));
						recordaltnamerkts.setField(new Field("TYP_TYPE_CD", "ALT_NAME.GDA_NAME"));
						recordaltnamerkts.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
						recordaltnamerkts.setField(new Field("NAME", data.getAltName_rkts()));
						recordaltnamerkts.setField(new Field("DESCRIPTION", data.getAltDesc_rkts()));

						RecordKey keyaltnamerkts = new RecordKey();
						keyaltnamerkts.setSystemName("MULTIUPDATE");

						altnamerequestrkts.setGenerateSourceKey(true);
						altnamerequestrkts.setRecordKey(keyaltnamerkts);

						altnamerequestrkts.setRecord(recordaltnamerkts);
						altnamerequestrkts.setInteractionId(interactionid);

						localPutResponse1 = saveOrUpdate(altnamerequestrkts, getSiperianClient());
					}
					if ((data.getAltName_model() != null) && (!"".equals(data.getAltName_model())))
					{
						Record recordaltnamemodel = new Record();

						recordaltnamemodel.setSiperianObjectUid("BASE_OBJECT.C_ALT_NAME");
						recordaltnamemodel.setField(new Field("HUB_STATE_IND", "1"));

						recordaltnamemodel.setField(new Field("ACTIVE_FLAG", "Y"));
						recordaltnamemodel.setField(new Field("TYP_TYPE_CD", "ALT_NAME.GDA_NAME"));
						recordaltnamemodel.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));
						recordaltnamemodel.setField(new Field("NAME", data.getAltName_model()));
						recordaltnamemodel.setField(new Field("DESCRIPTION", data.getAltDesc_model()));

						RecordKey keyaltnamemodel = new RecordKey();
						keyaltnamemodel.setSystemName("MULTIUPDATE");

						altnamerequestmodel.setGenerateSourceKey(true);
						altnamerequestmodel.setRecordKey(keyaltnamemodel);

						altnamerequestmodel.setRecord(recordaltnamemodel);
						altnamerequestmodel.setInteractionId(interactionid);

						localPutResponse1 = saveOrUpdate(altnamerequestmodel, getSiperianClient());
					}

					GDARowid = defdarearesponse.getRecordKey().getRowid().trim();

				}
				else
				{
					String dfareafromDate = "";
					String dfareatoDate = "";
					try
					{
						SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date frdt = fr.parse(data.getCity_Valid_from());

						SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						dfareafromDate = fr1.format(frdt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing From Date" + data.getCity_Valid_from(), exp);
						dfareafromDate = getDatabaseDate(getConnection()).toString();
					}
					try
					{
						SimpleDateFormat to = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date todt = to.parse(data.getCity_Valid_thru());

						SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						dfareatoDate = to1.format(todt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing To Date" + data.getCity_Valid_thru(), exp);
						dfareatoDate = "31-12-9999 12:59:59";
					}
					String relfromDate = "";
					String reltoDate = "";
					try
					{
						SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date frdt = fr.parse(data.getCity_Valid_from());

						SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						relfromDate = fr1.format(frdt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing From Date" + data.getCity_Valid_from(), exp);
						relfromDate = getDatabaseDate(getConnection()).toString();
					}
					try
					{
						SimpleDateFormat to = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date todt = to.parse(data.getCity_Valid_thru());

						SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						reltoDate = to1.format(todt);
					}
					catch (Exception exp)
					{
						logger.error("Error Parsing To Date" + data.getCity_Valid_thru(), exp);
						reltoDate = "31-12-9999 12:59:59";
					}
					Record recorddfndArea = new Record();

					logger.info(" Name " + data.getCityname());
					logger.info(" TYP_TYPE_CD " + data.getType());
					logger.info(" dfareafromDate " + dfareafromDate);
					logger.info(" dfareatoDate " + dfareatoDate);

					recorddfndArea.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA");
					recorddfndArea.setField(new Field("HUB_STATE_IND", "1"));
					recorddfndArea.setField(new Field("NAME", data.getCityname()));
					recorddfndArea.setField(new Field("DESCRIPTION", data.getDescription()));
					recorddfndArea.setField(new Field("TYP_TYPE_CD", "GDA.CITY"));
					recorddfndArea.setField(new Field("VALID_FROM_DT", dfareafromDate));
					recorddfndArea.setField(new Field("VALID_THRU_DT", dfareatoDate));
					recorddfndArea.setField(new Field("ACTIVE_FLAG", data.getStatus()));
					recorddfndArea.setField(new Field("OPN_IND", "U"));
					recorddfndArea.setField(new Field("TDS_TMZ_ROWID", data.getTimezone()));
					recorddfndArea.setField(new Field("TDS_DST_ROWID", data.getDaylightsaving()));
					recorddfndArea.setField(new Field("LAT_GEOSPTL", data.getLatitude()));
					recorddfndArea.setField(new Field("LNG_GEOSPTL", data.getLongitude()));

					RecordKey keydfarea = new RecordKey();

					keydfarea.setSystemName("MULTIUPDATE");

					keydfarea.setRowid(data.getGDARowid());
					defdarearequest.setGenerateSourceKey(true);
					defdarearequest.setRecordKey(keydfarea);

					defdarearequest.setRecord(recorddfndArea);
					defdarearequest.setInteractionId(interactionid);

					PutResponse defdarearesponse = saveOrUpdate(defdarearequest, getSiperianClient());

					Record recorddfndCity = new Record();

					String cityRowid = getCityowID(this.connection, data.getGDARowid());

					recorddfndCity.setSiperianObjectUid("BASE_OBJECT.C_GDA_CITY");
					recorddfndCity.setField(new Field("HUB_STATE_IND", "1"));
					recorddfndCity.setField(new Field("GDA_DFND_AREA_ROWID", data.getGDARowid()));
					recorddfndCity.setField(new Field("PORT_FLAG", data.getPortExists()));
					recorddfndCity.setField(new Field("OLSON_TZ", data.getOztimezone()));

					RecordKey keyGdaCity = new RecordKey();

					keyGdaCity.setSystemName("MULTIUPDATE");

					keyGdaCity.setRowid(cityRowid);
					defdareaCityrequest.setGenerateSourceKey(true);
					defdareaCityrequest.setRecordKey(keyGdaCity);

					defdareaCityrequest.setRecord(recorddfndCity);
					defdareaCityrequest.setInteractionId(interactionid);

					PutResponse gdacityresponse = saveOrUpdate(defdareaCityrequest, getSiperianClient());
					if ((data.getWorkaround_type_rowid() != null) && (!"".equals(data.getWorkaround_type_rowid())))
					{
						Record recordworkaround = new Record();

						recordworkaround.setSiperianObjectUid("BASE_OBJECT.C_TMP_WRKRND_RSN");

						recordworkaround.setField(new Field("HUB_STATE_IND", "1"));
						recordworkaround.setField(new Field("TYP_TYPE_ROWID", getWrkArndTypTypeRowID(getConnection(), data.getWorkaround_type())));
						recordworkaround.setField(new Field("GDA_DFND_AREA_ROWID", data.getGDARowid()));

						RecordKey keyworkreason = new RecordKey();

						keyworkreason.setSystemName("MULTIUPDATE");

						keyworkreason.setRowid(data.getWorkaround_type_rowid());
						wrkaroundreasrequest.setGenerateSourceKey(true);
						wrkaroundreasrequest.setRecordKey(keyworkreason);

						wrkaroundreasrequest.setRecord(recordworkaround);
						wrkaroundreasrequest.setInteractionId(interactionid);
						defdarearelresponse = saveOrUpdate(wrkaroundreasrequest, getSiperianClient());
					}
					else
					{
						Record recordworkaround = new Record();

						recordworkaround.setSiperianObjectUid("BASE_OBJECT.C_TMP_WRKRND_RSN");

						recordworkaround.setField(new Field("HUB_STATE_IND", "1"));
						recordworkaround.setField(new Field("TYP_TYPE_ROWID", getWrkArndTypTypeRowID(getConnection(), data.getWorkaround_type())));
						recordworkaround.setField(new Field("GDA_DFND_AREA_ROWID", defdarearesponse.getRecordKey().getRowid()));

						RecordKey keyworkreason = new RecordKey();

						keyworkreason.setSystemName("MULTIUPDATE");

						wrkaroundreasrequest.setGenerateSourceKey(true);
						wrkaroundreasrequest.setRecordKey(keyworkreason);

						wrkaroundreasrequest.setRecord(recordworkaround);
						wrkaroundreasrequest.setInteractionId(interactionid);
						defdarearelresponse = saveOrUpdate(wrkaroundreasrequest, getSiperianClient());
					}
					Record recorddfndArearel = new Record();

					recorddfndArearel.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");

					recorddfndArearel.setField(new Field("HUB_STATE_IND", "1"));
					recorddfndArearel.setField(new Field("TYP_TYPE_CD", data.getType()));
					recorddfndArearel.setField(new Field("GDA_DFND_AREA_PRNT_ROWID", data.getParentRowid()));
					recorddfndArearel.setField(new Field("GDA_DFND_AREA_CHLD_ROWID", data.getGDARowid()));
					recorddfndArearel.setField(new Field("OPN_IND", "I"));
					recorddfndArearel.setField(new Field("VALID_FROM_DT", relfromDate));
					recorddfndArearel.setField(new Field("VALID_THRU_DT", reltoDate));


					RecordKey keydfarearel = new RecordKey();

					keydfarearel.setSystemName("MULTIUPDATE");

					keydfarearel.setRowid(data.getRelRowid());
					defdarearelrequest.setGenerateSourceKey(true);

					defdarearelrequest.setRecordKey(keydfarearel);

					defdarearelrequest.setRecord(recorddfndArearel);
					defdarearelrequest.setInteractionId(interactionid);
					defdarearelresponse = saveOrUpdate(defdarearelrequest, getSiperianClient());

					GDARowid = data.getGDARowid().trim();

				}



			}
			responseMap.put("STATUS", "SUCCESS");
			responseMap.put("INTERACTIONID", interactionid);

			String Action ="";

			if ("I".equals(data.getOperation())) {
				Action = "Create";
			}
			else {

				Action = "Update";
			}

			responseMap.put("ACTION", Action);
			responseMap.put("GDAROWID", GDARowid);


		}
		catch (Exception exception)
		{
			exception.printStackTrace(System.out);

			responseMap.put("STATUS", "FAILED");
			responseMap.put("INTERACTIONID", "");

			logger.error(" Error in UpsertGEOData ", exception);
		}

		logger.info(" UpsertGEOData DAO Ends ");
		return responseMap;
	}

	private String getGEOID()
	{
		EjbSiperianClient sipClient = (EjbSiperianClient)this.siperianClient;
		CleanseRequest request = new CleanseRequest();
		request.setCleanseFunctionName("Maersk Cleanse and Validation Library|GenerateGeoID");
		StringBuilder varname1 = new StringBuilder();
		varname1.append("SELECT * FROM ");
		varname1.append("C_ALT_CODE ");
		varname1.append("INNER JOIN C_TYP_TYPE  ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		varname1.append("WHERE C_TYP_TYPE.TYP_MSTR_TYPE_CD = 'ALT_CODE' ");
		varname1.append("AND   C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ");
		varname1.append("AND   C_ALT_CODE.CODE = ?");
		String inputSQL = varname1.toString();
		Record record = new Record();
		Field verifySQL = new Field();
		verifySQL.setName("verificationSql");
		verifySQL.setValue("inputSQL");
		record.setField(verifySQL);
		request.setRecord(record);
		CleanseResponse response = (CleanseResponse)sipClient.process(request);
		String geoId = response.getRecord().getField("generatedID").getStringValue();
		return geoId;
	}

	public Connection getConnection()
	{
		return this.connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public SiperianClient getSiperianClient()
	{
		return this.siperianClient;
	}

	public void setSiperianClient(SiperianClient siperianClient)
	{
		this.siperianClient = siperianClient;
	}

	public CleansePutResponse saveOrUpdate(CleansePutRequest request, SiperianClient client)
	{
		return (CleansePutResponse)client.process(request);
	}

	public PutResponse saveOrUpdate(PutRequest request, SiperianClient client)
	{
		return (PutResponse)client.process(request);
	}

	public DeleteResponse saveOrUpdate(DeleteRequest request, SiperianClient client)
	{
		return (DeleteResponse)client.process(request);
	}
}
