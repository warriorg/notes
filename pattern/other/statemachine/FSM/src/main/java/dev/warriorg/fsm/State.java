package dev.warriorg.fsm;

public enum State {
    /**
     * 待审核
     */
    APPROVE,

    /**
     * 拒绝
     */
    REFUSED,

    /**
     * 同意
     */
    PASS;
}
