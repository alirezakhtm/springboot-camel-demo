package com.khtm.test.camel.cameldatabase;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseCamelRoute extends RouteBuilder {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure() throws Exception {
        from("sql:select id from testdb.tbl_user where status = '" +
                OrderStatus.NEW +
                "'" +
                "?" +
                "consumer.onConsume=update testdb.tbl_user set status = '" +
                OrderStatus.PENDING +
                "'" +
                " where id = :#id")
                .to("log:com.khtm.test.camel.cameldatabase?level=INFO");

    }
}
