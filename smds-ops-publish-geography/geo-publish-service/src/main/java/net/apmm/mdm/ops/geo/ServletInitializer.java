package net.apmm.mdm.ops.geo;

/*
public class ServletInitializer {
}
*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

/**
 * Added only because - Spring boot application is getting deployed into weblogic
 * Should be removed when moving out of weblogic
 */
@Slf4j
public class ServletInitializer extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(GeoPublishServiceApplication.class);
    }
}
