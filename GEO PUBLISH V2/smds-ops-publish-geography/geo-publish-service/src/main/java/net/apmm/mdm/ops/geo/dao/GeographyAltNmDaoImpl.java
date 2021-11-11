package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyAlternateNameData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyAltNmDaoImpl implements GeographyAltNmDao {

    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveAltNameByGeoRowId")
    @Autowired
    String retrieveAltNameByGeoRowId;

    @Autowired
    public GeographyAltNmDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GeographyAlternateNameData> retrieveAltNameDetailsByGeoRowId(String geoRowID) {
        log.debug("Fetching capacity Details for GeoAltNm :: " + geoRowID);
        try {

            return smdsJdbcTemplate.query(retrieveAltNameByGeoRowId,
                    new Object[]{geoRowID},
                    new GeographyAltNmMapper()

            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting geoAltnm details  :: " + e);
        }


    }


}
