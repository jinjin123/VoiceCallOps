package com.saicmotor.ops.wwx.dialog.questions;

public class command extends BaseQuestionImpl {
    private String notdo = new String("(rm|vi|echo|touch|reboot|shutdown|init)");

    public command(String qtext) {
        super(qtext, null);
    }

    public command() {
        super("请输入正确的操作指令：", null);
    }

    public boolean isValid(String answer) {
        if(answer.matches(notdo)){
            this.data = "该指令不允许";
            return false;
        }else {
            this.data = answer;
            return true;
        }
    }

}
