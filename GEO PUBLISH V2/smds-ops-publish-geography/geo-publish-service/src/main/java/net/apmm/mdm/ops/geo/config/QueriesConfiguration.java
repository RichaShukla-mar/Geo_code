package net.apmm.mdm.ops.geo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class QueriesConfiguration {

    @Bean("retrieveGeographyByRowId")
    public String retrieveGeographyByRowId()
    {
        return readFileSql("queries/retrieve_geographyInfo_by_rowid.sql");
    }

    @Bean("retrieveAltNameByGeoRowId")
    public String retrieveAltNameByGeoRowId() {return readFileSql("queries/retrieve_geographyaltname_by_rowid.sql");  }

    @Bean("retrieveAltCodeByGeoRowId")
    public String retrieveAltCodeByGeoRowId()
    {
        return readFileSql("queries/retrieve_geographyaltcode_by_rowid.sql");
    }

    @Bean("retrieveFencebyGeoRowId")
    public String retrieveFencebyGeoRowId()
    {
        return readFileSql("queries/retrieve_geographyfence_by_rowid.sql");
    }

    @Bean("retrieveCountryByGeoRowId")
    public String retrieveCountryByGeoRowId()
    {
        return readFileSql("queries/retrieve_geographycountry_by_rowid.sql");
    }

    @Bean("retrieveCountryAltCodeByCountryId")
    public String retrieveCountryAltCodeByCountryId()
    {
        return readFileSql("queries/retrieve_geographycountryaltcode_by_rowid.sql");
    }

    @Bean("retrieveParentByGeoRowId")
    public String retrieveParentByGeoRowId()
    {
        return readFileSql("queries/retrieve_geographyparent_by_rowid.sql");
    }

    @Bean("retrieveParentAltCodeByParentId")
    public String retrieveParentAltCodeByParentId()
    {
        return readFileSql("queries/retrieve_geographyparentalternatecd_by_rowid.sql");
    }

    @Bean("retrieveSubCityParentByGeoRowId")
    public String retrieveSubCityParentByGeoRowId()
    {
        return readFileSql("queries/retrieve_geographysubcityparent_by_rowid.sql");
    }

    @Bean("retrieveSubCityParentAltCodeByParentId")
    public String retrieveSubCityParentAltCodeByParentId()
    {
        return readFileSql("queries/retrieve_geographysubcityparentalternatecd_by_rowid.sql");
    }

    @Bean("retrieveBdaByGeoRowId")
    public String retrieveBdaByGeoRowId()
    {
        return readFileSql("queries/retrieve_geographybda_by_rowid.sql");
    }

    @Bean("retrieveBdaAltCodeByBdaId")
    public String retrieveBdaAltCodeByBdaId()
    {
        return readFileSql("queries/retrieve_geographybdaaltcd_by_rowid.sql");
    }

    @Bean("retrieveBdaLocationByGeoRowId")
    public String retrieveBdaLocationByGeoRowId()
    {
        return readFileSql("queries/retrieve_geographybdalocation_by_rowid.sql");
    }

    @Bean("retrieveBdaLocationAltCodeByBdaId")
    public String retrieveBdaLocationAltCodeByBdaId()
    {
        return readFileSql("queries/retrieve_geographybdalocationaltcode_by_rowid.sql");
    }





    @Bean("empPublishGeoConfiguration")
    public String empPublishGeoConfiguration()
    {

        return "select PROPERTY_NAME, PROPERTY_VALUE from OPS_APPLICATION_CONFIG where APPLICATION_NAME = 'EMP_PUBLISH_GEO-V2'";
    }

    private String readFileSql(String pathToFile) {
        StringBuilder fileContentSB = new StringBuilder();
        try
        {
            InputStream inputStream = new ClassPathResource(pathToFile).getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            fileContentSB.append(br.lines().collect(Collectors.joining(System.lineSeparator())));

            log.debug("fileContentSB ::: "+fileContentSB.toString());
        }
        catch (IOException e)
        {
            log.error("IO Exception while reading file containing query not found in path :: " + pathToFile, e);
        }
        return fileContentSB.toString();
    }
}
