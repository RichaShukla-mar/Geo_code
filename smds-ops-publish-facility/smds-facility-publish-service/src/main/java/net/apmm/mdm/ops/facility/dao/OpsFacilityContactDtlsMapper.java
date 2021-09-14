package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityContactDetailData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityContactDtlsMapper implements RowMapper<OpsFacilityContactDetailData> {
    @Override
    public OpsFacilityContactDetailData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityContactDetailData.builder()
                .firstName(resultSet.getString("firstName"))
                .lastName(resultSet.getString("lastName"))
                .jobTitle(resultSet.getString("jobTitle"))
                .department(resultSet.getString("department"))
                .internationalDialingCdPhone(resultSet.getString("internationalDialingCdPhone"))
                .extension(resultSet.getString("extension"))
                .phoneNumber(resultSet.getString("phoneNumber"))
                .internationalDialingCdMobile(resultSet.getString("internationalDialingCdMobile"))
                .mobileNumber(resultSet.getString("mobileNumber"))
                .internationalDialingCdFax(resultSet.getString("internationalDialingCdFax"))
                .faxNumber(resultSet.getString("faxNumber"))
                .emailAddress(resultSet.getString("emailAddress"))
                .validThroughDate(resultSet.getString("validThroughDate"))
                .build();


    }


}
