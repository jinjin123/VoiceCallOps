package com.saicmotor.ops.wwx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saicmotor.ops.wwx.service.BaiduYuYinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by Shen_JM on 2017/10/30.
 */
@Service
public class BaiduYuYinServiceImpl implements BaiduYuYinService{
    private static Logger log = LoggerFactory.getLogger(BaiduYuYinServiceImpl.class);

    @Autowired
    @Qualifier("jsonMapper")
    @SuppressWarnings({"SpringJavaAutowiringInspection"})
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${baidu.api.format}")
    private String baidu_api_format;
    @Value("${baidu.api.rate}")
    private String baidu_api_rate;
    @Value("${baidu.api.channel}")
    private String baidu_api_channel;
    @Value("${baidu.api.cuid}")
    private String baidu_api_cuid;
    @Value("${baidu.api.token}")
    private String baidu_api_token;
    @Value("${baidu.api.lan}")
    private String baidu_api_lan;
    @Value("${baidu.api.callback}")
    private String baidu_api_callback;
    @Value("${baidu.api.url}")
    private String baidu_api_url;


    public Map<String, Object> getVoiceRecognition(File mediaFile) throws Exception {
//        try{
//        	HttpHeaders headers = new HttpHeaders();
//        	headers.setContentType(MediaType.APPLICATION_JSON);
//            Map<String,Object> body = new HashMap<String, Object>();
//            body.put("format", baidu_api_format);
//            body.put("rate", baidu_api_rate);
//            body.put("channel", baidu_api_channel);
//            body.put("cuid", baidu_api_cuid);
//            body.put("token", baidu_api_token);
//            body.put("lan", baidu_api_lan);
//            body.put("len", mediaFile.length());
//            body.put("speech", DatatypeConverter.printBase64Binary(loadFile(mediaFile)));
//            
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonParams = mapper.writeValueAsString(body);
////            body.put("url", wxVoiceUrl);
////            body.put("callback", baidu_api_callback);
//            log.debug("POST {}\nbody:{}", baidu_api_url, body);
//            HttpEntity<String> entity = new HttpEntity<String>(jsonParams,headers);
////            Map<String, Object> resultMap = restTemplate.postForObject(baidu_api_url, entity, Map.class);
////            log.debug("resultMap:{}\n{}",resultMap.get("result"), resultMap.get("errmsg"));
//            ResponseEntity<Map> response = restTemplate.exchange(baidu_api_url, HttpMethod.POST, entity, Map.class);
//            log.debug("Response:{}\n{}",response.getStatusCodeValue(), response.getBody());
//            return response.getBody();
//        }catch(Exception e){
//            log.error(e.getMessage(), e);
//            throw e;
//        }
    	
        try{
          Map<String,Object> body = new HashMap<String, Object>();
          body.put("format", baidu_api_format);
          body.put("rate", baidu_api_rate);
          body.put("channel", baidu_api_channel);
          body.put("cuid", baidu_api_cuid);
          body.put("token", baidu_api_token);
          body.put("lan", baidu_api_lan);
          body.put("len", mediaFile.length());
          body.put("speech", DatatypeConverter.printBase64Binary(loadFile(mediaFile)));

            log.debug("POST {}\nbody:{}", baidu_api_url, body);
            ResponseEntity<Map> response = restTemplate.postForEntity(baidu_api_url, body, Map.class);
            log.debug("Response:{}\n{}",response.getStatusCodeValue(), response.getBody());
            return response.getBody();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    
    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
