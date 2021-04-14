package me.warriorg.design.behavioral.observer.weather;


public interface Subject {

    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObserver();
}
