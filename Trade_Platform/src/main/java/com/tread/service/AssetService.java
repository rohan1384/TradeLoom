package com.tread.service;

import java.util.List;

import com.tread.model.Asset;
import com.tread.model.Coin;
import com.tread.model.User;

public interface AssetService {

	Asset createAsset(User user,Coin coin, double quantity);
	
	
	Asset getAssetById(Long assestId) throws Exception;
	
	Asset getAssetByUserIdAndId(Long userId,Long assetId);
	
	List<Asset>getUsersAssets(Long userId);
	
	Asset updateAsset(Long assetId,double quantity) throws Exception;
	
	Asset findAssetByUserIdAndCoinId(Long assetId,String coinId);
	
	void deleteAsset(Long assetId);
	
}
