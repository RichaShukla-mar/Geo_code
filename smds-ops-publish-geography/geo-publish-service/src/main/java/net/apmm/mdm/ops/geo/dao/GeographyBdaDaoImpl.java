package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyBDADetailsData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyBdaDaoImpl implements GeographyBdaDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveBdaByGeoRowId")
    @Autowired
    String retrieveBdaByGeoRowId;

    @Autowired
    public GeographyBdaDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographyBDADetailsData> retrieveBdaDetailsByGeoRowId(String geoRowID) {
        log.debug("Fetching BDA Details for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveBdaByGeoRowId,
                    new Object[]{geoRowID},
                    new GeographyBdaMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting BDADetails Details:: " + e);
        }
    }
}
