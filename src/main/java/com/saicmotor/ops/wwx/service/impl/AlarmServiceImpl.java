package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.AlarmService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by Shen_JM on 2017/11/20.
 */
@Service
public class AlarmServiceImpl implements AlarmService {
    private static Logger log = LoggerFactory.getLogger(AlarmServiceImpl.class);

    @Value("${yunwei.alarm.url}")
    private String yAlarmUrl ;

    @Value("${yunwei.alarmList.url}")
    private String yAlarmListUrl ;
    
    @Value("${yunwei.alarmDetail.url}")
    private String yAlarmDetailUrl ;
    
    @Value("${yunwei.alarmOrigin.url}")
    private String yAlarmOriginUrl ;
    
    @Value("${yunwei.alarmIndicator.url}")
    private String yAlarmIndicatorUrl ;
    
    @Value("${yunwei.alarmStatus.url}")
    private String yAlarmStatusUrl ;
    
    @Value("${yunwei.alarmModule.url}")
    private String yAlarmModuleUrl ;
    
    @Value("${yunwei.alarmLevel.url}")
    private String yAlarmLevelUrl ;

    @Autowired
    private HttpHelper restUtil;
    

    public Map<String,Object> getAlarmList(String start, String length, String origin, String status, String module_id_one, String level) throws Exception {
    	
        String url = this.yAlarmUrl + "start=" + start + "&length=" + length + "&level=" + level + "&origin=" + origin +
        		"&status=" + status + "&module_id_one=" + module_id_one;
        Map<String,Object> result = restUtil.getJson(url);
        int total = (Integer)((Map)result.get("body")).get("total");
        List<Map> resultList = refactor( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("total", total);
        resultMap.put("resultList", resultList);
        return resultMap;
    }

    public List<Map> getAlarmListNew() throws Exception {
        String url = this.yAlarmListUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> data = (List)((Map)result.get("body")).get("data");
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("name", map.get("name"));
            tmp.put("value", (String)map.get("value"));
            resultList.add(tmp);
        }
        return resultList;
    }


    public Map<String,Object> getAlarmDetail(int id) throws Exception {
    	
        String url = this.yAlarmDetailUrl + "id=" + id;
        Map<String,Object> result = restUtil.getJson(url);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Map<String,Object> tmp = (Map)((Map)result.get("body")).get("data");
        resultMap.put("level_des", (String)tmp.get("level_des"));
        resultMap.put("indicator_des", (String)tmp.get("indicator_des"));
        resultMap.put("ops_principal", (String)tmp.get("ops_principal"));
        resultMap.put("status_des", (String)tmp.get("status_des"));
    	String module1 = (String)tmp.get("module1");
    	String module2 = (String)tmp.get("module2");
    	String module3 = (String)tmp.get("module3");
    	String module = module1 + "-" + module2 + "-" + module3;
    	resultMap.put("module", module);
    	resultMap.put("create_time", (String)tmp.get("create_time"));
    	resultMap.put("content", (String)tmp.get("content"));
    	resultMap.put("notify_type_des", (String)tmp.get("notify_type_des"));
    	
    	resultMap.put("origin", tmp.get("origin"));
    	resultMap.put("status", tmp.get("status"));
    	resultMap.put("indicator", tmp.get("indicator"));
    	resultMap.put("level", tmp.get("level"));
    	resultMap.put("notify_type", tmp.get("notify_type"));
    	resultMap.put("module_id", tmp.get("module_id"));
    	resultMap.put("id", tmp.get("id"));
    	
    	resultMap.put("recovery_time", (String)tmp.get("recovery_time"));
    	resultMap.put("ip", (String)tmp.get("ip"));
    	resultMap.put("origin_des", (String)tmp.get("origin_des"));
    	resultMap.put("modify_time", (String)tmp.get("modify_time"));
    	
        return resultMap;
    }
    
    public List<Map> getAlarmLevel() throws Exception {
        String url = this.yAlarmLevelUrl;
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
    
    public List<Map> getAlarmOrigin() throws Exception {
        String url = this.yAlarmOriginUrl;
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
    
    public List<Map> getAlarmIndicator() throws Exception {
        String url = this.yAlarmIndicatorUrl;
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
    
    public List<Map> getAlarmStatus() throws Exception {
        String url = this.yAlarmStatusUrl;
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
    
    public List<Map> getAlarmModule() throws Exception {
        String url = this.yAlarmModuleUrl;
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
    
    
    private List<Map> refactor(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("level", map.get("level"));
        	tmp.put("level_des", (String)map.get("level_des"));
        	tmp.put("indicator_des", (String)map.get("indicator_des"));
        	tmp.put("ip", (String)map.get("ip"));
        	tmp.put("ops_principal", (String)map.get("ops_principal"));
        	tmp.put("status_des", (String)map.get("status_des"));
        	String module1 = (String)map.get("module1");
        	String module2 = (String)map.get("module2");
        	String module3 = (String)map.get("module3");
        	String module = module1 + "-" + module2 + "-" + module3;
        	tmp.put("module", module);
        	
        	tmp.put("create_time", (String)map.get("create_time"));
        	resultList.add(tmp);
        }
        return resultList;
    }

    
    
}
