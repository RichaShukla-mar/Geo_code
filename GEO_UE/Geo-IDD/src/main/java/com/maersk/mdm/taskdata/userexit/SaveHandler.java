package com.maersk.mdm.taskdata.userexit;

import com.maersk.mdm.taskdata.data.BindGeographyData;
import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.*;
import com.siperian.dsapp.common.util.Logger;
import com.siperian.sif.client.SiperianClient;
import com.siperian.sif.message.Field;
import com.siperian.sif.message.Password;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.RecordKey;
import com.siperian.sif.message.SiperianObjectType;
import com.siperian.sif.message.SiperianRequest;
import com.siperian.sif.message.SiperianResponse;
import com.siperian.sif.message.mrm.PutRequest;
import com.siperian.sif.message.mrm.PutResponse;
import com.siperian.sif.message.mrm.SearchQueryRequest;
import com.siperian.sif.message.mrm.SearchQueryResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jfree.util.Log;

import java.util.ArrayList;  
import java.util.Collection;  

public class SaveHandler extends AbstractBaseOperationPlugin implements ISaveOperationPlugin {
	SiperianClient sipClient = null;
    private static final Logger LOG = Logger.getLogger(SaveHandler.class);
    Collection<String> collection=new ArrayList<>();
    Collection<String> collection1=new ArrayList<>();
    Collection<String> CountryList=new ArrayList<>();  
    Connection connection = null;
    final private SaveHandlerHelper helper;

    public SaveHandler() {
        this.helper = new SaveHandlerHelper(this);
    }

    /**
     * Used to invoke custom cleansing and implicit child adding
     */
    public OperationResult beforeSave(BDDObject bddObject) {
        DefinedArea definedArea = null;
        boolean success = false;
        try {
            ExternalResources externalResources = helper.initializeCodeTypeIDs();
            definedArea = new DefinedArea(bddObject);
            
          


            // C_TYP_TYPE.ROWID_OBJECT value for 'GEOID' code type.
            //String geoIdTypeRowidObject = helper.initializeCodeTypeIDs();
            String geoIdTypeRowidObject = externalResources.getRowidObjectGEOID();

            // It is not allowed to add or modify GEOID code manually
            if (definedArea.getNumberOfChangedAltCodes(geoIdTypeRowidObject) > 0) {
                throw new MaerskValidationException("It is not allowed to add or modify GEOID code manually");
            }

            // For the case of new records let's generate GEOID automatically
            synchronized (helper) {
				
		
            	if (SaveHandlerHelper.getAlternateCodeValue(definedArea.getAltCodes(), geoIdTypeRowidObject) == null) {
            		String generatedId = helper.generateId();
            		definedArea.addGeoIdChild(generatedId, geoIdTypeRowidObject);
            		LOG.debug("Successfully added new GEO ID child - " + generatedId);
            	}
            }
         
            if (definedArea.getDefAreaType() != DefAreaType.CONTINENT) {
            	if(definedArea.getDefAreaType() != DefAreaType.BDA )
            	{
                String validationResults = helper.invokeComplexValidation(definedArea);
                if (validationResults != null) {
                    throw new MaerskValidationException(validationResults);
                }
            	}
            } 
    		String mdmORSid = getOperationContext().getValue(OperationContext.ORS_ID).toString();
			LOG.info("Before hitting generateGeographyPublishData method");
			OperationResult oprResult=generateGeographyPublishData(bddObject,mdmORSid);
			LOG.info("After hitting generateGeographyPublishData method");
			if(oprResult!=OperationResult.OK) {
				return new OperationResult(new OperationExecutionError("SIP-80001", new String[] {"Error in generateGeographyPublishData method"}, getLocalizationGate()));
			}
            success = true;
        } catch (MaerskConfException e) {
            String errorMessage = "MaerskCustom. Configuration issue detected. ";
            LOG.error(errorMessage, e);
            // TODO error code bundle. It is configuration error. Should never happen in production
            return new OperationResult(new OperationExecutionError("SIP-40001", new String[]{errorMessage + e.getMessage()}, getLocalizationGate()));
        } catch (MaerskValidationException e) {
            // TODO error code bundle. It is logical validation error
            return new OperationResult(new OperationExecutionError("SIP-40001", new String[]{"MaerskCustom. " + e.getMessage()}, getLocalizationGate()));
        } catch (Exception e) {
            // TODO error code bundle. It is unknown error. Should never happen in production
            String errorMessage = "MaerskCustom. Unknown exception happend. ";
            LOG.error(errorMessage, e);
            return new OperationResult(new OperationExecutionError("SIP-40001", new String[]{errorMessage + e.getMessage()}, getLocalizationGate()));
        } finally {
            if (!success && definedArea != null) {
                definedArea.silentlyRemoveAutoGeneratedGeoId();
            }
        }
        return OperationResult.OK;
    }

	private OperationResult generateGeographyPublishData(BDDObject bddObject, String mdmORSid) throws SQLException {
		try {
			LOG.info("Inside Method generateGeographyPublishData");
			Geography geoEvent = new Geography();
			BindGeographyData geoData = new BindGeographyData(bddObject, mdmORSid, getOperationContext(),
					getLocalizationGate(), geoEvent);
			OperationResult operationResult = geoData.setGeographyData();
			if(operationResult==OperationResult.OK) {
				return OperationResult.OK;
			}
			else {
				return new OperationResult(new OperationExecutionError("SIP-40002", new String[]{"MaerskCustom. Unknown exception happend. " + "Unable to publish Geo Data"}, getLocalizationGate()));
			}
		} catch (MaerskConfException exception) {
			LOG.info("Error in method generateGeographyPublishData" + exception);
			String errorMessage = "MaerskCustom. Unknown exception happend. ";
            LOG.error(errorMessage, exception);
            return new OperationResult(new OperationExecutionError("SIP-40003", new String[]{errorMessage + exception.getMessage()}, getLocalizationGate()));
		}
	}
	/**
     * Used display confirmation dialogs
     */
    public OperationResult beforeEverything(BDDObject bddObject) {
    	String mdmORSid = this.getOperationContext().getValue("ors id").toString();
		Connection conn = this.getDatabaseConnection(mdmORSid);
		try {
        	
			LOG.info(bddObject.getObjectName());
			if ("Country".equalsIgnoreCase(bddObject.getObjectName()) || "Site".equalsIgnoreCase(bddObject.getObjectName())){
				List<BDDObject> altCodes = bddObject.getChildren("AlternateCode");
				GenerateNotAllowedListCountry();
				LOG.info("GenerateListDone");
				for(BDDObject altcode : altCodes) {
					String AltCodeRowid = altcode.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
					LOG.info(AltCodeRowid);
					if (collection1.contains(getAltCodetypeName(AltCodeRowid,this.getOperationContext())) && altcode.isRemoved()) {
						//Anil Change RKTS Decom
						LOG.info("RKST,RKTS, GEOID is not allowed to delete" );
						return new OperationResult(new OperationExecutionError("SIP-500303", getLocalizationGate()));
					}
			}
			}
		
			if ("City".equalsIgnoreCase(bddObject.getObjectName()) || "CitySubArea".equalsIgnoreCase(bddObject.getObjectName())){
			     // test deleted alt codes 	
				/******************    184026: Validation for GEO DELETE START ***********************/
				// 1. Allowed ALT CODE DETETION : UN CODE, UN CODE(Lookup Only), UN CODE(Return Only), Schedule D, Schedule K,SMDG,BIC. THROW ERRORS FOR OTHERS
				List<BDDObject> altCodes = bddObject.getChildren("AlternateCode");
				GenerateNotAllowedList();
				LOG.info("GenerateListDone");
				boolean isUNCodeDeleted=false;
				boolean isUNLookupDeleted=true;
				for(BDDObject altcode : altCodes) {
					String AltCodeRowid = altcode.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
					LOG.info(AltCodeRowid);
					if (collection.contains(getAltCodetypeName(AltCodeRowid,this.getOperationContext())) && altcode.isRemoved()) {
						//Anil Change RKTS Decom
						LOG.info("RKST GEOID is not allowed to delete" );
						return new OperationResult(new OperationExecutionError("SIP-500301", getLocalizationGate()));
					}
					if ((AltCodeRowid.equalsIgnoreCase(this.getUncodeRowid(conn).trim()) && altcode.isRemoved())) {
						LOG.info("UN CODE IS REMOVED");
						isUNCodeDeleted=true;
					}
					if (AltCodeRowid.equalsIgnoreCase(this.getUncodeLookupRowid(conn).trim()) && !(altcode.isRemoved())) {
						LOG.info("UN lookup code is not removed.");
						isUNLookupDeleted=false;
					}
					
			    	}
				LOG.info("isUNCodeDeleted:" + isUNCodeDeleted);
				LOG.info("isUNLookupDeleted" + isUNLookupDeleted);				
				     if (isUNCodeDeleted && !isUNLookupDeleted) {
				    	 LOG.info("UNCODE CANNOT BE DELETED IF UNLOOKUP IS PRESENT. Please delete both or delete UNLOOKUP first." );
						return new OperationResult(new OperationExecutionError("SIP-500302", getLocalizationGate()));
				     }
				     
				     List<BDDObject> bdaTypes = bddObject.getChildren("BDA");
				     boolean isPoolBDA=false;
				     if(bdaTypes!=null) {
				    	 if(bdaTypes.isEmpty() || bdaTypes.size()==0) {
				    	 throw new MaerskValidationException("City/SubCity Must have atlease one Pool BDA");
				    	 }
				     
					else {
						int counter = 0;
						if(!bddObject.isRemoved()) {
						for (BDDObject bdaObject : bdaTypes) {
							if (!bdaObject.isRemoved()) {
								LOG.info("Inside else to check city BDA");
								Object bdaType = bdaObject.getValue("C_GDA_DFND_AREA|TYP_TYPE_CD");
								Object bdaName = bdaObject.getValue("C_GDA_DFND_AREA|NAME");
								if (bdaName != null) {
									LOG.info("BDA Name is ::" + bdaName.toString());
									LOG.info("Check if BDA is Pool BDA");
									isPoolBDA = Utility.isBDAPoolBDA(bdaName, mdmORSid);
									if (isPoolBDA) {
										LOG.info("Pool BDA True for " + bdaName);
										counter++;
										LOG.info("Counter Value inside loop is " + counter);
									}
								}
							}

						}
						LOG.info("Counter Value outside Loop is " + counter);
						if (counter == 0) {
							throw new MaerskValidationException("City/SubCity Must have atlease one Active Pool BDA");
							}
						}
				     }
				     }
				
				/******************    184026: Validation for GEO DELETE START ***********************/
			}
			
			/****************Sub City pool Bda Start*************/
			
			/*if ("CitySubArea".equalsIgnoreCase(bddObject.getObjectName())){
				List<BDDObject> bdaTypes = bddObject.getChildren("BDA");
			     boolean isPoolBDA=false;
			     if(bdaTypes!=null) {
			    	 if(bdaTypes.isEmpty() || bdaTypes.size()==0) {
			    	 throw new MaerskValidationException("CitySubArea Must have atlease one Pool BDA");
			    	 }
			     
				else {
					int counter = 0;
					if(bddObject.isCreated()) {
					for (BDDObject bdaObject : bdaTypes) {
						if (!bdaObject.isRemoved()) {
							LOG.info("Inside else to check citySubArea BDA");
							Object bdaType = bdaObject.getValue("C_GDA_DFND_AREA|TYP_TYPE_CD");
							Object bdaName = bdaObject.getValue("C_GDA_DFND_AREA|NAME");
							if (bdaName != null) {
								LOG.info("BDA Name is ::" + bdaName.toString());
								LOG.info("Check if BDA is Pool BDA");
								isPoolBDA = Utility.isBDAPoolBDA(bdaName, mdmORSid);
								if (isPoolBDA) {
									LOG.info("Pool BDA True for " + bdaName);
									counter++;
									LOG.info("Counter Value inside loop is " + counter);
								}
							}
						}

					}
					LOG.info("Counter Value outside Loop is " + counter);
					if (counter == 0) {
						throw new MaerskValidationException("SubCity Must have atlease one Pool BDA");
						}
					}
			     }
			     }
			     
			     *//****************Sub City pool Bda ENd*************//*
			     
			     List<BDDObject> altCodes = bddObject.getChildren("AlternateCode");
					GenerateNotAllowedList();
					LOG.info("GenerateListDone");
					boolean isUNCodeDeleted=false;
					boolean isUNLookupDeleted=true;
					for(BDDObject altcode : altCodes) {
						String AltCodeRowid = altcode.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
						LOG.info(AltCodeRowid);
						if (collection.contains(getAltCodetypeName(AltCodeRowid,this.getOperationContext())) && altcode.isRemoved()) {
							//Anil Change RKTS Decom
							LOG.info("RKST GEOID is not allowed to delete" );
							return new OperationResult(new OperationExecutionError("SIP-500301", getLocalizationGate()));
						}
						if ((AltCodeRowid.equalsIgnoreCase(this.getUncodeRowid(conn).trim()) && altcode.isRemoved())) {
							LOG.info("UN CODE IS REMOVED");
							isUNCodeDeleted=true;
						}
						if (AltCodeRowid.equalsIgnoreCase(this.getUncodeLookupRowid(conn).trim()) && !(altcode.isRemoved())) {
							LOG.info("UN lookup code is not removed.");
							isUNLookupDeleted=false;
						}
						
				    	}
					LOG.info("isUNCodeDeleted:" + isUNCodeDeleted);
					LOG.info("isUNLookupDeleted" + isUNLookupDeleted);				
					     if (isUNCodeDeleted && !isUNLookupDeleted) {
					    	 LOG.info("UNCODE CANNOT BE DELETED IF UNLOOKUP IS PRESENT. Please delete both or delete UNLOOKUP first." );
							return new OperationResult(new OperationExecutionError("SIP-500302", getLocalizationGate()));
					     }
					
				
			}*/
        	
			/******************CitySUbArea END*****************/
			
            DefinedArea definedArea = new DefinedArea(bddObject);

            // Let's check uniqueness of alternate names
            if (!SaveHandlerHelper.checkUniqueness(definedArea.getAltNames(), String.CASE_INSENSITIVE_ORDER)) {
                throw new MaerskValidationException("Duplicate alternate names are not allowed");
            }
            
            // Generic check applicable to all defined area types
			/*
			 * if (!SaveHandlerHelper.checkUniqueness(definedArea.getAltCodes(),
			 * AltCode.CODE_TYPE_COMPARATOR)) { throw new
			 * MaerskValidationException("None of the alternate codes should appear twice");
			 * }
			 */

            validateAttemptOfDeactivation(definedArea);
            //if (definedArea.getDefAreaType() != DefAreaType.CONTINENT) {
            logger.info("BDA Type in This case is ::"+definedArea.getDefAreaType());
            		if((definedArea.getDefAreaType() != DefAreaType.BDA) && (definedArea.getDefAreaType() != DefAreaType.CONTINENT)){
                // Let's check parents, there should not be exactly one active relationship to parent
            	if (definedArea.getNumberOfActiveRelsToParent() != 1) {
            		LOG.info("Inside check if Active Parent available");
                    throw new MaerskValidationException("There should exactly one active parent's relatioship");
                }

                // Let's check if parent has been changed (another parent area has been chosen).
                // It should be done before validating ALT_CODEs as parent change might break RKST codes consistency
                validateParentChange(definedArea);
            		}
                ExternalResources externalResources = helper.initializeCodeTypeIDs();
                validateAltCodes(definedArea, externalResources,bddObject);

                String message = checkIfCofirmationIsRequired(definedArea);
                if (message != null) {
                    //SIP-37004=CONFIRMATION||Are you sure you want to save record {0}?
                    return new OperationResult(new OperationExecutionError("SIP-37004", new String[]{message}, getLocalizationGate()));
            }
                
                if(definedArea.getDefAreaType()== DefAreaType.BDA ){
                	
                	if(helper.checkDuplicateBDARelationship(definedArea.getBdaChilds()))
                		throw new MaerskValidationException("Duplicate Childs not allowed");
                	if(helper.checkDuplicateBDARelationship(definedArea.getBdaParents()))
                		throw new MaerskValidationException("Duplicate Parents not allowed");
                }else
                {
                	if(definedArea.getDefAreaType()!= DefAreaType.CONTINENT ){
                		if(helper.checkDuplicateBDARelationship(definedArea.getGdaBdas()))
                		throw new MaerskValidationException("Duplicate BDAs not allowed");
                	}
                }
                
                //Check for duplicate state in country
               if (definedArea.isColumnChanged(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME) && definedArea.getDefAreaType()== DefAreaType.PROVINCE){
            	   if(!(helper.checkForDuplicateStateInCountry(definedArea.getParentWithActiveRel().getName(), definedArea.getName()))){
         		   throw new MaerskValidationException("State/Prov " + definedArea.getName() + " already exists in " + definedArea.getParentWithActiveRel().getName());    
            	   }
               }
               if (definedArea.getDefAreaType()== DefAreaType.SITE){
              	 //Check if address line 1 is same as parent city name
              	   if(!(helper.compareAddrLn1ParentCityName(definedArea.getSiteAddressLine1(), definedArea.getParentWithActiveRel().getName()))){
                 		   throw new MaerskValidationException("Site Address Line1 should not be same as Parent City Name"); 
                     }  
              	 //Check if Site type is set as TO BE UPDATED
                     if(definedArea.getSiteType().equals(helper.fetchRowidForTypeCode(IDDConstants.DATA_TABLE_TYP_SITE_TYPE))){
                  	   throw new MaerskValidationException("Site Type cannot be set as 'To be updated'");
                     }
              
                 }    
               //Check for duplicate site in city
               if (definedArea.isColumnChanged(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME) &&  definedArea.getDefAreaType()== DefAreaType.SITE){
            	   if(!(helper.checkForDuplicateSiteInCity(definedArea.getParentWithActiveRel().getName(), definedArea.getName()))){
            		   throw new MaerskValidationException("Site " + definedArea.getName() + " already exists in " + definedArea.getParentWithActiveRel().getName());    
            	   }
               }
               //Check for postal code mandatory flag and throw warning if postal code not given
               if (definedArea.getDefAreaType()== DefAreaType.CITY){
                   if (checkIfPostalCodeMandatoryForCountry(definedArea)){
            		   return new OperationResult(new OperationExecutionError("SIP-50001", getLocalizationGate()));
            	   }
               }
               
             //Richa Code Started here
               /*************Validate City/SubCity Active Indicator***********/
               if (bddObject.getObjectName().equalsIgnoreCase("City") || bddObject.getObjectName().equalsIgnoreCase("CitySubArea") ||
            		   bddObject.getObjectName().equalsIgnoreCase("Country") ||  bddObject.getObjectName().equalsIgnoreCase("StateProv")) {
            	   List<BDDObject> cityaltcodes = bddObject.getChildren("AlternateCode");
            	   if (cityaltcodes != null && cityaltcodes.size() > 0) {
            		   for (BDDObject cityaltcode : cityaltcodes){
            			String AltCode= cityaltcode.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
               			String toComparehsud = getHsudRowid(conn).trim();
               			String toLnsRowid = getLnsRowid(conn).trim();
               			String toComparehsudNum = getHsudNumRowid(conn).trim();
               			String gdaStatus = bddObject.getValue("C_GDA_DFND_AREA|ACTIVE_FLAG").toString().trim();
               			LOG.info("validate City/SubCity Active Indicator" + gdaStatus +" toComparehsud " + toComparehsud + " toLnsRowid" + toLnsRowid);
               			if (gdaStatus.equalsIgnoreCase("N") && (AltCode.equalsIgnoreCase(toComparehsud) || AltCode.equalsIgnoreCase(toComparehsudNum) || AltCode.equalsIgnoreCase(toLnsRowid))
               					&& !cityaltcode.isRemoved()){
               				throw new MaerskValidationException("Location code cannot be deactivated unless the mapped LNS or HSUD code is unlinked.");
               				}}}
            	   
            	   //Check timezone for update records records.            	   
            	  if (!bddObject.isRemoved()){
            		  String timeZone ="";
            		  LOG.info("Before timeZone value" + bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE));
            		   timeZone = (String) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE);
            		   LOG.info("timeZone value" + timeZone);
            		   if(timeZone==null){
            			   throw new MaerskValidationException("Found Time Zone is not present, Please update it");   
            		   }
            		   
            		   
            	   }
                      }
               /*************Validate City/SubCity Active Indicator END ***********/
               
               if (bddObject.getObjectName().equalsIgnoreCase("City") || bddObject.getObjectName().equalsIgnoreCase("CitySubArea")) {
            	   List<BDDObject> cityaltcode = bddObject.getChildren("AlternateCode");
   				LOG.info("value of child adding both cityaltcode1 object r : " + cityaltcode);
   				if (cityaltcode != null && cityaltcode.size() > 0) {
   					LOG.info("getinto city adding both cityaltcode2 object r : " + cityaltcode);
   		  if ((getUncodeCount(cityaltcode,conn)>0) && (getUncodeReturnCount(cityaltcode,conn)>0)){
   			LOG.info("getinto city adding both altcode check object r : " + cityaltcode);
   			//return new OperationResult(new OperationExecutionError("SIP-500177", this.getLocalizationGate()));
   			throw new MaerskValidationException("Both Code type UN code and UN code(Return Only) are detected. Only one should be present.");
   				} }
   				
   				
               }
               
               if (bddObject.getObjectName().equalsIgnoreCase("City") || bddObject.getObjectName().equalsIgnoreCase("CitySubArea")) {
            	   List<BDDObject> cityaltcode = bddObject.getChildren("AlternateCode");
   				LOG.info("Check uncode and uncodelookup Condition1 : " + cityaltcode);
   				if (cityaltcode != null && cityaltcode.size() > 0) {
   					LOG.info("Check uncode and uncodelookup Condition2 : " + cityaltcode);
   		     if ((getUncodeLookupCount(cityaltcode,conn)>0) && (getUncodeCount(cityaltcode,conn)==0)){
   			LOG.info("Check uncode and uncodelookup Condition5 : " + ((getUncodeLookupCount(cityaltcode,conn)>0) && (getUncodeCount(cityaltcode,conn)==0)));
   			//return new OperationResult(new OperationExecutionError("SIP-500178", this.getLocalizationGate()));
   			throw new MaerskValidationException("Code type UN_Code(lookup Only) may only be present if also code type UN_Code is present.");
   				} }
               }
               
               
               if (bddObject.getObjectName().equalsIgnoreCase("City") || bddObject.getObjectName().equalsIgnoreCase("CitySubArea")) {
            	   List<BDDObject> cityaltcode = bddObject.getChildren("AlternateCode");
   				LOG.info("value of child adding both cityaltcode1 object r : " + cityaltcode);
   				if (cityaltcode != null && cityaltcode.size() > 0) {
   					LOG.info("getinto city adding both cityaltcode2 object r : " + cityaltcode);
   		     if ((getUncodeLookupCount(cityaltcode,conn)>0) && (getUncodeReturnCount(cityaltcode,conn)>0)){
   			LOG.info("getinto city adding both altcode check object r : " + cityaltcode);
   			//return new OperationResult(new OperationExecutionError("SIP-500178", this.getLocalizationGate()));
   			throw new MaerskValidationException("Both Code type UN code(Lookup Only) or UN code(Return Only) are detected. Only one should be present.");
   				} }
               }
               
                //Generic check applicable to all defined area types
               if (!checkUniquenessLookup(definedArea.getAltCodes(), AltCode.CODE_TYPE_COMPARATOR,conn)) {
                   throw new MaerskValidationException("None of the alternate codes should appear twice,Except UN_CODE(LOOKUP Only)");
               }
             
       //ScheduleD/K check
               
               if (bddObject.getObjectName().equalsIgnoreCase("City")) {
            	   Utility util = new Utility();
            	   GenerateAllowedList();
            	   LOG.info("GenerateListDone");
            	   List<BDDObject> cityaltcodeK = bddObject.getChildren("AlternateCode");
            	   List<BDDObject> city_Hierarchy = bddObject.getChildren("Hierarchy");
            	   LOG.info("value of child adding ScheduleD/K check object r : " + cityaltcodeK);
            	   if (cityaltcodeK != null && cityaltcodeK.size() > 0) {
            		for (BDDObject schduleKcheck : cityaltcodeK){
            			if(!schduleKcheck.isRemoved()){
            			String AltCodeValue = schduleKcheck.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
            			String tocomapreKrowid = getScheduleKRowid(conn).trim();
            			if (AltCodeValue.equalsIgnoreCase(tocomapreKrowid)){            				            			
            		   LOG.info("getinto HierarchyKstate1 object r : " + cityaltcodeK);
            		   LOG.info("getinto HierarchyKstate2 object r : " + AltCodeValue);
            		   LOG.info("getinto HierarchyKstate3 object r : " + tocomapreKrowid);
            		   LOG.info("getinto HierarchyKstate4 object r : " + city_Hierarchy);
            	    for (BDDObject cityHierprovCountry : city_Hierarchy) {
            	    LOG.info("getinto HierarchyKstate5 object r : " + cityHierprovCountry);
            		 String stateCountrynameK =  util.getStateParentname(cityHierprovCountry, mdmORSid);
            		 LOG.info("getinto HierarchyKstate6 object r : " + stateCountrynameK);
            		 if (stateCountrynameK!= null ){
            			 LOG.info("getinto HierarchyKstate7 object r : " + (stateCountrynameK!= null) + "TETS" + CountryList.contains(stateCountrynameK));
            		if (CountryList.contains(stateCountrynameK)){
            			 LOG.info("getinto HierarchyKstate8 object r : " + (stateCountrynameK.equalsIgnoreCase("United States")));
            			 throw new MaerskValidationException("A Schedule D code can only be assigned to a city in the United States");	 
            		 }}            		   
            	   }}
            		}
            	   }}
               }
               
               
               if (bddObject.getObjectName().equalsIgnoreCase("City")) {
            	   Utility util = new Utility();
            	   GenerateAllowedList();
            	   LOG.info("GenerateListDone" + CountryList);
            	   List<BDDObject> cityaltcodeK = bddObject.getChildren("AlternateCode");
            	   List<BDDObject> city_Hierarchy = bddObject.getChildren("Hierarchy");
            	   LOG.info("value of child adding ScheduleD/K check object r : " + cityaltcodeK);
            	   if (cityaltcodeK != null && cityaltcodeK.size() > 0) {
            	for (BDDObject schduleKcheck : cityaltcodeK){
            		if(!schduleKcheck.isRemoved()){
            			String AltCodeValue = schduleKcheck.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
            			String tocomapreKrowid = getScheduleKRowid(conn).trim();
            			if (AltCodeValue.equalsIgnoreCase(tocomapreKrowid)){            				            			
            		   LOG.info("getinto HierarchyKCntry1 object r : " + cityaltcodeK);
            		   LOG.info("getinto HierarchyKCntry2 object r : " + AltCodeValue);
            		   LOG.info("getinto HierarchyKCntry3 object r : " + tocomapreKrowid);
            		   LOG.info("getinto HierarchyKCntry4 object r : " + city_Hierarchy);
            	    for (BDDObject cityHierprovCountry : city_Hierarchy) {
            	    LOG.info("getinto HierarchyKCntry5 object r : " + cityHierprovCountry);
            		 String CountrynameK =  util.getCountryParentname(cityHierprovCountry, mdmORSid);
            	  LOG.info("getinto HierarchyKCntry6 object r : " + CountrynameK);
            		 if (CountrynameK!=null){
            			 LOG.info("getinto HierarchyKCntry8 object r : " +  CountrynameK!=null +"TEST"+ CountryList.contains(CountrynameK));
            		 if(CountryList.contains(CountrynameK)){
            			 LOG.info("getinto HierarchyKCntry9 object r : " + CountrynameK.equalsIgnoreCase("United States"));
            			 throw new MaerskValidationException("A Schedule D code can only be assigned to a city in the United States");	 
            		 }}            		   
            	   }}
            		}
            		}}
               }
               
               if (bddObject.getObjectName().equalsIgnoreCase("City")) {
            	   Utility util = new Utility();
            	   GenerateAllowedList();
            	   LOG.info("GenerateListDone" + CountryList);
            	   List<BDDObject> cityaltcodeD = bddObject.getChildren("AlternateCode");
            	   List<BDDObject> city_Hierarchy = bddObject.getChildren("Hierarchy");
            	   LOG.info("value of child adding ScheduleD/K check object r : " + cityaltcodeD);
            	   if (cityaltcodeD != null && cityaltcodeD.size() > 0) {
            		for (BDDObject schduleDcheck : cityaltcodeD){
            			if(!schduleDcheck.isRemoved()){
            			String AltCodeValueD = schduleDcheck.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
            			String tocomapreDrowid = getScheduleDRowid(conn).trim();
            			if (AltCodeValueD.equalsIgnoreCase(tocomapreDrowid)){            				            			
            		   LOG.info("getinto HierarchyDstate1 object r : " + schduleDcheck);
            		   LOG.info("getinto HierarchyDstate2 object r : " + AltCodeValueD);
            		   LOG.info("getinto HierarchyDstate3 object r : " + tocomapreDrowid);
            		   LOG.info("getinto HierarchyDstate4 object r : " + city_Hierarchy);
            	    for (BDDObject cityHierprovCountryD : city_Hierarchy) {
            	    LOG.info("getinto HierarchyDstate5 object r : " + cityHierprovCountryD);
            		 String stateCountrynameD =  util.getStateParentname(cityHierprovCountryD, mdmORSid);
            		 LOG.info("getinto HierarchyDstate6 object r : " + stateCountrynameD);
            	 if (stateCountrynameD!= null){
            			 LOG.info("getinto HierarchyDstate7 object r : " + (stateCountrynameD!= null));
            			LOG.info("getinto HierarchyDstate8 object r : " + !CountryList.contains(stateCountrynameD));	 
            		if(!CountryList.contains(stateCountrynameD)){
            			 LOG.info("getinto HierarchyDstate9 object r : " );
            			 throw new MaerskValidationException("A Schedule K code can only be assigned to a city outside United States");	 
            		 }}            		   
            	   }}
            		}}
               }
               }
               
               if (bddObject.getObjectName().equalsIgnoreCase("City")) {
            	   Utility util = new Utility();
            	   GenerateAllowedList();
            	   LOG.info("GenerateListDone" + CountryList);
            	   List<BDDObject> cityaltcodeD = bddObject.getChildren("AlternateCode");
            	   List<BDDObject> city_Hierarchy = bddObject.getChildren("Hierarchy");
            	   LOG.info("value of child adding ScheduleD/K check object r : " + cityaltcodeD);
            	   if (cityaltcodeD != null && cityaltcodeD.size() > 0) {
            		for (BDDObject schduleDcheck : cityaltcodeD){
            			if(!schduleDcheck.isRemoved()){
            			String AltCodeValueD = schduleDcheck.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
            			String tocomapreDrowid = getScheduleDRowid(conn).trim();
            			if (AltCodeValueD.equalsIgnoreCase(tocomapreDrowid)){            				            			
            		   LOG.info("getinto HierarchyD1Cntry1 object r : " + schduleDcheck);
            		   LOG.info("getinto HierarchyD1Cntry2 object r : " + AltCodeValueD);
            		   LOG.info("getinto HierarchyD1Cntry3 object r : " + tocomapreDrowid);
            		   LOG.info("getinto HierarchyD1Cntry4 object r : " + city_Hierarchy);
            	    for (BDDObject cityHierprovCountryD : city_Hierarchy) {
            	    LOG.info("getinto HierarchyD1Cntry5 object r : " + cityHierprovCountryD);
            		String CountrynameD =  util.getCountryParentname(cityHierprovCountryD, mdmORSid);
            		LOG.info("getinto HierarchyD1Cntry6 object r : " + CountrynameD);
            		 if (CountrynameD!=null){
            			 LOG.info("getinto HierarchyD1Cntry7 object r : " + (CountrynameD!=null));
            			LOG.info("getinto HierarchyD1Cntry8 object r : " +  !CountryList.contains(CountrynameD));	 
            		 if(!CountryList.contains(CountrynameD)){
            			 LOG.info("getinto HierarchyDCntry9 object r : " + !CountryList.contains(CountrynameD) );
            			 throw new MaerskValidationException("A Schedule K code can only be assigned to a city outside United States");	 
            		 }}            		   
            	   }}
            		}}
               }
               }
     //Richa Code Ended here 
                 
        } catch (MaerskConfException e) {
            String errorMessage = "MaerskCustom. Configuration issue detected. ";
            LOG.error(errorMessage, e);
            // TODO error code bundle. It is configuration error. Should never happen in production
            return new OperationResult(new OperationExecutionError("SIP-40001", new String[]{errorMessage + e.getMessage()}, getLocalizationGate()));
        } catch (MaerskValidationException e) {
            // TODO error code bundle. It is logical validation error
            return new OperationResult(new OperationExecutionError("SIP-40001", new String[]{"Maersk. " + e.getMessage()}, getLocalizationGate()));
        } catch (Exception e) {
            // TODO error code bundle. It is unknown error. Should never happen in production
            String errorMessage = "MaerskCustom. Unknown exception happend. ";
            LOG.error(errorMessage, e);
            return new OperationResult(new OperationExecutionError("SIP-40001", new String[]{errorMessage + e.getMessage()}, getLocalizationGate()));
        }
        
           
        return OperationResult.OK;
    }

  //Richa Added
    public Connection getDatabaseConnection(String mdmORSid) {

		try {
			String dataSourceName = "jdbc/siperian-" + mdmORSid.toLowerCase() + "-ds";
			LOG.info((Object) ("The MDM DataSource name is ::" + dataSourceName));
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dataSourceName);
			connection = ds.getConnection();
			LOG.info((Object) "getConnection end - success");
			return connection;
		} catch (Exception e) {
			LOG.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
			return null;
		}

	}
    
    //########
    

	private int getUncodeCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
				LOG.info("getUncodeCount loop");
				if (AltCodeValue.equalsIgnoreCase(this.getUncodeRowid(conn))){
				count++;
				}
				LOG.info("getUncodeCount after count loop1 " + AltCodeValue.equalsIgnoreCase(this.getUncodeRowid(conn)) +" " +  count );
			}
		}
		return count;
	}
    
	private int getUncodeLookupCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
				LOG.info("getUncodeCount1 loop");
				if (AltCodeValue.equalsIgnoreCase(getUncodeLookupRowid(conn)))
				{
				++count;
				}
				LOG.info("getUncodeLookupCount after count loop1 " + AltCodeValue.equalsIgnoreCase(this.getUncodeLookupRowid(conn)) +" " +  count );
			}
		}
		return count;
	}
    
	
	private int getUncodeReturnCount(List<BDDObject> objects, Connection conn) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeValue = object.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
				LOG.info("getUncodeCount2 loop");
				if (AltCodeValue.equalsIgnoreCase(getUncodeReturnRowid(conn))){
					
				count++;
				}
				LOG.info("getUncodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(this.getUncodeReturnRowid(conn)) +" " + count );
			}
		}
		return count;
	}
    
    
    //########
    
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
				LOG.info("Get UNCODE rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public static String getUncodeLookupRowid(Connection conn) {
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
				LOG.info("Get UNCODELOOKUP rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
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
				LOG.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
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
				LOG.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
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
				LOG.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public String getHsudRowid(Connection conn) {
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
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				LOG.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
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
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				LOG.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
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
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				LOG.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public String getRktsRowid(Connection conn) {
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
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				LOG.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    public String getGeoAltCdRowid(Connection conn) {
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
				partyRoleName = rs.getString("ROWID_OBJECT").trim();
				LOG.info("Get GEOID rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
			}
			return partyRoleName;
		} catch (Exception e) {
			LOG.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
			return "NOT AVAILABLE";
		}
	}
    
    static public <T> boolean checkUniquenessLookup(Collection<T> elements, Comparator<T> comparator,Connection conn) {
   	
    	String rowidcheck =getUncodeLookupRowid(conn);
       List<T> buffer = new ArrayList<T>();
       for (T element : elements) {
        	AltCode altcdValue=(AltCode) element;
        	LOG.info("Element is ::"+altcdValue);
        	if(!altcdValue.getCodeTypeId().equalsIgnoreCase(rowidcheck)){
           for (T processedElement : buffer) {
           	LOG.info("To see Values are printed on screen1 # " + element +"Values of processedElement # " + processedElement );
               if (comparator.compare(processedElement, element) == 0) {
                   // A duplicate is detected
                   return false;
               }
           }
        }
           buffer.add(element);
       }
       
       
       return true;
   }
   
    
    
    //Richa Added
    
    private void validateParentChange(DefinedArea definedArea) throws MaerskConfException, MaerskValidationException {
        if (definedArea.getParentWithRemovedRel() != null) {
            DefAreaType defAreaType = definedArea.getDefAreaType();
            if (defAreaType == DefAreaType.POSTAL_CODE || defAreaType == DefAreaType.CITY) {
                String oldCountryRowid = helper.getRemovedParentRowid(DefAreaType.COUNTRY, definedArea);
                if (oldCountryRowid == null) {
                    throw new MaerskValidationException("Failed to find previous parent country for " + definedArea.getName());
                }

                String newCountryRowid = helper.getActiveParentRowid(DefAreaType.COUNTRY, definedArea);
                if (newCountryRowid == null) {
                    throw new MaerskValidationException("Failed to find new parent country for " + definedArea.getName());
                }

                // It is possible to change parents only in case new parent belongs to the same country as previous parent belongs to
                if (!newCountryRowid.equals(oldCountryRowid)) {
                    throw new MaerskValidationException("The new parent has to be within the same country either directly or through hierarchy");
                }
            } else if (defAreaType == DefAreaType.SITE || defAreaType == DefAreaType.PROVINCE || defAreaType == DefAreaType.COUNTY) {
                throw new MaerskValidationException("For site and state/province geography items it is not allowed to change parent");
            } else { // defAreaType == DefAreaType.COUNTRY || defAreaType == DefAreaType.CONTINENT
                // Do nothing
            }
        }
    }

    /**
     * Performs all different kinds of alternate code validations. Note that it requires presence of parent defined area
     *
     * @param definedArea instance of definedArea
     * @param externalResources list of external resource
     * @param bddObject 
     * @throws MaerskValidationException in case business validation fails
     * @throws MaerskConfException in case something is wrong with the configuration
     */
    private void validateAltCodes(DefinedArea definedArea, ExternalResources externalResources, BDDObject bddObject) throws MaerskValidationException, MaerskConfException {
        List<AltCode> defAreaAltCodes = definedArea.getAltCodes();
        //String codeValueGeoId = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectGEOID());
        String codeValueRKST = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectRKST());
        String codeValueRKTS = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectRKTS());
        String codeValueMODEL = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectMODEL());
        String codeValueSTATE = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectSTATE());
        String codeValuePROVINCE = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectPROVINCE());
        String codeValuePOSTCODE = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectPOSTCODE());
        String codeValueBDACODE = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectBDACODE());
        String codeValueContinentCode = SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectContinentCode());
        String codeValueISOTRTRY=SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectISOTRTRY());
        String codeValueIata=SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectIataCode());
        String codeValueHusd=SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectHsudCode());
        String codeValueLns=SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectLnsCode());
        String codeValueHusdnum=SaveHandlerHelper.getAlternateCodeValue(defAreaAltCodes, externalResources.getRowidObjectHsudNum());
        
        DefAreaType defAreaType = definedArea.getDefAreaType();
        
        if (defAreaType == DefAreaType.CONTINENT)
        {
        	if (codeValueContinentCode == null) {
    	 		throw new MaerskValidationException("Continent Code is mandatory for Continents");
    	 	}
        }
        
        if (defAreaType == DefAreaType.BDA)
        {	
        	 	if (codeValueBDACODE == null) {
        	 		throw new MaerskValidationException("BDA code is mandatory for any BDA");
        	 	}
        		String bdaType = definedArea.getBDAType();
        	
        		SearchQueryRequest searchRequest = new SearchQueryRequest();
        		searchRequest.setAdvancedMode(false);
        		searchRequest.setDisablePaging(true);
        		searchRequest.setReturnTotal(false);
        		searchRequest.setRemoveDuplicates(false);
        		searchRequest.setRecordsToReturn(100); 
        		searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_BDA_TYPE_CODE);


        		StringBuilder filterCriteria = new StringBuilder();
        		filterCriteria.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_BDA_TYPE).append(" IN (");
        		filterCriteria.append("'").append(bdaType).append("')").append(" AND ");
        		filterCriteria.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_BDA_CODE).append("=").append("'").append(codeValueBDACODE).append("'");
        		filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_ROWID_OBJECT).append(" NOT LIKE '").append(definedArea.getRowidObject()).append("%'");
            
        		searchRequest.setFilterCriteria(filterCriteria.toString());

        		SearchQueryResponse searchResponse;
        		try {
        			searchResponse = (SearchQueryResponse)executeRequest(searchRequest);
        		} catch (Exception e) {
                String errorMessage = "BDA CODE search";
                LOG.error(errorMessage, e);
                throw new MaerskConfException(errorMessage, e);
            }

            if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
            	throw new MaerskValidationException("BDA CODE must be unique within BDA Type");
            }
                }

        if (defAreaType == DefAreaType.COUNTRY ) {
            if (codeValueRKTS == null) {
                throw new MaerskValidationException("RKTS code is required countries");
            }
        }
        	
        if (defAreaType == DefAreaType.COUNTRY || defAreaType == DefAreaType.CITY || defAreaType == DefAreaType.SITE || defAreaType==defAreaType.SUB_CITY) {
            if (codeValueRKST == null) {
                throw new MaerskValidationException("RKST code is required countries, cities, sites and Sub City");
            }
  //          List<BDDObject> cityDetails = bddObject.getChildren("CityDetails");	
            //String isMaerskCityFlag=cityDetails.get(0).getValue("C_GDA_CITY|MAERSK_CITY").toString().trim();
            //LOG.info("ANIL RKTS :: MAERSK CITY IND ::"+isMaerskCityFlag);
			if (codeValueRKTS == null/* && isMaerskCityFlag.equalsIgnoreCase("N") */) {
              //Anil- RKTS Removal //  
            	//throw new MaerskValidationException("RKTS code is required countries, cities and sites");
            }

            // RKTS codes must be unique within all defined areas of the same type
            String defAreaName = helper.getOtherDefAreaWithAltCode(definedArea, externalResources.getRowidObjectRKTS(), codeValueRKTS);
            if (defAreaName != null) {                
                throw new MaerskValidationException("RKTS code must be unique within the all objects of the same geography type. Found [" + defAreaName + "] of type [" + defAreaType.getCode() + "]");
            }
            
            /**************Richa Change for HSUD Start*******/
            String defAreaName1 = helper.getOtherDefAreaWithAltCode(definedArea, externalResources.getRowidObjectHsudNum(), codeValueHusdnum);
            if (defAreaName1 != null) {                
                throw new MaerskValidationException("HUSD code must be unique within the all objects of the same geography type. Found [" + defAreaName1 + "] of type [" + defAreaType.getCode() + "]");
            }
            /**************Richa Change for HSUD End*******/
        }

        
        
        /**************SubCityArea Change1********/
        if (defAreaType == DefAreaType.CITY || defAreaType == DefAreaType.SITE  || defAreaType == DefAreaType.SUB_CITY) {
            if (definedArea.isCreated() && codeValueMODEL == null) {
                throw new MaerskValidationException("MODEL code is required for cities, SubCity and sites");
            }
        }
        /**************SubCityArea********/

        if (defAreaType == DefAreaType.PROVINCE) {
            if (codeValueSTATE == null && codeValuePROVINCE == null) {
                throw new MaerskValidationException("Either STATE or PROVINCE alternate code should present");
            }

            if (codeValueSTATE != null && codeValuePROVINCE != null) {
                throw new MaerskValidationException("Both STATE or PROVINCE alternate codes detected. Only one should present");
            }

            if (codeValueSTATE != null) {
                List<String> defAreaRowids = helper.getAltCodeWithinParents(externalResources.getRowidObjectSTATE(), codeValueSTATE, definedArea.getParentWithActiveRel().getRowidObject());
                if ((defAreaRowids.size() > 1) || (defAreaRowids.size() == 1 && !defAreaRowids.contains(definedArea.getRowidObject()))) {
                    throw new MaerskValidationException("STATE alternate code should be unique within country");
                }
            }

            if (codeValuePROVINCE != null) {
                List<String> defAreaRowids = helper.getAltCodeWithinParents(externalResources.getRowidObjectPROVINCE(), codeValuePROVINCE, definedArea.getParentWithActiveRel().getRowidObject());
                if ((defAreaRowids.size() > 1) || (defAreaRowids.size() == 1 && !defAreaRowids.contains(definedArea.getRowidObject()))) {
                    throw new MaerskValidationException("PROVINCE alternate code should be unique within country");    
                }
            }
            if(codeValueISOTRTRY == null){
            	throw new MaerskValidationException("ISO TERRITORY is required for State/Prov");
            }
            
            String defAreaName1 = helper.getOtherDefAreaWithAltCode(definedArea, externalResources.getRowidObjectHsudNum(), codeValueHusdnum);
            if (defAreaName1 != null) {                
                throw new MaerskValidationException("HUSD code must be unique within the all objects of the same geography type. Found [" + defAreaName1 + "] of type [" + defAreaType.getCode() + "]");
            }
        }

        
        if (defAreaType == DefAreaType.CITY || defAreaType == DefAreaType.SUB_CITY) {
        	String olsonTimeZone ="";
        	 List<BDDObject> cityDetails = bddObject.getChildren("CityDetails");
        	 List<BDDObject> altCodes = bddObject.getChildren("AlternateCode");
        	 String mdmORSid = this.getOperationContext().getValue("ors id").toString();
     		Connection conn = this.getDatabaseConnection(mdmORSid);
        	 String cityrowid = bddObject.getRowId().toString().trim();
        	 LOG.info("cityDetails!=null && cityDetails.size()>0" + cityDetails!=null && cityDetails.size()>0);
        	 if(cityDetails!=null && cityDetails.size()>0){
        		for(BDDObject cityDetail:cityDetails ){
        	 String isMaerskCityFlag=cityDetail.getValue(IDDConstants.MDM_TABLE_GDACITY_COLUMN_NAME_MAERSK_CITY).toString().trim();
        	         	 LOG.info("C_GDA_CITY|MAERSK_CITY" + cityDetails +  "isMaerskCityFlag" +isMaerskCityFlag +"test" + cityDetails.get(0).getValue("C_GDA_CITY|MAERSK_CITY").toString().trim());
        	 
        	 olsonTimeZone=(String) cityDetails.get(0).getValue(IDDConstants.MDM_TABLE_GDACITY_COLUMN_NAME_OLSONTZ);
        	 LOG.info("C_GDA_CITY|OLSON_TZ" + cityDetail +  "OLSON_TZ" +olsonTimeZone +"test1" + cityDetail.getValue(IDDConstants.MDM_TABLE_GDACITY_COLUMN_NAME_OLSONTZ));
        	 if(!bddObject.isRemoved()){
        		 if(olsonTimeZone == null){
        			 throw new MaerskValidationException("Found OLSON Time zone value is blank ,Please update it");
        		 }
        		}
        		}
        	
        	         
        	// in case of delete RKTS code for Maersk/Non-Maersk City
        	//Comment temp for Testing
        	/*for(BDDObject altCode: altCodes){
        	  String AltCodeRowid = altCode.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim(); 
        	  
        		if(altCode.isRemoved()){
        			LOG.info("altCode.isRemoved" + altCode + "codeValueRKTS" + codeValueRKTS + "AltCodeRowid" + AltCodeRowid );
        			if(AltCodeRowid.equalsIgnoreCase(getRktsRowid(conn))){
        				if (isMaerskCityFlag.equalsIgnoreCase("Y")) {              
        	              	throw new MaerskValidationException("RKTS Code not Found for City/SubCity,Maersk City Indicator should be Unchecked");
        	        		}
        				
        			}
        			
        		}
        		if(!altCode.isRemoved()){
        		if (codeValueRKTS != null && isMaerskCityFlag.equalsIgnoreCase("N")) {              
                  	throw new MaerskValidationException("RKTS Code Found for City/SubCity,Maersk City Indicator should be checked");
            		}
            	
            	if (codeValueRKTS == null && isMaerskCityFlag.equalsIgnoreCase("Y")) {              
                  	throw new MaerskValidationException("RKTS Code not Found for City/SubCity,Maersk City Indicator should be Unchecked");
            		}
        		}
        		
        	}*/
        	 }
        	
            // For newly created cities, if model code is not a reserved value,
            // let's make sure that it matches to at least one existing RKST code
            if (definedArea.isCreated() && !codeValueMODEL.equals(IDDConstants.DATA_CITY_MODEl_CODE_RESERVED_VALUE)) {
                if (!SaveHandlerHelper.partialEquals(codeValueMODEL, codeValueRKST, 2)) {
                    throw new MaerskValidationException("Model Code first two letters must be same as the RKST code first two letters");
                }

                List<String> rowidObjects = helper.getActiveAltCodeRowidObjects(externalResources.getRowidObjectRKST(), codeValueMODEL, null, 1);
                if (rowidObjects.isEmpty()) {
                    throw new MaerskValidationException("Invalid Model code. Model code must be a RKST code of an existing and active location");
                }
            }

            String parentRowid = helper.getActiveParentRowid(DefAreaType.COUNTRY, definedArea);
            if (parentRowid == null) {
                throw new MaerskValidationException("Failed to find COUNTRY parent for " + definedArea.getName());
            }
            List<BasicAltCode> parentAltCodes = helper.getAltCodes(parentRowid);

            String parentCodeValueRKST = SaveHandlerHelper.getAlternateCodeValue(parentAltCodes, externalResources.getRowidObjectRKST());
            if (parentCodeValueRKST == null) {
                throw new MaerskValidationException("Parent COUNTRY does not have RKST code");
            } else if (!SaveHandlerHelper.partialEquals(parentCodeValueRKST, codeValueRKST, 2)) {
                throw new MaerskValidationException("First two character of RKST code must be same as RKST code of the country. Country RKST code is: " + parentCodeValueRKST);
            }
            /*************Richa Change for HSUD Country Cd********/
            if (codeValueHusd != null) {
            if (!SaveHandlerHelper.partialEquals(parentCodeValueRKST, codeValueHusd, 2)) {
                throw new MaerskValidationException("First two character of HSUD code must be same as RKST code of the country. Country RKST code is: " + parentCodeValueRKST);
            }
            
          }
            if (codeValueLns !=null) {
                               
                if (!SaveHandlerHelper.partialEquals(parentCodeValueRKST, codeValueLns, 2)) {
                    throw new MaerskValidationException("First two character of LNS code must be same as RKST code of the country. Country RKST code is: " + parentCodeValueRKST);
                }
                }
            /*************Richa Change for HSUD Country Cd********/
            
        } else if (defAreaType == DefAreaType.SITE) {
            // For newly created cities, if mode code is not a reserved value,
            // let's make sure that it matches to at least one existing RKST code
            if (definedArea.isCreated() && !codeValueMODEL.equals(IDDConstants.DATA_SITE_MODEl_CODE_RESERVED_VALUE)) {
                if (!SaveHandlerHelper.partialEquals(codeValueMODEL, codeValueRKST, 2)) {
                    throw new MaerskValidationException("Model Code first two letters must be same as the RKST code first two letters");
                }

                List<String> rowidObjects = helper.getActiveAltCodeRowidObjects(externalResources.getRowidObjectRKST(), codeValueMODEL, null, 1);
                if (rowidObjects.isEmpty()) {
                    throw new MaerskValidationException("Invalid Model code. Model code must be a RKST code of an existing and active location");
                }                   
            }

            String parentRowid = helper.getActiveParentRowid(DefAreaType.CITY, definedArea);
            if (parentRowid == null) {
                throw new MaerskValidationException("Failed to find CITY parent for " + definedArea.getName());
            }
            List<BasicAltCode> parentAltCodes = helper.getAltCodes(parentRowid);

            String parentCodeValueRKST = SaveHandlerHelper.getAlternateCodeValue(parentAltCodes, externalResources.getRowidObjectRKST());
            if (parentCodeValueRKST == null) {
                throw new MaerskValidationException("Parent CITY does not have RKST code");
            } else if (!SaveHandlerHelper.partialEquals(parentCodeValueRKST, codeValueRKST, 5)) {
                throw new MaerskValidationException("First five character of RKST code must be same as RKST code of the city. City RKST code is: " + parentCodeValueRKST);
            }

            String parentCodeValueRKTS = SaveHandlerHelper.getAlternateCodeValue(parentAltCodes, externalResources.getRowidObjectRKTS());
           /* if (parentCodeValueRKTS == null) {
                throw new MaerskValidationException("Parent CITY does not have RKTS code");
            } else if (!SaveHandlerHelper.partialEquals(parentCodeValueRKTS,codeValueRKTS, 3)) {
                throw new MaerskValidationException("First three character of RKTS code must be same as RKTS code of the city. City RKTS code is: " + parentCodeValueRKTS);
            }*///Richa's Commented
            
            if ((parentCodeValueRKTS !=null && codeValueRKTS != null) && (!SaveHandlerHelper.partialEquals(parentCodeValueRKTS,codeValueRKTS, 3))) {
                throw new MaerskValidationException("First three character of RKTS code must be same as RKTS code of the city. City RKTS code is: " + parentCodeValueRKTS);
            }
            

            // Let's check POSTAL_CODE field correctness
            String sitePostalCode = definedArea.getSitePostalCode();
            if (sitePostalCode != null) {
                List<String> parentRowids = helper.getSiteParentRowids(definedArea);
                List<String> postalCodeRowids = helper.getAltCodeWithinParents(externalResources.getRowidObjectPOSTCODE(), sitePostalCode, parentRowids);
                if (postalCodeRowids.isEmpty()) {
                    throw new MaerskValidationException("Invalid Postal Code. At least one of site parents (city, state or country) should have associated POSTAL_CODE child with the same value");    
                }

                
            }
        } else if (defAreaType == DefAreaType.POSTAL_CODE) {
            if (codeValuePOSTCODE == null) {
                throw new MaerskValidationException("POSTAL_CODE code is required for PostalCode geography items");
            }
            /**************SubCityArea Change2********/
        }/*else if (defAreaType == DefAreaType.SUB_CITY ) {
            // For newly created cities, if model code is not a reserved value,
            // let's make sure that it matches to at least one existing RKST code
            if (definedArea.isCreated() && !codeValueMODEL.equals(IDDConstants.DATA_CITY_MODEl_CODE_RESERVED_VALUE)) {
                if (!SaveHandlerHelper.partialEquals(codeValueMODEL, codeValueRKST, 2)) {
                    throw new MaerskValidationException("Model Code first two letters must be same as the RKST code first two letters");
                }

                List<String> rowidObjects = helper.getActiveAltCodeRowidObjects(externalResources.getRowidObjectRKST(), codeValueMODEL, null, 1);
                if (rowidObjects.isEmpty()) {
                    throw new MaerskValidationException("Invalid Model code. Model code must be a RKST code of an existing and active location");
                }
            }
        }*/
        /**************SubCityArea Change2********/

    }

    private void validateAttemptOfDeactivation(DefinedArea definedArea) throws MaerskValidationException, MaerskConfException {
        if (!definedArea.isCreated() && definedArea.isColumnChanged(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)) {
            // attempt to invalidate existing defined area
            if (!IDDConstants.DATA_TABLE_DEFAREA_ACTIVE_FLAG_YES_VALUE.equals(definedArea.getStatusString())) {
                List<String> activeChildNames = helper.getActiveChildrenNames(definedArea, IDDConstants.NUMBER_OF_VIOLATED_CHILDREN_TO_RETURN);
                if (!activeChildNames.isEmpty()) {
                    throw new MaerskValidationException("It is impossible to invalidate this geography items, because it has at least following active children: " + activeChildNames);
                }
            }
        }
    }

    private String checkIfCofirmationIsRequired(DefinedArea definedArea) throws MaerskConfException {
        // In case of edit operation let's check and warn if timezone or name or status has been chaged
        if (definedArea.isChanged() && !definedArea.isCreated() && !definedArea.isRemoved()) {
            StringBuilder message = new StringBuilder();

            switch (definedArea.getDefAreaType()) {
                case COUNTRY:
                case PROVINCE:
                case COUNTY: {
                    if (definedArea.isColumnChanged(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE)) {
                        message.append("Timezone has been changed. ");
                    }
                    // no break here - continue with other checks
                }
                case CONTINENT:
                case SITE: {
                    if (definedArea.isColumnChanged(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)) {
                        message.append("Name has been changed. ");
                    }

                    if (definedArea.isColumnChanged(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)) {
                        boolean currentStatus = IDDConstants.DATA_TABLE_DEFAREA_ACTIVE_FLAG_YES_VALUE.equals(definedArea.getStatusString());
                        String statusText = currentStatus ? "Active" : "Inactive";
                        message.append("Status has been changed to ").append(statusText).append(". ");
                    }
                    break;
                }
                case CITY:
                case POSTAL_CODE:
                case BDA:
                    // Do nothing
            }

            if (message.length() > 0) {
                return ". " + message.toString() + "All child geography items will be affected";
            }
        }
        return null;
    }
    public boolean checkIfPostalCodeMandatoryForCountry(DefinedArea definedArea) throws MaerskConfException, MaerskValidationException {
    	if (definedArea.isCreated()){
    		if (helper.getPostalCodeMandatoryFlagCountry(definedArea.getParentWithActiveRel().getRowidObject()).equalsIgnoreCase("M")){
        		return true;
          	}
            else{
            	return false;
            }
    	}
    	else{
    			return false;
    		}	
    }

    public OperationResult afterEverything(BDDObject bddObject) {
    	String mdmORSid = this.getOperationContext().getValue("ors id").toString();
    	Connection conn = this.getDatabaseConnection(mdmORSid);
    	if ((bddObject.getObjectName().equalsIgnoreCase("Continent")) ||
    		(bddObject.getObjectName().equalsIgnoreCase("Country"))||(bddObject.getObjectName().equalsIgnoreCase("StateProv"))
    		||(bddObject.getObjectName().equalsIgnoreCase("City"))||(bddObject.getObjectName().equalsIgnoreCase("PostalCode"))
    		||(bddObject.getObjectName().equalsIgnoreCase("BusinessDefinedArea"))
    		||(bddObject.getObjectName().equalsIgnoreCase("CitySubArea")))
    	{
    		String Action = geoAction(bddObject);
			LOG.info("Geo Transaction Case:" + Action);
			
			String geoRowId =	bddObject.getRowId();
			String geoRowIdFinal = geoRowId.replaceAll(" ", "%20");
		  LOG.info("geoRowId value:" + geoRowId + "Value of");
		  LOG.info("geoRowId value1:" + geoRowId.replaceAll(" ", "%20"));
		  
		  CloseableHttpClient httpclient = HttpClients.createDefault();
			
			RequestBuilder reqbuilder = RequestBuilder.post().setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			
			Properties prop = new Properties();
			
			InputStream is = SaveHandler.class.getResourceAsStream("/com/maersk/mdm/taskdata/userexit/EMPGEO.properties");
		  
			try {
				prop.load(is);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String str = prop.getProperty("endpoint.geoemp.service.url");
			
            LOG.info("Property str:" + str);			
			
			RequestBuilder reqbuilder1 = reqbuilder.setUri(str+Action+"&geoRowID="+geoRowIdFinal);
			
			LOG.info("URI=" + reqbuilder1.getUri());
			
			
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
				LOG.info(EntityUtils.toString(httpresponse.getEntity()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			 LOG.info(httpresponse.getStatusLine());
    	}
    	
    	if (bddObject.getObjectName().equalsIgnoreCase("Site")){    		
    		List<BDDObject> siteAltCD = bddObject.getChildren("AlternateCode");
    		LOG.info("Value of siteAltCD check object r : " + siteAltCD);
    		if (siteAltCD != null && siteAltCD.size() > 0) {
    		for (BDDObject siteGeoID : siteAltCD){
    		String AltCodeTyp = siteGeoID.getValue("C_ALT_CODE|TYP_TYPE_ROWID").toString().trim();
    		LOG.info("Value of AltCodeTyp check object r : " + AltCodeTyp);
    		String AltCodeValue = siteGeoID.getValue("C_ALT_CODE|CODE").toString().trim();
    		LOG.info("Value of AltCodeValue check object r : " + AltCodeValue);
    		String comaprerowid = getGeoAltCdRowid(conn).trim();
    		LOG.info("Value of comaprerowid check object r : " + comaprerowid);
    		if (AltCodeTyp.equalsIgnoreCase(comaprerowid)){
    			Utility utl = new Utility();
    			String fctRowid = utl.getFCTRowid(AltCodeValue,mdmORSid);
    			LOG.info("Value of fctRowid check object r : " + fctRowid);
    			String Action = geoAction(bddObject);
    			LOG.info("Geo Transaction Case:" + Action);
    			
    			
    			String fctRowIdFinal = fctRowid.replaceAll(" ", "%20");
    		  LOG.info("fctRowid value:" + fctRowid + "Value of");
    		  LOG.info("fctRowid value1:" + fctRowid.replaceAll(" ", "%20"));
    		  
    		  CloseableHttpClient httpclient = HttpClients.createDefault();
    			
    			RequestBuilder reqbuilder = RequestBuilder.post().setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    			
    			Properties prop = new Properties();
    			
    			InputStream is = SaveHandler.class.getResourceAsStream("/com/maersk/mdm/taskdata/userexit/EMPFCT.properties");
    		  
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
    			
                LOG.info("Property str:" + str);			
    			
    			RequestBuilder reqbuilder1 = reqbuilder.setUri(str+Action+"&fctRowID="+fctRowIdFinal);
    			
    			LOG.info("URI=" + reqbuilder1.getUri());
    			
    			
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
    				LOG.info(EntityUtils.toString(httpresponse.getEntity()));
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		   
    			 LOG.info(httpresponse.getStatusLine());
        	}
    			
    		}
    		
    		}
    		
    	}
        return OperationResult.OK;
    }

    public OperationResult afterSave(BDDObject bddObject) {
        return OperationResult.OK;
    }
    
    public String geoAction(BDDObject bddObject) {
		if (bddObject.isCreated()) {
			return "Create";
		} else {
			return "Update";
		}
	
	}
    public OperationType getOperationType() {
        return OperationType.SAVE_OPERATION;
    }

    SiperianResponse executeRequest(SiperianRequest request) {
        prepareRequest(request);
        return processRequest(request);
    }    
       
    /******************    200293: ALTCODE STATIC LIST  START ***********************/
	private void GenerateNotAllowedList() {
		    LOG.info("Inside GenerateList Started");	       
	        collection.add("ALT_CODE.RKST");  
	        //collection.add("ALT_CODE.RKTS");  
	        collection.add("ALT_CODE.GEOID");         
	        LOG.info("Inside GenerateList ended");	
	     
	    } 
	
	private void GenerateNotAllowedListCountry() {
	    LOG.info("Inside GenerateList Started");	       
        collection1.add("ALT_CODE.RKST");  
        collection1.add("ALT_CODE.RKTS");  
        collection1.add("ALT_CODE.GEOID");         
        LOG.info("Inside GenerateList ended");	
     
    }
	
	private void GenerateAllowedList() {
	    CountryList.add("United States");
        CountryList.add("Virgin Islands (US)");
        CountryList.add("Guam");
        CountryList.add("Puerto Rico");
        
    } 
	/******************    200293: ALTCODE STATIC LIST  END ***********************/
	/******************    200293: Get Alt code type name  start ***********************/
	private String getAltCodetypeName(String AltCodeRowid , OperationContext operationContext ) {
			String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", AltCodeRowid, "CODE", "ROWID_OBJECT");
			String typCdValue=Utility.getValueFromDB(operationContext, getTypeCdQuery);
			LOG.info("typCdValue: " + typCdValue);
			return typCdValue;
}
	/******************    200293: Get Alt code type name  ends ***********************/
	
}
