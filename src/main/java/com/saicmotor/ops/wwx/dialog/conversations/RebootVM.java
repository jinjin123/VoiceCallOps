package com.saicmotor.ops.wwx.dialog.conversations;

import com.saicmotor.ops.wwx.dialog.Question;
import com.saicmotor.ops.wwx.dialog.questions.HostIP;
import com.saicmotor.ops.wwx.dialog.questions.UserNe;
import com.saicmotor.ops.wwx.dialog.questions.UserPwd;
import com.saicmotor.ops.wwx.dialog.questions.Confirm;
import com.saicmotor.ops.wwx.dialog.questions.ConfirmDone;
import com.saicmotor.ops.wwx.service.ChecknetService;
import java.util.*;

public class RebootVM extends BaseConversationImpl {

    public RebootVM() {
        super("重启服务器","管理员输入IP/密码重启服务器");

        this.appendQuestion(new HostIP("请输入正确的服务器IP："));
        this.appendQuestion(new UserNe("getipwithservice","xx",0,1));
        this.appendQuestion(new UserPwd());
        this.appendQuestion(new Confirm("confirmwithservice","xx",0,1));
        //get the which Q match the A
        this.appendQuestion(new ConfirmDone("ConfirmDoneserver","xx",0,1,2,3));
    }

    public String buildAction() {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String[] args = String.format("%1$s,%2$s,%3$s,%4$s",getDatas()).split(",");
			result.put("test", this.appCtx.getBean(ChecknetService.class).checkserver(args[0]));
			log.info("{}",((Map)result.get("test")).get("cmd_status"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return String.format("%s",((Map)result.get("test")).get("cmd_status"));
    }
}
