package com.FlightBooking.jwt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.FlightBooking.helper.ResponseHelperClass;
import com.FlightBooking.helper.UserDetailsHelperClass;
import com.FlightBooking.jwt.model.JwtRequest;
import com.FlightBooking.jwt.model.JwtResponse;
import com.FlightBooking.jwt.model.UserCandidate;
import com.FlightBooking.jwt.service.ServiceUtilities;
import com.FlightBooking.jwt.service.UserService;
import com.FlightBooking.jwt.utilities.JWTUtility;

@RestController
//@CrossOrigin("*")
public class HomeController {

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@GetMapping("/test")
	public String home() {
		return "Welcome to aws console!!";
	}

	@PostMapping("authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

		System.out.println(jwtRequest + "jwtRequest");
		System.out.println("inside authentication");
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			//authenticate.

		} catch (BadCredentialsException e) {
			e.printStackTrace();
			return new JwtResponse("invalid credentials", false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new JwtResponse("invalid credentials", false);
		}

		final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());

		final String token = jwtUtility.generateToken(userDetails);

		return new JwtResponse(token, true);
	}

	@Autowired
	UserService Uservice;

	@GetMapping("validateToken")
	public Integer validate(HttpServletRequest httpServletRequest) throws Exception {

		String authorization = httpServletRequest.getHeader("Authorization");
		System.out.println(authorization + "jwt token");

		System.out.println(authorization + "authorization");

		try {
			String token = authorization.substring(3);
			Boolean validateToken = jwtUtility.validateToken(token,
					Uservice.loadUserByUsername(jwtUtility.getUsernameFromToken(token)));
			if (validateToken) {
				return new Integer(1);
			} else {
				return new Integer(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new Integer(0);
		}
	}

	@GetMapping("getuserid")
	public String getUserId(HttpServletRequest request) {

		System.out.println("authorization getUserid" + request.getHeader("Authorization"));
		String usernameFromToken = jwtUtility.getUsernameFromToken(request.getHeader("Authorization").trim());
		return usernameFromToken;

	}
	
	@GetMapping("getuserrole")
	public String getuserrole(HttpServletRequest request) {

		System.out.println("authorization getUserid" + request.getHeader("Authorization"));
		String usernameFromToken = jwtUtility.getRoleFromToken(request.getHeader("Authorization").trim().substring(3));
		return usernameFromToken;

	}
	
	
	@GetMapping("getuserdetailsnew")
	public ResponseHelperClass getuserdetailsnew(HttpServletRequest request) {
		UserDetailsHelperClass helper=new UserDetailsHelperClass();
		ResponseHelperClass res=new ResponseHelperClass();
		try
		{
			System.out.println("authorization getUserid" + request.getHeader("Authorization").substring(3));
			String usernameFromToken = jwtUtility.getUsernameFromToken(request.getHeader("Authorization").trim().substring(3));
			String role = jwtUtility.getRoleFromToken(request.getHeader("Authorization").trim().substring(3));
			
			helper.setRole(role);
			helper.setUsername(usernameFromToken);
			
			res.setData(helper);
			res.setStatus(true);
			return res;
		}
		catch (Exception e) {
			// TODO: handle exception
			res.setData("invalid jwttoken");
			res.setStatus(false);
			return res;
		}
		
	

	}

	@Autowired
	ServiceUtilities serviceUtilities;

	@PostMapping("/user/register")
	public 	ResponseEntity<ResponseHelperClass> Userregiser(@RequestBody UserCandidate userCandidate) {
		RestTemplate template = new RestTemplate();
		ResponseEntity<ResponseHelperClass> exchange = template.postForEntity(
				serviceUtilities.getEurkaClientBaseUriofBookingServiceMain() + "/user/register",userCandidate ,
				ResponseHelperClass.class);
		System.out.println(exchange.getStatusCodeValue());
		System.out.println(exchange.getBody());
		return exchange;

	}
}
