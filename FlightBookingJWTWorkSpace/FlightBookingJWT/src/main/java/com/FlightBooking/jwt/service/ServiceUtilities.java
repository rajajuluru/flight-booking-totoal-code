package com.FlightBooking.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.FlightBooking.jwt.model.UserCandidate;

@Service
public class ServiceUtilities {

	private final String baseurl = "http://localhost:9090/getUserDetails/";
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	public String getEurkaClientBaseUriofBookingServiceMain() {
		ServiceInstance serviceInstance = loadBalancerClient.choose("FLIGHTBOOKINGSERVICES");
		return serviceInstance.getUri().toString();
	}

	public UserCandidate getUserPasswordFrmDB(String username) {
		System.out.println(username + "username");
		RestTemplate template = new RestTemplate();
		//System.out.println(getEurkaClientBaseUriofBookingServiceMain());
		ResponseEntity<UserCandidate> exchange = template.exchange(getEurkaClientBaseUriofBookingServiceMain()+"/getUserDetails/" + username, HttpMethod.GET, null, UserCandidate.class);
		System.out.println(exchange.getStatusCodeValue());
		System.out.println(exchange.getBody());
		return exchange.getBody();
	}
	
}
