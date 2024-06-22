package main.Secretary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InfoStation implements Subject, Serializable {
    private List<Observer> observers = new ArrayList<>();
    private String message;

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
        notifyObservers();
    }
}
