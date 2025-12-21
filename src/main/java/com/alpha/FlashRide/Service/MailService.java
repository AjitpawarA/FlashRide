package com.alpha.FlashRide.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
 @Autowired
 private JavaMailSender javaMailServer;
 
 public void sendMail(String tomail,String subject, String message) {
	  SimpleMailMessage mail = new  SimpleMailMessage();
	  mail.setTo(tomail);
	  mail.setSubject(subject);
	  mail.setFrom("samasneha123@gmail.com");
	  mail.setText(message);
	  
	  javaMailServer.send(mail);
	  
 }
 
}
