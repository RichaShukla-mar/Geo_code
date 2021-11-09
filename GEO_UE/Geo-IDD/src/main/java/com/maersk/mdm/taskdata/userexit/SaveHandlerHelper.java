package com.maersk.mdm.taskdata.userexit;


import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.dsapp.common.util.Logger;
import com.siperian.sif.message.Field;
import com.siperian.sif.message.HubStateIndicator;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.mrm.CleanseRequest;
import com.siperian.sif.message.mrm.CleanseResponse;
import com.siperian.sif.message.mrm.SearchQueryRequest;
import com.siperian.sif.message.mrm.SearchQueryResponse;

import java.util.*;

/**
 * Helper to work with SifAPI (cleanse, search requests, etc.)
 */
public class SaveHandlerHelper {

    final private SaveHandler saveHandler;

    private static final Logger LOG = Logger.getLogger(SaveHandlerHelper.class);


    public SaveHandlerHelper(SaveHandler saveHandler) {
        this.saveHandler = saveHandler;
    }

    public String generateId() throws MaerskConfException {
        CleanseRequest cleanseRequest = new CleanseRequest();
        cleanseRequest.setCleanseFunctionName(IDDConstants.MDM_CL_FUNC_NAME_GEOID);
        cleanseRequest.setRecord(new Record());
        CleanseResponse cleanseResponse;
        try {
            cleanseResponse = (CleanseResponse) saveHandler.executeRequest(cleanseRequest);
        } catch (Exception e) {
            String errorMessage = "While generatig GEO_ID failed to invoke cleanse function - " + cleanseRequest.getCleanseFunctionName();
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        String validationMessage = null;
        if (cleanseResponse != null && cleanseResponse.getMessage() != null && cleanseResponse.getRecord() != null) {
            Record record = cleanseResponse.getRecord();
            Field validationField = record.getField(IDDConstants.MDM_CL_FUNC_GEOID_FIELD_VALIDATION_STATUS);
            if (validationField != null && validationField.getStringValue() != null) {
                validationMessage = validationField.getStringValue().trim();
            } else {
                Field generatedIdField = record.getField(IDDConstants.MDM_CL_FUNC_GEOID_FIELD_GENERATED_ID);
                if (generatedIdField != null) {
                    return generatedIdField.getStringValue();
                }
            }
        }
        String errorMessage = "Failed generatig GEO_ID. cleanse function [" + cleanseRequest.getCleanseFunctionName() + "] returned: " + validationMessage;
        throw new MaerskConfException(errorMessage);
    }

    public ExternalResources initializeCodeTypeIDs() throws MaerskConfException {
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(100); 
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_TYPE_TABLE);


        StringBuilder filterCriteria = new StringBuilder();
        filterCriteria.append(IDDConstants.MDM_TABLE_TYPES_COLUMN_NAME_CODE).append(" IN (");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_GEOID).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_RKST).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_RKTS).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_MODEL).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_STATE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_PROVINCE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_BDACODE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_POSTCODE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_CONT_CODE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_ISO_TRTRY).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_IATACODE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_HSUDCODE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_LNSCODE).append("',");
        filterCriteria.append("'").append(IDDConstants.DATA_TABLE_ALTCODE_HSUDNUM).append("')");
        searchRequest.setFilterCriteria(filterCriteria.toString());

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While generating GEO_ID. failed to invoke search request that retrieves ROWID_OBJECT values of ALT_CODE types";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
            return new ExternalResources((List<Record>) searchResponse.getRecords());
        }
        throw new MaerskConfException("While generating GEO_ID. Search request that retrieves ROWID_OBJECT values of ALT_CODE types returned empty resultset");
    }

    public String invokeComplexValidation(DefinedArea definedArea) throws MaerskConfException {
        CleanseRequest cleanseRequest = new CleanseRequest();
        cleanseRequest.setCleanseFunctionName(IDDConstants.MDM_CL_FUNC_NAME_VALIDATE);
        Record record = new Record();
        record.setField(new Field(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_PARENT_ID, definedArea.getParentWithActiveRel().getRowidObject()));
        record.setField(new Field(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_AREA_ID, definedArea.getRowidObject()));
        record.setField(new Field(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_AREA_NAME, definedArea.getName()));
        record.setField(new Field(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_AREA_TYPE, definedArea.getDefAreaType().getCode()));
        record.setField(new Field(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_TIMEZONE, definedArea.getStandardTimezoneRowid()));
        record.setField(new Field(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_ALT_NAMES, definedArea.encodeAltNames()));
        record.setField(new Field(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_ALT_CODES, definedArea.encodeAltCodes()));
        cleanseRequest.setRecord(record);

        CleanseResponse cleanseResponse;
        try {
            cleanseResponse = (CleanseResponse) saveHandler.executeRequest(cleanseRequest);
        } catch (Exception e) {
            String errorMessage = "While validating defined area [" + definedArea.getName() + "] failed to invoke cleanse function - " + cleanseRequest.getCleanseFunctionName();
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        if (cleanseResponse != null && cleanseResponse.getMessage() != null && cleanseResponse.getRecord() != null) {
            Record cleansedRecord = cleanseResponse.getRecord();
            Field validationField = cleansedRecord.getField(IDDConstants.MDM_CL_FUNC_VALIDATE_FIELD_VALIDATION_STATUS);

            if (validationField != null) {
                return validationField.getStringValue();
            }
        }
        return null;
    }

    static public String safeGetStringValue(BDDObject bddObject, String fieldName) {
        String result = null;
        try {
            result = (String) bddObject.getValue(fieldName);
        } catch (Exception e) {
            LOG.debug("Failed to retrieve value for the field. It will be ignored. Field name - [" + fieldName + "]. BDDObject - " + bddObject);
        }
        return result;
    }

    public List<BasicAltCode> getAltCodes(String defAreaRowid) throws MaerskConfException {
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(50);
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_ALT_CODE);


        String filterCriteria = IDDConstants.MDM_PACKAGE_COLUMN_NAME_DEF_AREA_ROWID + " = '" + defAreaRowid + "'";
        searchRequest.setFilterCriteria(filterCriteria);

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While validating alt. codes failed to invoke search request that retrieves alt codes for parent with rowid - " + defAreaRowid;
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        List<BasicAltCode> altCodes = new ArrayList<BasicAltCode>();
        if (searchResponse != null && searchResponse.getRecords() != null) {
            for (Record record : (List<Record>)searchResponse.getRecords()) {
                String rowidObject = getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT);
                String codeTypeId = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_TYP_TYPE_ROWID);
                String codeValue = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_CODE);
                altCodes.add(new BasicAltCode(rowidObject, codeTypeId, codeValue));
            }
        }
        return altCodes;
    }

    /**
     * Search for the ROWID_OBJECT values of ALT_CODEs with particular codeTypeID and codeValue and optional value of
     * foreign key to defined area
     *
     * @param codeTypeRowid rowid of code type
     * @param codeValue code value
     * @param defAreaRowid optional ROWID_OBJECT value of DefinedArea. If provided then search will take it into account
     * @param recordsToReturn number of records to return
     * @return not null list of ROWID_OBJECT values of the first {recordsToReturn} found ALT_CODE
     * @throws MaerskConfException if something went wrong
     */
    public List<String> getAltCodeRowidObjects(String codeTypeRowid, String codeValue, String defAreaRowid, int recordsToReturn) throws MaerskConfException {
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(recordsToReturn);
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_ALT_CODE);
        


        StringBuilder filterCriteria = new StringBuilder();
        filterCriteria.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_TYP_TYPE_ROWID).append(" = '").append(codeTypeRowid).append("'");
        filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_CODE).append(" = '").append(codeValue).append("'");
        if (defAreaRowid != null) {
            filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_DEF_AREA_ROWID).append(" = '").append(defAreaRowid).append("'");
        }
        searchRequest.setFilterCriteria(filterCriteria.toString());

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While validating alt. codes failed to invoke search request that retrieves alt codes for parent with type [" + codeTypeRowid + "] and value [" + codeValue + "]";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        List<String> result = new ArrayList<String>();
        if (searchResponse != null && searchResponse.getRecords() != null) {
            for (Record record : (List<Record>)searchResponse.getRecords()) {
                String rowidObject = getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT);
                if (rowidObject != null) {
                    result.add(rowidObject);
                }
            }
        }
        return result;
    }
    
    public List<String> getActiveAltCodeRowidObjects(String codeTypeRowid, String codeValue, String defAreaRowid, int recordsToReturn) throws MaerskConfException {
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(recordsToReturn);
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_GDA_ACTIVE_ALT_CODE);

        StringBuilder filterCriteria = new StringBuilder();
        filterCriteria.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_CODE_TYP_TYPE_ROWID).append(" = '").append(codeTypeRowid).append("'");
        filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_CODE_VALUE).append(" = '").append(codeValue).append("'");
        if (defAreaRowid != null) {
            filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_DEFINED_AREA_ROWID).append(" = '").append(defAreaRowid).append("'");
            
        }
        searchRequest.setFilterCriteria(filterCriteria.toString());

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While validating alt. codes failed to invoke search request that retrieves alt codes for parent with type [" + codeTypeRowid + "] and value [" + codeValue + "]";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        List<String> result = new ArrayList<String>();
        if (searchResponse != null && searchResponse.getRecords() != null) {
            for (Record record : (List<Record>)searchResponse.getRecords()) {
                String rowidObject = getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT);
                if (rowidObject != null) {
                    result.add(rowidObject);
                }
            }
        }
        return result;
    }


    /**
     * Search for defined area names with particular the ALT_CODE value and that are different from the passed
     * defined area, but with the same type
     *
     * @param definedArea Defined Area that provides DefAreaType, plus its ROWID_OBJECT should be ignored
     * @param codeTypeRowid code type rowid to search for
     * @param codeValue code value
     * @return First found defined area name with provided ALT_CODE and defarea type
     * @throws MaerskConfException if something went wrong
     */
    public String getOtherDefAreaWithAltCode(DefinedArea definedArea, String codeTypeRowid, String codeValue) throws MaerskConfException {
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(1);
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_DEF_AREA_ALT_CODE);


        StringBuilder filterCriteria = new StringBuilder();
        filterCriteria.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_TYP_TYPE_ROWID);
        filterCriteria.append(" = '").append(codeTypeRowid).append("'");

        filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_CODE);
        filterCriteria.append(" = '").append(codeValue).append("'");

       /* filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_HUB_STATE_IND);
        filterCriteria.append(" = '").append("1").append("'");*/
        
        filterCriteria.append(" AND ").append(IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT);
        filterCriteria.append(" <> ").append(makeSqlConstant(definedArea.getRowidObject()));
        
       filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_TYPE);
        filterCriteria.append(" = '").append(definedArea.getDefAreaType().getTypTypeCode()).append("'");

        searchRequest.setFilterCriteria(filterCriteria.toString());
        
        LOG.info("filterCriteria" + filterCriteria);
        
        LOG.info("searchRequest" + searchRequest);
        
        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While validating alt. codes failed to invoke search request that retrieves alt codes for parent with type [" + codeTypeRowid + "] and value [" + codeValue + "]";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        if (searchResponse != null && searchResponse.getRecords() != null) {
            for (Record record : (List<Record>)searchResponse.getRecords()) {
                String defAreaName = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_NAME);
                if (defAreaName != null) {
                    return defAreaName;
                }
            }
        }
        return null;
    }

    /**
     * Searches for defined areas that are child of certain parent ({@param parentRowid}} and have
     * provided ALT_CODE value
     *
     * @param codeTypeRowid type of alt code
     * @param codeValue value of alt code
     * @param parentRowid ROWID_OBJECT value of a DefinedArea that is parent to this defined area
     * @return list of ROWID_OBJECT of defined areas that have provided ALT_CODE
     * @throws MaerskConfException is something went wrong
     */
    public List<String> getAltCodeWithinParents(String codeTypeRowid, String codeValue, String parentRowid) throws MaerskConfException {
        List<String> parentRowids = new ArrayList<String>();
        parentRowids.add(parentRowid);
        return getAltCodeWithinParents(codeTypeRowid, codeValue, parentRowids);
    }

    /**
     * Searches for defined areas that are child of certain parents ({@param cityOrStateOrCountryRowids}} and have
     * provided ALT_CODE value
     *
     * @param codeTypeRowid type of alt code
     * @param codeValue value of alt code
     * @param parentRowids list of defined area ROWID_OBJECT values that are parent to this defined area
     * @return list of ROWID_OBJECT of defined areas that have provided ALT_CODE
     * @throws MaerskConfException is something went wrong
     */
    public List<String> getAltCodeWithinParents(String codeTypeRowid, String codeValue, List<String> parentRowids) throws MaerskConfException {
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(2);
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_DEF_AREA_ALT_CODE);


        StringBuilder filterCriteria = new StringBuilder();
        filterCriteria.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_TYP_TYPE_ROWID);
        filterCriteria.append(" = '").append(codeTypeRowid).append("'");

        filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_CODE);
        filterCriteria.append(" = '").append(codeValue).append("'");

        filterCriteria.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_PARENT_ROWID);
        filterCriteria.append(" IN ").append(makeSqlInList(parentRowids));

        searchRequest.setFilterCriteria(filterCriteria.toString());

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While validating alt codes, failed to invoke search request that checks its value against parent defined areas";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        List<String> result = new ArrayList<String>();
        if (searchResponse != null && searchResponse.getRecords() != null) {
            for (Record record : (List<Record>)searchResponse.getRecords() ) {
                String rowid = getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT);
                if (rowid != null) {
                    result.add(rowid);
                }
            }
        }
        return result;
    }

    public List<String> getSiteParentRowids(DefinedArea definedArea) throws MaerskConfException {
        if (definedArea.getParentWithActiveRel() == null) {
            throw new MaerskConfException("While validating alt. codes failed to invoke search request because there is no parent object");
        }
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(1);
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_DEFINED_AREA_PARENT);

        String filterCriteria = IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT + " = " + definedArea.getParentWithActiveRel().getRowidObject();

        searchRequest.setFilterCriteria(filterCriteria);

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While validating alt. codes failed to invoke search request that retrieves all parent ROWID_OBJECTs";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        List<String> result = new ArrayList<String>();
        result.add(definedArea.getParentWithActiveRel().getRowidObject());
        if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
            Record record = (Record) searchResponse.getRecords().iterator().next();
            String parentRowid = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_PARENT_ROWID);
            if (parentRowid != null) {
                result.add(parentRowid);
            }
            String grParentRowid = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_GR_PARENT_ROWID);
            if (grParentRowid != null) {
                result.add(grParentRowid);
            }
        }
        return result;
    }

    public List<String> getActiveChildrenNames(DefinedArea definedArea, int numberOfRecordsToReturn) throws MaerskConfException {
        if (definedArea.getRowidObject() == null) {
            throw new MaerskConfException("Failed to invalidate geography item, because it does not have ROWID_OBJECT");
        }
        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(true);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(numberOfRecordsToReturn);
        if (definedArea.getDefAreaType() != DefAreaType.BDA)
        {
        	searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_DEFINED_AREA_PARENT);
        }
        else
        {
        	searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_BDA_AS_PARENT);
        }

        StringBuilder filter = new StringBuilder();
        filter.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_PARENT_ROWID).append(" = '").append(definedArea.getRowidObject()).append("'");
        filter.append(" AND ").append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_VALID_TO).append(" > SYSDATE");
        searchRequest.setFilterCriteria(filter.toString());

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "While invalidating geography item, failed to invoke search request that retrieves child records";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        List<String> activeChildNames = new ArrayList<String>();
        if (searchResponse != null && searchResponse.getRecords() != null) {
            LOG.debug("Searching for child records of defined area with ROWID_OBJECT [" + definedArea.getRowidObject() +
                    "] returned N of records: " + searchResponse.getRecords().size());

            for (Record record : (List<Record>)searchResponse.getRecords() ) {
                String childName = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_NAME);
                if (childName != null) {
                    activeChildNames.add(childName);
                }
            }
        }
        return activeChildNames;
    }

    public String getActiveParentRowid(DefAreaType parentType, DefinedArea definedArea) throws MaerskConfException {
        String parentRowid;
        if (definedArea.getParentWithActiveRel() == null) {
            throw new MaerskConfException("While validating parent rels. Failed to invoke search request because there is no active parent object");
        } else if (parentType.equals(definedArea.getParentWithActiveRel().getDefAreaType())) {
            parentRowid = definedArea.getParentWithActiveRel().getRowidObject();
        } else {
            parentRowid = getParentRowidInternally(parentType, definedArea.getParentWithActiveRel().getRowidObject());
        }
        return parentRowid;
    }

    public String getRemovedParentRowid(DefAreaType parentType, DefinedArea definedArea) throws MaerskConfException {
        String parentRowid;
        if (definedArea.getParentWithRemovedRel() == null) {
            throw new MaerskConfException("While validating parent rels. Failed to invoke search request because there is no just removed parent object");
        } else if (parentType.equals(definedArea.getParentWithRemovedRel().getDefAreaType())) {
            parentRowid = definedArea.getParentWithRemovedRel().getRowidObject();
        } else {
            parentRowid = getParentRowidInternally(parentType, definedArea.getParentWithRemovedRel().getRowidObject());
        }
        return parentRowid;
    }

    private String getParentRowidInternally(DefAreaType parentType, String definedAreaRowid) throws MaerskConfException {
        if (definedAreaRowid == null) {
            throw new MaerskConfException("While searching for parents failed to invoke search request because of null rowid");
        }

        SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(1);
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_DEFINED_AREA_PARENT);


        String filterCriteria = IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT + " = '" + definedAreaRowid + "'";
        searchRequest.setFilterCriteria(filterCriteria);

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "Failed to invoke search request that retrieves parent with type - " + parentType.getCode();
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
            Record record = (Record) searchResponse.getRecords().iterator().next();

            LOG.debug("Searching for parent with type [" + parentType.getCode() + "] returned following record: " + record);

            String defAreaTypeString = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_TYPE);
            String parentTypeString = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_PARENT_TYPE);
            String grandParentTypeString = getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_GR_PARENT_TYPE);

            if (parentType.equals(DefAreaType.findAreaTypeByTypTypeCode(defAreaTypeString))) {
                return getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT); // current def area rowid
            } else if (parentType.equals(DefAreaType.findAreaTypeByTypTypeCode(parentTypeString))) {
                return getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_PARENT_ROWID); // parent
            } else if (parentType.equals(DefAreaType.findAreaTypeByTypTypeCode(grandParentTypeString))) {
                return getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_GR_PARENT_ROWID); // grandparent
            }else {
                throw new MaerskConfException("While validating alt. codes. Failed to find either parent or grandparent with type - " + parentType.getCode());
            }
        }
        throw new MaerskConfException("Search request that retrieves parent type [" + parentType.getCode() + "] returned empty resultset");
    }

    static public String getTrimmedFieldValue(Record record, String fieldName) {
        Field field = record.getField(fieldName);
        if (field != null) {
            String valueString = (String) field.getValue();
            if (valueString != null && valueString.trim().length() > 0) {
                return valueString.trim();
            }
        }
        return null;
    }

    static public String getAlternateCodeValue(List<? extends BasicAltCode> altCodes, String codeTypeRowid) {
        for (BasicAltCode altCode : altCodes) {
            if (codeTypeRowid.equals(altCode.getCodeTypeId())) {
                return altCode.getCodeValue();
            }
        }
        return null;
    }

    static public String getAlternateCodeRowidObject(List<? extends BasicAltCode> altCodes, String codeTypeRowid) {
        for (BasicAltCode altCode : altCodes) {
            if (codeTypeRowid.equals(altCode.getCodeTypeId())) {
                return altCode.getRowidObject();
            }
        }
        return null;
    }


    static boolean partialEquals(String str1, String str2, int subStringLength) {
        if (str1 == null || str2 == null || str1.length() < subStringLength || str2.length() < subStringLength) {
            return false;
        } else {
            return str1.substring(0, subStringLength).equals(str2.substring(0,subStringLength));
        }
    }

    static public <T> boolean checkUniqueness(Collection<T> elements, Comparator<T> comparator) {
        List<T> buffer = new ArrayList<T>();
        for (T element : elements) {
            for (T processedElement : buffer) {
                if (comparator.compare(processedElement, element) == 0) {
                    // A duplicate is detected
                    return false;
                }
            }
            buffer.add(element);
        }
        return true;
    }

    /**
     * Compares dates without seconds
     * @param date1 date to compare
     * @param date2 date to compare
     * @return true if two days are equal in terms of days
     */
    static public boolean datesAreEqual(Date date1, Date date2) {
        return IDDConstants.DATE_FORMAT.format(date1).equals(IDDConstants.DATE_FORMAT.format(date2));
    }

    static public String makeSqlConstant(String input) {
        return input == null ? "NULL" : ("'" + input + "'");
    }

    static public String makeSqlInList(List<String> inputs) {
        StringBuilder result = new StringBuilder(" (");
        for (Iterator<String> inputIterator = inputs.iterator(); inputIterator.hasNext(); ) {
            String input = inputIterator.next();
            result.append("'").append(input).append("'");
            if (inputIterator.hasNext()) {
                result.append(",");
            }
        }
        result.append(") ");
        return result.toString();
    }
    
    public boolean checkDuplicateBDARelationship( List<DefinedAreaParent> bdaRelationshipObjects)
    {
    	
    	List <String> bdaRelRowIDObjects = new ArrayList<String>();
    	for (DefinedAreaParent bdaRelationshipObject : bdaRelationshipObjects) {
    	 	    
        if (!(bdaRelationshipObject.isRemoved())) {
        	bdaRelRowIDObjects.add(bdaRelationshipObject.getRowidObject());
        	
        } 
    	}
    	return	!(checkUniqueness(bdaRelRowIDObjects, String.CASE_INSENSITIVE_ORDER));
    	
    }
    public boolean checkForDuplicateStateInCountry(String countryName,String stateName) throws MaerskConfException{
    	
    	SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(true);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(100);
        //SET PKG NAME HERE
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_STATE_PROV);
        //SET FILTER HERE
        StringBuilder filter = new StringBuilder();
        filter.append(IDDConstants.DATA_DEFAREA_TYPE_COUNTRY).append(" = '").append(countryName).append("'");
        filter.append(" AND COLUMN.").append (IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME).append(" = '").append(stateName).append("'");
        searchRequest.setFilterCriteria(filter.toString());
        //QUERY
        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
            if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
                  return false;
            }
            else{
                  return true;
            }
        }
            catch (Exception e) {
                String errorMessage = "While validating duplicate state/prov within country, failed to invoke search request ";
                LOG.error(errorMessage, e);
                throw new MaerskConfException(errorMessage, e);
            }    
    	
    }
public boolean checkForDuplicateSiteInCity(String cityName,String siteName) throws MaerskConfException{
    	
    	SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(true);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(100);
        //SET PKG NAME HERE
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_SITE);
        //SET FILTER HERE
        StringBuilder filter = new StringBuilder();
        filter.append(IDDConstants.DATA_DEFAREA_TYPE_CITY).append(" = '").append(cityName).append("'");
        filter.append(" AND COLUMN.").append (IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME).append(" = '").append(siteName).append("'");
        searchRequest.setFilterCriteria(filter.toString());
        //QUERY
        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
            if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
                  return false;
            }
            else{
                  return true;
            }
        }
            catch (Exception e) {
                String errorMessage = "While validating duplicate site within city, failed to invoke search request ";
                LOG.error(errorMessage, e);
                throw new MaerskConfException(errorMessage, e);
            }    
    	
    }
	//Check if address line 1 is same as parent city name
	public boolean compareAddrLn1ParentCityName(String addrLn1,String cityName){
		LOG.debug("ADDRESSLINE1 VALIDATION: ADRLINE1=" + addrLn1 + ",CITY NAME=" + cityName);
		if (addrLn1.compareToIgnoreCase(cityName)==0){
		return false;
	}
	else{
		return true;
	}
}
	//Get Rowid for single type code
	public String fetchRowidForTypeCode(String typeCode) throws MaerskConfException{
		SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(false);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(100); 
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_TYPE_TABLE);


        StringBuilder filterCriteria = new StringBuilder();
        filterCriteria.append(IDDConstants.MDM_TABLE_TYPES_COLUMN_NAME_CODE).append(" = '").append(typeCode).append("'");
        searchRequest.setFilterCriteria(filterCriteria.toString());

        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        } catch (Exception e) {
            String errorMessage = "Failed while executing request to retrieve ROWID_OBJECT value for ".concat(typeCode) ;
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }

        if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
        	List<Record> records=(List<Record>) searchResponse.getRecords();
        	for (Record record : records) {
        		LOG.debug("TBU ROWIDOBJECT:" + SaveHandlerHelper.getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT));
        		return SaveHandlerHelper.getTrimmedFieldValue(record, IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT);
        	}
        }
        throw new MaerskConfException("While executing request to retrieve ROWID_OBJECT value for ".concat(typeCode).concat( "returned empty resultset"));
	}
 	
public String getPostalCodeMandatoryFlagCountry(String parentId) throws MaerskConfException{
    	
		String parentAreaType =getParentAreaType(parentId) ;
		String countryId="";
		if(parentAreaType.equalsIgnoreCase(IDDConstants.DATA_TABLE_TYP_TYPE_PROVINCE)){
			countryId=getCountryRowidForState(parentId);
		}
		else{
			countryId=parentId;
		}
		SearchQueryRequest searchRequest = new SearchQueryRequest();
        searchRequest.setAdvancedMode(false);
        searchRequest.setDisablePaging(true);
        searchRequest.setReturnTotal(true);
        searchRequest.setRemoveDuplicates(false);
        searchRequest.setRecordsToReturn(1);
        //SET PKG NAME HERE
        searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_GDA_COUNTRY);
        //SET FILTER HERE
        StringBuilder filter = new StringBuilder();
        filter.append(IDDConstants.MDM_PACKAGE_COLUMN_NAME_COUNTRY_ID).append(" = '").append(countryId).append("'");
        searchRequest.setFilterCriteria(filter.toString());
        //QUERY
        SearchQueryResponse searchResponse;
        try {
            searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
            if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
            	List<Record> records=(List<Record>) searchResponse.getRecords();
            	for (Record record : records) {
            		return SaveHandlerHelper.getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_PSTL_CD_MNDTRY_FLG);	
            	}
            }
            
            throw new MaerskConfException("While executing request to retrieve Postal Code Mandatory Flag for parent country rowid(".concat(parentId).concat( ") returned empty resultset"));
        }
            catch (Exception e) {
                String errorMessage = "While fetching postal code mandatory flag for country, failed to invoke search request ";
                LOG.error(errorMessage, e);
                throw new MaerskConfException(errorMessage, e);
            }    	
    }

public String getParentAreaType(String parentId) throws MaerskConfException{
	SearchQueryRequest searchRequest = new SearchQueryRequest();
    searchRequest.setAdvancedMode(false);
    searchRequest.setDisablePaging(true);
    searchRequest.setReturnTotal(true);
    searchRequest.setRemoveDuplicates(false);
    searchRequest.setRecordsToReturn(1);
    //SET PKG NAME HERE
    searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_PKG_DEFINED_AREA);
    //SET FILTER HERE
    StringBuilder filter = new StringBuilder();
    filter.append(IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT).append(" = '").append(parentId).append("'");
    searchRequest.setFilterCriteria(filter.toString());
    //QUERY
    SearchQueryResponse searchResponse;
    try {
        searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
        	List<Record> records=(List<Record>) searchResponse.getRecords();
        	for (Record record : records) {
        		return SaveHandlerHelper.getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_AREA_TYPE_CODE);	
        	}
        }
        
        throw new MaerskConfException("While executing request to retrieve area type for parent rowid(".concat(parentId).concat( ") returned empty resultset"));
    }
        catch (Exception e) {
            String errorMessage = "While fetching area type for parent, failed to invoke search request ";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }
	
}
public String getCountryRowidForState(String stateId) throws MaerskConfException{
	SearchQueryRequest searchRequest = new SearchQueryRequest();
    searchRequest.setAdvancedMode(false);
    searchRequest.setDisablePaging(true);
    searchRequest.setReturnTotal(true);
    searchRequest.setRemoveDuplicates(false);
    searchRequest.setRecordsToReturn(1);
    //SET PKG NAME HERE
    searchRequest.setSiperianObjectUid(IDDConstants.MDM_PACKAGE_NAME_DEFINED_AREA_PARENT);
    //SET FILTER HERE
    StringBuilder filter = new StringBuilder();
    filter.append(IDDConstants.MDM_COLUMN_NAME_ROWID_OBJECT).append(" = '").append(stateId).append("'");
    searchRequest.setFilterCriteria(filter.toString());
    //QUERY
    SearchQueryResponse searchResponse;
    try {
        searchResponse = (SearchQueryResponse) saveHandler.executeRequest(searchRequest);
        if (searchResponse != null && searchResponse.getRecords() != null && !searchResponse.getRecords().isEmpty()) {
        	List<Record> records=(List<Record>) searchResponse.getRecords();
        	for (Record record : records) {
        		return SaveHandlerHelper.getTrimmedFieldValue(record, IDDConstants.MDM_PACKAGE_COLUMN_NAME_PARENT_ROWID);	
        	}
        }
        
        throw new MaerskConfException("While executing request to retrieve country rowid for state rowid(".concat(stateId).concat( ") returned empty resultset"));
    }
        catch (Exception e) {
            String errorMessage = "While fetching country rowid for state, failed to invoke search request ";
            LOG.error(errorMessage, e);
            throw new MaerskConfException(errorMessage, e);
        }
}

}
