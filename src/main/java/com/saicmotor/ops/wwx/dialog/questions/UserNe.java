package com.saicmotor.ops.wwx.dialog.questions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.*;

public class UserNe extends BaseQuestionImpl  {

    private static Logger log = LoggerFactory.getLogger(UserNe.class);
    private int[] idx;


    @Autowired
    private HttpHelper restUtil;


    public UserNe(String qtext, String data, int... idxs) {
        super(qtext, data);
        log.info("q:{},data:{} idxs:{}",qtext,data,idxs);
        this.idx= idxs;
    }


    public boolean isValid(String answer) {
        log.info("a:{}",answer);
        this.data = answer;
        return true;
    }

    @Override
    public  String getQuestion() {
            // return A: ip address  || add tag input user or restart confirm
            return String.format("user|%s",this.conversation.getDataById(idx[0]));
    }
}
