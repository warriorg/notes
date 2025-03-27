package dev.warriorg.fsm;

import java.text.MessageFormat;

public class ApproveRefusedAction implements Action {

    @Override
    public boolean action(Context context) {
        System.out.println(MessageFormat.format("审批人{0}审批了价格，审批结果为拒绝", context.getParam()));
        return true;
    }
}
