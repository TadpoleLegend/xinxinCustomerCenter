package com.ls.mail;

import java.io.Serializable;

public class SimpleMail implements Serializable{

	private static final long serialVersionUID = -7944797510525668770L;

	public String subject;
	public String content;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
