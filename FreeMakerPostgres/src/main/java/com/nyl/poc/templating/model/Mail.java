package com.nyl.poc.templating.model;

public class Mail {
	public Mail() {

	}

	private String to;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public MailContent getMailContent() {
		return mailContent;
	}

	public void setMailContent(MailContent mailContent) {
		this.mailContent = mailContent;
	}

	private String from;
	private String templateId;
	private MailContent mailContent;

}
