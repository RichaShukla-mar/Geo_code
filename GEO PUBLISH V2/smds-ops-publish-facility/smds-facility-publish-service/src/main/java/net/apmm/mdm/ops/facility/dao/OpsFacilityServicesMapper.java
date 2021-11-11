package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityServicesData;
import net.apmm.mdm.ops.facility.util.util;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityServicesMapper implements RowMapper<OpsFacilityServicesData> {
    @Override
    public OpsFacilityServicesData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityServicesData.builder()
                .serviceName(resultSet.getString("serviceName"))
                .serviceCode(resultSet.getString("serviceCode"))
                .serviceDescription(resultSet.getString("serviceDescription"))
                .validThroughDate(resultSet.getTimestamp("validThroughDate")!=null? util.changeInputCreateDate(resultSet.getTimestamp("validThroughDate")) : 0)
                .build();


    }


}
