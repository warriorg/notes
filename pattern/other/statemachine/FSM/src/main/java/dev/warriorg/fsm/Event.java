package dev.warriorg.fsm;

public enum Event {
    /**
     * 审批通过
     */
    APPROVE_PASS,

    /**
     * 审批拒绝
     */
    APPROVE_REFUSED,

    /**
     * 复核通过
     */
    RECHECK_PASS,

    /**
     * 复核不通过
     */
    RECHECK_REFUSED;
}
