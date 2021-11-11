package net.apmm.mdm.ops.facility.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "maersk.deployment.environment", havingValue = "weblogic", matchIfMissing = false)
public class DatabaseConfig {


    @Bean(name = "smdsDataSource", destroyMethod = "")
    public DataSource dataSource() throws NamingException {
        log.info("Initializing SMDS DATA SOURCE");
        JndiTemplate jndiTemplate = new JndiTemplate();
        return jndiTemplate.lookup("jdbc/SMDS-WEB-SERVICES", DataSource.class);
    }

    @Bean(name = "smdsJdbcTemplate")
    public JdbcTemplate smdsJdbcTemplate(@Qualifier("smdsDataSource") DataSource smdsDataSource) {
        log.info("Initializing SMDS JDBC Template");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(smdsDataSource);
        log.info("Got jdbcTemplate ::: "+jdbcTemplate);
        return jdbcTemplate;
    }

    @Bean(name = "smdsMdDataSource", destroyMethod = "")
    public DataSource mdDataSource() throws NamingException {
        log.info("Initializing MD DATA SOURCE");
        JndiTemplate jndiTemplate = new JndiTemplate();
        return jndiTemplate.lookup("jdbc/SMDS-WEB-SERVICES-SMD", DataSource.class);
    }


    @Bean(name = "smdsMdJdbcTemplate")
    public JdbcTemplate smdsMdJdbcTemplate(@Qualifier("smdsMdDataSource") DataSource mdDatasource) {
        log.info("Initializing MD JDBC Template");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(mdDatasource);
        log.info("Got MdjdbcTemplate ::: "+jdbcTemplate);
        return jdbcTemplate;
    }

    @Bean(name = "namedSmdsMdJdbcTemplate")
    public NamedParameterJdbcTemplate namedSmdsJdbcTemplate(@Qualifier("smdsMdDataSource") DataSource datasource) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        return namedParameterJdbcTemplate;
    }
}
