package com.khtm.test.camel.cameldatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderItemMessageTranslator {

    private Logger logger = LoggerFactory.getLogger(OrderItemMessageTranslator.class);

    public String transformToOrderItemMessage(Map<String, Object> orderIds) {
        String output = null;
        try {
            if (orderIds == null) {
                throw new Exception("Order id was not bound to the method via integration framework.");
            }
            if (!orderIds.containsKey("id")) {
                throw new Exception("Could not find a valid key of 'id' for the order ID.");
            }
            if (orderIds.get("id") == null || !(orderIds.get("id") instanceof Integer)) {
                throw new Exception("The order id was not correctly provided or formatted.");
            }
            output = "Successful Process for Order id.";
        } catch (Exception e) {
            logger.error("Order processing failed: " + e.getMessage(), e);
        }
        return output;
    }
}
