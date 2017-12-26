package com.saicmotor.ops.wwx.controller;

import com.saicmotor.ops.wwx.service.DashboardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
@RequestMapping("/dashboard")
public class DashboardCtrl {
    private static Logger log = LoggerFactory.getLogger(DashboardCtrl.class);

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping("/count")
    public ResponseEntity<Map> getCount(){
        try{
        	Map<String,Object> count = dashboardService.getCount();
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", count);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/lineChart")
    public ResponseEntity<Map> getLineChart(){
        try{
            Map lineChartMap = dashboardService.getLineChart();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", lineChartMap);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/serverPie")
    public ResponseEntity<Map> getServerPie(){
        try{
            Map serverPieMap = dashboardService.getServerPie();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", serverPieMap);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/netdevicesPie")
    public ResponseEntity<Map> getNetdevicesPie(){
        try{
            Map netdevicesPieMap = dashboardService.getNetdevicesPie();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", netdevicesPieMap);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/tenantTopPie")
    public ResponseEntity<Map> getTenantTopPie(){
        try{
            Map tenantTopPieMap = dashboardService.getTenantTopPie();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", tenantTopPieMap);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/keyTenantInfo")
    public ResponseEntity<Map> getKeyTenantInfo(){
        try{
            Map keyTenantInfoMap = dashboardService.getKeyTenantInfo();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", keyTenantInfoMap);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/getIdcLineInfo")
    public ResponseEntity<Map> getIdcLineInfo(){
        try{
            Map getIdcLineInfoMap = dashboardService.getIdcLineInfo();
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", getIdcLineInfoMap);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
