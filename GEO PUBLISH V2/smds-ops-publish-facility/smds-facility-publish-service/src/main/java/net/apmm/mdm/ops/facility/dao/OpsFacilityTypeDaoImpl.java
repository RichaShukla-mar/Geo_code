package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentAltCodeData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityTypeData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityTypeDaoImpl implements OpsFacilityTypeDao {

    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctTypeByFctOpsRowId")
    @Autowired
    String retrieveFctTypeByFctOpsRowId;

    @Autowired
    public OpsFacilityTypeDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityTypeData> retrieveFctTypeDtlByFctOpsRowId(String fctOpsRowid) {
        log.debug("Fetching ParentDetails for GeoRowid :: " + fctOpsRowid);
        try {
            return smdsJdbcTemplate.query(retrieveFctTypeByFctOpsRowId,
                    new Object[]{fctOpsRowid},
                    new OpsFacilityTypeMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting FacilityType Details:: " + e);
        }
    }
}
