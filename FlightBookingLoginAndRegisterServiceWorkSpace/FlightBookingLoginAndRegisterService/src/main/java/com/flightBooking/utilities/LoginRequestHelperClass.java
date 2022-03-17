package com.flightBooking.utilities;
import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class LoginRequestHelperClass implements Serializable{

@NotNull(message="emailid cannot be empty")
@NotBlank(message="emailid cannot be empty")	
private String emailid;
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((emailid == null) ? 0 : emailid.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	LoginRequestHelperClass other = (LoginRequestHelperClass) obj;
	if (emailid == null) {
		if (other.emailid != null)
			return false;
	} else if (!emailid.equals(other.emailid))
		return false;
	if (password == null) {
		if (other.password != null)
			return false;
	} else if (!password.equals(other.password))
		return false;
	return true;
}
@NotNull(message="password cannot be empty")
@NotBlank(message="password cannot be empty")
private String password;
public String getEmailid() {
	return emailid;
}
@Override
public String toString() {
	return "LoginRequestHelperClass [emailid=" + emailid + ", password=" + password + "]";
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
}
