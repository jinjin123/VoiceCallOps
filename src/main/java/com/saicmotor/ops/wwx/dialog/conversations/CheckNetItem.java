package com.saicmotor.ops.wwx.dialog.conversations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.saicmotor.ops.wwx.dialog.Question;
import com.saicmotor.ops.wwx.dialog.questions.HostIP;
import com.saicmotor.ops.wwx.dialog.questions.NetItem;
import com.saicmotor.ops.wwx.service.ChecknetService;
import java.util.*;

public class CheckNetItem extends BaseConversationImpl {
    private static Logger log = LoggerFactory.getLogger(CheckNetItem.class);

    public CheckNetItem() {
        super("检查网络","检查网络连通性");

        this.appendQuestion(new HostIP("请输入正确的服务器IP："));
        this.appendQuestion(new NetItem());
    }

//   getBean  call the  method
    public String buildAction() {
		Map<String,Object> result = new HashMap<String,Object>();
		try {			
			String[] args = String.format("%1$s,%2$s",getDatas()).split(",");
			result.put("test", this.appCtx.getBean(ChecknetService.class).NetItemCheck(args[0],args[1]));
			log.info("{}",((Map)result.get("test")).get("cmd_status"));
		}catch(Exception e) {
			e.printStackTrace();
		}
//        return String.format("opt,%1$s,%2$s,%3$s,%4$s", getDatas());
		return String.format("%s",((Map)result.get("test")).get("cmd_status"));

    }
}
