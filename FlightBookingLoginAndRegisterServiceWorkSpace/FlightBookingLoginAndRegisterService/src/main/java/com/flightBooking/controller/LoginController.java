package com.flightBooking.controller;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flightBooking.utilities.JwtRequest;
import com.flightBooking.utilities.JwtResponse;
import com.flightBooking.utilities.LoginRequestHelperClass;
import com.flightBooking.utilities.ResponseHelperClass;
import com.flightBooking.utilities.UserCandidate;

@RestController
@RequestMapping("authenticate")
public class LoginController implements Serializable {
	/*
	 * @Autowired private RestTemplate template;
	 */
	@Autowired
	JwtRequest jwtRequest;
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private final String BaseURL = "http://localhost:9090";

	private String getEurkaClientBaseUriofBookingServiceMain() {
		ServiceInstance serviceInstance = loadBalancerClient.choose("FLIGHTBOOKINGSERVICES");
		return serviceInstance.getUri().toString();
	}
	
	private String getjwtClient() {
		ServiceInstance serviceInstance = loadBalancerClient.choose("JWT-CLIENT");
		return serviceInstance.getUri().toString();
	}

	@PostMapping("/User/Login")
	public ResponseEntity<JwtResponse> UserLogin(@RequestBody LoginRequestHelperClass helperClass) {
		logger.info("helperClass in logging " + helperClass);
		RestTemplate template = new RestTemplate();
		jwtRequest.setPassword(helperClass.getPassword());
		jwtRequest.setUsername(helperClass.getEmailid());	
 ResponseEntity<JwtResponse> postForEntity = template.postForEntity(	getjwtClient()+"/authenticate", jwtRequest, JwtResponse.class);
System.out.println(postForEntity+"postForEntity");
		return postForEntity;
	}
	
	
	@PostMapping("/User/Register")
	public ResponseEntity<ResponseHelperClass> UserRegister(@RequestBody UserCandidate userCandidate) {
		logger.info("userCandidate in logging " + userCandidate);
		// http://LTIN314322.cts.com:9090
		RestTemplate template = new RestTemplate();
		String eurkaClientBaseUriofBookingServiceMain = getEurkaClientBaseUriofBookingServiceMain();
		System.out.println(eurkaClientBaseUriofBookingServiceMain + "eurkaClientBaseUriofBookingServiceMain");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserCandidate> httpEntity = new HttpEntity<UserCandidate>(userCandidate, headers);
		ResponseEntity<ResponseHelperClass> exchange = template.exchange(
				eurkaClientBaseUriofBookingServiceMain + "/user/register", HttpMethod.POST, httpEntity,
				ResponseHelperClass.class);

		return exchange;
	}
	
	
}
