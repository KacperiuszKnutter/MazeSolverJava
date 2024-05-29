import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Scanner;



public class AppTUI {
    
    private Maze maze;
    public MazeData mazeData;


    private Maze.CoordinatesSetter coordinatesSetter;
    private Maze.LoadMaze mazeLoader;
    private Maze.MazeSolver mazeSolver;
    private Maze.MazeSaver mazeSaver;

    private int choice = 0;
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;



    public int printMenu()
    {

        int result = 0;
        boolean valid = false;
        Scanner scanner;
        String answer;

        while (!valid) {

            System.out.println("===== Main Menu =====");
            System.out.println("1. Load Maze");
            if(mazeData.isMazeSolved())
            {
                System.out.println("2. Save Maze");
            }
            else{
                System.out.println("2. Solve Maze");
            }
            if(mazeData.isMazeSolved())
            {
                System.out.println("3. Clear Maze");
            }
            else{
                System.out.println("3. Pick Endpoints");
            }
            System.out.println("4. Maze Info");
            System.out.println("5. Exit Program");

            scanner = new Scanner(System.in);
            System.out.print("Selected option from menu: ");
            answer = scanner.nextLine();

            

            try {
                result = Integer.parseInt(answer);
                if(0 < result && result <= 5)
                {
                    valid = true;
                }
                else{
                    System.out.println("Please enter number from 1 to 4");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return result;
        




    }



    public int getUserNumber(String question, int axis)
    {

        int maxValue;
        String axisName;

        if(axis == 1)
        {
            maxValue = mazeData.getRows();
            axisName = "Rows";
        }
        else
        {
            maxValue = mazeData.getCols();
            axisName = "Columns";
        }

        int result = 0;
        boolean valid = false;
        Scanner scanner;
        String answer;

        while (!valid) {


            scanner = new Scanner(System.in);
            System.out.print(question);
            answer = scanner.nextLine();

            

            try {
                result = Integer.parseInt(answer);
                
                if(0 < result && result <= maxValue)
                {
                    valid = true;
                }
                else{
                    System.out.println("You have to enter number from 0 to " + maxValue + " for " + axisName);
                };

            
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return result;
        




    }


    public AppTUI() {

        maze = new Maze();
        mazeData = maze.mazeData;
        


        coordinatesSetter = maze.new CoordinatesSetter();
        mazeLoader = maze.new LoadMaze();
        mazeSolver = maze.new MazeSolver();
        mazeSaver = maze.new MazeSaver();


        System.out.println("===== Welcome to our Maze Solver =====");

        while(choice != 5)
        {
            choice = printMenu();

            switch(choice)
            {
                case 1:
                    {
                        try {
                        System.out.println("Loading Maze...");
                        mazeLoader.loadMaze("maze-512x256.txt");
                        // mazeLoader.printMaze(mazeData.getMaze());
                        
                        } catch (IOException exception) {
                            System.out.println("Blad");
                        }



                        break;
                    }
                case 3:
                    {
                        if(!mazeData.isMazeSolved())
                        {
                            boolean isCorrect = false;

                            System.out.println("Wybierz położenie początkowe labiryntu");

                            while(!isCorrect)
                            {
                                startRow = getUserNumber("Podaj Wiersz punktu początkowego: ", 1);

                                startCol = getUserNumber("Podaj Kolumnę punktu początkowego: ", 2);

                                startRow -= 1;
                                startCol -= 1;

                                if(mazeData.getMazeElement(startRow, startCol) == ' ')
                                {
                                    isCorrect = true;
                                }
                                else
                                {
                                    System.out.println("W tym miejscu jest ściana");
                                }
                            }
                                
                            coordinatesSetter.setStartCoordinates(startCol, startRow);

                            isCorrect = false;

                            while(!isCorrect)
                            {
                                endRow = getUserNumber("Podaj Wiersz punktu końcowego: ", 1);

                                endCol = getUserNumber("Podaj Kolumnę punktu końcowego: ", 2);

                                endRow -= 1;
                                endCol -= 1;

                                if(mazeData.getMazeElement(endRow, endCol) == ' ')
                                {
                                    isCorrect = true;
                                }
                                else
                                {
                                    System.out.println("W tym miejscu jest ściana");
                                }
                            }
                            coordinatesSetter.setEndCoordinates(endCol, endRow);


                        }

                        break;
                    }
                case 2:
                    {
                        
                        mazeSolver.solveMaze();

                        try{
                            mazeSaver.saveMaze("pliczek.txt");
                        } 
                        catch (IOException e) 
                        {
                            e.printStackTrace();
                        
                        }
                        break;
                    }
                 case 4:
                    {

                        System.out.println("Właściwości Labiryntu");


                        System.out.println("Punkt początkowy " + mazeData.getMazeStartRow()+ " | " + mazeData.getMazeStartCol());
                        System.out.println("Punkt końcowy" + mazeData.getMazeEndRow()+ " | " + mazeData.getMazeEndCol());

                        System.out.println("Ilość Wierszy " + mazeData.getRows());
                        System.out.println("Ilość Kolumn" + mazeData.getCols());


                        mazeSolver.printMaze(mazeData.getMaze());


                        System.out.println("Długość Ścieżki " + mazeData.getPathLength());

                        break;
                    }
            }
        }
        

        




    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppTUI());
    }

    
    
}
