package dev.warriorg.fsm;

public class Context {

  private String param;

  public Context(String param) {
    this.param = param;
  }

  public String getParam() {
    return this.param;   
  }

  public void setParam(String param) {
      this.param = param;
    }
}
