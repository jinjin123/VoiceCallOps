package com.saicmotor.ops.wwx.service;

import org.springframework.http.ResponseEntity;

/**
 * Created by kevinsun0716 on 2017/10/24.
 */
public interface WWXService {

    ResponseEntity<byte[]> replyTextMsg() throws Exception;

}
