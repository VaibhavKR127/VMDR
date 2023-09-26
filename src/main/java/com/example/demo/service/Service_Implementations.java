package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Assets;
import com.example.demo.repository.AssetsRepo;

import jakarta.transaction.Transactional;

@Service
public class Service_Implementations implements Service_Declarations{

	@Autowired
	private AssetsRepo arep;
	
	@Override
	@Transactional
	public List<Assets> DisplayAllAssets() {
		// TODO Auto-generated method stub
		List<Assets> assetList = arep.findAll();
		return assetList;
	}

	@Override
	@Transactional
	public void insertAssets(Assets asset) {
		
		arep.save(asset);
	}

	@Override
	@Transactional
	public void deleteAssets(String assetCode) {
		// TODO Auto-generated method stub
		arep.deleteById(assetCode);
	}

	@Override
	@Transactional
	public void updateAssets(Assets asset) {
		// TODO Auto-generated method stub
		arep.save(asset);
	}

	@Override
	@Transactional
	public Assets getAssetsByCode(String code) {
		// TODO Auto-generated method stub
		Assets asset = arep.findById(code).get();
		return asset;
	}

	
}
