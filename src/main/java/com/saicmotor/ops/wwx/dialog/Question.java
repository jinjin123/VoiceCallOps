package com.saicmotor.ops.wwx.dialog;

public interface Question {

    /**
     * 获取问题文字
     * @return
     */
//    String getQuestion();
    String getQuestion() ;

    /**
     * 验证回答是否合法.
     * @param answer
     * @return
     */
    boolean isValid(String answer);

    /**
     * 将答案转换成内部可识别的数据。
     * @return
     */
    String getData();

    /**
     * 设置会话实例
     * @param conversation
     */
    void setConversation(Conversation conversation);

}
