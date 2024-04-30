import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;

public class Maze {
    //Tylko do testow
    private int[][] maze ={{0,0,0,0,0,0,0,0,0,0},
                           {1,1,1,1,1,1,0,0,1,0},
                           {0,0,0,1,0,1,1,1,1,0},
                           {0,1,1,1,0,0,1,0,0,0},
                           {0,1,0,1,1,1,1,1,1,0},
                           {0,1,0,0,0,0,1,0,1,0},
                           {0,1,0,1,0,0,1,0,1,0},
                           {0,1,1,1,1,1,1,0,1,0},
                           {0,1,0,0,1,0,0,0,1,1},
                           {0,0,0,0,0,0,0,0,0,0}
                          }; 
    private int rows = 10;
    private int cols = 10;

    
    public Maze() {
        
    }

    //Ta metoda bedzie dostawac tez nazwe pliku i sprawdzac czy rozserzenie jest bin czy txt, w zaleznosci od rozszerzenia pliku wywola metode readFromBinary albo readFromTxt i na jej podstawie malowac labirynt
    public void PaintMaze(int cellSize, JPanel panel) {
        panel.removeAll();
        JPanel mazeP = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mazeP.setBackground(new Color(0x003BAE));
        mazeP.setSize(rows*cellSize,cols*cellSize);
        mazeP.setLayout(new GridLayout(rows,cols));
        panel.add(mazeP);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JPanel cell = new JPanel();
                if (maze[i][j] == 0) { 
                    cell.setBackground(Color.BLACK);
                } else if (maze[i][j] == 1){ 
                    cell.setBackground(Color.WHITE);
                }
                
                cell.setPreferredSize(new Dimension(cellSize, cellSize));
                mazeP.add(cell);
            }
        }
        
        panel.revalidate(); 
        panel.repaint(); 
    }

    //Do czytania informacji Binarnie
    public void readBinaryMaze(File file) {
      
    }
    //Do czytania labiryntu z plikow txt
    public void readTextMaze(JPanel panel) {
     
    }
}
