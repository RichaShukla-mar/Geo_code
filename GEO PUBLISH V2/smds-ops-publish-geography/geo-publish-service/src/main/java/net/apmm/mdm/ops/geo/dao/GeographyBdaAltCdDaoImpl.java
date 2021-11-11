package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyBDAAlternateCodeData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyBdaAltCdDaoImpl implements GeographyBdaAltCdDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveBdaAltCodeByBdaId")
    @Autowired
    String retrieveBdaAltCodeByBdaId;

    @Autowired
    public GeographyBdaAltCdDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<GeographyBDAAlternateCodeData> retrieveBdaAltDetailsCodeByBdaId(String bdaRowID) {
        log.debug("Fetching BDAALTCODE Details for GeoRowid :: " + bdaRowID);
        try {
            return smdsJdbcTemplate.query(retrieveBdaAltCodeByBdaId,
                    new Object[]{bdaRowID},
                    new GeographyBdaAltCdMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting BDAALTCODE Details :: " + e);
        }
    }

}
