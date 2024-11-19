package org.example.transporteservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.channel.DirectChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class TcpConfig {

//    @Bean
//    public TcpNetServerConnectionFactory serverConnectionFactory() {
//        return new TcpNetServerConnectionFactory(6000); // Cambia el puerto si es necesario
//    }
//
//    @Bean
//    public TcpInboundGateway tcpInboundGateway() {
//        TcpInboundGateway gateway = new TcpInboundGateway();
//        gateway.setConnectionFactory(serverConnectionFactory());
//        gateway.setRequestChannel(receiveChannel());
//        gateway.setReplyChannel(sendChannel());
//        return gateway;
//    }
//
//    @Bean
//    public MessageChannel receiveChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageChannel sendChannel() {
//        return new DirectChannel();
//    }
}
