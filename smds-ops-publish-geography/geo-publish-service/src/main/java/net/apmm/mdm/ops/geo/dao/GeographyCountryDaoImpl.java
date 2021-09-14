package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyCountryData;
import net.apmm.mdm.ops.geo.dao.model.GeographyParentDetailsData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyCountryDaoImpl implements GeographyCountryDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveCountryByGeoRowId")
    @Autowired
    String retrieveCountryByGeoRowId;

    @Autowired
    public GeographyCountryDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographyCountryData> retrieveCountryDetailsByGeoRowId(String geoRowID,String geoRowID1,String geoRowID2,String geoRowID3) {
        log.debug("Fetching Country Details for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveCountryByGeoRowId,
                    new Object[]{geoRowID,geoRowID1,geoRowID2,geoRowID3},
                    new GeographyCountryMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting CountryDetails Details:: " + e);
        }
    }
}
