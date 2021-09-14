package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyAlternateCodesData;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyAltCdMapper implements RowMapper<GeographyAlternateCodesData> {


    @Override
    public GeographyAlternateCodesData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyAlternateCodesData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();
    }
}
