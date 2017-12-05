package com.saicmotor.ops.wwx.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Shen_JM on 2017/12/05.
 */
public interface AssetManageService {

	/**
	 * 查询服务器列表
	 * @param resKeyword
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getAssetManageResource(int resKeyword) throws Exception;
	
	
	/**
	 * 网络设备列表API
	 * @param tenantKeyword
	 * @param resKeyword
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getAssetManageTenant(String tenantKeyword , int resKeyword) throws Exception;
}

