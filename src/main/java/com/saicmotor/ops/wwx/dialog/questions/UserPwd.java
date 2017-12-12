package com.saicmotor.ops.wwx.dialog.questions;

public class UserPwd extends BaseQuestionImpl {

    public UserPwd(String qtext) {
        super(qtext, null);
    }

    public UserPwd() {
        super("请输入密码：", null);
    }

    public boolean isValid(String answer) {
        this.data = answer;
        return true;
    }

}
