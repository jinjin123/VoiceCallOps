package com.saicmotor.ops.wwx.service;

import java.util.Map;

/**
 * Created by Jimmy on 2017/12/16.
 */
public interface  CommandService {
//    Map<String,Object> execcommand(String url) throws Exception;
  Map<String,Object> execcommand(String ip,String user,String pwd, String cmd) throws Exception;
}
