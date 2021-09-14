package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographySubCityParentAlternateCodeData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographySubCityParentAltCdDaoImpl implements GeographySubCityParentAltCdDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveSubCityParentAltCodeByParentId")
    @Autowired
    String retrieveSubCityParentAltCodeByParentId;

    @Autowired
    public GeographySubCityParentAltCdDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographySubCityParentAlternateCodeData> retrieveSubCityParentAltCodeDtlsByParentId(String geoRowID) {
        log.debug("Fetching ParentDetails for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveSubCityParentAltCodeByParentId,
                    new Object[]{geoRowID},
                    new GeographySubCityParentAltCdMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting ParentDetails Details:: " + e);
        }
    }


}
