package com.flightBooking.utilities;

import org.springframework.stereotype.Component;

@Component
public class JwtResponse {

    public JwtResponse(String jwtToken,boolean statu) {
		super();
		this.jwtToken = jwtToken;
		this.status=statu;
	}
    public JwtResponse() {
  		super();
  		
  	}

    private boolean status=false;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	private String jwtToken;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}
