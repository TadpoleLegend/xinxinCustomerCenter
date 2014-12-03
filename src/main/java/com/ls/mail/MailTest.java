package com.ls.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.ls.util.PropertiesUtil;

public class MailTest {
	public static void main(String [] args){
		 SimpleMailSender sms = MailSenderFactory
		            .getSender(MailSenderType.SMTP);
		 SimpleMail sm = new SimpleMail();
		 sm.setSubject("Mail Test");
		 sm.setContent("wo kao");
		        List<String> recipients = new ArrayList<String>();
		        recipients.add(PropertiesUtil.getProperty("recept_1", "xinxin.properties"));
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
