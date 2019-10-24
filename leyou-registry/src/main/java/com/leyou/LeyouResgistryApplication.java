package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author YYDCYY
 * @create 2019-10-17
 */
@SpringBootApplication
@EnableEurekaServer
public class LeyouResgistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouResgistryApplication.class);
    }
}
