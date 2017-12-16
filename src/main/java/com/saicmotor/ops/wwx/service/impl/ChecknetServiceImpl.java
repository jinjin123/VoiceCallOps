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


import java.util.*;

@Service
public class ChecknetServiceImpl implements ChecknetService {
    private static Logger log = LoggerFactory.getLogger(ChecknetServiceImpl.class);

    @Autowired
    private HttpHelper restUtil;

    public Map<java.lang.String, Object> checkserver(String url) throws Exception {
        try{
            Map<String,Object> resulttmp = restUtil.getJsonCustom(url);
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("status", (Map)resulttmp.get("body"));
            return resultMap;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }

}
