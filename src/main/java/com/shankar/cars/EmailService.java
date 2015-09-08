package com.shankar.cars;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

	public static boolean sendEmail(String email, String newUserActivationCode,
			Long userId, String userName) throws Exception {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "This is your activation code : "
				+ newUserActivationCode;

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
			return false;
			// ...
		} catch (MessagingException e) {
			// ...
			return false;
		}

		return true;

	}

}

// ...
