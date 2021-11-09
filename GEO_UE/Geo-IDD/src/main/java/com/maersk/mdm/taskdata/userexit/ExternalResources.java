package com.maersk.mdm.taskdata.userexit;


import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;
import com.siperian.sif.message.Record;

import java.util.List;

/**
 * Helper class to keep values of all external constants (resource bundles)
 */
public class ExternalResources {

    private String rowidObjectGEOID;

    private String rowidObjectRKST;

    private String rowidObjectRKTS;

    private String rowidObjectMODEL;

    private String rowidObjectSTATE;

    private String rowidObjectPROVINCE;

    private String rowidObjectPOSTCODE;
    
    private String rowidObjectBDACODE;
    
    private String rowidObjectContinentCode;
    
    private String rowidObjectIataCode;
    
    private String rowidObjectHsudCode;
    
    private String rowidObjectLnsCode;
    
    private String rowidObjectHsudNum;
    
    
    //Added for ISO TERR Mandatory validation #28MAY2013
    private String rowidObjectISOTRTRY;
    
   
	private ExternalResources(BDDMessagesLocalizationGate localizationGate) throws MaerskConfException {
        rowidObjectGEOID = getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_GEOID_TYPE, localizationGate);
        rowidObjectRKST = getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_RKST_TYPE, localizationGate);
        rowidObjectRKTS = getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_RKTS_TYPE, localizationGate);
        rowidObjectMODEL = getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_MODEL_TYPE, localizationGate);
        rowidObjectSTATE = getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_STATE_TYPE, localizationGate);
        rowidObjectPROVINCE = getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_PROVINCE_TYPE, localizationGate);
        rowidObjectPOSTCODE = getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_POSTCODE, localizationGate);
        rowidObjectIataCode= getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_IATA_CODE, localizationGate);
        rowidObjectHsudCode= getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_HSUD_CODE, localizationGate);
        rowidObjectLnsCode= getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_LNS_CODE, localizationGate);
        rowidObjectHsudNum= getRequiredExternalResource(IDDConstants.EXT_RESOURCE_ROWID_HSUDNUM_CODE, localizationGate);
        checkSelfConsistency();
    }

    public ExternalResources(List<Record> records) throws MaerskConfException {
        for (Record record : records) {
            String code = SaveHandlerHelper.getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_CODE);
            String rowidObject = SaveHandlerHelper.getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT);

            if (IDDConstants.DATA_TABLE_ALTCODE_GEOID.equals(code)) {
                rowidObjectGEOID = rowidObject;
            } else if (IDDConstants.DATA_TABLE_ALTCODE_RKST.equals(code)) {
                rowidObjectRKST = rowidObject;
            } else if (IDDConstants.DATA_TABLE_ALTCODE_RKTS.equals(code)) {
                rowidObjectRKTS = rowidObject;
            } else if (IDDConstants.DATA_TABLE_ALTCODE_MODEL.equals(code)) {
                rowidObjectMODEL = rowidObject;
            } else if (IDDConstants.DATA_TABLE_ALTCODE_STATE.equals(code)) {
                rowidObjectSTATE = rowidObject;
            } else if (IDDConstants.DATA_TABLE_ALTCODE_PROVINCE.equals(code)) {
                rowidObjectPROVINCE = rowidObject;
            } else if (IDDConstants.DATA_TABLE_ALTCODE_POSTCODE.equals(code)) {
                rowidObjectPOSTCODE = rowidObject;
            }
            else if (IDDConstants.DATA_TABLE_ALTCODE_BDACODE.equals(code)) {
            	rowidObjectBDACODE = rowidObject;
            }
            else if (IDDConstants.DATA_TABLE_ALTCODE_CONTINENT_CODE.equals(code))
            {
            	rowidObjectContinentCode=rowidObject;
            }
            else if (IDDConstants.DATA_TABLE_ALTCODE_ISO_TRTRY.equals(code))
            {
            	rowidObjectISOTRTRY=rowidObject;
            }
            else if (IDDConstants.DATA_TABLE_ALTCODE_IATACODE.equals(code))
            {
            	rowidObjectIataCode = rowidObject;
            }
            else if (IDDConstants.DATA_TABLE_ALTCODE_HSUDCODE.equals(code))
            {
            	rowidObjectHsudCode = rowidObject;
            }            
            else if (IDDConstants.DATA_TABLE_ALTCODE_LNSCODE.equals(code))
            {
            	rowidObjectLnsCode = rowidObject;
            }
            else if (IDDConstants.DATA_TABLE_ALTCODE_HSUDNUM.equals(code))
            {
            	rowidObjectHsudNum = rowidObject;
            }
        }
        checkSelfConsistency();
   }

    private void checkSelfConsistency() throws MaerskConfException {
        if (rowidObjectGEOID == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_GEOID);
        } else if (rowidObjectRKST == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_RKST);
        } else if (rowidObjectRKTS == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_RKTS);
        } else if (rowidObjectMODEL == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_MODEL);
        } else if (rowidObjectSTATE == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_STATE);
        } else if (rowidObjectPROVINCE == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_PROVINCE);
        } else if (rowidObjectPOSTCODE == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_POSTCODE);
        } else if (rowidObjectIataCode == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_IATACODE);
        } else if (rowidObjectHsudCode == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_HSUDCODE);
        } else if (rowidObjectLnsCode == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_LNSCODE);
        }else if (rowidObjectHsudNum == null) {
            throw new MaerskConfException("Failed to initialize ROWID_OBJECT value of " + IDDConstants.DATA_TABLE_ALTCODE_HSUDNUM);
        }
    }

    static private String getRequiredExternalResource(String resourceKey, BDDMessagesLocalizationGate localizationGate) throws MaerskConfException {
        String value = localizationGate.getLocalizedMessage(resourceKey, new String[]{});
        if (value == null || value.trim().length() == 0 || resourceKey.equals(value)) {
            throw new MaerskConfException("Failed to detect value for external resource - " + resourceKey);
        }
        return value.trim();
    }

    public String getRowidObjectGEOID() {
        return rowidObjectGEOID;
    }

    public String getRowidObjectRKST() {
        return rowidObjectRKST;
    }

    public String getRowidObjectRKTS() {
        return rowidObjectRKTS;
    }

    public String getRowidObjectMODEL() {
        return rowidObjectMODEL;
    }

    public String getRowidObjectSTATE() {
        return rowidObjectSTATE;
    }

    public String getRowidObjectPROVINCE() {
        return rowidObjectPROVINCE;
    }

    public String getRowidObjectPOSTCODE() {
        return rowidObjectPOSTCODE;
    }
    public String getRowidObjectBDACODE() {
        return rowidObjectBDACODE;
    }
    
    public String getRowidObjectContinentCode() {
        return rowidObjectContinentCode;
    }
    public String getRowidObjectISOTRTRY() {
		return rowidObjectISOTRTRY;
	}
	public String getRowidObjectIataCode() {
		return rowidObjectIataCode;
	}
    
	public String getRowidObjectHsudCode() {
		return rowidObjectHsudCode;
	}
	
	public String getRowidObjectLnsCode() {
		return rowidObjectLnsCode;
	}
	
	public String getRowidObjectHsudNum() {
		return rowidObjectHsudNum;
	}
}
