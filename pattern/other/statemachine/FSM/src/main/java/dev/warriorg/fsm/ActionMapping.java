package dev.warriorg.fsm;

public class ActionMapping {
    /**
     * 当前状态
     */
    private State currentState;

    /**
     * 次态
     */
    private State nextState;

    /**
     * 动作
     */
    private Action action;

    /**
     * 事件
     */
    private Event event;


    public static ActionMapping ofMap(State currentState, Event event, State nextState, Action action) {
        return new ActionMapping(currentState, event, nextState, action);
    }

    private ActionMapping(State currentState, Event event, State nextState, Action action) {
        this.currentState = currentState;
        this.nextState = nextState;
        this.action = action;
        this.event = event;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
