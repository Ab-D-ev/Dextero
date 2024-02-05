package com.example.springboot.Login;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmailSendLoginPasswordUserService {
	
	@Autowired
    private JavaMailSender emailSender;
	
	public void senduserloginpasswordEmail(Mail mail,String text) throws MessagingException, IOException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail.getMailTo());
		message.setFrom(mail.getFrom());
		message.setText(text);
		message.setSubject(mail.getSubject());
		emailSender.send(message);

	}
}
