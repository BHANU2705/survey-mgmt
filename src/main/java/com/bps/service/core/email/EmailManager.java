package com.bps.service.core.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailManager {
	
	public void sendEmail(String toEmail, String subject, String body) {
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("bpstech73@gmail.com", "Orange@123"));
		email.setSSLOnConnect(true);
		try {
			email.setFrom("bpstech73@gmail.com", "Admin Survey Management");
			email.setSubject(subject);
			email.setMsg(body);
			email.addTo(toEmail);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	public void sendSignUpEmail(String toEmail) {
		String subject = "Welcome | Survey Management";
		String body = "Welcome to Survey Management. You have been signed up as an Admin role.";
		sendEmail(toEmail, subject, body);
	}
	
	public void sendUserOffBoardingEmail(String toEmail, String userName) {
		String subject = "Sorry to see you go | Survey Management";
		String body = "Hey " + userName + ", what happened? Did we do anything unexpected? Sorry to see you go.";
		sendEmail(toEmail, subject, body);
	}
}
