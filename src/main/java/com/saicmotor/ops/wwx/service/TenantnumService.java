package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/13.
 */
public interface  TenantnumService {
	/**
	 *  get the how much tenant
	 */
    Map<String,Object> gettenantnum(String length) throws Exception;
}
