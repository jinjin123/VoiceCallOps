package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.CommandService;
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
public class CommandServiceImpl implements CommandService {
    private static Logger log = LoggerFactory.getLogger(CommandServiceImpl.class);

    @Autowired
    private HttpHelper restUtil;
    @Value("${yunwei.command.url}")
    private String ycommandUrl;
    

    public Map<java.lang.String, Object> execcommand(String ip,String user,String pwd, String cmd) throws Exception {
//  public Map<java.lang.String, Object> execcommand(String url) throws Exception {
        try{
        		String url =  String.format(ycommandUrl,ip,user,pwd,cmd);
        		url = url.replaceAll(" ","%20");
            Map<String,Object> resulttmp = restUtil.getJson(url);
            Map<String,Object> result =  new HashMap<String,Object>();
            result.put("cmd_status", ((Map)resulttmp.get("body")).get("content"));
            return result;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    
}
