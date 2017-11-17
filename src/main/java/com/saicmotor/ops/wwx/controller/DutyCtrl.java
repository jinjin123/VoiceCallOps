package com.saicmotor.ops.wwx.controller;

import com.saicmotor.ops.wwx.service.DutyPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
@RequestMapping("/duty")
public class DutyCtrl {
    private static Logger log = LoggerFactory.getLogger(DutyCtrl.class);

    @Autowired
    private DutyPlanService dutyPlanService;

    @RequestMapping("/range")
    public ResponseEntity<Map> rangeQuery(String st, String ed){
        try{
            Calendar calendar = Calendar.getInstance(Locale.CHINA);

            Date s1 = calendar.getTime();
            calendar.add(Calendar.DATE, 30);
            Date s2 = calendar.getTime();

            List<Map> plans = dutyPlanService.getRangePlan(s1, s2);

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("success", true);
            result.put("data", plans);

            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }catch(Throwable t){
            log.error(t.getMessage(), t);
            return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
