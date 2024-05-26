import java.util.ArrayList;
import java.util.List;

public class Observable  {
    private List <Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(MazeData mazeData) {
        for (Observer observer : observers) {
            observer.update(mazeData);
        }
    }

    public void performAction() {
        // Symulacja długotrwałej akcji
        try {
            Thread.sleep(2000); // symulacja opóźnienia
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Akcja zakończona, powiadomienie obserwatorów
        String result = "Action Completed";
        notifyObservers(result);
    }
}