package com.maersk.mdm.taskdata.userexit;

import com.siperian.bdd.userexits.datamodel.BDDObject;

import java.util.Comparator;

/**
 * Model class to represent single alternative code
 */
public class AltCode extends BasicAltCode {

    final private BDDObject altCodeObject;

    public AltCode(BDDObject altCodeObject) throws MaerskConfException {
        super((String) altCodeObject.getSystemValue(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_ROWID_OBJECT),
              (String) altCodeObject.getValue(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_TYPEID),
              (String) altCodeObject.getValue(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_VALUE));
        this.altCodeObject = altCodeObject;
    }

    public BDDObject getAltCodeObject() {
        return altCodeObject;
    }

    static final public Comparator<AltCode> CODE_TYPE_COMPARATOR = new Comparator<AltCode>() {
        
        public int compare(AltCode altCode1, AltCode altCode2) {
            if (altCode1 == null && altCode2 == null) {
                return 0;
            } else if (altCode1 == null || altCode2 == null) {
                return 1;
            } else {
                return altCode1.getCodeTypeId().compareTo(altCode2.getCodeTypeId());
            }
        }
    };
}
