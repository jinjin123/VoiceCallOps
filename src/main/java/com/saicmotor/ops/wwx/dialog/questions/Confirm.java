package com.saicmotor.ops.wwx.dialog.questions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.saicmotor.ops.wwx.service.RestartwithService;

import java.util.*;

public class Confirm extends BaseQuestionImpl  {

    private static Logger log = LoggerFactory.getLogger(Confirm.class);
    private int[] idx;


    public Confirm(String qtext, String data, int... idxs) {
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
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("test",this.appCtx.getBean(RestartwithService.class).restartconfirm(this.conversation.getDataById(idx[0]).toString()));
			log.info("ip match service {}",  this.conversation.getDataById(idx[0]));
		}catch(Exception e) {
			e.printStackTrace();
		}
	    return String.format("%s",  ((Map)result.get("test")).get("msgResult"));
//            return String.format("confirm|%s",this.conversation.getDataById(idx[0]));
    }

}
