package coms309.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class CodeRunnerWebsocketControllerConfig {
    
    @Bean
    public ServerEndpointExporter serverEdnpointExporter() {
        return new ServerEndpointExporter();
    }

}
