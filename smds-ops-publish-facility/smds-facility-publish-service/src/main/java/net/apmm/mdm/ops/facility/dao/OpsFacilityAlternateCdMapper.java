package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAddressData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAlternateCodesData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityAlternateCdMapper implements RowMapper<OpsFacilityAlternateCodesData> {
    @Override
    public OpsFacilityAlternateCodesData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityAlternateCodesData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();


    }


}
