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


import java.util.*;

@Service
public class RebootServiceImpl implements RebootService {
    private static Logger log = LoggerFactory.getLogger(RebootServiceImpl.class);

    @Autowired
    private HttpHelper restUtil;

    public Map<java.lang.String, Object> restartServer(String url) throws Exception {
        try{
            Map<String,Object> resulttmp = restUtil.getJsonCustom(url);
            return resulttmp;
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
