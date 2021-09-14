package net.apmm.mdm.ops.geo.dao;


import net.apmm.mdm.ops.geo.dao.model.GeographySubCityParentDetailsData;

import java.util.List;

public interface GeographySubCityParentDao {

    public List<GeographySubCityParentDetailsData> retrieveSubCityParentDetailsByGeoRowId(String geoRowID);

}
