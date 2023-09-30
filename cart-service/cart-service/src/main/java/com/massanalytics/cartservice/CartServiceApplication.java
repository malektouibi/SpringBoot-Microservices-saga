package com.massanalytics.cartservice;

import com.massanalytics.cartservice.config.AxonXstreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@EnableDiscoveryClient
@Import({ AxonXstreamConfig.class })
public class CartServiceApplication {

	public static void main(String[] args) { SpringApplication.run(CartServiceApplication.class, args); }

}
