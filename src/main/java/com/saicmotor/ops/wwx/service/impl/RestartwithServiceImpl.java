package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.RestartwithService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import freemarker.template.Template;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import java.io.StringWriter;

import java.util.*;

@Service
public class RestartwithServiceImpl implements RestartwithService {
    private static Logger log = LoggerFactory.getLogger(RestartwithServiceImpl.class);
    @Value("${yunwei.getserverservice.url}")
    private String ygetserverserviceUrl ;
    @Autowired
    private HttpHelper restUtil;
    @Autowired
    private FreeMarkerConfigurer freemarker;
    
    public Map<java.lang.String, Object> restartconfirm(String ip) throws Exception {
        try{
            Map<String,Object> resulttmp = restUtil.getJson(String.format(ygetserverserviceUrl,ip));
            //return list
            List<Map> resultList = refactor( (List)((Map)resulttmp.get("body")).get("data") );
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("resultList", resultList);

            List<Map> alarms = (List) resultMap.get("resultList");
            log.info("alarms{}", alarms);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("msgType", "text");
            result.put("msgFtl", "confirm/msg.ftl");
            result.put("data",alarms);
            result.put("ip",ip);
            log.info("result{}", result);
            Template tpl = freemarker.getConfiguration().getTemplate((String) result.get("msgFtl"));
            StringWriter out = new StringWriter();

            tpl.process(result, out);
            result.put("msgResult", out.toString());
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
            tmp.put("level", map.get("level"));
            resultList.add(tmp);
        }
        return resultList;
    }
}
