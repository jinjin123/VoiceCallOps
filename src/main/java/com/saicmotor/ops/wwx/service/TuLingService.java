package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by kevinsun0716 on 2017/10/23.
 */
public interface TuLingService {

    Map<String,Object> getTalkAnswer(String usrId, String question, String location) throws Exception;

}
