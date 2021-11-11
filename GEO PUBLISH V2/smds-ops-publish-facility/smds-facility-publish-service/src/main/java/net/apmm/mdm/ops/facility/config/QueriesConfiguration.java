package net.apmm.mdm.ops.facility.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class QueriesConfiguration {

    @Bean("retrieveOpsFctByRowId")
    public String retrieveOpsFctByRowId()
    {
        return readFileSql("queries/retrieve_FacilityOpsInfo_by_rowid.sql");
    }

    @Bean("retrieveFctAddressByFctRowId")
    public String retrieveFctAddressByFctRowId() {
        return readFileSql("queries/retrieve_FacilityAddressInfo_by_rowid.sql");
    }

    @Bean("retrieveParentByFctRowId")
    public String retrieveParentByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityParentDetails_by_rowid.sql");
    }

    @Bean("retrieveParentAltCodeByParentId")
    public String retrieveParentAltCodeByParentId()
    {
        return readFileSql("queries/retrieve_FacilityParentAltCd_by_rowid.sql");
    }

    @Bean("retrieveFctDetailsByFctRowId")
    public String retrieveFctDetailsByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityDetailsInfo_by_rowid.sql");
    }

    @Bean("retrieveFctTypeByFctOpsRowId")
    public String retrieveFctTypeByFctOpsRowId()
    {
        return readFileSql("queries/retrieve_FacilityTypeInfo_by_rowid.sql");
    }

    @Bean("retrieveFctAltCodeByFctRowId")
    public String retrieveFctAltCodeByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityAltCodeInfo_by_rowid.sql");
    }

    @Bean("retrieveFctOpeningHrsFctRowId")
    public String retrieveFctOpeningHrsFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityOpeningHrsInfo_by_rowid.sql");
    }

    @Bean("retrieveFctTransportModeByFctRowId")
    public String retrieveFctTransportModeByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityTransportModeInfo_by_rowid.sql");
    }

    @Bean("retrieveFctServiceByFctRowId")
    public String retrieveFctServiceByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityServiceInfo_by_rowid.sql");
    }

    @Bean("retrieveFctFenceByFctRowId")
    public String retrieveFctFenceByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityGeoFence_by_rowid.sql");
    }

    @Bean("retrieveFctBdaByFctRowId")
    public String retrieveFctBdaByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityGeoBda_by_rowid.sql");
    }

    @Bean("retrieveFctBdaAltCdByFctRowId")
    public String retrieveFctBdaAltCdByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityGeoBdaAltCd_by_rowid.sql");
    }

    @Bean("retrieveFctContactInfoByFctRowId")
    public String retrieveFctContactInfoByFctRowId()
    {
        return readFileSql("queries/retrieve_FacilityContactInfo_by_rowid.sql");
    }


    @Bean("empPublishFacilityConfiguration")
    public String empPublishFacilityConfiguration()
    {
        /*return "select PROPERTY_NAME, PROPERTY_VALUE from OPS_APPLICATION_CONFIG where APPLICATION_NAME = 'EMP_PUBLISH_VESSEL'";*/
        return "select PROPERTY_NAME, PROPERTY_VALUE from OPS_APPLICATION_CONFIG where APPLICATION_NAME = 'EMP_PUBLISH_FCT'";
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
