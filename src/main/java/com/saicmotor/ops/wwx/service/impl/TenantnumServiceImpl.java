package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.TenantnumService;
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
public class TenantnumServiceImpl implements TenantnumService {
    private static Logger log = LoggerFactory.getLogger(TenantnumServiceImpl.class);

    @Value("${yunwei.tenantnum.url}")
    private String ytenantnumUrl;

    @Autowired
    private HttpHelper restUtil;

    public Map<java.lang.String, Object> gettenantnum(String length) throws Exception {
        String url = this.ytenantnumUrl;
        try{
            Map<String,Object> resulttmp = restUtil.getJson(url);
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("result",((Map)resulttmp.get("body")).get("total"));
            return result;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }

}
