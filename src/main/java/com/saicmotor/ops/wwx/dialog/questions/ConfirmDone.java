package com.saicmotor.ops.wwx.dialog.questions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfirmDone extends BaseQuestionImpl{
    private static Logger log = LoggerFactory.getLogger(ConfirmDone.class);
    private int[] idx;

    public ConfirmDone(String qtxt,String data, int... idxs) {
        super(qtxt, data);
        log.info("q:{},data:{} idxs:{}",qtext,data,idxs);
        this.idx= idxs;
    }


    public boolean isValid(String answer){
            this.data = answer;
            return true;
    }

    @Override
    public  String getQuestion() {
            log.info("done{}",String.format("%s|%s|%s|%s",this.conversation.getDataById(idx[3]),this.conversation.getDataById(idx[0]),this.conversation.getDataById(idx[1]),this.conversation.getDataById(idx[2])));
            return String.format("%s|%s|%s|%s",this.conversation.getDataById(idx[3]),this.conversation.getDataById(idx[0]),this.conversation.getDataById(idx[1]),this.conversation.getDataById(idx[2]));
    }
}