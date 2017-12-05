package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.AssetManageService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by Shen_JM on 2017/12/05.
 */
@Service
public class AssetManageServiceImpl implements AssetManageService {
    private static Logger log = LoggerFactory.getLogger(AssetManageServiceImpl.class);

    @Value("${yunwei.assetManageResource.url}")
    private String yAssetManageResourceUrl ;
    @Value("${yunwei.assetManageTenant.url}")
    private String yAssetManageTenantUrl ;
    
    @Autowired
    private HttpHelper restUtil;
    

    public Map<String,Object> getAssetManageResource(int resKeyword) 
    			throws Exception {
    	
        String url = this.yAssetManageResourceUrl + resKeyword;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map<String,Object>> rourceList = refactor( (List)((Map<String,Object>)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("rourceList", rourceList);
        return resultMap;
    }

    
    public Map<String,Object> getAssetManageTenant(String tenantKeyword , int resKeyword) throws Exception {
        String url = this.yAssetManageTenantUrl + "tenantKeyword=" + tenantKeyword + "&resKeyword=" + resKeyword;
		Map<String,Object> result = restUtil.getJson(url);
		List<Map<String,Object>> tenantList = refactorTenant( (List)((Map)result.get("body")).get("data") );
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("tenantList", tenantList);
		return resultMap;
    }
    
    private List<Map<String,Object>> refactor(List<Map> data) throws Exception{
    	List<Map<String,Object>> rourceList = new ArrayList<Map<String,Object>>();
        for (Map<String,Object> map : data) {
        	Map<String,Object> resultsMap = new HashMap<String,Object>();
        	resultsMap.put("name", map.get("name"));
        	
        	Map<String,Object> dataMap = (Map<String,Object>)map.get("data");
        	Map<String,Object> tempMap = new HashMap<String,Object>();
        	tempMap.put("totalcount", dataMap.get("count"));
        	
    		List<Map<String,Object>> detailList = (List) dataMap.get("detail");
    		List<Map<String,Object>> detailLists = new ArrayList<Map<String,Object>>();
    		for (Map<String,Object> detailMaps : detailList) {
//    			String region = (String) detailMaps.get("region");
//    			int count = (Integer) detailMaps.get("count");
//    			String detailResult = region + ":" + "数量是: " + count;
//    			detailLists.add(detailResult);
    			Map<String,Object> detailMap = new HashMap<String,Object>();
    			detailMap.put("region", detailMaps.get("region"));
    			detailMap.put("count", detailMaps.get("count"));
    			detailLists.add(detailMap);
    		}
    		tempMap.put("detail", detailLists);
    		
    		resultsMap.put("data",tempMap);
    		
    		rourceList.add(resultsMap);
        }
        return rourceList;
    }

    private List<Map<String,Object>> refactorTenant(List<Map> data) throws Exception{
    	List<Map<String,Object>> tenantList = new ArrayList<Map<String,Object>>();
        for (Map<String,Object> map : data) {
    		Map<String,Object> resultMap = new HashMap<String,Object>();
    		resultMap.put("tenant_name", map.get("tenant_name"));
    		
    		List<Map<String,Object>> resourcesList = (List) map.get("resources");
    		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
    		for (Map<String,Object> resourcesMaps : resourcesList) {
    			Map<String,Object> resourceMap = new HashMap<String,Object>();
    			resourceMap.put("name", resourcesMaps.get("name"));
    			resourceMap.put("value", resourcesMaps.get("value"));
    			resultList.add(resourceMap);
    		}
    		
    		resultMap.put("resources", resultList);
    		tenantList.add(resultMap);
        	}
        return tenantList;
    }
    
}
