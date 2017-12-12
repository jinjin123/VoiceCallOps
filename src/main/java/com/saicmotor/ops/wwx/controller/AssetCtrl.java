package com.saicmotor.ops.wwx.controller;

import com.saicmotor.ops.wwx.service.AssetManageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
@RequestMapping("/asset")
public class AssetCtrl {
    private static Logger log = LoggerFactory.getLogger(AssetCtrl.class);

    @Autowired
    private AssetManageService assetManageService;

    @RequestMapping("/tenant")
    public ResponseEntity<Map> alarmQuery(String tenantKeyword , int resKeyword){
        try{
    		byte[] tenantKeywordByte =  Base64.getDecoder().decode(tenantKeyword);  
    		String tenantKeywordStr = new String(tenantKeywordByte);
        	Map<String,Object> alarms = assetManageService.getAssetManageTenant(tenantKeywordStr, resKeyword);

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
