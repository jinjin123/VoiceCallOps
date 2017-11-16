package com.saicmotor.ops.wwx.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpHelper {
    private static Logger log = LoggerFactory.getLogger(HttpHelper.class);

    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    @SuppressWarnings({"SpringJavaAutowiringInspection"})
    private ObjectMapper jsonMapper;

    private Map<String,Object> request(String method, String url,  Map<String,Object> querys, Map<String,Object> headers, Map<String,Object> body) throws Exception{
        URIBuilder uriBuilder = new URIBuilder(url).setCharset(Charset.forName("UTF-8"));
        if( querys!=null && querys.size()>0 ){
            for (String key : querys.keySet()) {
                uriBuilder.addParameter(key, querys.get(key).toString());
            }
        }
        log.debug("{} {}\nheaders:{}\nbody:{}", method, uriBuilder.build().toString(), headers, body);

        HttpRequest req = null;
        if ( "GET".equalsIgnoreCase(method) ){
            req = new HttpGet(uriBuilder.build().toString());
        }else if (method.toUpperCase().startsWith("POST")) {
            req = new HttpPost(uriBuilder.build().toString());
        }

        if( headers!=null && headers.size()>0 ){
            for(String key : headers.keySet()){
                req.addHeader(key, headers.get(key).toString());
            }
        }

        if( "POST-JSON".equalsIgnoreCase(method) ){
            StringEntity entity = new StringEntity(jsonMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
            ((HttpPost)req).setEntity(entity);
        }else if("POST-FORM".equalsIgnoreCase(method)) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String key : body.keySet()) {
                parameters.add(new BasicNameValuePair(key, body.get(key).toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            ((HttpPost)req).setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            if ("GET".equalsIgnoreCase(method)) {
                response = httpClient.execute((HttpGet) req);
            } else if (method.toUpperCase().startsWith("POST")) {
                response = httpClient.execute((HttpPost) req);
            }

            Map result = new HashMap<String, Object>();
            result.put("code", response.getStatusLine().getStatusCode());
            result.put("body", EntityUtils.toByteArray(response.getEntity()));

            log.debug("response : {}\nbody:{}", result.get("code"), new String((byte[])result.get("body"),"UTF-8"));
            return result;
        }catch (Exception t){
            log.error(t.getMessage(),t);
            throw t;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public Map<String,Object> getJson(String url) throws Exception{
        Map result = request("GET", url, null, null, null);
        byte[] body = (byte[])result.get("body");
        if( body!=null && body.length>0 ){
            Object json = jsonMapper.readValue(body, Object.class);
            result.put("body", json);
        }
        return result;
    }

    public Map<String,Object> getJson(String url, Map<String,Object> querys) throws Exception{
        Map result = request("GET", url, querys, null, null);
        byte[] body = (byte[])result.get("body");
        if( body!=null && body.length>0 ){
            Object json = jsonMapper.readValue(body, Object.class);
            result.put("body", json);
        }
        return result;
    }

    public Map<String,Object> getBodyAsByteArray(String url, Map<String,Object> querys) throws Exception{
        return request("GET", url, querys, null,null);
    }

    public Map<String,Object> postJson(String url, Map<String,Object> body) throws Exception{
        Map result = request("POST-JSON", url, null, null, body);
        byte[] tmp = (byte[])result.get("body");
        if( tmp!=null && tmp.length>0 ){
            Object json = jsonMapper.readValue(tmp, Object.class);
            result.put("body", json);
        }
        return result;
    }

}
