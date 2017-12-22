package com.saicmotor.ops.wwx.dialog.questions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saicmotor.ops.wwx.utils.HttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saicmotor.ops.wwx.service.ServerwithService;


import java.util.*;

public class UserNe extends BaseQuestionImpl  {

    private static Logger log = LoggerFactory.getLogger(UserNe.class);
    private int[] idx;


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
    	// get the template  convert out to String 
    			Map<String,Object> result = new HashMap<String,Object>();
    			try {
    				result.put("test",this.appCtx.getBean(ServerwithService.class).getService(this.conversation.getDataById(idx[0]).toString()));
	    			log.info("ip match service {}",  this.conversation.getDataById(idx[0]));
	    		}catch(Exception e) {
    				e.printStackTrace();
    			}
            return String.format("%s",  ((Map)result.get("test")).get("msgResult"));
//            return String.format("%s",this.conversation.getDataById(idx[0]));
    }
}
