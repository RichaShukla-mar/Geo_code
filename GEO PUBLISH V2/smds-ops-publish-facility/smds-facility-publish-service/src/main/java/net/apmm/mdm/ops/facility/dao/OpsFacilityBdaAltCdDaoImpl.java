package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityBdaAltCdData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityBdaAltCdDaoImpl implements OpsFacilityBdaAltCdDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctBdaAltCdByFctRowId")
    @Autowired
    String retrieveFctBdaAltCdByFctRowId;

    @Autowired
    public OpsFacilityBdaAltCdDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityBdaAltCdData> retrieveFctBdaAltCdDtlByFctRowId(String fctRowID) {
        log.debug("Fetching fctRowID and header Info  :: " + fctRowID);
        try {
              return  smdsJdbcTemplate.query(retrieveFctBdaAltCdByFctRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityBdaAltCdMapper()
            );

        } catch (Exception e) {
            throw new DataRetrievalException("Error getting Facility Bda AltCode details  :: " + e);
        }

}

}
