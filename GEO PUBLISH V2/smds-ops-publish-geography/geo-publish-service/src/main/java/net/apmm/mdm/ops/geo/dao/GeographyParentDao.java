package net.apmm.mdm.ops.geo.dao;


import net.apmm.mdm.ops.geo.dao.model.GeographyParentDetailsData;

import java.util.List;

public interface GeographyParentDao {

    public List<GeographyParentDetailsData> retrieveParentDetailsByGeoRowId(String geoRowID,String geoRowID1);

}
