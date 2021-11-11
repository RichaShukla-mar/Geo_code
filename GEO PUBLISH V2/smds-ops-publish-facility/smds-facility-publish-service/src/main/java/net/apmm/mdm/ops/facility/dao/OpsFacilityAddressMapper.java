package net.apmm.mdm.ops.facility.dao;
import org.springframework.jdbc.core.RowMapper;
import net.apmm.mdm.ops.facility.dao.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
public class OpsFacilityAddressMapper  implements RowMapper<OpsFacilityAddressData> {
    @Override
    public OpsFacilityAddressData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityAddressData.builder().houseNumber(resultSet.getString("houseNumber"))
                .street(resultSet.getString("street"))
                .city(resultSet.getString("city"))
                .postalCode(resultSet.getString("postalCode"))
                .poBox(resultSet.getString("poBox"))
                .district(resultSet.getString("district"))
                .territory(resultSet.getString("territory"))
                .countryName(resultSet.getString("countryName"))
                .countryCode(resultSet.getString("countryCode"))
                .addressLine2(resultSet.getString("addressLine2"))
                .addressLine3(resultSet.getString("addressLine3"))
                .latitude(resultSet.getString("latitude"))
                .longitude(resultSet.getString("longitude"))
                .addressQualityCheckIndicator(resultSet.getString("addressQualityCheckIndicator"))
                .build();


    }


}
