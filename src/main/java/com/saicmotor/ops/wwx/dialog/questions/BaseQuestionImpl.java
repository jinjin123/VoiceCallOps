package com.saicmotor.ops.wwx.dialog.questions;

import com.saicmotor.ops.wwx.dialog.Conversation;
import com.saicmotor.ops.wwx.dialog.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public abstract class BaseQuestionImpl implements Question {
    protected static Logger logger = LoggerFactory.getLogger(BaseQuestionImpl.class);
    protected ApplicationContext appCtx;
    protected Conversation conversation;
    protected String qtext;
    protected String data;
    protected String errMsg;


    public BaseQuestionImpl(String qtext, String data){
        this.qtext = qtext;
        this.data = data;
    }

    public void setApplicationContext(ApplicationContext appCtx){
        this.appCtx = appCtx;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getQuestion() {
        return (errMsg!=null?(errMsg+","):"") + qtext;
    }

    public abstract boolean isValid(String answer) ;

    public String getData() {
        return data;
    }



}
