package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityDetailData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentDetailsData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityDetailsMapper implements RowMapper<OpsFacilityDetailData> {

    @Override
    public OpsFacilityDetailData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityDetailData.builder()
                .fctOpsRowid(resultSet.getString("fctOpsRowid"))
                .weightLimitCraneKg(resultSet.getString("weightLimitCraneKg"))
                .weightLimitYardKg(resultSet.getString("weightLimitYardKg"))
                .vesselAgent(resultSet.getString("vesselAgent"))
                .gpsFlag(resultSet.getString("gpsFlag"))
                .gsmFlag(resultSet.getString("gsmFlag"))
                .oceanFreightPricing(resultSet.getString("oceanFreightPricing"))
                .commFacilityBrand(resultSet.getString("commFacilityBrand"))
                .commFacilityType(resultSet.getString("commFacilityType"))
                .commExportEnquiriesEmail(resultSet.getString("commExportEnquiriesEmail"))
                .commImportEnquiriesEmail(resultSet.getString("commImportEnquiriesEmail"))
                .commFacilityFunction(resultSet.getString("commFacilityFunction"))
                .commFacilityFunctionDesc(resultSet.getString("commFacilityFunctionDesc"))
                .commInternationalDialCode(resultSet.getString("commInternationalDialCode"))
                .telephoneNumber(resultSet.getString("telephoneNumber"))
                .build();
    }
}
