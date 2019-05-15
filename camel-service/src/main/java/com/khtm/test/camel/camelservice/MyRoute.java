package com.khtm.test.camel.camelservice;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().host("localhost").port(8963);
        from("timer:hello?period={{timer.period}}")
                .setHeader("id", simple("${random(1,3)}"))
                .log("Send request to api with id = {id}")
                .to("rest:get:book/select-id/{id}")
                .log("${body}");
    }
}
