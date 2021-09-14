package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityFenceData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityFenceDaoImpl implements OpsFacilityFenceDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctFenceByFctRowId")
    @Autowired
    String retrieveFctFenceByFctRowId;

    @Autowired
    public OpsFacilityFenceDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityFenceData> retrieveFctFenceDtlByFctRowId(String fctRowID) {
        log.debug("Fetching fctRowID and header Info  :: " + fctRowID);
        try {
              return  smdsJdbcTemplate.query(retrieveFctFenceByFctRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityFenceMapper()
            );

        } catch (Exception e) {
            throw new DataRetrievalException("Error getting Facility Fence details  :: " + e);
        }

}

}
