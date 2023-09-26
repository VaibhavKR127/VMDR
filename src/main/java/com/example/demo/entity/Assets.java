package com.example.demo.entity;



import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="assets")
public class Assets {
	
	@Id
	@Column(name="assetCode")
	private String assetCode;
	
	@Column(name="assetName")
	private String assetName;
	
	@Column(name="hostName")
	private String hostName;
	
	@Column(name="ipAddress")
	private String ipAddress;
	
	@Column(name="assetType")
	private String assetType;
	
	@Column(name="primaryAssetOwner")
	private String primaryAssetOwner;
	
	@Column(name="secondaryAssetOwner")
	private String secondaryAssetOwner;
	
	@Column(name="creationDate")
	private Date creationDate;

	@Column(name="lastModified")
	private Date lastModified;

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getPrimaryAssetOwner() {
		return primaryAssetOwner;
	}

	public void setPrimaryAssetOwner(String primaryAssetOwner) {
		this.primaryAssetOwner = primaryAssetOwner;
	}

	public String getSecondaryAssetOwner() {
		return secondaryAssetOwner;
	}

	public void setSecondaryAssetOwner(String secondaryAssetOwner) {
		this.secondaryAssetOwner = secondaryAssetOwner;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Assets(String assetCode, String assetName, String hostName, String ipAddress, String assetType,
			String primaryAssetOwner, String secondaryAssetOwner, Date creationDate, Date lastModified) {
		super();
		this.assetCode = assetCode;
		this.assetName = assetName;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.assetType = assetType;
		this.primaryAssetOwner = primaryAssetOwner;
		this.secondaryAssetOwner = secondaryAssetOwner;
		this.creationDate = creationDate;
		this.lastModified = lastModified;
	}

	public Assets(String assetCode, String assetName, String hostName, String ipAddress, String assetType,
			String primaryAssetOwner, String secondaryAssetOwner, Date creationDate) {
		super();
		this.assetCode = assetCode;
		this.assetName = assetName;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.assetType = assetType;
		this.primaryAssetOwner = primaryAssetOwner;
		this.secondaryAssetOwner = secondaryAssetOwner;
		this.creationDate = creationDate;
	}
	public Assets() {
		
	}

	@Override
	public String toString() {
		return "Assets [assetCode=" + assetCode + ", assetName=" + assetName + ", hostName=" + hostName + ", ipAddress="
				+ ipAddress + ", assetType=" + assetType + ", primaryAssetOwner=" + primaryAssetOwner
				+ ", secondaryAssetOwner=" + secondaryAssetOwner + ", creationDate=" + creationDate + ", lastModified="
				+ lastModified + "]";
	}
	
	
}
