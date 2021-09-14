package net.apmm.mdm.ops.geo.dao;



import net.apmm.mdm.ops.geo.dao.model.GeographyParentAlternateCodeData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyParentAltCdMapper implements RowMapper<GeographyParentAlternateCodeData> {


    @Override
    public GeographyParentAlternateCodeData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyParentAlternateCodeData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();
    }
}
