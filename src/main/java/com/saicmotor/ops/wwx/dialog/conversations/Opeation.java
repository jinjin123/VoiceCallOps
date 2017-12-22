package com.saicmotor.ops.wwx.dialog.conversations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.saicmotor.ops.wwx.dialog.Question;
import com.saicmotor.ops.wwx.dialog.questions.HostIP;
import com.saicmotor.ops.wwx.dialog.questions.UserNe;
import com.saicmotor.ops.wwx.dialog.questions.UserPwd;
import com.saicmotor.ops.wwx.dialog.questions.command;
import com.saicmotor.ops.wwx.service.CommandService;
import java.util.*;

public class Opeation extends BaseConversationImpl {
    private static Logger log = LoggerFactory.getLogger(Opeation.class);


    public Opeation() {
        super("操作服务器","操作命令");

        this.appendQuestion(new HostIP("请输入正确的服务器IP："));
        this.appendQuestion(new UserNe("getipwithservice","xx",0,1));
        this.appendQuestion(new UserPwd());
        this.appendQuestion(new command("请输入正确的操作指令"));

    }

//   Done with result action
    public String buildAction() {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String[] args = String.format("%1$s,%2$s,%3$s,%4$s",getDatas()).split(",");
			result.put("test", this.appCtx.getBean(CommandService.class).execcommand(args[0],args[1],args[2],args[3]));
			log.info("{}",((Map)result.get("test")).get("cmd_status"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return String.format("%s",((Map)result.get("test")).get("cmd_status"));
    }
}
