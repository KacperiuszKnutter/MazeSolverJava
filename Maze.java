import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Maze extends JPanel {


    private List<mazeObserver> observers = new ArrayList<>();

    public void addObserver(mazeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(mazeObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (mazeObserver observer : observers) {
            observer.mazeChanged();
        }
    }
    
    public void updateMaze(int Rows, int Cols, int AllCellSize, char [][] mazearr) {
        this.rows = Rows;
        this.cols = Cols;
        this.cellSize = AllCellSize;
        this.maze = mazearr;
        // notifyObservers();
    }
    
    public int rows = 0;
    public int cols = 0;
    public int cellSize;
    public char[][] maze;
    
    public Maze() {

    }


    
}