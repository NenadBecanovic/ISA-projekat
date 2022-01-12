package com.application.bekend.DTO;

public class EmailDTO {
	
	private String title;
	private String content;
	private String userEmail;
	
	public EmailDTO(String title, String content, String userEmail) {
		super();
		this.title = title;
		this.content = content;
		this.userEmail = userEmail;
	}
	
	public EmailDTO() {
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
