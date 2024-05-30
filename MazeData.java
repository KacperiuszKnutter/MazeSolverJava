import java.util.ArrayList;

public class MazeData {

    private int rows = 0;
    private int cols = 0;
    private int cellSize = 10;
    private char[][] maze;

    private int mazeStartRow;
    private int mazeStartCol;
    private int mazeEndRow;
    private int mazeEndCol;
    private int pathLength;
    private boolean mazeSolved;

    private int enableSolveMaze;
    private int enableSelectPosin;



    private ArrayList<String> messages;



    public void setPathLength(int pathlength)
    {
        this.pathLength = pathlength;
    }

    public int getPathLength()
    {
        return this.pathLength;
    }





    // Gettery i Settery dla messages
    public ArrayList<String> getMessages() {
        return new ArrayList<>(messages); 
    }



    public void setMessages(ArrayList<String> messages) {
        this.messages = new ArrayList<>(messages); 
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void clearMessages() {
        this.messages.clear();
    }



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
    public void resetMazeArray()
    {
        for(int i = 0; i < getRows(); i++)
        {
            for(int j = 0; j < getCols(); j++)
            {
                if(this.maze[i][j] != 'X')
                {
                    this.maze[i][j] = ' ';
                }
            }
        }
        clearMessages();
        addMessage("Maze was Cleared");
    }

    public void setMazeElement(int elRow, int elCol, char elValue)
    {
        this.maze[elRow][elCol] = elValue;
    }

    public char getMazeElement(int elRow, int elCol)
    {
        return this.maze[elRow][elCol];
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

    public void setSolveButtonEnabled(int choice)
    {
        this.enableSolveMaze = choice;
    }

    public void setSelectPosinEnabled(int choice)
    {
        this.enableSelectPosin = choice;
    }

    public boolean isSolveButtonEnabled()
    {
        if(this.enableSolveMaze == 0 )
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean isSelectPosinEnabled()
    {
        if(this.enableSelectPosin == 0 )
        {
            return false;
        }
        else
        {
            return true;
        }
    }



    public void setPathinMaze(int y, int x)
    {
        this.maze[y][x] = 'B' ;//Znak sciezki dowolnie
    }

    public boolean isGridPathinMaze(int x, int y)
    {
        if(this.maze[y][x] == 'B') {
            return true;
        }
        return false;
    }

    public void setVisitedMaze(int x, int y)
    {
        this.maze[y][x] = 'V' ;//Oznaczenie ze odwiedzilismy
    }

    public boolean isGridVisitedMaze(int x, int y)
    {
        if(this.maze[y][x] == 'V') {
            return true;
        }
        return false;
    }

    public boolean isPosAvailable(int x, int y)
    {
        if(this.maze[y][x] == ' ' ||this.maze[y][x] == 'P' || this.maze[y][x] == 'K' ) {
            return true;
        }
        return false;
    }
    
    public void setMazeSolved(boolean isMazeSolved)
    {
        this.mazeSolved = isMazeSolved;
    }

    public boolean isMazeSolved()
    {
        return this.mazeSolved;
    }


    public MazeData() {

        this.rows = 0;
        this.cols = 0;
        this.cellSize = 10;
        this.mazeStartRow = 0;
        this.mazeStartCol = 0;
        this.mazeEndRow = 0;
        this.mazeEndCol = 0;
        this.messages = new ArrayList<>();
        this.messages.add("Welcome to our Program!");
        this.messages.add("Please load a Maze!");
        this.enableSolveMaze = 0;
        this.enableSelectPosin = 0;
        this.mazeSolved = false;
    }

}