package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentAltCodeData;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityParentAltMapper implements RowMapper<OpsFacilityParentAltCodeData> {

    @Override
    public OpsFacilityParentAltCodeData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityParentAltCodeData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();
    }
}
