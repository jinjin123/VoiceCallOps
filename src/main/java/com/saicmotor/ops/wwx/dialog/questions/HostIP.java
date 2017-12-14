package com.saicmotor.ops.wwx.dialog.questions;

import java.util.regex.Pattern;

public class HostIP extends BaseQuestionImpl{
    private String fmt = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

    public HostIP(String qtxt) {
        super(qtxt, null);
    }

    public HostIP() {
        super("请输入IP地址：", null);
    }

    public boolean isValid(String answer){
        if ( Pattern.matches(fmt, answer) ){
            this.data = answer;
            return true;
        }else{
            this.errMsg = "ip地址错误";
            return false;
        }
    }


}
