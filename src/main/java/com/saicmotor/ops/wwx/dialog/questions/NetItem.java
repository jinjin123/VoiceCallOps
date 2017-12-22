package com.saicmotor.ops.wwx.dialog.questions;

public class NetItem extends BaseQuestionImpl {

    public  NetItem(String qtext) {
        super(qtext, null);
    }

    public NetItem() {
        super("请选择您要检查的项目: \n" + 
        		"0) ping \n" +
        		"1) tracert \n" + 
        		"2) 80端口连通性", null);
    }

    public boolean isValid(String answer) {
        this.data = answer;
        return true;
    }

}
