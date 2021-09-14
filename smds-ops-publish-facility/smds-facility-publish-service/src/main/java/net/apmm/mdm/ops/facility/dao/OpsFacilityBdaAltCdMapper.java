package net.apmm.mdm.ops.facility.dao;


import net.apmm.mdm.ops.facility.dao.model.OpsFacilityBdaAltCdData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityBdaAltCdMapper implements RowMapper<OpsFacilityBdaAltCdData> {
    @Override
    public OpsFacilityBdaAltCdData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityBdaAltCdData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();


    }


}
