package net.apmm.mdm.ops.geo;

import net.apmm.mdm.ops.geo.service.PublishGeographyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application for spring boot starter.
 * @author Ramesh Varma.K
 */

@SpringBootApplication(scanBasePackageClasses = PublishGeographyService.class)
@EnableAutoConfiguration(exclude = { JmxAutoConfiguration.class, ErrorMvcAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "net.apmm.mdm.ops.geo.*"} )
public class GeoPublishServiceApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GeoPublishServiceApplication.class, args);

    }

}
