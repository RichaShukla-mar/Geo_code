package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityTransportModesData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityTransportModeMapper implements RowMapper<OpsFacilityTransportModesData> {
    @Override
    public OpsFacilityTransportModesData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityTransportModesData.builder()
                .transportMode(resultSet.getString("transportMode"))
                .transportCode(resultSet.getString("transportCode"))
                .transportDescription(resultSet.getString("transportDescription"))
                .validThroughDate(resultSet.getString("validThroughDate"))
                .build();


    }


}
