package com.zama.learning;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;  

/**
 * Simple app to send mail using SMTP server.
 * 
 * @author zama
 *
 */
public class MailSender {
	public static void main(String[] args) {
		String host = System.getProperty("host");
		String port = System.getProperty("port");
		String user = System.getProperty("user");
		String password = System.getProperty("password");
		String to = System.getProperty("to");
		String sender = System.getProperty("sender");
		String tls = System.getProperty("tls");

		System.out.println("host: "+host+", port: "+port+", user: "+user+", password: "+password+", to: "+to+", tls: "+tls);

		if(host != null && port != null & user != null && password != null && to != null) {
			Session session = getSession(host, user, password, port, tls); 

			//Compose the message  
			try {  
				MimeMessage message = new MimeMessage(session);  
				message.setFrom(new InternetAddress(sender));  
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				message.setSubject("Testing SMTP client");  
				message.setText("Sent from "+host +" with TLS: "+tls);  

				//send the message  
				Transport.send(message);  

				System.out.println("message sent successfully...");  
			} catch (MessagingException e) {e.printStackTrace();}  
		}else {
			System.err.println("Invalid params!!!!");	
			System.exit(0);
		}
	}

	private static Session getSession(String host, String user, String password, String port, String tls) {
		Properties props = new Properties();  
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.port", port);  
		props.put("mail.smtp.auth", "true");  
		
		if("true".equalsIgnoreCase(tls)) {
			props.put("mail.smtp.starttls.enable", "true");
		}

		Session session = Session.getDefaultInstance(props,  
				new javax.mail.Authenticator() {  
			protected PasswordAuthentication getPasswordAuthentication() {  
				return new PasswordAuthentication(user,password);  
			}  
		});
		return session;
	}  
}
