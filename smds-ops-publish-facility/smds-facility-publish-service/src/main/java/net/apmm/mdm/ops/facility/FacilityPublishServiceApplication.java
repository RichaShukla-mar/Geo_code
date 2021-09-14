package net.apmm.mdm.ops.facility;

import net.apmm.mdm.ops.facility.service.PublishFacilityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackageClasses = PublishFacilityService.class)
@EnableAutoConfiguration(exclude = { JmxAutoConfiguration.class, ErrorMvcAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "net.apmm.mdm.ops.facility.*"} )
public class FacilityPublishServiceApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(FacilityPublishServiceApplication.class, args);

    }

}
