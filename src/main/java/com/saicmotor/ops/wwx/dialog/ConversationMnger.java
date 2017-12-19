package com.saicmotor.ops.wwx.dialog;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConversationMnger implements ApplicationContextAware {
    private static Logger log = LoggerFactory.getLogger(ConversationMnger.class);

    protected ApplicationContext appCtx;
    private Set<Conversation> conversations ;
    private Map<String,Conversation> cache = new HashMap<String, Conversation>();

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }


    public String talk(String openId, String answer) throws Exception{
        Conversation curConversation = null;

        //del talk session
        if(answer.matches("(.*)取消(.*)")){
            curConversation = cache.remove(openId);
            return answer;
        }
        //判断是否存在会话。
        curConversation = cache.get(openId);


        //判断是否开启新的会话
        if( curConversation==null ){
            for(Conversation cv : conversations){
                if ( cv.isFire(answer) ){
                    curConversation = cv.getClass().newInstance();
                    cache.put(openId, curConversation);
                    return curConversation.getQuestion();
                }
            }
        }

        //执行会话过程.
        if ( curConversation!=null){
            curConversation.isValid(answer);
            if( curConversation.isCompleted() ){
                cache.remove(openId);
                return curConversation.buildAction();
            }else{
                return curConversation.getQuestion();
            }
        }

        return answer;
    }
}
