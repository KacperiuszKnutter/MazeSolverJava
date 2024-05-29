

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Maze {

    public MazeData mazeData;
    

    public Maze() {
        mazeData = new MazeData();
    }

    public class CoordinatesSetter
    {

        private List<Observer> observers = new ArrayList<>();

        private int coordinateType;

        public CoordinatesSetter(Observer mazePanel, Observer mazeNavbar, Observer mazeSidePanel)
        {
            addObserver(mazePanel);
            addObserver(mazeNavbar);
            addObserver(mazeSidePanel);
        }

        public void setStartCoordinates(int startColValue, int startRowValue)
        {
            
            mazeData.setMazeStartRow(startRowValue);
            mazeData.setMazeStartCol(startColValue);
            this.coordinateType = 1;
            notifyObservers();
        }


        public void setEndCoordinates(int endColValue, int endRowValue)
        {
            mazeData.setMazeEndRow(endRowValue);
            mazeData.setMazeEndCol(endColValue);
            this.coordinateType = 0;
            notifyObservers();
        }

        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        public void notifyObservers() {
            if(this.coordinateType == 1)
            {
                mazeData.addMessage("Start Point Chosen");
            }
            else
            {
                mazeData.addMessage("End Point Chosen");
            }
            
            for (Observer observer : observers) {
                observer.update(mazeData);
            }
        }


    }

    public class LoadMaze{

        private FileRead czytaczpliku;
        private List<Observer> observers = new ArrayList<>();
        private char[][] maze;

        public LoadMaze() {
        }

        public void loadMaze(String selectedFile, Observer mazePanel, Observer mazeNavbar, Observer mazeSidePanel) throws IOException {

            addObserver(mazePanel);
            addObserver(mazeNavbar);
            addObserver(mazeSidePanel);

            try {
                czytaczpliku = new FileRead();
                maze = czytaczpliku.wczytajLabirynt(selectedFile);
                mazeData.setMaze(maze);
                mazeData.setRows(czytaczpliku.getHeight());
                mazeData.setCols(czytaczpliku.getWidth());
                mazeData.setCellSize(10);
                mazeData.setSelectPosinEnabled(1);



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
            mazeData.addMessage("Maze was successfuly loaded");
            for (Observer observer : observers) {
                observer.update(mazeData);
            }
        }
    }
    public class MazeSolver {


        public MazeSolver()
        {
    
        }

        private List<Observer> observers = new ArrayList<>();
        private Node [][] Nodes; 

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
        
        private void initializeGraph(){
            //Tworzymy Wierzcholki tylko tam gdzie nie mamy ścian
            Nodes = new Node[mazeData.getRows()][mazeData.getCols()];


            for (int i = 0; i < mazeData.getRows(); i++) 
            {
                for (int j = 0; j < mazeData.getCols(); j++)
                {
                    Nodes[i][j] = new Node(j, i);
                    if(mazeData.getMazeElement(i, j) == 'X'){
                        Nodes[i][j].setEmpty(true);
                    }
                    else
                    {
                        Nodes[i][j].setEmpty(false);
                    }
                    
                }
            }
        }

        //Tutaj dodatkowo informujemy juz o sąsiadach (mozliwych kierunkach) dla wierzcholkow
        public void buildGraph() {

            int up;
            int down;
            int left;
            int right;

            for (int i = 0; i < Nodes.length; i++) {
                for (int j = 0; j < Nodes[i].length; j++)
                {
                    if (!Nodes[i][j].isEmpty()) 
                    {
                        Node leftNode = null;
                        Node upperNode = null;
                        Node rightNode = null;
                        Node downNode = null;
                        
                        up = i - 1;
                        down = i + 1;
                        left = j - 1;
                        right = j + 1;

                        if(left > 0) 
                        {
                            leftNode = Nodes[i][left];
                            // if (leftNode != null)
                            // System.out.println("Lewo "+ leftNode.getXPos() + " " + leftNode.getYPos());
                        }
                        if(up > 0)
                        {
                            upperNode = Nodes[up][j];
                            // if (upperNode != null)
                            // System.out.println("Gora "+ upperNode.getXPos() + " " + upperNode.getYPos());
                        } 
                        if(right < mazeData.getCols() - 1)
                        {
                            rightNode =  Nodes[i][right];
                            // if (rightNode != null)
                            // System.out.println("Prawo "+ rightNode.getXPos() + " " + rightNode.getYPos());
                        }
                        if(down < mazeData.getRows() - 1) 
                        {
                            downNode =  Nodes[down][j];
                            // if (downNode != null)
                            // System.out.println("Dol "+ downNode.getXPos() + " " + downNode.getYPos());

                        }
                        
                    

                        Nodes[i][j].setDirections(leftNode, upperNode, rightNode, downNode);
                    }
                }	
            }
        }

        public void traversal(Node endN, Node[][] prevN){
            Node newPath = endN;

            while(newPath != null){
                newPath = prevN[newPath.getYPos()][newPath.getXPos()];
                if(newPath != null) {
                    mazeData.setPathinMaze(newPath.getYPos(), newPath.getXPos());
                }
            }
        }


        public void BFS(Node startNode, Node endNode, int graphLenght, int graphWidth){

            Queue<Node> queue= new LinkedList<>();
            //Musimy zapisywac w tablicy poprzednie wierzcholki zeby moc ich uzyc przy powrocie
            Node[][] previousNodes = new Node[graphLenght][graphWidth];

            Node currNode;

            queue.add(startNode);

            //System.out.println("START "+ startNode.getYPos() + " " + startNode.getXPos());

            while(!queue.isEmpty()){

                
                // System.out
                currNode = queue.poll();
                if (currNode.EndFound(mazeData)){
                    break;
                }

                if(!currNode.wasVisited(mazeData)){
                    mazeData.setVisitedMaze(currNode.getXPos(),currNode.getYPos());
                    for(Node neighbourNode: currNode.getAdjecentNodes(mazeData)){
                        if (neighbourNode != null && !neighbourNode.wasVisited(mazeData))
                        {
                            //System.out.println("Punkt " + neighbourNode.getYPos() + " " + neighbourNode.getXPos());
                            queue.add(neighbourNode);
                            previousNodes[neighbourNode.getYPos()][neighbourNode.getXPos()] = currNode;
                        }
                    }
                }
                //System.out.println("==== Nastepna Tura ====");

            }
            traversal(endNode,previousNodes);
        }
        

        public void solveMaze(Observer mazePanel){
            addObserver(mazePanel);
            initializeGraph();
            buildGraph();
            BFS(Nodes[mazeData.getMazeStartRow()][mazeData.getMazeStartCol()], Nodes[mazeData.getMazeEndRow()][mazeData.getMazeEndCol()], mazeData.getRows(), mazeData.getCols());    
            notifyObservers();     
            
        }

        public static void printMaze(char[][] lab){
        for(int i=0; i <lab.length; i++){
            for(int j = 0; j< lab[i].length; j++){
                System.out.print(lab[i][j]);
            }
            System.out.println();
        }
    }
        
    }

   
}
