package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.ChecknetService;
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
public class ChecknetServiceImpl implements ChecknetService {
    private static Logger log = LoggerFactory.getLogger(ChecknetServiceImpl.class);
    @Value("${yunwei.checknet.url}")
    private String ychecknetUrl;
    @Value("${yunwei.checknetitem.url}")
    private String checknetitemUrl;
    @Autowired
    private HttpHelper restUtil;

    public Map<java.lang.String, Object> checkserver(String ip) throws Exception {
        try{
        		String url = String.format(ychecknetUrl,"ping -c 3 " + ip);
        		url = url.replaceAll(" ","%20");
            Map<String,Object> resulttmp = restUtil.getJsonCustom(url);
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("cmd_status",((Map)resulttmp.get("body")).get("content"));
            return resultMap;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Map<java.lang.String, Object> NetItemCheck(String ip,String item) throws Exception {
        try{
        		
        		String url = String.format(checknetitemUrl,ip,item);
//        		url = url.replaceAll(" ","%20");
            Map<String,Object> resulttmp = restUtil.getJsonCustom(url);
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("cmd_status",((Map)resulttmp.get("body")).get("content"));
            return resultMap;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
