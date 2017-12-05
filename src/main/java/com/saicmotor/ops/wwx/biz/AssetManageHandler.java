package com.saicmotor.ops.wwx.biz;

import com.saicmotor.ops.wwx.service.AssetManageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AssetManageHandler extends BizHandler {
    private static Logger log = LoggerFactory.getLogger(AssetManageHandler.class);

    @Autowired
    private AssetManageService assetManageService;


    public Map<String,Object> getAssetManageResource(String... params) throws Exception{
    	String resKeywordStr = params[0];
    	int resKeyword = Integer.parseInt(resKeywordStr);

    	Map<String,Object> resourceMap = assetManageService.getAssetManageResource(resKeyword);
        List<Map> resources = (List) resourceMap.get("rourceList");

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msgType", "text");
        result.put("msgFtl", "asset/rource.ftl");
        result.put("data",resources);
        return result;
    }
    
    public Map<String,Object> getAssetManageTenant(String... params) throws Exception{
    	String tenantKeywordBase64  = params[0];
    	String resKeywordStr = params[1];
    	int resKeyword = Integer.parseInt(resKeywordStr);
    	
		byte[] tenantKeywordByte =  Base64.getDecoder().decode(tenantKeywordBase64);  
		String tenantKeyword = new String(tenantKeywordByte);

    	Map<String,Object> tenantMap = assetManageService.getAssetManageTenant(tenantKeyword, resKeyword);
        List<Map> tenants = (List) tenantMap.get("tenantList");

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msgType", "text");
        result.put("msgFtl", "asset/tenant.ftl");
        result.put("data",tenants);
        return result;
    }

}
