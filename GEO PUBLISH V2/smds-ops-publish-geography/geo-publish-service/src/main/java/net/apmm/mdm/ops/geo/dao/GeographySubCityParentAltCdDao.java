package net.apmm.mdm.ops.geo.dao;



import net.apmm.mdm.ops.geo.dao.model.GeographySubCityParentAlternateCodeData;

import java.util.List;

public interface GeographySubCityParentAltCdDao {

    public List<GeographySubCityParentAlternateCodeData> retrieveSubCityParentAltCodeDtlsByParentId(String geoRowID);

}
