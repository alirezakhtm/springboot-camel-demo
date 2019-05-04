package com.khtm.test.camel.firstexample.components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        System.out.println("Start ...");
        makeRouteForMovingFile();
        System.out.println("Stop ...");
    }

    private void makeRouteForMovingFile() {
        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input")
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output");
    }

    private void makeRouteForMovingSpecificFile(String fileName) {
        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input")
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output");
    }
    // 14:30
}
