package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/13.
 */
public interface  ChecknetService {
    Map<String,Object> checkserver(String url) throws Exception;
}
