package com.flightBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class FlightBookingLoginAndRegisterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightBookingLoginAndRegisterServiceApplication.class, args);
	}
	
	/*@Bean
	@LoadBalanced
	public RestTemplate template()
	{
		return new RestTemplate();
	}*/

}
