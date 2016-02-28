package com.inf749.secureLogin.models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConnectionHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConnection;
	private Integer userId;
	private Timestamp timestampCreation;
	private Boolean validConnection;
	private String ipConnection;
	
	public Integer getIdConnection() {
		return idConnection;
	}
	public void setIdConnection(Integer idConnection) {
		this.idConnection = idConnection;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Timestamp getTimestampCreation() {
		return timestampCreation;
	}
	public void setTimestampCreation(Timestamp timestampCreation) {
		this.timestampCreation = timestampCreation;
	}
	public Boolean getValidConnection() {
		return validConnection;
	}
	public void setValidConnection(Boolean validConnection) {
		this.validConnection = validConnection;
	}
	public String getIpConnection() {
		return ipConnection;
	}
	public void setIpConnection(String ipConnection) {
		this.ipConnection = ipConnection;
	}
	
}
