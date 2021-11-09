package com.maersk.mdm.taskdata.userexit;

import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.dsapp.common.util.Logger;

import java.util.Date;

public class DefinedAreaParent {

    private static final Logger LOG = Logger.getLogger(DefinedAreaParent.class);

    // calculated based on validFrom, validTo values of relation
    final private boolean relationActive;

    final private String rowidObject;

    final private String name;

    final private DefAreaType defAreaType;

    final private boolean removed;

    public DefinedAreaParent(BDDObject bddObject) throws MaerskConfException {
        String rowidObjectString = (String) bddObject.getSystemValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT);
        rowidObject = (rowidObjectString != null && rowidObjectString.trim().length() > 0) ? rowidObjectString.trim() : null;

        name = (String) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME);

        String defAreaTypeString = (String) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD);
        defAreaType = DefAreaType.findAreaTypeByTypTypeCode(defAreaTypeString);
        if (defAreaType == null) {
            throw new MaerskConfException("Invalid configuration. Failed to find defined area type with code - " + defAreaTypeString + " for bbdObject - " + bddObject);
        }

        Date relationValidFrom = (Date) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREAREL_COLUMN_NAME_VALID_FROM);
        Date relationValidTo = (Date) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREAREL_COLUMN_NAME_VALID_TO);
        // String parentStatusString = (String) bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS);

        removed = bddObject.isRemoved();

        if (relationValidFrom == null || relationValidTo == null) {
            String errorMessage = "Invalid configuration. Failed to retrieve validFrom, validThru values for object - " + bddObject;
            LOG.error();
            // TODO error
            throw new RuntimeException(errorMessage);
        }

        //String parentActive = IDDConstants.DATA_TABLE_DEFAREA_ACTIVE_FLAG_YES_VALUE.equals(parentStatusString);
        Date currentDate = new Date();
        relationActive = currentDate.before(relationValidTo) && currentDate.after(relationValidFrom);
    }

    public boolean isRelationActive() {
        return !removed && relationActive;
    }

    public boolean isRemoved() {
        return removed;
    }

    public String getRowidObject() {
        return rowidObject;
    }

    public String getName() {
        return name;
    }

    public DefAreaType getDefAreaType() {
        return defAreaType;
    }
}
