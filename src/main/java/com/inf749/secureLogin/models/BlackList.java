package com.inf749.secureLogin.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlackList implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private String userName;
	private Timestamp timestampCreation;
	private String ipConnection;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getTimestampCreation() {
		return timestampCreation;
	}
	public void setTimestampCreation(Timestamp timestampCreation) {
		this.timestampCreation = timestampCreation;
	}
	public String getIpConnection() {
		return ipConnection;
	}
	public void setIpConnection(String ipConnection) {
		this.ipConnection = ipConnection;
	}
	
}
