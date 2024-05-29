
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


    MazePanel mazePanel;
    MazeSidePanel mazeSidePanel;
    MazeNavbar mazeNavbar;

    //obiekt maze zawierający wszystkie operacje dotyczące plików i rozwiązywania labiryntu
    private Maze maze;
    public MazeData mazeData;

    public Observers()
    {
        maze = new Maze();
        mazeData = maze.mazeData;
    }


    public class MazePanel extends JPanel implements Observer
    {

        private int startRowValue;
        private int startColValue;
        private int endRowValue;
        private int endColValue;


        //Obraz przechowujący labirynt
        private BufferedImage mazeImage;
        private int clickMode = 0;

        
        //prywatne informacje o labiryncie obiektu mazePanel
        private int mazeRows;
        private int mazeCols;
        private char[][] mazeGrid;
        private int CELLSIZE = 10;


        //Gdzie kliknieto na panelu mazePanel
        private int x;
        private int y;

        


        public void resetMazeArray()
        {
            mazeGrid[startColValue][startRowValue] = ' ';
            mazeGrid[endColValue][endRowValue] = ' ';
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

            for (int i = 0; i < mazeGrid.length; i++) {
                for (int j = 0; j < mazeGrid[0].length; j++) {

                    switch (mazeGrid[i][j]) {
                        case 'X':
                            g2d.setColor(Color.BLACK);
                            break;
                        case ' ':
                            g2d.setColor(Color.WHITE);
                            break;
                        case 'B':
                            g2d.setColor(Color.BLUE);
                            break;
                        case 'V':
                            g2d.setColor(Color.PINK);
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
            mazeData = mazeData;
            this.mazeRows = mazeData.getRows();
            this.mazeCols = mazeData.getCols();
            this.mazeGrid = mazeData.getMaze();
            this.CELLSIZE = mazeData.getCellSize();



            this.createMazeImage();

            //Ustawenie Preferenced Size
            int panelWidth = mazeCols * CELLSIZE;
            int panelHeight = mazeRows * CELLSIZE;

            setPreferredSize(new Dimension(panelWidth, panelHeight));

            Maze.CoordinatesSetter coordinatesSetter = maze.new CoordinatesSetter(mazePanel, mazeNavbar, mazeSidePanel);

            //Reagowanie na Klikanie Panelu mazePanel
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    x = event.getX() / CELLSIZE;
                    y = event.getY() / CELLSIZE;

                    if (x < mazeCols && y < mazeRows) {
                        if (mazeGrid[y][x] == ' ' && clickMode == 1) 
                        {
                            mazeGrid[y][x] = 'P';
                            clickMode = 2;
                            startRowValue = y;
                            startColValue = x;
                            updateMazeImage();
                            coordinatesSetter.setStartCoordinates(startColValue, startRowValue);
                            revalidate();
                            repaint();

                        } else if (mazeGrid[y][x] == ' ' && clickMode == 2) 
                        {
                            mazeGrid[y][x] = 'K';
                            clickMode = 0;
                            endRowValue = y;
                            endColValue = x;
                            updateMazeImage();

                            maze.mazeData.setSolveButtonEnabled(1);

                            coordinatesSetter.setEndCoordinates(endColValue, endRowValue);
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
            
            mazePanel = this;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(mazeImage, 0, 0, this);
        }
    }
    public class MazeSidePanel extends JPanel implements Observer
    {
        private ArrayList<String> messages;
        public Border messagepadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        public JPanel mazeMessages;
        public String lastMessage;
        public Font largeFont = new Font("Arial", Font.BOLD, 30);
        public Font mediumFont = new Font("Arial", Font.BOLD, 18);
        public Font smallFont = new Font("Arial", Font.BOLD, 12);
        public Border titlepadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        private JPanel mazeInfo;
        private JLabel mazepropslabel;
        private JLabel mazeprops;
        private JLabel startRowField;
        private JLabel startColumnField;
        private JLabel endRowField;
        private JLabel endColumnField;
        final static int DEFAULTWIDTH = 1920;
        final static int DEFAULTHEIGHT = DEFAULTWIDTH - 940;
        private JLabel startLabel;
        public int startRowValue = 0;
        public int startColValue = 0;
        public int endRowValue = 0;
        public int endColValue = 0;
        private int mazeRows = 0;
        private int mazeCols = 0;

        public MazeSidePanel()

        {
            

            //Stworzenie side panelu
             
           setBackground(new Color(0x000C18));
           setPreferredSize(new Dimension(DEFAULTWIDTH / 6, DEFAULTHEIGHT));
           setBorder(new EmptyBorder(DEFAULTHEIGHT / 30, 0, DEFAULTHEIGHT / 30, 0));



            
        
            //Maze Info zawiera informacje o labiryncie
            mazeInfo = new JPanel(new FlowLayout());
            mazeInfo.setBackground(new Color(0x000C18));
            mazeInfo.setPreferredSize(new Dimension(DEFAULTWIDTH / 6, DEFAULTHEIGHT / 3));
            mazeInfo.setLayout(new BoxLayout(mazeInfo, BoxLayout.Y_AXIS));

            
            //Tytuł Panelu
            mazepropslabel = new JLabel("Maze Properties");
            mazepropslabel.setFont(largeFont);
            mazepropslabel.setBorder(titlepadding);
            mazepropslabel.setForeground(Color.WHITE);
            mazepropslabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            //Informacje o wielkości labiryntu
            mazeprops = new JLabel("Rows: " + mazeRows + " Cols: " + mazeCols);
            mazeprops.setAlignmentX(Component.CENTER_ALIGNMENT);
            Border propspadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
            mazeprops.setBorder(propspadding);
            mazeprops.setForeground(Color.WHITE);
            mazeprops.setFont(mediumFont);



            //Tytuł dla współrzędnych Punktu Startowego
            startLabel = new JLabel("Start Point");
            startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            startLabel.setForeground(Color.WHITE);
            startLabel.setFont(mediumFont);

            //Tytuł dla współrzędnych Punktu Końcowego
            JLabel endLabel = new JLabel("End Point");
            endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            endLabel.setForeground(Color.WHITE);
            endLabel.setFont(mediumFont);



            //4 Panele zawierające Tytuł i wartość dla każdej współrzędnej punktu początkowego i końcowego
            startRowField = new JLabel();
            startColumnField = new JLabel(); 
            endRowField = new JLabel();
            endColumnField = new JLabel();

            mazeprops.setText("Rows: " + mazeData.getRows() + " Cols: " + mazeData.getCols());
            startRowField.setText(mazeData.getMazeStartRow() + "");
            startColumnField.setText(mazeData.getMazeStartCol()  + "");
            endRowField.setText(mazeData.getMazeEndRow()  + "");
            endColumnField.setText(mazeData.getMazeEndCol() + "");



            startRowField.setPreferredSize(new Dimension(30, 20));

            JLabel startrowLabel = new JLabel("Row: ");
            startrowLabel.setPreferredSize(new Dimension(200, 50));
            startrowLabel.setAlignmentX(JLabel.LEFT);
            startrowLabel.setForeground(Color.WHITE);
            startrowLabel.setFont(mediumFont);

            JPanel startRow = new JPanel();
            startRow.setLayout(new FlowLayout(FlowLayout.LEFT));
            startRow.add(startrowLabel);
            startRow.add(startRowField);
            startRow.setBackground(new Color(0x000C18));






          
            startColumnField.setPreferredSize(new Dimension(100, 20));

            JLabel startcolLabel = new JLabel("Column: ");
            startcolLabel.setPreferredSize(new Dimension(200, 50));
            startcolLabel.setAlignmentX(JLabel.LEFT);
            startcolLabel.setForeground(Color.WHITE);
            startcolLabel.setFont(mediumFont);

            JPanel startCol = new JPanel();
            startCol.setLayout(new FlowLayout(FlowLayout.LEFT));
            startCol.add(startcolLabel);
            startCol.add(startColumnField);
            startCol.setBackground(new Color(0x000C18));






            endRowField.setPreferredSize(new Dimension(100, 20));

            JLabel endrowLabel = new JLabel("Row: ");
            endrowLabel.setPreferredSize(new Dimension(200, 50));
            endrowLabel.setAlignmentX(JLabel.LEFT);
            endrowLabel.setForeground(Color.WHITE);
            endrowLabel.setFont(mediumFont);

            JPanel endRow = new JPanel();
            endRow.setLayout(new FlowLayout(FlowLayout.LEFT));
            endRow.add(endrowLabel);
            endRow.add(endRowField);
            endRow.setBackground(new Color(0x000C18));





           
            endColumnField.setPreferredSize(new Dimension(100, 20));

            JLabel endcolLabel = new JLabel("Column: ");
            endcolLabel.setPreferredSize(new Dimension(200, 50));
            endcolLabel.setAlignmentX(JLabel.LEFT);
            endcolLabel.setForeground(Color.WHITE);
            endcolLabel.setFont(mediumFont);

            JPanel endCol = new JPanel();
            endCol.setLayout(new FlowLayout(FlowLayout.LEFT));
            endCol.add(endcolLabel);
            endCol.add(endColumnField);
            endCol.setBackground(new Color(0x000C18));
            
            



            mazeInfo.add(mazepropslabel);

            mazeInfo.add(mazeprops);

            mazeInfo.add(startLabel);
            
            mazeInfo.add(startRow);

            mazeInfo.add(startCol);

            mazeInfo.add(endLabel);

            mazeInfo.add(endRow);
            
            mazeInfo.add(endCol);


        
            


            JLabel messageLabel = new JLabel("Messages");
            messageLabel.setBorder(propspadding);
            messageLabel.setBorder(titlepadding);
            messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            messageLabel.setForeground(Color.WHITE);
            messageLabel.setFont(largeFont);


        

            mazeMessages = new JPanel(new FlowLayout());
            mazeMessages.setBackground(new Color(0x000C18));
            mazeMessages.setPreferredSize(new Dimension(DEFAULTWIDTH / 6, 1000));
            mazeMessages.setLayout(new BoxLayout(mazeMessages, BoxLayout.Y_AXIS));


            this.messages = mazeData.getMessages();
            for(int i = 0; i < this.messages.size(); i++)
            {
                JLabel message = new JLabel(this.messages.get(i));
                message.setAlignmentX(Component.CENTER_ALIGNMENT);
                message.setForeground(Color.WHITE);
                message.setFont(smallFont);
                message.setBorder(messagepadding);
                mazeMessages.add(message);
            }

        

            mazeMessages.setBorder(propspadding);
            JScrollPane scrollPanelMessages = new JScrollPane(mazeMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPanelMessages.setPreferredSize(new Dimension(DEFAULTWIDTH / 6, DEFAULTHEIGHT / 3));

        






            add(mazeInfo, BorderLayout.NORTH);
            add(messageLabel);
            add(scrollPanelMessages, BorderLayout.SOUTH);

            revalidate();
            repaint();
            mazeSidePanel = this;
        }

        @Override
        public void update(MazeData mazeData)
        {
            mazeData = mazeData;
            
            mazeprops.setText("Rows: " + mazeData.getRows() + " Cols: " + mazeData.getCols());
            startRowField.setText(mazeData.getMazeStartRow() + "");
            startColumnField.setText(mazeData.getMazeStartCol()  + "");
            endRowField.setText(mazeData.getMazeEndRow()  + "");
            endColumnField.setText(mazeData.getMazeEndCol() + "");

            mazeMessages.removeAll();
            this.messages = mazeData.getMessages();
            for(int i = 0; i < this.messages.size(); i++)
            {
                JLabel message = new JLabel(this.messages.get(i));
                message.setAlignmentX(Component.CENTER_ALIGNMENT);
                message.setForeground(Color.WHITE);
                message.setFont(smallFont);
                message.setBorder(messagepadding);
                mazeMessages.add(message);
            }
            
            mazeMessages.revalidate();
            mazeMessages.repaint();
            
        }



    }



    public class MazeNavbar extends JPanel implements Observer
    {

        final static int DEFAULTWIDTH = 1920;
        final static int DEFAULTHEIGHT = DEFAULTWIDTH - 940;

        
        public String InputFile = "";
    
        
        private String selectedFile;
        public JButton SelectPosinMaze;
        public JButton SolveMazeButton;

        public Font largeFont = new Font("Arial", Font.BOLD, 30);
        public Font mediumFont = new Font("Arial", Font.BOLD, 18);
        public Font smallFont = new Font("Arial", Font.BOLD, 12);
        public Border titlepadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);


        public MazeNavbar()
        {
            mazeNavbar = this;

            Maze.CoordinatesSetter coordinatesSetter = maze.new CoordinatesSetter(mazePanel, mazeNavbar, mazeSidePanel);
            Maze.LoadMaze mazeLoader = maze.new LoadMaze();
            Maze.MazeSolver mazeSolver = maze.new MazeSolver();



            //Przycisk wczytania labiryntu z pliku
            JButton loadMazeButton = new JButton("Wczytaj Labirynt");
            loadMazeButton.setFont(new Font("Arial", Font.BOLD, 20));
            loadMazeButton.setPreferredSize(new Dimension(300, 40));
            loadMazeButton.setBackground(new Color(0x000C18));
            loadMazeButton.setForeground(Color.WHITE);
            loadMazeButton.setMargin(new Insets(0, 10, 0, 0));

            //Po naciśnięciu na przycisk
            loadMazeButton.addActionListener(e -> {
                JFileChooser filechooser = new JFileChooser();
                filechooser.setCurrentDirectory(new File("C:\\Users\\"));

                int result = filechooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = filechooser.getSelectedFile().getAbsolutePath();
                    try 
                    {
                        maze.mazeData.setSolveButtonEnabled(0);
                        maze.mazeData.setSelectPosinEnabled(1);
                        mazeLoader.loadMaze(selectedFile, mazePanel, mazeNavbar, mazeSidePanel);

                    } catch (IOException exception) {
                        System.out.println("Something went wrong");
                    }
                }
            });



            //Przycisk Rozwiązania labiryntu
            SolveMazeButton = new JButton("Rozwiaz Labirynt!");
            SolveMazeButton.setFont(new Font("Arial", Font.BOLD, 20));
            SolveMazeButton.setBackground(new Color(0x000C18));
            SolveMazeButton.setForeground(Color.WHITE);
            SolveMazeButton.setPreferredSize(new Dimension(300, 60));
            SolveMazeButton.addActionListener(e -> {
                mazeSolver.solveMaze(mazePanel);

            });


            //Przycisk Wybrania Startu i Końca
            SelectPosinMaze = new JButton("Wybierz Start i Koniec!");
            SelectPosinMaze.setFont(new Font("Arial", Font.BOLD, 20));
            SelectPosinMaze.setPreferredSize(new Dimension(300, 60));
            SelectPosinMaze.setBackground(new Color(0x000C18));
            SelectPosinMaze.setForeground(Color.WHITE);
            

            SelectPosinMaze.addActionListener(e -> {

                
                maze.mazeData.setSolveButtonEnabled(0);
                mazePanel.resetMazeArray();
                coordinatesSetter.setStartCoordinates(0, 0);
                coordinatesSetter.setEndCoordinates(0, 0);
                
            });


            //Navbar  
            setBorder(new EmptyBorder(DEFAULTHEIGHT / 30, DEFAULTWIDTH / 30, DEFAULTHEIGHT / 30, DEFAULTWIDTH / 30));
            setPreferredSize(new Dimension(DEFAULTWIDTH - DEFAULTWIDTH / 6, DEFAULTHEIGHT / 8));
            setBackground(new Color(0x003BAE));
            add(loadMazeButton, BorderLayout.WEST);
            add(SolveMazeButton, BorderLayout.CENTER);
            add(SelectPosinMaze, BorderLayout.EAST);
            


            SolveMazeButton.setEnabled(mazeData.isSolveButtonEnabled());
            SelectPosinMaze.setEnabled(mazeData.isSelectPosinEnabled());


            revalidate();
            repaint();

            
        }


        
        @Override
        public void update(MazeData mazeData)
        {
            mazeData = mazeData;
            SolveMazeButton.setEnabled(mazeData.isSolveButtonEnabled());
            SelectPosinMaze.setEnabled(mazeData.isSelectPosinEnabled());
        }
    }



    // public class MazeMessages extends JPanel implements Observer
    // {
        
    // }   
    // public class MazeInfo extends JPanel implements Observer
    // {
        
    // }

        

}
