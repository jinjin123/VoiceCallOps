package com.saicmotor.ops.wwx.dialog.questions;

public class TestQ extends BaseQuestionImpl {
    private int[] idx;

    public TestQ(String qtext, String data, int... idxs) {
        super(qtext, data);
        this.idx= idxs;
    }

    @Override
    public boolean isValid(String answer) {
        this.data = answer;
        return true;
    }

    @Override
    public String getQuestion() {
        return String.format("ip:%s  pwd:%s", this.conversation.getDataById(idx[0]), this.conversation.getDataById(idx[1]));
    }
}
