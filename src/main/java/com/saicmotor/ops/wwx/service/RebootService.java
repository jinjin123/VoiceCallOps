package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/16.
 */
public interface  RebootService {
    Map<String,Object> restartServer(String url) throws Exception;
//    Map<String,Object> getService(String url, String IP) throws Exception;
}
