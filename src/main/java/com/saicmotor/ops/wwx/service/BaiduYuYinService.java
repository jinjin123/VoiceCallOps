package com.saicmotor.ops.wwx.service;

import java.io.File;
import java.util.Map;

/**
 * Created by Shen_JM on 2017/10/30.
 */
public interface BaiduYuYinService {

    Map<String,Object> getVoiceRecognition(File mediaFile) throws Exception;

    String voice2txt(String format, String rate, byte[] data) throws Exception;
    
    String getAccessToken() throws Exception;

}
