package com.example.springboot.Login;

import java.util.List;
import java.util.Map;

public class Mail {
	
	private Integer id;
	private String MailTo;
	private String From;
	private String Subject;
	private List<Object> attachments;
    private Map<String, Object> props;

    public Mail() {}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMailTo() {
		return MailTo;
	}
	public void setMailTo(String mailTo) {
		MailTo = mailTo;
	}
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}

	public List<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
	
	
}
