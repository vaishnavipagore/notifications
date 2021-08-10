package com.example.notifications.notificationbody;

import com.google.gson.annotations.SerializedName;

public class NotificationResponse{

	@SerializedName("responsecode")
	private int responsecode;

	@SerializedName("status")
	private String status;

	public void setResponsecode(int responsecode){
		this.responsecode = responsecode;
	}

	public int getResponsecode(){
		return responsecode;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}