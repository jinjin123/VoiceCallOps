package com.saicmotor.ops.wwx.dialog.conversations;

import com.saicmotor.ops.wwx.dialog.Conversation;
import com.saicmotor.ops.wwx.dialog.Question;
import com.saicmotor.ops.wwx.dialog.questions.BaseQuestionImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConversationImpl implements Conversation{
    protected static Logger log = LoggerFactory.getLogger(BaseConversationImpl.class);

    protected ApplicationContext appCtx;
    protected String actName ;
    protected String actDesc ;
    private List<Question> questions;
    private int qidx = 0;

    public BaseConversationImpl(String actName, String actDesc){
        this.actName = actName;
        this.actDesc = actDesc;
        this.questions = new ArrayList<Question>();
    }

    public void setApplicationContext(ApplicationContext appCtx){
        this.appCtx = appCtx;
        for(Question q : questions){
        	((BaseQuestionImpl)q).setApplicationContext(appCtx);
        }
    }

    public void appendQuestion(Question question){
        questions.add(question);
        question.setConversation(this);
    }

    public String getActName() {
        return actName;
    }

    public String getActDesc() {
        return actDesc;
    }

    public boolean isFire(String txt) {
        if ( txt!=null && txt.indexOf(actName)>=0 ){
            return true;
        }else{
            return false;
        }
    }

    public String getQuestion() {
        Question q = questions.get(qidx);
        return q.getQuestion();
    }

    public boolean isValid(String answer) {
        Question q = questions.get(qidx);
        if ( q.isValid(answer) ){
            qidx++;
            return true;
        }else{
            return false;
        }
    }

    public boolean isCompleted() {
        return qidx >= questions.size();
    }

    public Object[] getDatas(){
        List<Object> dts= new ArrayList<Object>();
        for( Question q : questions ){
            dts.add(q.getData());
        }
        return dts.toArray();
    }

    public Object getDataById(int idx){
        return questions.get(idx).getData();
    }

    public abstract String buildAction() ;

}
