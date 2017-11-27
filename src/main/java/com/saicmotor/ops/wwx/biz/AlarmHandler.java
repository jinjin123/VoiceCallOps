package com.saicmotor.ops.wwx.biz;

import com.saicmotor.ops.wwx.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AlarmHandler extends BizHandler {
    private static Logger log = LoggerFactory.getLogger(AlarmHandler.class);

    @Autowired
    private AlarmService alarmService;


    public Map<String,Object> getAlarmList(String... params) throws Exception{
    	String start = params[0];
    	String length = params[1];
    	String origin = params[2];
    	String status = params[3];
    	String module_id_one = params[4];
    	String level = params[5];

    	Map<String,Object> alarmsMap = alarmService.getAlarmList(start, length, origin, status, module_id_one, level);
        List<Map> alarms = (List) alarmsMap.get("resultList");

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msgType", "text");
        result.put("msgFtl", "alarm/msg.ftl");
        result.put("data",alarms);
        return result;
    }

}
