package com.saicmotor.ops.wwx.service.impl;

import com.saicmotor.ops.wwx.service.WWXService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WWXServiceImpl implements WWXService{
    private static Logger log = LoggerFactory.getLogger(WWXService.class);

    @Autowired
    private HttpHelper restUtil;

    @Value("${wwx.Token}")
    private String sToken ;
    @Value("${wwx.CorpID}")
    private String sCorpID ;
    @Value("${wwx.CorpSecret}")
    private String sCorpSecret ;

    @Value("${wwx.AccessTokenUrl}")
    private String sAccessTokenUrl ;
    @Value("${wwx.VoiceUrl}")
    private String sVoiceUrl ;

    //simple reuse wwx access token
    private String wwxAccessToken ;
    private long wwxAccessTokenExpires;


    public ResponseEntity<byte[]> replyTextMsg() throws Exception {
        return null;
    }

    public byte[] downloadMediaObject(String mediaId) throws Exception {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("access_token" , getAccessToken());
        params.put("media_id", mediaId);
        Map<String,Object> data = restUtil.getBodyAsByteArray(sVoiceUrl, params);
        return (byte[])data.get("body");
    }

    public synchronized String getAccessToken() throws Exception {
        try{
            if( wwxAccessToken!=null && System.currentTimeMillis()<wwxAccessTokenExpires ){
                log.debug("reuse cached token : {}", wwxAccessToken);
                return wwxAccessToken;
            }

            Map<String,Object> uriVariables = new HashMap<String, Object>();
            uriVariables.put("corpid", sCorpID);
            uriVariables.put("corpsecret", sCorpSecret);
            Map<String,Object> data = restUtil.getJson(sAccessTokenUrl, uriVariables);

            wwxAccessToken = (String)((Map)data.get("body")).get("access_token");
            wwxAccessTokenExpires = System.currentTimeMillis() + (Integer)((Map)data.get("body")).get("expires_in")/2*1000;
            log.debug("get new wwx token : {}", wwxAccessToken);
            return wwxAccessToken;
        }catch(Exception e){
            wwxAccessTokenExpires = -1;
            wwxAccessToken = null;
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
