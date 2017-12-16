package com.saicmotor.ops.wwx.dialog.conversations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.saicmotor.ops.wwx.dialog.Question;
import com.saicmotor.ops.wwx.dialog.questions.HostIP;
import com.saicmotor.ops.wwx.dialog.questions.UserNe;
import com.saicmotor.ops.wwx.dialog.questions.UserPwd;
import com.saicmotor.ops.wwx.dialog.questions.command;
//import com.saicmotor.ops.wwx.dialog.questions.ConfirmDone;
//import com.saicmotor.ops.wwx.dialog.questions.result;

public class Opeation extends BaseConversationImpl {
    private static Logger log = LoggerFactory.getLogger(Opeation.class);


    public Opeation() {
        super("操作服务器","操作命令");

        this.appendQuestion(new HostIP("请输入IP?"));
        this.appendQuestion(new UserNe("getipwithservice","xx",0,1));
        this.appendQuestion(new UserPwd());
        this.appendQuestion(new command("请输入操作指令"));
//        this.appendQuestion(new Confirm("confirmwithservice","xx",0,1));
        //get the which Q match the A
//        this.appendQuestion(new ConfirmDone("ConfirmDoneserver","xx",0,1,2,3));
//        this.appendQuestion(new result("result","xx",0));
    }

//   Done with result action
    public String buildAction() {
        return String.format("opt,%1$s,%2$s,%3$s,%4$s", getDatas());
    }
}
