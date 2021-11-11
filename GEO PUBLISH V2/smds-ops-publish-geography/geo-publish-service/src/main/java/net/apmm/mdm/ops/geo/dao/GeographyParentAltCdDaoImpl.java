package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyParentAlternateCodeData;

import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyParentAltCdDaoImpl implements GeographyParentAltCdDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveParentAltCodeByParentId")
    @Autowired
    String retrieveParentAltCodeByParentId;

    @Autowired
    public GeographyParentAltCdDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographyParentAlternateCodeData> retrieveParentAltCodeDtlsByParentId(String geoRowID) {
        log.debug("Fetching ParentDetails for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveParentAltCodeByParentId,
                    new Object[]{geoRowID},
                    new GeographyParentAltCdMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting ParentDetails Details:: " + e);
        }
    }
}
