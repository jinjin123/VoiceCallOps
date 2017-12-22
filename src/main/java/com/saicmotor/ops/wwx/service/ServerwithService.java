package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/16.
 */
public interface  ServerwithService {
	/**
	 *  select server from the  production  service
	 */
	 Map<String,Object> getService(String ip) throws Exception;
}
