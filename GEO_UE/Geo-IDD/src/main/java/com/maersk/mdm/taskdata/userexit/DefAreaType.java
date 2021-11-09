package com.maersk.mdm.taskdata.userexit;



public enum DefAreaType {

    CONTINENT(IDDConstants.DATA_DEFAREA_TYPE_CONTINENT, IDDConstants.DATA_TABLE_TYP_TYPE_CONTINENT),

    COUNTRY(IDDConstants.DATA_DEFAREA_TYPE_COUNTRY, IDDConstants.DATA_TABLE_TYP_TYPE_COUNTRY),

    PROVINCE(IDDConstants.DATA_DEFAREA_TYPE_PROVINCE, IDDConstants.DATA_TABLE_TYP_TYPE_PROVINCE),

    COUNTY(IDDConstants.DATA_DEFAREA_TYPE_COUNTY, IDDConstants.DATA_TABLE_TYP_TYPE_COUNTY),

    CITY(IDDConstants.DATA_DEFAREA_TYPE_CITY, IDDConstants.DATA_TABLE_TYP_TYPE_CITY),

    SITE(IDDConstants.DATA_DEFAREA_TYPE_SITE, IDDConstants.DATA_TABLE_TYP_TYPE_SITE),
    BDA(IDDConstants.DATA_DEFAREA_TYPE_BDA, IDDConstants.DATA_TABLE_TYP_TYPE_BDA),
    POSTAL_CODE(IDDConstants.DATA_DEFAREA_TYPE_POSTALCODE, IDDConstants.DATA_TABLE_TYP_TYPE_POSTALCODE),
    SUB_CITY(IDDConstants.DATA_DEFAREA_TYPE_SUBCITY, IDDConstants.DATA_TABLE_TYP_TYPE_SUBCITY);

    final private String code;

    final private String typTypeCode;

    DefAreaType(String code,String typTypeCode) {
        this.code = code;
        this.typTypeCode = typTypeCode;
    }

    public String getCode() {
        return code;
    }

    public String getTypTypeCode() {
        return typTypeCode;
    }

    static public DefAreaType findAreaType(String code) {
        for (DefAreaType defAreaType : values()) {
            if (defAreaType.getCode().equals(code)) {
                return defAreaType;
            }
        }
        return null;
    }

    static public DefAreaType findAreaTypeByTypTypeCode(String typTypeCode) {
        for (DefAreaType defAreaType : values()) {
            if (defAreaType.getTypTypeCode().equals(typTypeCode)) {
                return defAreaType;
            }
        }
        return null;
    }
}
