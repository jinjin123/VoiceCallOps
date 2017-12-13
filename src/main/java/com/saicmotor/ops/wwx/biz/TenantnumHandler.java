package com.saicmotor.ops.wwx.biz;

import com.saicmotor.ops.wwx.service.TenantnumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class TenantnumHandler extends BizHandler {
    private static Logger log = LoggerFactory.getLogger(TenantnumHandler.class);

    @Autowired
    private TenantnumService tenantnumService;

    public Map<String,Object> getTenantnum(String... params) throws Exception{
        String length = params[0];

        Map<String,Object> alarmsMap = tenantnumService.gettenantnum(length);
        String total = alarmsMap.get("result").toString();

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msgType", "text");
        result.put("msgFtl", "tenant/msg.ftl");
        result.put("total",total);
        return result;
    }
}
