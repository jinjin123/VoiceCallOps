package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.DutyPlanService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Shen_JM on 2017/10/30.
 */
@Service
public class DutyPlanServiceImpl implements DutyPlanService {
    private static Logger log = LoggerFactory.getLogger(DutyPlanServiceImpl.class);

    @Value("${yunwei.schedue.url}")
    private String ySchedueUrl ;

    @Autowired
    private HttpHelper restUtil;
    

    public List<Map> getRangePlan(Date st, Date ed) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map> plans = getOriginData(st);
        long s1 = sdf.parse(sdf.format(st)).getTime();
        long s2 = sdf.parse(sdf.format(ed)).getTime();

        long tmp = 0;
        for(int i=plans.size()-1;i>=0;i--){
            tmp = ((Date)plans.get(i).get("date")).getTime();
            if( tmp<s1 || tmp>=s2 ){
                plans.remove(i);
            }
        }
        return plans;
    }

    public Map<String, Object> getTodayPlan() throws Exception {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date s1 = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        Date s2 = calendar.getTime();
        return getRangePlan(s1, s2).get(0);
    }

    public List<Map> getWeekPlan(Date st) throws Exception {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(st);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date s1 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date s2 = calendar.getTime();
        return getRangePlan(s1, s2);
    }

    public List<Map> getMonthPlan(Date st) throws Exception {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(st);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date s1 = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Date s2 = calendar.getTime();
        return getRangePlan(s1, s2);
    }

    private List<Map> getOriginData(Date st) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String url = this.ySchedueUrl + URLEncoder.encode(sdf.format(st),"UTF-8");
        Map<String,Object> result = restUtil.getJson(url);
        return refactor( (List)((Map)result.get("body")).get("data") );
    }

    private List<Map> refactor(List<Map> data) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TreeMap<String,Map> treeMap = new TreeMap<String, Map>();
        Map<String,Object> tmp = null;
        String title =null;
        for(Map m : data){
            tmp = treeMap.get((String)m.get("start"));
            if( tmp==null ){
                tmp = new HashMap<String,Object>();
                tmp.put("date", sdf.parse((String)m.get("start")));
                treeMap.put((String)m.get("start"), tmp);
            }
            title = (String)m.get("title");
            if( title.startsWith("早") ){
                tmp.put("day", title.substring(title.indexOf(":")+1).trim());
            }else{
                tmp.put("night", title.substring(title.indexOf(":")+1).trim());
            }
        }
        return new ArrayList<Map>(treeMap.values());
    }

}
