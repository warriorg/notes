package dev.warriorg.fsm;

import java.text.MessageFormat;

public class RecheckRefusedAction implements Action {

    @Override
    public boolean action(Context context) {
        System.out.println(MessageFormat.format("审批人{0}对未审核通过的价格进行复核，审批结果为拒绝", context.getParam()));
        return true;
    }
}
