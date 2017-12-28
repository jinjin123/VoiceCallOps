package com.saicmotor.ops.wwx.dialog.questions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.saicmotor.ops.wwx.service.RebootService;
import java.util.*;

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
		Map<String,Object> result = new HashMap<String,Object>();
		try {
				result.put("test",this.appCtx.getBean(RebootService.class).restartServer(this.conversation.getDataById(idx[0]).toString(),this.conversation.getDataById(idx[1]).toString(),this.conversation.getDataById(idx[2]).toString()));
				log.info("reboot return {}",  ((Map)result.get("test")).get("msg"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return String.format("%s,%s",  ((Map)result.get("test")).get("msg"),"正在重启,请稍后...回复'结果'将返回结果");
//            log.info("done{}",String.format("%s|%s|%s|%s",this.conversation.getDataById(idx[3]),this.conversation.getDataById(idx[0]),this.conversation.getDataById(idx[1]),this.conversation.getDataById(idx[2])));
    }
}