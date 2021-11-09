package com.maersk.mdm.taskdata.userexit;

import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.dsapp.common.util.Logger;

import java.util.*;

/**
 * Model class to represent DefinedArea object and its children
 */
public class DefinedArea {

    private static final Logger LOG = Logger.getLogger(DefinedArea.class);

    final private BDDObject bddObject;

    final private DefAreaType defAreaType;

    List<DefinedAreaParent> parentDefinedAreas = new ArrayList<DefinedAreaParent>();

    private DefinedAreaParent parentWithActiveRel = null;

    private DefinedAreaParent parentWithRemovedRel = null;
    
    private DefinedAreaParent bdaParentWithActiveRel = null;
    
    private DefinedAreaParent bdaParentWithRemovedRel = null;
    
    private DefinedAreaParent bdaChildWithActiveRel = null;
   

    private DefinedAreaParent bdaChildWithRemovedRel = null;
    
    private DefinedAreaParent gdaBdaWithActiveRel = null;
    
    private DefinedAreaParent gdaBdaWithRemovedRel = null;


    final private String rowidObject;

    final private String name;

    final private String standardTimezoneRowid;

    final private List<AltCode> altCodes = new ArrayList<AltCode>();

    final private List<String> altNames = new ArrayList<String>();
    
    List<DefinedAreaParent> bdaParents = new ArrayList<DefinedAreaParent>();
    List<DefinedAreaParent> bdaChilds = new ArrayList<DefinedAreaParent>();
    
    List<DefinedAreaParent> gdaBdas = new ArrayList<DefinedAreaParent>();

    private AltCode autoGeneratedGeoId = null;

    final private String statusString;
    
    private String bdaType=null;

    // Optional field that is specific for Site only and part of SitePhysicalLocation one-2-one child
    private String sitePostalCode = null;
    private String siteAddressLine1=null;
    //Field added for site type validation
    private String siteType=null;
    
  	

    public DefinedArea(BDDObject bddObject) throws MaerskConfException {
        this.bddObject = bddObject;

        String rowidObjectString = (String) bddObject.getSystemValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT);
        rowidObject = (rowidObjectString != null && rowidObjectString.trim().length() > 0) ? rowidObjectString.trim() : null;
        name = (String) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME);
        standardTimezoneRowid = SaveHandlerHelper.safeGetStringValue(bddObject, IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE);
        statusString = (String) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS);
        List<BDDObject> bdaTypes = bddObject.getChildren(IDDConstants.IDD_AREANAME_BDA_TYPE);
        if (bdaTypes != null) {
        	
            for (BDDObject bdaTypeObject : bdaTypes) {
            	 bdaType = (String)bdaTypeObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_BDATYPE );
            }
        }
        String defAreaTypeString = bddObject.getObjectName();
        defAreaType = DefAreaType.findAreaType(defAreaTypeString);
        if (defAreaType == null) {
            throw new MaerskConfException("Invalid configuration. Failed to find defined area type with code - " + defAreaTypeString + " for bbdObject - " + bddObject);
        }

        List<BDDObject> parentDefAreaBDDObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_HIERARCHY);
        if (parentDefAreaBDDObjects != null) {
            for (BDDObject parentDefAreaBDDObject : parentDefAreaBDDObjects) {
                DefinedAreaParent definedAreaParent = new DefinedAreaParent(parentDefAreaBDDObject);
                if (definedAreaParent.isRemoved()) {
                    parentWithRemovedRel = definedAreaParent;
                } else if (definedAreaParent.isRelationActive()) {
                    parentWithActiveRel = definedAreaParent;
                }
                parentDefinedAreas.add(definedAreaParent);
            }
        }

        List<BDDObject> bdaParentBDDObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_BDA_PARENT);
        if (bdaParentBDDObjects != null) {
            for (BDDObject bdaParentBDDObject : bdaParentBDDObjects) {
                DefinedAreaParent bdaParent = new DefinedAreaParent(bdaParentBDDObject);
                if (bdaParent.isRemoved()) {
                    bdaParentWithRemovedRel = bdaParent;
                } else if (bdaParent.isRelationActive()) {
                    bdaParentWithActiveRel = bdaParent;
                }
                bdaParents.add(bdaParent);
                
            }
        }
        List<BDDObject> gdaBdaBDDObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_GDA_BDA);
        if (gdaBdaBDDObjects != null) {
            for (BDDObject gdaBdaBDDObject : gdaBdaBDDObjects) {
                DefinedAreaParent gdaBda = new DefinedAreaParent(gdaBdaBDDObject);
                if (gdaBda .isRemoved()) {
                	gdaBdaWithRemovedRel = gdaBda ;
                } else if (gdaBda .isRelationActive()) {
                    gdaBdaWithActiveRel = gdaBda ;
                }
                gdaBdas.add(gdaBda);
                
            }
        }
        List<BDDObject> bdaChildBDDObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_BDA_CHILD);
        if (bdaChildBDDObjects != null) {
            for (BDDObject bdaChildBDDObject : bdaChildBDDObjects) {
                DefinedAreaParent bdaChild = new DefinedAreaParent(bdaChildBDDObject);
                if (bdaChild.isRemoved()) {
                    bdaChildWithRemovedRel = bdaChild;
                } else if (bdaChild.isRelationActive()) {
                    bdaChildWithActiveRel = bdaChild;
                }
                bdaChilds.add(bdaChild);
            }
        }
        List<BDDObject> altCodeObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_ALT_CODE);
        if (altCodeObjects != null) {
            for (BDDObject altCodeObject : altCodeObjects) {
                altCodes.add(new AltCode(altCodeObject));
            }
        } else {
            LOG.debug("Failed to detect child subject area - " + IDDConstants.IDD_AREANAME_ALT_CODE + " for " + name);
        }

        List<BDDObject> altNameObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_ALT_NAME);
        if (altNameObjects != null) {
            for (BDDObject altNameObject : altNameObjects) {
                String altName = (String) altNameObject.getValue(IDDConstants.MDM_TABLE_ALTNAME_COLUMN_NAME_NAME);
                if (altName == null || altName.trim().length() == 0) {
                    throw new MaerskConfException("Missing alt name value for " + name);
                } else {
                    altNames.add(altName.trim());
                }
            }
        } else {
            LOG.debug("Failed to detect child subject area - " + IDDConstants.IDD_AREANAME_ALT_NAME + " for " + getName());
        }

        List<BDDObject> sitePhysLocObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_SITE_PHYSICAL_LOC);
        if (sitePhysLocObjects != null && !sitePhysLocObjects.isEmpty()) {
            BDDObject sitePhysLoc = sitePhysLocObjects.iterator().next();            
            String postalCode = SaveHandlerHelper.safeGetStringValue(sitePhysLoc, IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_POSTAL_CODE);
            String addressLine1 = SaveHandlerHelper.safeGetStringValue(sitePhysLoc, IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_ADDR_LN1);
            if (postalCode != null && postalCode.trim().length() != 0) {
                this.sitePostalCode = postalCode.trim();
            }
            if (addressLine1 != null && addressLine1.trim().length() != 0) {
                this.siteAddressLine1 = addressLine1.trim();
            }
        }
        
        List<BDDObject> siteTypeObjects = bddObject.getChildren(IDDConstants.IDD_AREANAME_SITE_TYPE);
        if (siteTypeObjects != null && !siteTypeObjects.isEmpty()) {
            BDDObject siteType = siteTypeObjects.iterator().next();            
            String strSiteType = SaveHandlerHelper.safeGetStringValue(siteType, IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_SITE_TYPE);
             if (strSiteType != null && strSiteType.trim().length() != 0) {
                this.siteType = strSiteType.trim();
                LOG.debug("Site Type:" + strSiteType.trim());
             }
        }
    }

    public List<DefinedAreaParent> getParentDefinedAreas() {
        return parentDefinedAreas;
    }
    
    public List<DefinedAreaParent> getBdaParents(){
    	return bdaParents;
    }
    
    public List<DefinedAreaParent> getGdaBdas(){
    	return gdaBdas;
    }
    
    public List<DefinedAreaParent> getBdaChilds(){
    	return bdaChilds;
    }
    public DefAreaType getDefAreaType() {
        return defAreaType;
    }

    public boolean isCreated() {
        return bddObject.isCreated();
    }

    public boolean isChanged() {
        return bddObject.isChanged();
    }

    public boolean isRemoved() {
        return bddObject.isRemoved();
    }

    public DefinedAreaParent getParentWithActiveRel() {
        return parentWithActiveRel;
    }

    public DefinedAreaParent getParentWithRemovedRel() {
        return parentWithRemovedRel;
    }

    public List<AltCode> getAltCodes() {
        return altCodes;
    }

    public int getNumberOfActiveRelsToParent() {
        int activeRelCounter = 0;
        for (DefinedAreaParent definedAreaParent : getParentDefinedAreas()) {
            if (definedAreaParent.isRelationActive()) {
                ++activeRelCounter;
            }
        }
        return activeRelCounter;
    }

    public String getRowidObject() {
        return rowidObject;
    }

    public String getName() {
        return name;
    }

    public String getStandardTimezoneRowid() {
        return standardTimezoneRowid;
    }

    public String encodeAltNames() throws MaerskConfException {
        StringBuilder result = new StringBuilder();
        for (String altName : altNames) {
            result.append(altName).append(IDDConstants.CLEANSE_ENCODE_SEPARATOR);
        }
        return result.toString();
    }

    public String encodeAltCodes() throws MaerskConfException {
        StringBuilder result = new StringBuilder();
        for (AltCode altCode : altCodes) {
            result.append(altCode.getCodeTypeId()).append(IDDConstants.CLEANSE_ENCODE_SEPARATOR);
            result.append(altCode.getCodeValue()).append(IDDConstants.CLEANSE_ENCODE_SEPARATOR);
        }
        return result.toString();
    }

    public void addGeoIdChild(String geoIdCode, String geoIdTypeRowidObject) throws MaerskConfException {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_TYPEID, geoIdTypeRowidObject);
        values.put(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_VALUE, geoIdCode);
        BDDObject altCodeObject = bddObject.createChild(IDDConstants.IDD_AREANAME_ALT_CODE, values);
        autoGeneratedGeoId = new AltCode(altCodeObject);
        altCodes.add(autoGeneratedGeoId);
    }

    public void silentlyRemoveAutoGeneratedGeoId() {
        if (autoGeneratedGeoId != null) {
            try {
                altCodes.remove(autoGeneratedGeoId);
                bddObject.removeChild(autoGeneratedGeoId.getAltCodeObject());
            } catch (Exception e) {
                LOG.error("Failed to remove auto-generated GEOID object as a part of rollback operation", e);
            }
        }
    }    

    public int getNumberOfChangedAltCodes(String codeTypeId) {
        int counter = 0;
        for (AltCode altCode : altCodes) {
            if (altCode.getCodeTypeId().equals(codeTypeId)) {
                if (altCode.getAltCodeObject().isChanged()) {
                    ++counter;
                }
            }
        }
        return counter;
    }
    public String getBDAType()
    {
    	return bdaType;
    }

    public List<String> getAltNames() {
        return altNames;
    }

    public String getStatusString() {
        return statusString;
    }

    public boolean isColumnChanged(String columnName) {
        return bddObject.getChangedColumns().contains(columnName);
    }

    public String getSitePostalCode() {
        return sitePostalCode;
    }
    
    public String getSiteAddressLine1() {
  		return siteAddressLine1;
  	}
    
    public String getSiteType() {
		return siteType;
	}
}
