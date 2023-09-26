package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Assets;

public interface Service_Declarations {

	
	public List<Assets> DisplayAllAssets();
	public Assets getAssetsByCode(String code);
	public void insertAssets(Assets Asset);
	public void deleteAssets(String assetCode);
	public void updateAssets(Assets Assets);
	//public List<Assets> searchCompByName(String cname);
	
	
}
