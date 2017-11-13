package com.saicmotor.ops.wwx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.saicmotor.ops.wwx.service.SearchService;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Shen_JM on 2017/10/30.
 */
@Service("SearchServiceImpl")
public class SearchServiceImpl implements SearchService{
    private static Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    @Qualifier("jsonMapper")
    @SuppressWarnings({"SpringJavaAutowiringInspection"})
    private ObjectMapper jsonMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private XmlMapper xmlMapper;
    
    @Value("${yunwei.schedue.url}")
    private String ySchedueUrl ;
    
    private static CloseableHttpClient httpClient;
    
//
//    public List<String> getSchedue(String actionUrl,int days) throws Exception {
//    	
//        try{
//        	Date sysDate = new Date();
//            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//            String dateStr = sdfDate.format(sysDate);
//            String newDateStr = sdfDate.format(new Date(sysDate.getTime() + days* 24 * 60 * 60 * 1000));
//            
////        	String encodeSchedueUrl = URLEncoder.encode(actionUrl, "UTF-8");
//            log.debug("GET {}\n", actionUrl);
//            
//            Map response = callQcCdnApi(actionUrl);
//            Map<String,Object> resultMap = jsonMapper.readValue((String)response.get("body"), Map.class);
//            log.info("-->类型: " + resultMap.get("data").getClass());
////            List<Map<String,Object>> allSchedueData = jsonMapper.readValue(resultMap.get("data").toString(),List.class);
//            List<Map<String,Object>> allSchedueData = (List)resultMap.get("data");
//            List<String> schedueData = new ArrayList<String>();	
//            for(Map<String,Object> map: allSchedueData) {
//            	String schedueDate = map.get("start").toString();
//            	if (schedueDate.compareTo(dateStr)>=0 && newDateStr.compareTo(schedueDate)>=0){
//            		String scheduePlan = map.get("title").toString();
//            		String scheduePlanFmt = String.format("%s : %s\n", schedueDate,scheduePlan);
//            		schedueData.add(scheduePlanFmt);
//            	}
//            }
//            return schedueData;
//        }catch(Exception e){
//            log.error(e.getMessage(), e);
//            throw e;
//        }
//    }
    
    public String getSchedue(Map<String,Object> paramsMap) throws Exception {
    	String result = "";
        try{
        	int days = (Integer)paramsMap.get("days");
        	Date sysDate = new Date();
        	SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String dateTime = sdfTime.format(sysDate);
    		String dateTimeEncode = URLEncoder.encode(dateTime, "UTF-8");
    		String actionUrl = ySchedueUrl + dateTimeEncode;
    		
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdfDate.format(sysDate);
            String newDateStr = sdfDate.format(new Date(sysDate.getTime() + days* 24 * 60 * 60 * 1000));
            
            log.debug("GET {}\n", actionUrl);
            
            Map response = callQcCdnApi(actionUrl);
            Map<String,Object> resultMap = jsonMapper.readValue((String)response.get("body"), Map.class);
            log.info("-->类型: " + resultMap.get("data").getClass());
//            List<Map<String,Object>> allSchedueData = jsonMapper.readValue(resultMap.get("data").toString(),List.class);
            List<Map<String,Object>> allSchedueData = (List)resultMap.get("data");
            List<String> schedueData = new ArrayList<String>();	
            for(Map<String,Object> map: allSchedueData) {
            	String schedueDate = map.get("start").toString();
            	if (schedueDate.compareTo(dateStr)>=0 && newDateStr.compareTo(schedueDate)>=0){
            		String scheduePlan = map.get("title").toString();
            		String scheduePlanFmt = String.format("%s : %s\n", schedueDate,scheduePlan);
            		schedueData.add(scheduePlanFmt);
            	}
            }
            if (schedueData!=null) {
            	StringBuilder sBuilder=new StringBuilder();
            	for(String str:schedueData){
            		sBuilder.append(str);
            	}
            	result = sBuilder.toString();
            }
            return result;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    
    public static Map callQcCdnApi(String url) throws Exception{
        long st = System.currentTimeMillis();
        if (httpClient==null){
            synchronized (SearchServiceImpl.class){
                if (httpClient==null){
                    httpClient = HttpClients.createDefault();
                }
            }
        }
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(httpGet);

        Map result = new HashMap();
        try{
            int code = response.getStatusLine().getStatusCode();
            result.put("code", code);

            BufferedInputStream bis = null;
            ByteArrayOutputStream bos = null;
            try{
                if ( response.getEntity()!=null ){
                    bos = new ByteArrayOutputStream();
                    bis = new BufferedInputStream(response.getEntity().getContent());
                    byte[] tmp = new byte[1024];
                    int len = 0;
                    while( (len=bis.read(tmp)) >=0 ){
                        bos.write(tmp,0,len);
                    }
                    //result.put("body", StringEscapeUtils.unescapeJava(bos.toString("UTF-8")));
                    result.put("body", bos.toString("UTF-8"));
                }
            }finally {
                if ( bis!=null ) bis.close();
                if ( bos!=null ) bos.close();
            }
        }finally {
            response.close();
        }
        log.debug("call api:{} used {}ms : \nhttp code = {}\nbody={}",url , (System.currentTimeMillis()-st) ,result.get("code"), result.get("body"));
        return result;
    }
}
