package com.saicmotor.ops.wwx.controller;

import com.saicmotor.ops.wwx.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
@RequestMapping("/alarm")
public class AlarmCtrl {
    private static Logger log = LoggerFactory.getLogger(AlarmCtrl.class);

    @Autowired
    private AlarmService alarmService;

    @RequestMapping("/alarmQuery")
    public ResponseEntity<Map> alarmQuery(String start, String length, String origin, String status, String module_id_one, String level){
        try{
            List<Map> alarms = alarmService.getAlarmList(start, length, origin, status, module_id_one, level);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarms);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
