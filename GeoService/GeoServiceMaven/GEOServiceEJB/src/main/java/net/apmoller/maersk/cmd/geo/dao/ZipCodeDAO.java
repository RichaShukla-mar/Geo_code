package net.apmoller.maersk.cmd.geo.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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
import com.siperian.sif.message.mrm.GetRequest;
import com.siperian.sif.message.mrm.GetResponse;
import com.siperian.sif.message.mrm.PutRequest;
import com.siperian.sif.message.mrm.PutResponse;

import net.apmoller.maersk.cmd.geo.bo.GEOBO;
import net.apmoller.maersk.cmd.geo.bo.ZipCodeBO;
import net.apmoller.maersk.cmd.geo.util.GEOConstants;
import net.apmoller.maersk.cmd.geo.util.GEOUtils;
import net.apmoller.maersk.cmd.geo.util.PropertiesLoader;

/**
 * Session Bean implementation class ZipCodeDAO
 */

public class ZipCodeDAO extends GEOUtils {

    /**
     * @see GEODAO#GEODAO()
     */

    Connection connection;
    SiperianClient siperianClient;
    private static final String			SRC_PKEY_OBJECT			= "SRC_PKEY_OBJECT";
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ZipCodeDAO.class);

    public ZipCodeDAO() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Map<String, String> upsertGEOData(ZipCodeBO data){

        logger.info(" UpsertGEOData DAO Starts ");

        Map<String, String> responseMap = new LinkedHashMap<String, String>();
        Map<String, CleansePutRequest> requestMap = new LinkedHashMap<String, CleansePutRequest>();

        PutRequest defdarearequest = new PutRequest();
        PutRequest defdarearelrequest = new PutRequest();
        PutRequest altcoderequest = new PutRequest();
        PutRequest altcoderequestGeo = new PutRequest();
        try{

            String interactionid = getInteractionId(getConnection());

            logger.info(" UpsertGEOData interactionid : "+interactionid);


            if (GEOConstants.GEO_INSERT_OPERATION.equals(data.getOperation())){

                String fromDate = "";
                String toDate = "";

                try{


                    SimpleDateFormat fr = new SimpleDateFormat("dd-MMM-yy");
                    Date frdt = fr.parse(data.getValid_from());

                    SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    fromDate = fr1.format(frdt);


                }catch(Exception exp){
                    logger.error("Error Parsing From Date"+data.getValid_from(), exp);
                    fromDate = getDatabaseDate(getConnection()).toString();
                }

                try{


                    SimpleDateFormat to = new SimpleDateFormat("dd-MMM-yy");
                    Date todt = to.parse(data.getValid_thru());

                    SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    toDate = to1.format(todt);


                }catch(Exception exp){
                    logger.error("Error Parsing To Date"+data.getValid_thru(), exp);
                    toDate = "31-12-9999 12:59:59";
                }

                String geoid = getGEOID();

                Record recorddfndArea = new Record();

                recorddfndArea.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA");
                recorddfndArea.setField(new Field("HUB_STATE_IND", "1"));
                recorddfndArea.setField(new Field("NAME", data.getPostalname()));
                recorddfndArea.setField(new Field("DESCRIPTION", data.getDescription()));
                recorddfndArea.setField(new Field("TYP_TYPE_CD", "GDA.POSTAL_CODE"));
                recorddfndArea.setField(new Field("VALID_FROM_DT", fromDate));
                recorddfndArea.setField(new Field("VALID_THRU_DT", toDate));
                recorddfndArea.setField(new Field("ACTIVE_FLAG", "Y"));
                recorddfndArea.setField(new Field("OPN_IND", "I"));

                RecordKey keydfarea = new RecordKey();

                keydfarea.setSystemName("MULTIUPDATE");
                keydfarea.setSourceKey(geoid);
                defdarearequest.setGenerateSourceKey(true);
                defdarearequest.setRecordKey(keydfarea);

                defdarearequest.setRecord(recorddfndArea);
                defdarearequest.setInteractionId(interactionid);

                PutResponse defdarearesponse = saveOrUpdate(defdarearequest, getSiperianClient());


                Record recorddfndArearel = new Record();

                recorddfndArearel.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");

                recorddfndArearel.setField(new Field("HUB_STATE_IND", "1"));
                recorddfndArearel.setField(new Field("TYP_TYPE_CD", data.getRelType()));
                recorddfndArearel.setField(new Field("GDA_DFND_AREA_PRNT_ROWID", data.getParentRowid()));
                recorddfndArearel.setField(new Field("GDA_DFND_AREA_CHLD_ROWID", defdarearesponse.getRecordKey().getRowid()));
                recorddfndArearel.setField(new Field("OPN_IND", "I"));
                recorddfndArearel.setField(new Field("VALID_FROM_DT", fromDate));
                recorddfndArearel.setField(new Field("VALID_THRU_DT", toDate));


                RecordKey keydfarearel = new RecordKey();

                keydfarearel.setSystemName("MULTIUPDATE");
                keydfarearel.setSourceKey(geoid);
                defdarearelrequest.setGenerateSourceKey(true);
                defdarearelrequest.setRecordKey(keydfarearel);


                defdarearelrequest.setRecord(recorddfndArearel);
                defdarearelrequest.setInteractionId(interactionid);
                PutResponse defdarearelresponse = saveOrUpdate(defdarearelrequest, getSiperianClient());

                /*
                 * Code to insert GEOID Starts. GEOID insertion required for new postal code only
                 */

                Record recordaltcodeGeo = new Record();

                recordaltcodeGeo.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
                recordaltcodeGeo.setField(new Field("HUB_STATE_IND", "1"));
                recordaltcodeGeo.setField(new Field("TYP_TYPE_ROWID", getTypTypeid(getConnection())));
                recordaltcodeGeo.setField(new Field("GDA_DFND_AREA_ROWID",  defdarearesponse.getRecordKey().getRowid()));
                recordaltcodeGeo.setField(new Field("CODE",geoid));
                recordaltcodeGeo.setField(new Field("OPN_IND", "I"));

                RecordKey keyaltcodeGeo = new RecordKey();
                keyaltcodeGeo.setSystemName("GEO");
                keyaltcodeGeo.setSourceKey(geoid);
                altcoderequestGeo.setGenerateSourceKey(true);
                altcoderequestGeo.setRecordKey(keyaltcodeGeo);

                altcoderequestGeo.setRecord(recordaltcodeGeo);
                altcoderequestGeo.setInteractionId(interactionid);

                PutResponse altcoderesponseGeo = saveOrUpdate(altcoderequestGeo, getSiperianClient());

                /*
                 * Code to insert GEOID Ends
                 */


                Record recordaltcode = new Record();

                recordaltcode.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
                recordaltcode.setField(new Field("HUB_STATE_IND", "1"));
                recordaltcode.setField(new Field("TYP_TYPE_ROWID", getTypTypeRowID(getConnection())));
                recordaltcode.setField(new Field("GDA_DFND_AREA_ROWID",  defdarearesponse.getRecordKey().getRowid()));
                recordaltcode.setField(new Field("CODE",data.getPostalcode()));
                recordaltcode.setField(new Field("OPN_IND", "I"));

                RecordKey keyaltcode = new RecordKey();
                keyaltcode.setSystemName("MULTIUPDATE");
                keyaltcode.setSourceKey(geoid);
                altcoderequest.setGenerateSourceKey(true);
                altcoderequest.setRecordKey(keyaltcode);

                altcoderequest.setRecord(recordaltcode);
                altcoderequest.setInteractionId(interactionid);

                PutResponse altcoderesponse = saveOrUpdate(altcoderequest, getSiperianClient());



            } else {
    			/*
    			GetResponse gdaGetres = null;

    			String gdarowid = data.getGDARowid();

    			if (gdarowid != null && !"".equals(gdarowid.trim())){
        			GetRequest gdaGetReq = new GetRequest();
    				RecordKey key = new RecordKey();

    				key.setRowid(gdarowid);

    				gdaGetReq.setRecordKey(key);
    				gdaGetReq.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA");

    				gdaGetres = (GetResponse) siperianClient.process(gdaGetReq);

    				if (gdaGetres == null){
    					throw new Exception("GDA Defined Area Rowid_Object "+gdarowid +" not exists");
    				}

    			}else{

    				throw new Exception("GDA Defined Area Rowid_Object missing for update transation");

    			}

*/
                String fromDate = "";
                String toDate = "";

                Record recorddfndArea = new Record();

                recorddfndArea.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA");

                if(data.getPostalname() != null && !"".equals(data.getPostalname().trim())){
                    recorddfndArea.setField(new Field("NAME", data.getPostalname()));
                }

                if(data.getDescription() != null && !"".equals(data.getDescription().trim())){
                    recorddfndArea.setField(new Field("DESCRIPTION", data.getDescription()));
                }

                if(data.getValid_from() != null && !"".equals(data.getValid_from().trim())){

                    try{


                        SimpleDateFormat fr = new SimpleDateFormat("dd-MMM-yy");
                        Date frdt = fr.parse(data.getValid_from());

                        SimpleDateFormat fr1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        fromDate = fr1.format(frdt);
                        System.out.println("From Date "+fromDate);
                        logger.info("From Date "+fromDate);

                    }catch(Exception exp){
                        logger.error("Error Parsing From Date"+data.getValid_from(), exp);
                        exp.printStackTrace(System.out);
                        fromDate = getDatabaseDate(getConnection()).toString();
                    }

                    recorddfndArea.setField(new Field("VALID_FROM_DT", fromDate));

                }

                if(data.getValid_thru() != null && !"".equals(data.getValid_thru().trim())){

                    try{


                        SimpleDateFormat to = new SimpleDateFormat("dd-MMM-yy");
                        Date todt = to.parse(data.getValid_thru());

                        SimpleDateFormat to1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        toDate = to1.format(todt);

                        System.out.println("To Date "+toDate);
                        logger.info("To Date "+toDate);

                    }catch(Exception exp){
                        exp.printStackTrace(System.out);
                        logger.error("Error Parsing To Date"+data.getValid_thru(), exp);
                        toDate = "31-12-9999 12:59:59";
                    }

                    recorddfndArea.setField(new Field("VALID_THRU_DT", toDate));

                }

                recorddfndArea.setField(new Field("ACTIVE_FLAG", "Y"));
                recorddfndArea.setField(new Field("TYP_TYPE_CD", "GDA.POSTAL_CODE"));
                recorddfndArea.setField(new Field("OPN_IND", "U"));

                RecordKey keydefarea = new RecordKey();
                keydefarea.setSystemName("MULTIUPDATE");
                //keydefarea.setSourceKey(geoid);
                keydefarea.setRowid(data.getGDARowid());
                defdarearequest.setGenerateSourceKey(false);
                defdarearequest.setRecordKey(keydefarea);


                defdarearequest.setRecord(recorddfndArea);
                defdarearequest.setInteractionId(interactionid);

                PutResponse defdarearesponse = saveOrUpdate(defdarearequest, getSiperianClient());


                if(data.getParentRowid() != null && !"".equals(data.getParentRowid().trim())){

                    boolean parentChanges = isParentChanged(data.getGDARowid(),data.getParentRowid(),connection);

                    if (parentChanges){

                        DeleteRequest deldefdarearelrequest = new DeleteRequest();

                        String relrowid = getParentRowID(data.getGDARowid(),connection);

                        Record delrecorddfndArearel = new Record();

                        RecordKey keyreldel = new RecordKey();
                        keyreldel.setSystemName("MULTIUPDATE");
                        keyreldel.setRowid(relrowid);

                        ArrayList<RecordKey> recordkeys = new ArrayList<RecordKey>();
                        recordkeys.add(keyreldel);
                        deldefdarearelrequest.setRecordKeys(recordkeys);
                        deldefdarearelrequest.setInteractionId(interactionid);
                        deldefdarearelrequest.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");

                        DeleteResponse deldefdarearelresponse = saveOrUpdate(deldefdarearelrequest, getSiperianClient());

                        Record recorddfndArearel = new Record();


                        recorddfndArearel.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");
                        recorddfndArearel.setField(new Field("HUB_STATE_IND", "1"));
                        recorddfndArearel.setField(new Field("TYP_TYPE_CD",data.getRelType()));
                        recorddfndArearel.setField(new Field("GDA_DFND_AREA_PRNT_ROWID", data.getParentRowid()));
                        recorddfndArearel.setField(new Field("GDA_DFND_AREA_CHLD_ROWID", data.getGDARowid()));
                        recorddfndArearel.setField(new Field("OPN_IND", "I"));
                        recorddfndArearel.setField(new Field("VALID_FROM_DT", getDatabaseDate(getConnection())));
                        recorddfndArearel.setField(new Field("VALID_THRU_DT", "31-12-9999 12:59:59"));
                        //recorddfndArearel.setField(new Field("VALID_THRU_DT", data.getValid_thru()));


                        RecordKey keydefarearel = new RecordKey();
                        keydefarearel.setSystemName("MULTIUPDATE");
                        //keydefarearel.setSourceKey(geoid);
                        defdarearelrequest.setGenerateSourceKey(true);
                        defdarearelrequest.setRecordKey(keydefarearel);


                        defdarearelrequest.setRecord(recorddfndArearel);
                        defdarearelrequest.setInteractionId(interactionid);

                        PutResponse defdarearelresponse = saveOrUpdate(defdarearelrequest, getSiperianClient());
                    }

                }

                //No need to update C_ALT_CODE during update transation
        		/*
        		String typerowid = getTypTypeRowID(getConnection());
        		String altcodeRowid = getAltCodeRowID(data.getGDARowid(),typerowid,getConnection());

        		Record recordaltcode = new Record();

        		recordaltcode.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");

        		recordaltcode.setField(new Field("HUB_STATE_IND", "1"));
        		recordaltcode.setField(new Field("TYP_TYPE_ROWID", typerowid));
        		recordaltcode.setField(new Field("GDA_DFND_AREA_ROWID",  data.getGDARowid()));
        		recordaltcode.setField(new Field("OPN_IND", "I"));


        		RecordKey keyaltcode = new RecordKey();
        		keyaltcode.setSystemName("MULTIUPDATE");
        		keyaltcode.setSourceKey(geoid);
        		keyaltcode.setRowid(altcodeRowid);
        		altcoderequest.setGenerateSourceKey(true);
        		altcoderequest.setRecordKey(keyaltcode);

        		altcoderequest.setInteractionId(interactionid);
        		altcoderequest.setRecord(recordaltcode);
        		PutResponse altcoderesponse = saveOrUpdate(altcoderequest, getSiperianClient());
        		*/
            }


            responseMap.put("STATUS", "SUCCESS");
            responseMap.put("INTERACTIONID", interactionid);



        }catch ( Exception exception){

            exception.printStackTrace(System.out);

            responseMap.put("STATUS", "FAILED");
            responseMap.put("INTERACTIONID", "");

            logger.error(" Error in UpsertGEOData ", exception);
        }

        logger.info(" UpsertGEOData DAO Ends ");

        return responseMap;

    }

    private String getGEOID(){
        EjbSiperianClient sipClient = (EjbSiperianClient) siperianClient;
        CleanseRequest request = new CleanseRequest();
        request.setCleanseFunctionName("Maersk Cleanse and Validation Library|GenerateGeoID");
        StringBuilder  varname1 = new StringBuilder();
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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public SiperianClient getSiperianClient() {
        return siperianClient;
    }

    public void setSiperianClient(SiperianClient siperianClient) {
        this.siperianClient = siperianClient;
    }

    public CleansePutResponse saveOrUpdate(CleansePutRequest request,SiperianClient client){
        return (CleansePutResponse) client.process(request);

    }

    public PutResponse saveOrUpdate(PutRequest request,SiperianClient client){
        return (PutResponse) client.process(request);

    }

    public DeleteResponse saveOrUpdate(DeleteRequest request,SiperianClient client){
        return (DeleteResponse) client.process(request);

    }

}
