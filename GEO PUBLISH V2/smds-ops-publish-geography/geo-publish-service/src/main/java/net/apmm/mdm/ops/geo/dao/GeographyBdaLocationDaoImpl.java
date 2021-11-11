package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyBDALocationsDetailsData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyBdaLocationDaoImpl implements GeographyBdaLocationDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveBdaLocationByGeoRowId")
    @Autowired
    String retrieveBdaLocationByGeoRowId;

    @Autowired
    public GeographyBdaLocationDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographyBDALocationsDetailsData> retrieveBdaLocationDtlsByGeoRowId(String geoRowID) {
        log.debug("Fetching BDA Details for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveBdaLocationByGeoRowId,
                    new Object[]{geoRowID},
                    new GeographyBdaLocationMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting BDADetails Details:: " + e);
        }
    }
}
