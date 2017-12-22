package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/16.
 */
public interface  RebootService {
	/**
	 *  transfer args to reboot
	 */
    Map<String,Object> restartServer(String ip,String  user,String pwd) throws Exception;
}