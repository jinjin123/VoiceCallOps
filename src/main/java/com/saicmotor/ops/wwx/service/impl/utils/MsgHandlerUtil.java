package com.saicmotor.ops.wwx.service.impl.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Shen_JM on 2017/10/31.
 */
@Service
public class MsgHandlerUtil {
	private static Logger log = LoggerFactory.getLogger(MsgHandlerUtil.class);
	
    @Autowired
    @Qualifier("jsonMapper")
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${wwx.AccessTokenUrl}")
    private String sAccessTokenUrl ;
	
	public Map<String,Object> getAccesToken(String sCorpID, String sCorpSecret) throws Exception {
	        try{
	            Map<String,Object> uriVariables = new HashMap<String, Object>();
	            Map<String,Object> resultMap = new HashMap<String, Object>();
	            uriVariables.put("corpid", sCorpID);
	            uriVariables.put("corpsecret", sCorpSecret);

	            log.debug("GET {}\nuriVariables:{}", sAccessTokenUrl, uriVariables);
	            resultMap = restTemplate.getForObject(sAccessTokenUrl, Map.class, uriVariables);
	            log.debug("Response:{}\n{}",resultMap.get("errmsg"), resultMap.get("access_token"),resultMap.get("expires_in"));
	            return resultMap;
	        }catch(Exception e){
	            log.error(e.getMessage(), e);
	            throw e;
	        }
	 }
}
