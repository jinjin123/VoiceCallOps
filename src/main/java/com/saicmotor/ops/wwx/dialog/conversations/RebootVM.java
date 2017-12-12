package com.saicmotor.ops.wwx.dialog.conversations;

import com.saicmotor.ops.wwx.dialog.questions.HostIP;
import com.saicmotor.ops.wwx.dialog.questions.UserPwd;

public class RebootVM extends BaseConversationImpl {

    public RebootVM() {
        super("重启服务器","管理员输入IP/密码重启服务器");

        this.appendQuestion(new HostIP("请输入服务器IP："));
        this.appendQuestion(new UserPwd());
    }

    public String buildAction() {
        return String.format("ACT.reboot(%1$s, %2$s)", getDatas());
    }

}