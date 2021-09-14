package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityBdaData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityBdaDaoImpl implements OpsFacilityBdaDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctBdaByFctRowId")
    @Autowired
    String retrieveFctBdaByFctRowId;

    @Autowired
    public OpsFacilityBdaDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityBdaData> retrieveFctBdaDtlByFctRowId(String fctRowID) {
        log.debug("Fetching fctRowID and header Info  :: " + fctRowID);
        try {
              return  smdsJdbcTemplate.query(retrieveFctBdaByFctRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityBdaMapper()
            );

        } catch (Exception e) {
            throw new DataRetrievalException("Error getting Facility Bda details From Geo :: " + e);
        }

}

}
