package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/16.
 */
public interface  RestartwithService {
    Map<String,Object> restartconfirm(String ip) throws Exception;
}
