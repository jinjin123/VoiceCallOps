package com.saicmotor.ops.wwx.controller;

import com.saicmotor.ops.wwx.service.CmdbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
@RequestMapping("/cmdb")
public class CmdbCtrl {
    private static Logger log = LoggerFactory.getLogger(CmdbCtrl.class);

    @Autowired
    private CmdbService cmdbService;

    @RequestMapping("/cmdbServer")
    public ResponseEntity<Map> cmdbServerList(String page, String length, String principal, String idc, String module_id, String logic_area, String state,String inner_ip){
        try{
        	Map<String,Object> cmdbServers = cmdbService.getCmdbServerList(page, length, principal, idc, module_id, logic_area, state, inner_ip);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbServers);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbModule")
    public ResponseEntity<Map> cmdbModule(String module_id){
        try{
            List<Map> cmdbModule = cmdbService.getCmdbModule(module_id);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbModule);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbUser")
    public ResponseEntity<Map> cmdbUser(){
        try{
            List<Map> cmdbUser = cmdbService.getCmdbUser();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbUser);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbIdc")
    public ResponseEntity<Map> cmdbIdc(){
        try{
            List<Map> cmdbIdc = cmdbService.getCmdbIdc();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbIdc);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbLogic")
    public ResponseEntity<Map> cmdbLogic(){
        try{
            List<Map> cmdbLogic = cmdbService.getCmdbLogic();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbLogic);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbState")
    public ResponseEntity<Map> cmdbState(){
        try{
            List<Map> cmdbState = cmdbService.getCmdbState();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbState);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/netdevicesFunc")
    public ResponseEntity<Map> netdevicesFunc(){
        try{
            List<Map> netdevicesFunc = cmdbService.getNetdevicesFunc();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", netdevicesFunc);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/netdevicesIdc")
    public ResponseEntity<Map> netdevicesIdc(){
        try{
            List<Map> netdevicesIdc = cmdbService.getNetdevicesIdc();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", netdevicesIdc);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/netdevicesModel")
    public ResponseEntity<Map> netdevicesModel(){
        try{
            List<Map> netdevicesModel = cmdbService.getNetdevicesModel();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", netdevicesModel);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/netdevicesPro")
    public ResponseEntity<Map> netdevicesPro(){
        try{
            List<Map> netdevicesPro = cmdbService.getNetdevicesPro();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", netdevicesPro);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/netdevicesType")
    public ResponseEntity<Map> netdevicesType(){
        try{
            List<Map> netdevicesType = cmdbService.getNetdevicesType();

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", netdevicesType);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbNetdevices")
    public ResponseEntity<Map> cmdbNetdevicesList(String page, String length, String netdev_idc, String netdev_type, String netdev_func, String netdev_pro, String netdev_model, String netdev_admin_ip){
        try{
        	Map<String,Object> cmdbNetdevices = cmdbService.getCmdbNetdevicesList(page, length, netdev_idc, netdev_type, netdev_func, netdev_pro, netdev_model,netdev_model);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbNetdevices);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbIdcexports")
    public ResponseEntity<Map> cmdbIdcexportsList(String page, String length){
        try{
        	Map<String,Object> cmdbIdcexports = cmdbService.getCmdbIdcexportsList(page, length);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbIdcexports);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbIdclines")
    public ResponseEntity<Map> cmdbIdclinesList(String page, String length){
        try{
        	Map<String,Object> cmdbIdclines = cmdbService.getCmdbIdclinesList(page, length);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbIdclines);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbServerMonitor")
    public ResponseEntity<Map> cmdbServerMonitor(String begin,String end ,int minuteInterval ,String ip){
        try{
        	List<Map> cmdbServerMonitor = cmdbService.getCmdbServerMonitor(begin, end,minuteInterval,ip);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbServerMonitor);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbNetdevicesMonitor")
    public ResponseEntity<Map> cmdbNetdevicesMonitor(String begin,String end ,int minuteInterval ,String ip){
        try{
        	List<Map> cmdbNetdevicesMonitor = cmdbService.getCmdbNetdevicesMonitor(begin, end,minuteInterval,ip);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbNetdevicesMonitor);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping("/cmdbIdcMonitor")
    public ResponseEntity<Map> cmdbIdcMonitor(String begin,String end ,int interval ,String id){
        try{
        	Map cmdbIdcMonitor = cmdbService.getCmdbIdcMonitor(begin, end,interval,id);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", cmdbIdcMonitor);
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
