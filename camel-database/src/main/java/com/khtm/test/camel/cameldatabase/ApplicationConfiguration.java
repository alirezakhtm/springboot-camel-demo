package com.khtm.test.camel.cameldatabase;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    private Environment environment;

    /**
     * This bean created for defining camel route, This route gets data from database with status 'NEW USER' and updates
     * them to status 'PENDING USER'
     */
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

                from("sql:select id from testdb.tbl_user where status='" + OrderStatus.PENDING + "'" +
                        "?consumer.onConsume=update testdb.tbl_user set status='" + OrderStatus.CANCELED + "' where id=:#id")
                        .id("Camel-Database-to-Queue")
                        .beanRef("orderItemMessageTranslator", "transformToOrderItemMessage")
                        .to("activemq:queue:USER_INFORMATION");
            }
        };
    }

    /**
     * Bean for using apache-activemq
     * */
    @Bean
    public ActiveMQConnectionFactory connectionFactory () {
        return new ActiveMQConnectionFactory(environment.getProperty("spring.activemq.broker-url"));
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public PooledConnectionFactory pooledConnectionFactory(){
        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMaxConnections(Integer.parseInt(environment.getProperty("poolConnectionFactory.maxConnection")));
        return factory;
    }

    @Bean
    public JmsConfiguration jmsConfiguration(){
        JmsConfiguration configuration = new JmsConfiguration();
        configuration.setConnectionFactory(pooledConnectionFactory());
        return configuration;
    }

    @Bean
    public ActiveMQComponent activeMq(){
        ActiveMQComponent activeMq = new ActiveMQComponent();
        activeMq.setConfiguration(jmsConfiguration());
        return activeMq;
    }
}
