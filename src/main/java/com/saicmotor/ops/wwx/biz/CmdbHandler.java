package com.saicmotor.ops.wwx.biz;

import com.saicmotor.ops.wwx.service.CmdbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CmdbHandler extends BizHandler {
    private static Logger log = LoggerFactory.getLogger(CmdbHandler.class);

    @Autowired
    private CmdbService cmdbService;


    public Map<String,Object> getCmdbServerList(String... params) throws Exception{
    	String page = params[0];
    	String length = params[1];
    	String principal = params[2];
    	String idc = params[3];
    	String module_id = params[4];
    	String logic_area = params[5];
    	String state = params[6];

    	Map<String,Object> cmdbServersMap = cmdbService.getCmdbServerList(page, length, principal, idc, module_id, logic_area, state);
        List<Map> cmdbServers = (List) cmdbServersMap.get("resultList");

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msgType", "text");
        result.put("msgFtl", "cmdb/cmdbServers.ftl");
        result.put("data",cmdbServers);
        return result;
    }
    
    public Map<String,Object> getCmdbNetdevicesList(String... params) throws Exception{
    	String page = params[0];
    	String length = params[6];
    	String netdev_idc = params[2];
    	String netdev_type = params[3];
    	String netdev_func = params[4];
    	String netdev_pro = params[5];
    	String netdev_model = params[1];

    	Map<String,Object> cmdbNetdevicesMap = cmdbService.getCmdbNetdevicesList(page, length, netdev_idc, netdev_type, netdev_func, netdev_pro, netdev_model);
        List<Map> cmdbNetdevices = (List) cmdbNetdevicesMap.get("resultList");

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msgType", "text");
        result.put("msgFtl", "cmdb/cmdbNetdevices.ftl");
        result.put("data",cmdbNetdevices);
        return result;
    }

}
