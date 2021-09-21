package net.apmm.mdm.ops.geo;

import net.apmm.mdm.ops.geo.service.PublishGeographyMQService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackageClasses = PublishGeographyMQService.class)
@EnableAutoConfiguration(exclude = { JmxAutoConfiguration.class, ErrorMvcAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "net.apmm.mdm.ops.geo.*"} )
public class GeoMQPublishServiceApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GeoMQPublishServiceApplication.class, args);

    }
}
