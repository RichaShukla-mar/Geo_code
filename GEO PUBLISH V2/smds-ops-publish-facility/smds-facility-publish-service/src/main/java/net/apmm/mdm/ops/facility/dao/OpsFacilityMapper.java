package net.apmm.mdm.ops.facility.dao;

import org.springframework.jdbc.core.RowMapper;
import net.apmm.mdm.ops.facility.dao.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityMapper implements RowMapper<OpsFacilityData> {
    @Override
    public OpsFacilityData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityData.builder().fctRowID(resultSet.getString("fctRowID"))
                .facilityName(resultSet.getString("facilityName"))
                .facilityType(resultSet.getString("facilityType"))
                .facilityExtOwned(resultSet.getString("facilityExtOwned"))
                .facilityStatus(resultSet.getString("facilityStatus"))
                .facilityExtExposed(resultSet.getString("facilityExtExposed"))
                .facilityURL(resultSet.getString("facilityURL"))
                .facilityDODAAC(resultSet.getString("facilityDODAAC"))
                .build();
    }


}
