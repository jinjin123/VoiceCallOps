package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/13.
 */
public interface  ChecknetService {
	/**
	 *    check net of ping 
	 */
    Map<String,Object> checkserver(String ip) throws Exception;
    
    /**
     *   check net item  option
     */
    Map<String,Object> NetItemCheck(String ip,String item) throws Exception;
}
