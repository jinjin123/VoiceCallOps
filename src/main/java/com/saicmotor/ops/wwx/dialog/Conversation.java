package com.saicmotor.ops.wwx.dialog;

public interface Conversation {

    /**
     * 获取会话名称，可等同于出发条件
     * @return
     */
    String getActName();

    /**
     * 返回会话描述
     * @return
     */
    String getActDesc();

    /**
     * 判断输入是否触发开始会话。
     * @param txt
     * @return
     */
    boolean isFire(String txt) ;

    /**
     * 取会话问题
     * @return
     */
    String getQuestion();

    /**
     * 判断用户回复是否合法
     * @param answer
     * @return
     */
    boolean isValid(String answer);

    /**
     * 判断会话中所包含问题是否全部完成。
     * @return
     */
    boolean isCompleted();

    /**
     * 构造指令.
     * @return
     */
    String buildAction();

}


