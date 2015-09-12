package com.shankar.cars;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.java.Log;

@Log
public class EmailService {

	public static boolean sendEmail(String email, String newUserActivationCode,
			Long userId, String userName) throws Exception {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String user_activation_code = PasswordService.decrypt(newUserActivationCode);
		String msgBody = "This is your new  password : " + user_activation_code;

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("admin@example.com",
					"Example.com Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
			/* "user@example.com" */email, "Mr./Ms. " + userName));
			msg.setSubject("Your Example.com account has been activated");
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (AddressException e) {
			log.severe("AddressException:" + e.getMessage());
			return false;
		} catch (MessagingException e) {
			log.severe("MessagingException:" + e.getMessage());
			return false;
		}

		return true;

	}

	public static boolean sendChangePasswordEmail(String email,
			String newUserActivationCode, Long userId, String userName)
			throws Exception {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String user_activation_code = PasswordService.decrypt(newUserActivationCode);
		String msgBody = "This is your new  password : " + user_activation_code;

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("admin@example.com",
					"Example.com Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
			/* "user@example.com" */email, "Mr./Ms. " + userName));
			msg.setSubject("Your Example.com account has been activated");
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (AddressException e) {
			log.severe("AddressException:" + e.getMessage());
			return false;
		} catch (MessagingException e) {
			log.severe("MessagingException:" + e.getMessage());
			return false;
		}

		return true;

	}
	
}

// ...
