package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.CmdbService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by Shen_JM on 2017/11/29.
 */
@Service
public class CmdbServiceImpl implements CmdbService {
    private static Logger log = LoggerFactory.getLogger(CmdbServiceImpl.class);

    @Value("${yunwei.cmdbServer.url}")
    private String yCmdbServerUrl ;
    @Value("${yunwei.cmdbModule.url}")
    private String yCmdbModuleUrl ;
    @Value("${yunwei.cmdbUser.url}")
    private String yCmdbUserUrl ;
    @Value("${yunwei.cmdbIdc.url}")
    private String yCmdbIdcUrl ;
    @Value("${yunwei.cmdbLogic.url}")
    private String yCmdbLogicUrl ;
    @Value("${yunwei.cmdbState.url}")
    private String yCmdbStateUrl ;

    @Value("${yunwei.cmdbNetdevicesFunc.url}")
    private String yCmdbNetdevicesFuncUrl ;
    @Value("${yunwei.cmdbNetdevicesIdc.url}")
    private String yCmdbNetdevicesIdcUrl ;
    @Value("${yunwei.cmdbNetdevicesModel.url}")
    private String yCmdbNetdevicesModelUrl ;
    @Value("${yunwei.cmdbNetdevicesPro.url}")
    private String yCmdbNetdevicesProUrl ;
    @Value("${yunwei.cmdbNetdevicesType.url}")
    private String yCmdbNetdevicesTypeUrl ;
    @Value("${yunwei.cmdbNetdevices.url}")
    private String yCmdbNetdevicesUrl ;
    
    @Autowired
    private HttpHelper restUtil;
    

    public Map<String,Object> getCmdbServerList(String page, String length, String principal, String idc, String module_id, String logic_area, String state) 
    			throws Exception {
    	
        String url = this.yCmdbServerUrl + "page=" + page + "&length=" + length + "&principal=" + principal + "&idc=" + idc + "&logic_area=" + 
        				logic_area + "&state=" + state + "&module_id=" + module_id;
        Map<String,Object> result = restUtil.getJson(url);
        int total = (Integer)((Map)result.get("body")).get("total");
        List<Map> resultList = refactor( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("total", total);
        resultMap.put("resultList", resultList);
        return resultMap;
    }

    public List<Map> getCmdbModule(String module_id) throws Exception {
        String url = this.yCmdbModuleUrl + module_id;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)((Map)result.get("body")).get("data")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getCmdbUser() throws Exception {
        String url = this.yCmdbUserUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getCmdbIdc() throws Exception {
        String url = this.yCmdbIdcUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getCmdbLogic() throws Exception {
        String url = this.yCmdbLogicUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getCmdbState() throws Exception {
        String url = this.yCmdbStateUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getNetdevicesFunc() throws Exception {
        String url = this.yCmdbNetdevicesFuncUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getNetdevicesIdc() throws Exception {
        String url = this.yCmdbNetdevicesIdcUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getNetdevicesModel() throws Exception {
        String url = this.yCmdbNetdevicesModelUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getNetdevicesPro() throws Exception {
        String url = this.yCmdbNetdevicesProUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public List<Map> getNetdevicesType() throws Exception {
        String url = this.yCmdbNetdevicesTypeUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("name", (String)map.get("name"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    public Map<String,Object> getCmdbNetdevicesList(String page, String length, String netdev_idc, String netdev_type, String netdev_func, String netdev_pro, String netdev_model) throws Exception {
        String url = this.yCmdbNetdevicesUrl + "page=" + page + "&length=" + length + "&netdev_idc=" + netdev_idc + "&netdev_type=" + netdev_type + "&netdev_func=" + 
        		netdev_func + "&netdev_pro=" + netdev_pro + "&netdev_model=" + netdev_model;
		Map<String,Object> result = restUtil.getJson(url);
		int total = (Integer)((Map)result.get("body")).get("total");
		List<Map> resultList = refactorNetdevices( (List)((Map)result.get("body")).get("data") );
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("total", total);
		resultMap.put("resultList", resultList);
		return resultMap;
    }
    
    private List<Map> refactor(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("inner_ip", (String)map.get("inner_ip"));
        	tmp.put("asset_no", (String)map.get("asset_no"));
        	
        	List<Map> allmodules = (List)map.get("allmodules");
        	tmp.put("ops_principal", (String)allmodules.get(0).get("ops_principal"));
        	String module3 = (String)allmodules.get(0).get("name");
        	String module2 = (String)allmodules.get(1).get("name");
        	String module1 = (String)allmodules.get(2).get("name");
        	String module = module1 + "-" + module2 + "-" + module3;
        	tmp.put("module", module);
        	resultList.add(tmp);
        }
        return resultList;
    }

    private List<Map> refactorNetdevices(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("netdev_admin_ip", (String)map.get("netdev_admin_ip"));
        	tmp.put("netdev_type", (String)map.get("netdev_type"));
        	tmp.put("netdev_asset_id", (String)map.get("netdev_asset_id"));
        	tmp.put("netdev_maintainer", (String)map.get("netdev_maintainer"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
}
