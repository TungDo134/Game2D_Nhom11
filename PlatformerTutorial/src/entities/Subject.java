package entities;

import achievements.Observer;

public interface Subject {
    public void addObserver(Observer board);
    public void removeObserver(Observer board);
    public void notifyObserver();

}
