package com.saicmotor.ops.wwx.dialog.questions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class result extends BaseQuestionImpl{
    private static Logger log = LoggerFactory.getLogger(result.class);
    private int[] idx;

    public result(String qtxt,String data, int... idxs) {
        super(qtxt, data);
        log.info("q:{},data:{} idxs:{}",qtext,data,idxs);
        this.idx= idxs;
    }


    public boolean isValid(String answer){
        this.data=answer;
        return true;
    }

    @Override
    public  String getQuestion() {
            log.info("done{}",this.conversation.getDataById(idx[0]));
            return String.format("result|%s",this.conversation.getDataById(idx[0]));
    }
}