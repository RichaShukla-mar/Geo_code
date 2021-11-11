package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyBDAAlternateCodeData;
import net.apmm.mdm.ops.geo.dao.model.GeographyBDALocationAlternateCodeData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyBdaLocationAltCdMapper implements RowMapper<GeographyBDALocationAlternateCodeData> {


    @Override
    public GeographyBDALocationAlternateCodeData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyBDALocationAlternateCodeData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();
    }
}
