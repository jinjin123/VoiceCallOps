package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.RebootService;
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
public class RebootServiceImpl implements RebootService {
    private static Logger log = LoggerFactory.getLogger(RebootServiceImpl.class);
    @Value("${yunwei.execserver.url}")
    private String yexecserverUrl ;
    @Autowired
    private HttpHelper restUtil;

    public Map<java.lang.String, Object> restartServer(String ip,String user,String pwd) throws Exception {
        try{
        	
            Map<String,Object> resulttmp = restUtil.getJson(String.format(yexecserverUrl,ip,user,pwd));
            
            return resulttmp;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }

}
