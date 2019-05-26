package com.khtm.test.camel.cameldatabase;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    /**
     * This bean created for defining camel route, This route gets data from database with status 'NEW USER' and updates
     * them to status 'PENDING USER'
     * */
    @Bean
    public RouteBuilder newWebsiteOrderRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("sql:select id from testdb.tbl_user where status='" + OrderStatus.PENDING + "'" +
                        "?consumer.onConsume=update testdb.tbl_user set status='" + OrderStatus.CAMCELED + "' where id=:#id")
                        .id("Camel-Database-Bean")
                        .beanRef("orderItemMessageTranslator", "transformToOrderItemMessage")
                        .to("log:com.khtm.test.camel.cameldatabase.User?level=INFO");
            }
        };
    }


}
