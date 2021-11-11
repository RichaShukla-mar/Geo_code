package net.apmm.mdm.ops.geo.dao;




import net.apmm.mdm.ops.geo.dao.model.GeographyCountryData;

import java.util.List;

public interface GeographyCountryDao {

    public List<GeographyCountryData> retrieveCountryDetailsByGeoRowId(String geoRowID,String geoRowID1,String geoRowID2,String geoRowID3);

}
