package org.apd.algorithm;

import java.util.ArrayList;

public class ObserverManager {
    private ArrayList<Observer> observers= new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void notify(boolean isLoggerOn){
        for(Observer observer : observers){
            observer.updateNotify(isLoggerOn);
        }
    }
}
