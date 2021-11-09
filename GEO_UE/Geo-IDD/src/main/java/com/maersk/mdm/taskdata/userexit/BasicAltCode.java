package com.maersk.mdm.taskdata.userexit;



/**
 * Model class to represent single alternative code
 */
public class BasicAltCode {

    final private String rowidObject;

    final private String codeTypeId;

    final private String codeValue;

    public BasicAltCode(String rowidObject, String codeTypeId, String codeValue) throws MaerskConfException {
        this.rowidObject = rowidObject;

        if (codeTypeId == null || codeTypeId.trim().length() == 0) {
            throw new MaerskConfException("Missing alt code Type ID");
        }
        this.codeTypeId = codeTypeId.trim();

        if (codeValue == null || codeValue.trim().length() == 0) {
            throw new MaerskConfException("Missing alt code value");
        }
        this.codeValue = codeValue.trim();
    }

    public String getRowidObject() {
        return rowidObject;
    }

    public String getCodeTypeId() {
        return codeTypeId;
    }

    public String getCodeValue() {
        return codeValue;
    }
}
