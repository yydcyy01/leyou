package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author YYDCYY
 * @create 2019-11-04
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouSearchApplication.class);
    }
}
