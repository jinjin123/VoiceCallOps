package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.CmdbService;
import com.saicmotor.ops.wwx.service.DashboardService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by Shen_JM on 2017/12/06.
 */
@Service
public class DashboardServiceImpl implements DashboardService {
    private static Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Value("${yunwei.dashboardCount.url}")
    private String yDashboardCountUrl ;
    @Value("${yunwei.dashboardLineChart.url}")
    private String yDashboardLineChartUrl ;
    @Value("${yunwei.dashboardServerPie.url}")
    private String yDashboardServerPieUrl ;
    @Value("${yunwei.dashboardNetdevicesPie.url}")
    private String yDashboardNetdevicesPieUrl ;
    @Value("${yunwei.dashboardTenantTopPie.url}")
    private String yDashboardTenantTopPieUrl ;
    @Value("${yunwei.dashboardKeyTenantInfo.url}")
    private String yDashboardKeyTenantInfoUrl ;

    @Autowired
    private HttpHelper restUtil;
    

    public Map<String,Object> getCount() throws Exception {
    	
        String url = this.yDashboardCountUrl;
        Map<String,Object> result = restUtil.getJson(url);
        Map map = (Map)((Map)result.get("body")).get("data");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("tenant_count", map.get("tenant_count"));
        resultMap.put("network_count", map.get("network_count"));
        resultMap.put("rack_count", map.get("rack_count"));
        resultMap.put("service_vm_total", map.get("service_vm_total"));
        resultMap.put("idc_export_line_count", map.get("idc_export_line_count"));
        resultMap.put("service_total", map.get("service_total"));
        return resultMap;
    }

    public Map getLineChart() throws Exception {
    	
        String url = this.yDashboardLineChartUrl;
        Map<String,Object> result = restUtil.getJson(url);
        Map lineChartMap = refactorLineChart( (Map)((Map)result.get("body")).get("data") );
//        Map<String,Object> resultMap = new HashMap<String,Object>();
//        resultMap.put("lineChartMap", lineChartMap);
        return lineChartMap;
    }
    
    public Map<String,Object> getServerPie() throws Exception {
    	
        String url = this.yDashboardServerPieUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    
    public Map<String,Object> getNetdevicesPie() throws Exception {
    	
        String url = this.yDashboardNetdevicesPieUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    
    public Map<String,Object> getTenantTopPie() throws Exception {
    	
        String url = this.yDashboardTenantTopPieUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorTopPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    
    public Map<String,Object> getKeyTenantInfo() throws Exception {
    	
        String url = this.yDashboardKeyTenantInfoUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorKeyTenantInfo( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    
    private List<Map> refactor(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("inner_ip", (String)map.get("inner_ip"));
        	tmp.put("asset_no", (String)map.get("asset_no"));
        	
        	List<Map> allmodules = (List)map.get("allmodules");
        	tmp.put("ops_principal", (String)allmodules.get(0).get("ops_principal"));
        	String module3 = (String)allmodules.get(0).get("name");
        	String module2 = (String)allmodules.get(1).get("name");
        	String module1 = (String)allmodules.get(2).get("name");
        	String module = module1 + "-" + module2 + "-" + module3;
        	tmp.put("module", module);
        	resultList.add(tmp);
        }
        return resultList;
    }

    private List<Map> refactorNetdevices(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("id", map.get("id"));
        	tmp.put("netdev_admin_ip", (String)map.get("netdev_admin_ip"));
        	tmp.put("netdev_type", (String)map.get("netdev_type"));
        	tmp.put("netdev_asset_id", (String)map.get("netdev_asset_id"));
        	tmp.put("netdev_maintainer", (String)map.get("netdev_maintainer"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    private Map refactorLineChart(Map data) throws Exception{
    	Map lineChartMap = new HashMap();
    	List<Map> seriesList = new ArrayList();
    	for(Map seriesMap: (List<Map>)data.get("series")) {
    		Map seriesReturnMap = new HashMap();
    		List dataList = new ArrayList();
    		for(Object value:(List)seriesMap.get("data")) {
    			dataList.add(value);
    		}
    		seriesReturnMap.put("data", dataList);
    		seriesReturnMap.put("name", seriesMap.get("name"));
    		seriesList.add(seriesReturnMap);
    	}
    	lineChartMap.put("series", seriesList);
    	
    	List<String> xTimeList = new ArrayList(); 
    	for(String xTime: (List<String>)data.get("x_time")){
    		xTimeList.add(xTime);
    	}
    	lineChartMap.put("x_time", xTimeList);
    	
    	List<String> legendList = new ArrayList(); 
    	for(String legend: (List<String>)data.get("legend")){
    		legendList.add(legend);
    	}
    	lineChartMap.put("legend", legendList);
    	
        return lineChartMap;
    }
    
    private List<Map> refactorCountPie(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("location", (String)map.get("location"));
        	tmp.put("count", map.get("count"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    private List<Map> refactorTopPie(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map<String,Object> tmp = new HashMap<String,Object>();
        	tmp.put("tenant", (String)map.get("tenant"));
        	tmp.put("count", map.get("count"));
        	resultList.add(tmp);
        }
        return resultList;
    }
    
    private List<Map> refactorKeyTenantInfo(List<Map> data) throws Exception{
    	List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
        	Map dataMap = new HashMap();
        	dataMap.put("tenant_name", (String)map.get("tenant_name"));
        	
        	List<Map> resourcesList = new ArrayList<Map>();
        	for(Map resourcesMap:(List<Map>) map.get("resources")) {
        		Map<String,Object> tmp = new HashMap<String,Object>();
        		tmp.put("id", (String)resourcesMap.get("id"));
        		tmp.put("value", resourcesMap.get("value"));
        		tmp.put("name", (String)resourcesMap.get("name"));
        		resourcesList.add(tmp);
        	}
        	dataMap.put("resources", resourcesList);
        	
        	resultList.add(dataMap);
        }
        return resultList;
    }
}
