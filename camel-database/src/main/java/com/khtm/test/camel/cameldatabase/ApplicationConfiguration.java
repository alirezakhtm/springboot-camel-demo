package com.khtm.test.camel.cameldatabase;

import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

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
                        "?consumer.onConsume=update testdb.tbl_user set status='" + OrderStatus.CANCELED + "' where id=:#id")
                        .id("Camel-Database-Bean")
                        .beanRef("orderItemMessageTranslator", "transformToOrderItemMessage")
                        .to("log:com.khtm.test.camel.cameldatabase.User?level=INFO");

                from("sql:select * from testdb.tbl_user where status='" + OrderStatus.NEW + "'")
                        .id("Camel-Database-Logger")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                LoggerFactory.getLogger(">>>>>> TEST - BODY >>>>>>>>").info(
                                        exchange.getIn().getBody(String.class)
                                );
                            }
                        })
                        .log(">>>> Camel Direct");
            }
        };
    }


}
