package com.saicmotor.ops.wwx.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by kevinsun0716 on 2017/10/24.
 */
public interface WWXService {

    ResponseEntity<byte[]> replyTextMsg() throws Exception;

    String getAccessToken() throws Exception;

    byte[] downloadMediaObject(String mediaId) throws Exception;

}
