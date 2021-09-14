package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyBDAAlternateCodeData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyBdaAltCdMapper implements RowMapper<GeographyBDAAlternateCodeData> {


    @Override
    public GeographyBDAAlternateCodeData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyBDAAlternateCodeData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();
    }
}
