package com.example.springboot.Login;

import java.io.IOException;
//import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;

@Service
@Transactional
public class EmailForgetPasswordService {
	
	@Autowired
    private JavaMailSender emailSender;
	
	public void sendforgetpasswordEmail(Mail mail,String text) throws MessagingException, IOException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail.getMailTo());
		message.setFrom(mail.getFrom());
		message.setText(text);
		message.setSubject(mail.getSubject());
		emailSender.send(message);
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,
//                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                StandardCharsets.UTF_8.name());
//        Context context = new Context();
//        context.setVariables(mail.getProps());  
//        String html = templateEngine.process("supplier-email", context);
//        helper.setTo(mail.getMailTo());
//        helper.setText(html, true);
//        helper.setSubject(mail.getSubject());
//        helper.setFrom(mail.getFrom());
//        emailSender.send(message);
    }
	
}
