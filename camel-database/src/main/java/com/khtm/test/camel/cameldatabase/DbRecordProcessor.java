package com.khtm.test.camel.cameldatabase;

import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DbRecordProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String data = exchange.getIn().getBody(String.class);
        LoggerFactory.getLogger(">>>>>> TEST - BODY >>>>>>>>").info(data);
        data = data.replace("{", "{\"").replace("}", "\"}")
                .replace(", ", "\", \"").replace("=", "\":\"");
        User user = new GsonBuilder().create().fromJson(data, User.class);
        LoggerFactory.getLogger(">>>>>> TEST - BODY >>>>>>>>").info(user.toString());
    }
}
