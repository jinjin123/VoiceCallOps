package com.saicmotor.ops.wwx.dialog.conversations;

import com.saicmotor.ops.wwx.dialog.Question;
import com.saicmotor.ops.wwx.dialog.questions.HostIP;
import com.saicmotor.ops.wwx.dialog.questions.UserNe;
import com.saicmotor.ops.wwx.dialog.questions.UserPwd;
import com.saicmotor.ops.wwx.dialog.questions.Confirm;
import com.saicmotor.ops.wwx.dialog.questions.ConfirmDone;
//import com.saicmotor.ops.wwx.dialog.questions.result;

public class RebootVM extends BaseConversationImpl {

    public RebootVM() {
        super("重启服务器","管理员输入IP/密码重启服务器");

        this.appendQuestion(new HostIP("请输入正确的服务器IP："));
        this.appendQuestion(new UserNe("getipwithservice","xx",0,1));
        this.appendQuestion(new UserPwd());
        this.appendQuestion(new Confirm("confirmwithservice","xx",0,1));
        //get the which Q match the A
        this.appendQuestion(new ConfirmDone("ConfirmDoneserver","xx",0,1,2,3));
//        this.appendQuestion(new result("result","xx",0));
    }

//   Done with result action
    public String buildAction() {
        return String.format("result|%1$s", getDatas());
    }
}
