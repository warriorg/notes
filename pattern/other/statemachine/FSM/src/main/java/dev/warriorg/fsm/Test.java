package dev.warriorg.fsm;

import static dev.warriorg.fsm.Event.APPROVE_REFUSED;
import static dev.warriorg.fsm.Event.RECHECK_PASS;
import static dev.warriorg.fsm.Event.RECHECK_REFUSED;
import static dev.warriorg.fsm.State.APPROVE;
import static dev.warriorg.fsm.State.REFUSED;

public class Test {

    public static void main(String[] args) {
        StateMachine machine = new StateMachine();

        machine.transform(APPROVE,APPROVE_REFUSED,new Context("咲夜"));
        machine.transform(REFUSED,RECHECK_REFUSED,new Context("咲夜"));
        machine.transform(REFUSED,RECHECK_PASS,new Context("帕秋莉"));
        machine.transform(REFUSED,RECHECK_REFUSED,new Context("张三"));
    }
}
