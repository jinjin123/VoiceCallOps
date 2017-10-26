package com.saicmotor.ops.wwx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saicmotor.ops.wwx.service.TuLingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevinsun0716 on 2017/10/23.
 */
@Service
public class TuLingServiceImpl implements TuLingService{
    private static Logger log = LoggerFactory.getLogger(TuLingServiceImpl.class);

    @Autowired
    @Qualifier("jsonMapper")
    @SuppressWarnings({"SpringJavaAutowiringInspection"})
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${tuling.api.url}")
    private String tuling_api_url;
    @Value("${tuling.api.key}")
    private String tuling_api_key;


    public Map<java.lang.String, Object> getTalkAnswer(String usrId, String question, String location) throws Exception {
        try{
            Map<String,Object> body = new HashMap<String, Object>();
            body.put("key", tuling_api_key);
            body.put("userid", usrId);
            body.put("info", question);
            body.put("loc", location);

            log.debug("POST {}\nbody:{}", tuling_api_url, body);
            ResponseEntity<Map> response = restTemplate.postForEntity(tuling_api_url, body, Map.class);
            log.debug("Response:{}\n{}",response.getStatusCodeValue(), response.getBody());
            return response.getBody();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
