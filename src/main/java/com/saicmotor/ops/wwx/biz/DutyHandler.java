package com.saicmotor.ops.wwx.biz;

import com.saicmotor.ops.wwx.service.DutyPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class DutyHandler extends BizHandler {
    private static Logger log = LoggerFactory.getLogger(DutyHandler.class);

    @Autowired
    private DutyPlanService dutyPlanService;


    public Map<String,Object> getDutyPlan(String... params) throws Exception{
        int days ;
        try{
            days = Integer.parseInt(params[0]);
            days = (days>=1 && days<=7)?days:3;
        }catch(Throwable t){
            days = 3;
        }

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Date s1 = calendar.getTime();
        calendar.add(Calendar.DATE, days);
        Date s2 = calendar.getTime();
        List<Map> plans = dutyPlanService.getRangePlan(s1,s2);

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msgType", "text");
        result.put("msgFtl", "duty/msg.ftl");
        result.put("data",plans);
        return result;
    }

}
