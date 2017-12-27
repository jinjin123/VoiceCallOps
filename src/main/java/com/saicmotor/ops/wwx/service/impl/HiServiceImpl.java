package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.HiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class HiServiceImpl implements HiService {
    private static Logger log = LoggerFactory.getLogger(HiServiceImpl.class);

    @Value("${yunwei.alarmList.url}")
    private String yAlarmListUrl ;

    @Value("${yunwei.wechatname.url}")
    private String ywechatnameUrl ;
    @Autowired
    private HttpHelper restUtil;

    public Map<java.lang.String, Object> getHianswer(String url, String user) throws Exception {

        try{
            Map usertmp = restUtil.getJson(String.format(ywechatnameUrl,user));
            Object wechatname = ((Map)usertmp.get("body")).get("name");
            Map<String,Object> resulttmp = restUtil.getJson(url);
            //return list
            List<Map> resultList = refactor( (List)((Map)resulttmp.get("body")).get("data") );
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("resultList", resultList);

            List<Map> alarms = (List) resultMap.get("resultList");

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("msgType", "text");
            result.put("msgFtl", "hiinfo/msg.ftl");
            result.put("data",alarms);
            result.put("whoask", wechatname);
            return result;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    //  ioop list put into map
    private List<Map> refactor(List<Map> data) throws Exception{
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("name", (String)map.get("name"));
            tmp.put("value", (String)map.get("value"));
            resultList.add(tmp);
        }
        return resultList;
    }
}
