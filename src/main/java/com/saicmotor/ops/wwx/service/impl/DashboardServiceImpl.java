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
    @Value("${yunwei.dashboardgetIdcLineInfo.url}")
    private String yDashboardgetIdcLineInfoUrl ;
    @Value("${yunwei.dashboardgetDtLineInfo.url}")
    private String yDashboardgetDtLineInfoUrl ;
    @Value("${yunwei.dashboardgetDtcputen.url}")
    private String yDashboardgetDtcputenUrl ;
    @Value("${yunwei.dashboardgetDtcpuelv.url}")
    private String yDashboardgetDtcpuelvUrl ;
    @Value("${yunwei.dashboardgetDtmemten.url}")
    private String yDashboardgetDtmemtenUrl ;
    @Value("${yunwei.dashboardgetDtmemelv.url}")
    private String yDashboardgetDtmemelvUrl ;
    @Value("${yunwei.dashboardgetDtngxses.url}")
    private String yDashboardgetDtngxsesUrl ;
    @Value("${yunwei.dashboardgetDtngxact.url}")
    private String yDashboardgetDtngxactUrl ;
    @Value("${yunwei.dashboardgetBmlvscn.url}")
    private String yDashboardgetBmlvscnUrl;
	@Value("${yunwei.dashboardgetBmtgone.url}")
	private String yDashboardgetBmtgoneUrl ;
	@Value("${yunwei.dashboardgetBmtgtwo.url}")
	private String yDashboardgetBmtgtwoUrl ;
	@Value("${yunwei.dashboardgetBmtgthree.url}")
	private String yDashboardgetBmtgthreeUrl ;
	@Value("${yunwei.dashboardgetBmtgfour.url}")
	private String yDashboardgetBmtgfourUrl ;
	@Value("${yunwei.dashboardgetBmngxline.url}")
	private String yDashboardgetBmngxLineUrl ;
	@Value("${yunwei.dashboardgetBmngxpie.url}")
	private String yDashboardgetBmngxPieUrl ;
    
	@Value("${yunwei.dashboardgetpyvm.url}")
	private String yDashboardgetpyvmUrl ;
	@Value("${yunwei.dashboardgetpyvmdata.url}")
	private String yDashboardgetpyvmdataUrl ;
    @Autowired
    private HttpHelper restUtil;
    

    public Map<String,Object> getCount() throws Exception {
    	
        String url = this.yDashboardCountUrl;
        Map<String,Object> result = restUtil.getJson(url);
        Map map = (Map)((Map)result.get("body")).get("data");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("tenant_count", map.get("tenant_count"));
        resultMap.put("network_count", map.get("network_count"));
        resultMap.put("container_data", map.get("container_data"));
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
    
    public Map<String,Object> getIdcLineInfo() throws Exception {
        String url = this.yDashboardgetIdcLineInfoUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    
    public Map<String,Object> getDtLineInfo() throws Exception {
        String url = this.yDashboardgetDtLineInfoUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    
    public Map<String,Object> getDtcputenPie() throws Exception {
        String url = this.yDashboardgetDtcputenUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    
    public Map<String,Object> getDtcpuelvPie() throws Exception {
        String url = this.yDashboardgetDtcpuelvUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    
    public Map<String,Object> getDtmemtenPie() throws Exception {
        String url = this.yDashboardgetDtmemtenUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    public Map<String,Object> getDtmemelvPie() throws Exception {
        String url = this.yDashboardgetDtmemelvUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    public Map<String,Object> getDtngxsesPie() throws Exception {
        String url = this.yDashboardgetDtngxsesUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }
    public Map<String,Object> getDtngxact() throws Exception {
        String url = this.yDashboardgetDtngxactUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    public Map<String,Object> getBmlvscon() throws Exception {
        String url = this.yDashboardgetBmlvscnUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    public Map<String,Object> getBmtgone() throws Exception {
        String url = this.yDashboardgetBmtgoneUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    public Map<String,Object> getBmtgtwo() throws Exception {
        String url = this.yDashboardgetBmtgtwoUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    public Map<String,Object> getBmtgthree() throws Exception {
        String url = this.yDashboardgetBmtgthreeUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    public Map<String,Object> getBmtgfour() throws Exception {
        String url = this.yDashboardgetBmtgfourUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    public Map<String,Object> getBmngxLine() throws Exception {
        String url = this.yDashboardgetBmngxLineUrl;
        Map<String,Object> result = restUtil.getJson(url);
//        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map lineChartMap = refactorIdcLineChart( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", lineChartMap);
//        return resultMap;
        return lineChartMap;
    }
    public Map<String,Object> getBmngxPie() throws Exception {
        String url = this.yDashboardgetBmngxPieUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorCountPie( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList", resultList);
        return resultMap;
    }

    
    public Map<String,Object> getpyvm() throws Exception {
        String url = this.yDashboardgetpyvmUrl;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorpyvm( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList",resultList);
        return resultMap;
    }
    
    public Map<String,Object> getpyvmdata(String hostname) throws Exception {
        String url = this.yDashboardgetpyvmdataUrl + hostname;
        Map<String,Object> result = restUtil.getJson(url);
        List<Map> resultList = refactorpyvmdata( (List)((Map)result.get("body")).get("data") );
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultList",resultList);
        return resultMap;
    }
    private List<Map> refactorpyvm(List<Map> data) throws Exception{
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("hostname", map.get("hostname"));
            tmp.put("ip", map.get("ip"));
            tmp.put("etip", map.get("etip"));
            tmp.put("wanip", map.get("wanip"));
            tmp.put("rack", map.get("rack"));
            tmp.put("pos", map.get("pos"));
            tmp.put("vm_num", map.get("vm_num"));
            resultList.add(tmp);
        }
        return resultList;
    }
    private List<Map> refactorpyvmdata(List<Map> data) throws Exception{
        List<Map> resultList = new ArrayList<Map>();
        for (Map map : data) {
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("hostname", map.get("hostname"));
            tmp.put("ip", map.get("ip"));
            tmp.put("vm_hostname", map.get("vm_hostname"));
            tmp.put("vmip", map.get("vmip"));
            tmp.put("vmhd", map.get("vmhd"));
            tmp.put("vm_num", map.get("vm_num"));
            resultList.add(tmp);
        }
        return resultList;
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
    
    private Map refactorIdcLineChart(List<Map> data) throws Exception{
    	Map lineChartMap = new HashMap();
    	List<Map> BigDataList = new ArrayList();
	for(Map seriesMap: (List<Map>)data) {
		Map dataReturnMap = new HashMap();
		List<String> xTimeList = new ArrayList(); 
		List seriesList = new ArrayList();
		for(Map seriesData : (List<Map>)seriesMap.get("series")) {
			Map seriesReturnMap = new HashMap();
			List dataList = new ArrayList();
			for(Object value:(List<Map>)seriesData.get("data")) {
				dataList.add(value);
			}
			seriesReturnMap.put("data",dataList);
			seriesReturnMap.put("unit",(String)seriesData.get("unit"));
			seriesReturnMap.put("name",  (String)seriesData.get("name"));
			seriesList.add(seriesReturnMap);
		}
		dataReturnMap.put("series",seriesList );
		dataReturnMap.put("export_line_name", (String)seriesMap.get("export_line_name") );
		for(String xTime: (List<String>)seriesMap.get("x_time")) {
			xTimeList.add(xTime);
		}
		dataReturnMap.put("x_time",xTimeList );
		BigDataList.add(dataReturnMap);
	}
	lineChartMap.put("data", BigDataList);
	return lineChartMap;
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
