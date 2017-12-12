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
        	Map<String,Object> alarms = alarmService.getAlarmList(start, length, origin, status, module_id_one, level);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarms);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/alarmList")
    public ResponseEntity<Map> alarmList(){
        try{
            Map<String,Object> alarms = alarmService.getAlarmListNew();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarms);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/alarmDetail")
    public ResponseEntity<Map> alarmDetail(int id){
        try{
            Map<String,Object> alarmDetail = alarmService.getAlarmDetail(id);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarmDetail);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    choose different alarm level interface
    @RequestMapping("/alarmLevel")
    public ResponseEntity<Map> alarmLevel(){
        try{
            List<Map> alarmLevel = alarmService.getAlarmLevel();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarmLevel);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/alarmOrigin")
    public ResponseEntity<Map> alarmOrigin(){
        try{
            List<Map> alarmOrigin = alarmService.getAlarmOrigin();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarmOrigin);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/alarmIndicator")
    public ResponseEntity<Map> alarmIndicator(){
        try{
            List<Map> alarmIndicator = alarmService.getAlarmIndicator();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarmIndicator);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/alarmStatus")
    public ResponseEntity<Map> alarmStatus(){
        try{
            List<Map> alarmStatus = alarmService.getAlarmStatus();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarmStatus);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/alarmModule")
    public ResponseEntity<Map> alarmModule(){
        try{
            List<Map> alarmModule = alarmService.getAlarmModule();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", alarmModule);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
