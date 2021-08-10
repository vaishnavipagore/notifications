package com.example.notifications.notificationbody;

import com.google.gson.annotations.SerializedName;

public class NotificationBody{

	@SerializedName("title")
	private String title;

	@SerializedName("message")
	private String message;

	@SerializedName("deviceToken")
	private String deviceToken;

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setDeviceToken(String deviceToken){
		this.deviceToken = deviceToken;
	}

	public String getDeviceToken(){
		return deviceToken;
	}
}