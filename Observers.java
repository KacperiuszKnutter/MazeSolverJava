
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
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

public class Observers {

    public static class MazePanel extends JPanel implements Observer
    {

        public int startRowValue = 0;
        public int startColValue = 0;
        public int endRowValue = 0;
        public int endColValue = 0;


        //Obraz przechowujący labirynt
        private BufferedImage mazeImage;
        private int clickMode = 0;

        
        //prywatne informacje o labiryncie obiektu mazePanel
        private int mazeRows = 0;
        private int mazeCols = 0;
        private char[][] maze;
        private int CELLSIZE = 10;


        //Gdzie kliknieto na panelu mazePanel
        private int x;
        private int y;

        


        public void resetMazeArray()
        {
            maze[startRowValue][startColValue] = ' ';
            maze[endRowValue][endColValue] = ' ';
            clickMode = 1;
            updateMazeImage();
            revalidate();
            repaint();
        }

        private void createMazeImage() 
        {
            int imageWidth = mazeCols * CELLSIZE;
            int imageHeight = mazeRows * CELLSIZE;
            mazeImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            this.updateMazeImage();
        }

        private void updateMazeImage() {

            Graphics2D g2d = mazeImage.createGraphics();

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {

                    switch (maze[i][j]) {
                        case 'X':
                            g2d.setColor(Color.BLACK);
                            break;
                        case ' ':
                            g2d.setColor(Color.WHITE);
                            break;
                        case 'P':
                            g2d.setColor(Color.ORANGE);
                            break;
                        case 'K':
                            g2d.setColor(Color.RED);
                            break;
                        default:
                            g2d.setColor(Color.WHITE);
                    }
                    g2d.fillRect(j * CELLSIZE, i * CELLSIZE, CELLSIZE, CELLSIZE);
                }
            }
            g2d.dispose();
        }


        @Override
        public void update(MazeData mazeData)
        {
            this.mazeRows = mazeData.getRows();
            this.mazeCols = mazeData.getCols();
            this.maze = mazeData.getMaze();
            this.CELLSIZE = mazeData.getCellSize();



            this.createMazeImage();

            //Ustawenie Preferenced Size
            int panelWidth = mazeCols * CELLSIZE;
            int panelHeight = mazeRows * CELLSIZE;

            setPreferredSize(new Dimension(panelWidth, panelHeight));

            //Reagowanie na Klikanie Panelu mazePanel
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    x = event.getX() / CELLSIZE;
                    y = event.getY() / CELLSIZE;

                    if (x < mazeCols && y < mazeRows) {
                        if (maze[y][x] == ' ' && clickMode == 1) 
                        {
                            maze[y][x] = 'P';
                            clickMode = 2;
                            startRowValue = y;
                            startColValue = x;
                            updateMazeImage();
                            revalidate();
                            repaint();

                        } else if (maze[y][x] == ' ' && clickMode == 2) 
                        {
                            maze[y][x] = 'K';
                            clickMode = 0;
                            endRowValue = y;
                            endColValue = x;
                            updateMazeImage();
                            revalidate();
                            repaint();
                        }
                    }
                }
            });

            revalidate();
            repaint();
        
        }

        public MazePanel() 
        {
            //Na początku nie ma żadnych danych wiec ustawiamy defaultowe
            this.mazeRows = 0;
            this.mazeCols = 0;
            this.CELLSIZE = 10;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(mazeImage, 0, 0, this);
        }
    }








    // public class MazeMessages extends JPanel implements Observer
    // {
        
    // }   
    // public class MazeInfo extends JPanel implements Observer
    // {
        
    // }

        

}
