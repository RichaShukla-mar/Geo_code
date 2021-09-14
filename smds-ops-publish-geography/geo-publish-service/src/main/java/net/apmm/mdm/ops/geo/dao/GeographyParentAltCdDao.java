package net.apmm.mdm.ops.geo.dao;


import net.apmm.mdm.ops.geo.dao.model.GeographyParentAlternateCodeData;


import java.util.List;

public interface GeographyParentAltCdDao {

    public List<GeographyParentAlternateCodeData> retrieveParentAltCodeDtlsByParentId(String geoRowID);

}
