package com.ls.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class MailTest {
	public static void main(String [] args){
		 SimpleMailSender sms = MailSenderFactory
		            .getSender(MailSenderType.SMTP);
		 SimpleMail sm = new SimpleMail();
		 sm.setSubject("Mail Test");
		 sm.setContent("wo kao");
		        List<String> recipients = new ArrayList<String>();
		        recipients.add("charles.liu@bleum.com");
		        try {
		            for (String recipient : recipients) {
		            sms.send(recipient, sm);
		            }
		        } catch (AddressException e) {
		            e.printStackTrace();
		        } catch (MessagingException e) {
		            e.printStackTrace();
		        }
	}
}
