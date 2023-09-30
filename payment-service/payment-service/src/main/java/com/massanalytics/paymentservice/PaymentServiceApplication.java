package com.massanalytics.paymentservice;

import com.massanalytics.paymentservice.config.AxonXstreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import({ AxonXstreamConfig.class })
public class PaymentServiceApplication {

	public static void main(String[] args) { SpringApplication.run(PaymentServiceApplication.class, args); }

}
