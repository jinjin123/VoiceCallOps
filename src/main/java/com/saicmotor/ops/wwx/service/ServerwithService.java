package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/13.
 */
public interface  ServerwithService {
    Map<String,Object> getService(String url,String ip) throws Exception;
//    Map<String,Object> getService(String url, String IP) throws Exception;
}
