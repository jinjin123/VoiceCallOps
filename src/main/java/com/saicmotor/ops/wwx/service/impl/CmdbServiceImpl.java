package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.CmdbService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    
    @Value("${yunwei.cmdbIdcexports.url}")
    private String yCmdbIdcexportsUrl ;
    @Value("${yunwei.cmdbIdclines.url}")
    private String yCmdbIdclinesUrl ;
    
    @Value("${yunwei.cmdbServerMonitor.url}")
    private String yCmdbServerMonitorUrl ;
    @Value("${yunwei.cmdbNetdevicesMonitor.url}")
    private String yCmdbNetdevicesMonitorUrl ;
    @Value("${yunwei.cmdbIdcMonitor.url}")
    private String yCmdbIdcMonitorUrl ;
    
    @Autowired
    private HttpHelper restUtil;
    

    public Map<String,Object> getCmdbServerList(String page, String length, String principal, String idc, String module_id, String logic_area, String state, String inner_ip) 
    			throws Exception {
    	
        String url = this.yCmdbServerUrl + "page=" + page + "&length=" + length + "&principal=" + principal + "&idc=" + idc + "&logic_area=" + 
        				logic_area + "&state=" + state + "&module_id=" + module_id + "&inner_ip=" + inner_ip;
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
    
    public Map<String,Object> getCmdbNetdevicesList(String page, String length, String netdev_idc, String netdev_type, String netdev_func, String netdev_pro, String netdev_model, String netdev_admin_ip) throws Exception {
        String url = this.yCmdbNetdevicesUrl + "page=" + page + "&length=" + length + "&netdev_idc=" + netdev_idc + "&netdev_type=" + netdev_type + "&netdev_func=" + 
        		netdev_func + "&netdev_pro=" + netdev_pro + "&netdev_model=" + netdev_model + "&netdev_admin_ip=" + netdev_admin_ip;
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


    public Map<String,Object> getCmdbIdcexportsList(String page, String length) throws Exception {
        String url = this.yCmdbIdcexportsUrl + "page=" + page + "&length=" + length;
		Map<String,Object> result = restUtil.getJson(url);
		int total = (Integer)((Map)result.get("body")).get("total");
		List<Map> resultList = refactorIdcexports( (List)((Map)result.get("body")).get("data") );
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("total", total);
		resultMap.put("resultList", resultList);
		return resultMap;
    }
    
    public Map<String,Object> getCmdbIdclinesList(String page, String length) throws Exception {
        String url = this.yCmdbIdclinesUrl + "page=" + page + "&length=" + length;
		Map<String,Object> result = restUtil.getJson(url);
		int total = (Integer)((Map)result.get("body")).get("total");
		List<Map> resultList = refactorIdclines( (List)((Map)result.get("body")).get("data") );
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("total", total);
		resultMap.put("resultList", resultList);
		return resultMap;
    }
    
    public List<Map> getCmdbServerMonitor(String begin,String end ,int minuteInterval ,String ip) throws Exception {
    	// 转换成时间戳
    	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Long beginTime = format.parse(begin).getTime();
    	Long endTime = format.parse(end).getTime();
    	
    	String url = this.yCmdbServerMonitorUrl + "beginTime=" + beginTime + "&endTime=" + endTime + "&minuteInterval=" + minuteInterval + "&ip=" + ip;
		Map<String,Object> result = restUtil.getJson(url);
		List<Map> resultList = refactorMonitor( (List)((Map)result.get("body")).get("data") );
		return resultList;
    }
    
    public List<Map> getCmdbNetdevicesMonitor(String begin,String end ,int minuteInterval ,String ip) throws Exception {
    	// 转换成时间戳
    	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Long beginTime = format.parse(begin).getTime();
    	Long endTime = format.parse(end).getTime();
    	
        String url = this.yCmdbNetdevicesMonitorUrl + "beginTime=" + beginTime + "&endTime=" + endTime + 
        		"&minuteInterval=" + minuteInterval + "&ip=" + ip;
		Map<String,Object> result = restUtil.getJson(url);
		List<Map> resultList = refactorMonitor( (List)((Map)result.get("body")).get("data") );
		return resultList;
    }
    
    public Map getCmdbIdcMonitor(String begin,String end ,int interval ,String id) throws Exception {
    	// 转换成时间戳
    	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Long beginTime = format.parse(begin).getTime();
    	Long endTime = format.parse(end).getTime();
    	
    	String url = this.yCmdbIdcMonitorUrl + "beginTime=" + beginTime + "&endTime=" + endTime + "&interval=" + interval + "&id=" + id;
		Map<String,Object> result = restUtil.getJson(url);
		Map resultMap = refactorIdcMonitor( (Map)((Map)result.get("body")).get("data") );
		return resultMap;
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
    
    private List<Map> refactorIdcexports(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("line_operator", (String)map.get("line_operator"));
        	tmp.put("export_device_ip", (String)map.get("export_device_ip"));
        	tmp.put("line_tech_available_speed", (String)map.get("line_tech_available_speed"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    private List<Map> refactorIdclines(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("spe_line_name", (String)map.get("spe_line_name"));
        	tmp.put("spe_line_id", (String)map.get("spe_line_id"));
        	tmp.put("line_operator", (String)map.get("line_operator"));
        	tmp.put("spe_line_speed", (String)map.get("spe_line_speed"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    private List<Map> refactorMonitor(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("humanizedValue", map.get("humanizedValue"));
        	tmp.put("value", map.get("value"));
        	tmp.put("time", map.get("time"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    private Map refactorIdcMonitor(Map data) throws Exception{
    	Map idcMonitorMap = new HashMap();
    	List<Map> seriesList = new ArrayList();
    	for(Map seriesMap: (List<Map>)data.get("series")) {
    		Map seriesReturnMap = new HashMap();
    		List dataList = new ArrayList();
    		for(Object value:(List)seriesMap.get("data")) {
    			dataList.add(value);
    		}
    		seriesReturnMap.put("data", dataList);
    		seriesReturnMap.put("name", seriesMap.get("name"));
    		seriesReturnMap.put("unit", seriesMap.get("unit"));
    		
    		seriesList.add(seriesReturnMap);
    	}
    	idcMonitorMap.put("series", seriesList);
    	
    	List<String> timeList = new ArrayList(); 
    	for(String time: (List<String>)data.get("time")){
    		timeList.add(time);
    	}
    	idcMonitorMap.put("x_time", timeList);
    	
        return idcMonitorMap;
    }
}
