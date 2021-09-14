package net.apmm.mdm.ops.geo.dao;




import net.apmm.mdm.ops.geo.dao.model.GeographyCountryAltCdData;
import net.apmm.mdm.ops.geo.dao.model.GeographyCountryData;

import java.util.List;

public interface GeographyCountryAltCdDao {

    public List<GeographyCountryAltCdData> retrieveCountryAltCodeDetailsByCountryId(String geoRowID);

}
