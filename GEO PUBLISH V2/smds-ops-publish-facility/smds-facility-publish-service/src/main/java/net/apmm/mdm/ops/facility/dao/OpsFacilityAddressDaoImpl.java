package net.apmm.mdm.ops.facility.dao;
import lombok.extern.slf4j.Slf4j;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAddressData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityAddressDaoImpl implements OpsFacilityAddressDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctAddressByFctRowId")
    @Autowired
    String retrieveFctAddressByFctRowId;

    @Autowired
    public OpsFacilityAddressDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityAddressData> retrieveFctAddressDtlByFctRowId(String fctRowID) {
        log.debug("Fetching fctRowID and header Info  :: " + fctRowID);
        try {
              return  smdsJdbcTemplate.query(retrieveFctAddressByFctRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityAddressMapper()
            );

        } catch (Exception e) {
            throw new DataRetrievalException("Error getting FacilityAddress details  :: " + e);
        }

}

}
