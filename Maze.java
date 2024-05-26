

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {

    private MazeData mazeData;
    private char[][] maze;

    public Maze() {
        mazeData = new MazeData();
    }

    public class LoadMaze extends JPanel {

        private FileRead czytaczpliku;
        private List<Observer> observers = new ArrayList<>();

        public LoadMaze() {
            // Constructor logic if needed
        }

        public void loadMaze(String selectedFile, Observer mazePanel) throws IOException {
            addObserver(mazePanel);

            try {
                czytaczpliku = new FileRead();
                maze = czytaczpliku.wczytajLabirynt(selectedFile);
                mazeData.setMaze(maze);
                mazeData.setRows(czytaczpliku.getHeight());
                mazeData.setCols(czytaczpliku.getWidth());
                mazeData.setCellSize(10);
                notifyObservers();
            } catch (IOException exception) {
                System.out.println("Blad");
            }
        }

        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(mazeData);
            }
        }
    }

   
}
