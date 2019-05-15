package com.khtm.test.camel.firstexample.components;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class CamelRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        System.out.println("Start ...");
//        makeRouteForMovingFile();
//        makeRouteForMovingSpecificFile(".txt");
//        makeRouteForMovingSpecificFileWithBody("java");
//        fileProcess();
//        multipleFilesProcess();
//        multipleFilesProcessByCostumeProcessor();
        System.out.println("Stop ...");
    }

    private void makeRouteForMovingFile() {
        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input")
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output");
    }

    private void makeRouteForMovingSpecificFile(String fileName) {
        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input")
                .filter(header(Exchange.FILE_NAME).contains(fileName))
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output");
    }

    private void makeRouteForMovingSpecificFileWithBody(String content) {
        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input")
                .filter(body().startsWith(content))
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output");
    }

    private void fileProcess() {
        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input?noop=true")
                .process(p -> {
                    String body = p.getIn().getBody(String.class);
                    StringBuilder sb = new StringBuilder();
                    Arrays.stream(body.split(" ")).forEach(s -> {
                        sb.append(s + ",");
                    });
                    p.getIn().setBody(sb);
                })
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output?fileName=records.csv");
    }

    private void multipleFilesProcess() {
        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input")
                .unmarshal().csv().split(body().tokenize(",")).choice()
                .when(body().contains("Closed"))
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output?fileName=closed.csv")
                .when(body().contains("Pending"))
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output?fileName=pending.csv")
                .when(body().contains("Interest"))
                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output?fileName=interest.csv");
    }

//    private void multipleFilesProcessByCostumeProcessor() {
//        from("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/input")
//                .unmarshal().csv().split(body().tokenize(",")).choice()
//                .when(body().contains("Closed"))
//                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output?fileName=closed.csv")
//                .when(body().contains("Pending"))
//                .to("file:/home/alireza/Projects/IntelliJ/source-for-apache-camel-example/output?fileName=pending.csv")
//                .when(body().contains("Interest"))
//                .to();
//    }

}
