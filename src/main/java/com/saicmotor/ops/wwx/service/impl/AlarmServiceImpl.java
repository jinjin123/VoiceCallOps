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
    @Value("${yunwei.alarmDetail.url}")
    private String yAlarmDetailUrl ;

    @Autowired
    private HttpHelper restUtil;
    

    public List<Map> getAlarmList(String start, String length, String origin, String status, String module_id_one, String level) throws Exception {
    	
        String url = this.yAlarmUrl + "start=" + start + "&length=" + length + "&level=" + level + "&origin=" + origin + "&status=" + status + "&module_id_one=" + module_id_one;
        Map<String,Object> result = restUtil.getJson(url);
        return refactor( (List)((Map)result.get("body")).get("data") );
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
        return resultMap;
    }
    
    
    private List<Map> refactor(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        Map<String,Object> tmp = new HashMap<String,Object>();
        if (data == null || data.size()==0) {
        	return new ArrayList<Map>();
        }
        for (Map map : data) {
        	tmp.put("id", map.get("id"));
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
