package me.warriorg.design.behavioral.observer.weather;

public interface Observer {
    /***
     *
     * @param temp
     * @param humidity
     * @param pressure
     */
    void update(float temp, float humidity, float pressure);
}
