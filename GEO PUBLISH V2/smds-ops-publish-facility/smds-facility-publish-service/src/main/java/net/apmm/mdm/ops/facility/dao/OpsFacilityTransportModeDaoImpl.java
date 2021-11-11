package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityTransportModesData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityTransportModeDaoImpl implements OpsFacilityTransportModeDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctTransportModeByFctRowId")
    @Autowired
    String retrieveFctTransportModeByFctRowId;

    @Autowired
    public OpsFacilityTransportModeDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityTransportModesData> retrieveFctTransportModeDtlByFctRowId(String fctRowID) {
        log.debug("Fetching fctRowID and header Info  :: " + fctRowID);
        try {
              return  smdsJdbcTemplate.query(retrieveFctTransportModeByFctRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityTransportModeMapper()
            );

        } catch (Exception e) {
            throw new DataRetrievalException("Error getting Facility OpeningHrs details  :: " + e);
        }

}

}
