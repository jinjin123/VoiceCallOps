package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/13.
 */
public interface  HiService {
    Map<String,Object> getHianswer(String url, String user) throws Exception;
}
