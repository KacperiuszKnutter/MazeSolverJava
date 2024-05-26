import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazeData {

    private int rows = 0;
    private int cols = 0;
    private int cellSize = 10;
    private char[][] maze;

    private int mazeStartRow;
    private int mazeStartCol;
    private int mazeEndRow;
    private int mazeEndCol;


    public void updateMaze(int Rows, int Cols, int CellSize, char [][] mazearr) {
        this.rows = Rows;
        this.cols = Cols;
        this.cellSize = CellSize;
        this.maze = mazearr;
    }

    //GETTERY
    public int getRows()
    {
        return this.rows;
    }

    public int getCols()
    {
        return this.cols;
    }
    
    public int getCellSize()
    {
        return this.cellSize;
    }

    public char[][] getMaze()
    {
        return this.maze;
    }

    //SETTERY
    public void setRows(int rows)
    {
        this.rows = rows;
    }

    public void setCols(int cols)
    {
        this.cols = cols;
    }
    
    public void setCellSize(int cellSize)
    {
        this.cellSize = cellSize;
    }

    public void setMaze(char [][] maze)
    {
        this.maze = maze;
    }
    public void resetMazeArray(int startRowValue, int startColValue, int endRowValue, int endColValue)
    {
        this.maze[startRowValue][startColValue] = ' ';
        this.maze[endRowValue][endColValue] = ' ';
    }


    
    // Setters and Getters
    public int getMazeStartRow() {
        return mazeStartRow;
    }

    public void setMazeStartRow(int mazeStartRow) {
        this.mazeStartRow = mazeStartRow;
    }

    public int getMazeStartCol() {
        return mazeStartCol;
    }

    public void setMazeStartCol(int mazeStartCol) {
        this.mazeStartCol = mazeStartCol;
    }

    public int getMazeEndRow() {
        return mazeEndRow;
    }

    public void setMazeEndRow(int mazeEndRow) {
        this.mazeEndRow = mazeEndRow;
    }

    public int getMazeEndCol() {
        return mazeEndCol;
    }

    public void setMazeEndCol(int mazeEndCol) {
        this.mazeEndCol = mazeEndCol;
    }

    
    public MazeData() {

    }

}