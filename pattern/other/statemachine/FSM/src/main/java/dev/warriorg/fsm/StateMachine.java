package dev.warriorg.fsm;

import java.util.ArrayList;
import java.util.List;

import static dev.warriorg.fsm.Event.APPROVE_PASS;
import static dev.warriorg.fsm.Event.APPROVE_REFUSED;
import static dev.warriorg.fsm.Event.RECHECK_PASS;
import static dev.warriorg.fsm.Event.RECHECK_REFUSED;
import static dev.warriorg.fsm.State.APPROVE;
import static dev.warriorg.fsm.State.PASS;
import static dev.warriorg.fsm.State.REFUSED;

public class StateMachine {
    /**
     * 状态转移表
     */
    private List<ActionMapping> mappings = new ArrayList<>();
    {
        //F(APPROVE,APPROVE_PASS)->(PASS,ApprovePassAction)
        mappings.add(ActionMapping.ofMap(APPROVE, APPROVE_PASS, PASS, new ApprovePassAction()));

        //F(APPROVE,APPROVE_REFUSED)->(REFUSED,ApproveRefusedAction)
        mappings.add(ActionMapping.ofMap(APPROVE, APPROVE_REFUSED, REFUSED, new ApproveRefusedAction()));

        //F(REFUSED,RECHECK_PASS)->(PASS,RecheckPassAction)
        mappings.add(ActionMapping.ofMap(REFUSED, RECHECK_PASS, PASS, new RecheckPassAction()));

        //F(REFUSED,RECHECK_REFUSED)->(REFUSED,RecheckRefusedAction)
        mappings.add(ActionMapping.ofMap(REFUSED, RECHECK_REFUSED, REFUSED, new RecheckRefusedAction()));

    }

    public boolean transform(State currentState, Event event, Context context) {
        ActionMapping actionMapping = getMapping(currentState, event);
        if (null == actionMapping) {
            throw new RuntimeException("未找到相应的映射");
        }
        Action action = actionMapping.getAction();
        action.action(context);
        return true;
    }

    private ActionMapping getMapping(State currentState, Event event) {
        if (mappings.size() > 0) {
            for (ActionMapping n : mappings) {
                if (n.getCurrentState().equals(currentState) && n.getEvent().equals(event)) {
                    return n;
                }
            }
        }
        return null;
    }
}
