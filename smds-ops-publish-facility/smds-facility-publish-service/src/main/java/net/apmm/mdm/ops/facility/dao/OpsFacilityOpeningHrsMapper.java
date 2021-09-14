package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAlternateCodesData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityOpeningHoursData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityOpeningHrsMapper implements RowMapper<OpsFacilityOpeningHoursData> {
    @Override
    public OpsFacilityOpeningHoursData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityOpeningHoursData.builder()
                .day(resultSet.getString("day"))
                .openTimeHours(resultSet.getString("openTimeHours"))
                .openTimeMinutes(resultSet.getString("openTimeMinutes"))
                .closeTimeHours(resultSet.getString("closeTimeHours"))
                .closeTimeMinutes(resultSet.getString("closeTimeMinutes"))
                .build();


    }


}
