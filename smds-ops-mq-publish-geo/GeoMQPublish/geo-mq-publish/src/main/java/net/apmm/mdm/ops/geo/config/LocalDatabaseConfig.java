package net.apmm.mdm.ops.geo.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "maersk.deployment.environment", havingValue = "local", matchIfMissing = false)
public class LocalDatabaseConfig {

    @Bean(name = "smdsDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        driverManagerDataSource.setUrl("jdbc:oracle:thin:@scrbsmddk002539:1521/SMDSSIT");
        driverManagerDataSource.setUsername("MDM_INFM_SMDS");
        driverManagerDataSource.setPassword("MDM_INFM_SMDS_SIT");
        return driverManagerDataSource;
    }

    @Bean(name = "smdsJdbcTemplate")
    public JdbcTemplate smdsJdbcTemplate(@Qualifier("smdsDataSource") DataSource smdsDataSource) {
        log.info("Initializing SMDS JDBC Template");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(smdsDataSource);
        log.info("Got jdbcTemplate ::: "+jdbcTemplate);
        return jdbcTemplate;
    }



}

