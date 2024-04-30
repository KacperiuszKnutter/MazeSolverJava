import javax.swing.*;
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



public class AppGUI extends JFrame {

    final static int DEFAULTWIDTH = 1920;
    final static int DEFAULTHEIGHT = DEFAULTWIDTH - 840;
    final static int CELLSIZE = 20;
    private JTextField fileNameTextField;
    public String CurrFileExtention = "";
    public String InputFile = "";

    //Radio Buttony
    JRadioButton BinButton = new JRadioButton(".bin");
    JRadioButton TxtButton = new JRadioButton(".txt");



    //Zaznaczono RadioButton z rozszerzeniem bin lub txt, ustawiamy file ext na bin lub txt
    ActionListener FileTypeChanger = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
                if(e.getSource()== BinButton){
                    CurrFileExtention = ".bin";
                    System.out.println(CurrFileExtention);
                }
                else if(e.getSource()== TxtButton){
                    CurrFileExtention = ".txt";
                    System.out.println(CurrFileExtention);
                }
        }
    };
    //Szukamy pliku o takiej nazwie:
    ActionListener fileInfo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            InputFile = fileNameTextField.getText() + CurrFileExtention;
            System.out.println(InputFile);
        }
    };

    public AppGUI() {

        //Ramka
        JFrame frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
        frame.getContentPane().setBackground(new Color(0x003BAE));
        frame.setLayout(new BorderLayout());

        //Pole z nazwa pliku
        JLabel fileName = new JLabel("|| Podaj nazwe pliku:");
        fileName.setForeground(Color.WHITE);
        fileName.setFont(new Font("Mv Boli",Font.BOLD, 20));
        fileNameTextField = new JTextField(20);
        fileNameTextField.setText("Twoja nazwa pliku");
        fileNameTextField.addActionListener(fileInfo);
        fileNameTextField.setFont(new Font("Mv Boli",Font.BOLD, 20));

        //Trzeba dodac RadioButtony do grupy zeby mozna bylo zaznaczyc tylko 1
        JLabel FileExtention = new JLabel("|| Wybierz rozszerzenie pliku:");
        FileExtention.setForeground(Color.WHITE);
        FileExtention.setFont(new Font("Mv Boli",Font.BOLD, 20));
        ButtonGroup FileExtentionTypes = new ButtonGroup();
        FileExtentionTypes.add(BinButton);
        FileExtentionTypes.add(TxtButton);
        BinButton.setBackground(new Color(0x000C18));
        TxtButton.setBackground(new Color(0x000C18));
        BinButton.setForeground(Color.WHITE);
        TxtButton.setForeground(Color.WHITE);
        BinButton.setFont(new Font("Mv Boli",Font.BOLD, 20));
        TxtButton.setFont(new Font("Mv Boli",Font.BOLD, 20));
        BinButton.addActionListener(FileTypeChanger);
        TxtButton.addActionListener(FileTypeChanger);

        
        //Menu Na Dole
        JPanel solverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        solverPanel.setBackground(new Color(0x000C18));
        solverPanel.setBounds(0,DEFAULTHEIGHT-DEFAULTHEIGHT/8,DEFAULTWIDTH,DEFAULTHEIGHT/16);
        frame.add(solverPanel, BorderLayout.SOUTH);

        //Panel na srodku ktory wyswietla namalowany labirynt
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(0x003BAE));
        centerPanel.setBounds(0,DEFAULTHEIGHT/16,DEFAULTWIDTH,DEFAULTHEIGHT-DEFAULTHEIGHT/8);
        frame.add(centerPanel,BorderLayout.CENTER);

        //Przycisk do generowania labiryntu w zaleznosci od rodzaju
        JButton DrawMazeButton = new JButton("Generuj Labirynt");
        DrawMazeButton.setFont( new Font("Mv Boli",Font.BOLD, 20));
        DrawMazeButton.setPreferredSize(new Dimension(300, 40));
        DrawMazeButton.setBackground(new Color(0x003BAE));
        DrawMazeButton.setForeground(Color.WHITE);
        DrawMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Maze mazeBuilder = new Maze();
                mazeBuilder.PaintMaze(CELLSIZE,centerPanel);
            }
        });

        //Przycisk do generowania labiryntu w zaleznosci od rodzaju + trzeba dodac metody potem
        JButton SolveMazeButton = new JButton("Znajdz najkrotsza sciezke!");
        SolveMazeButton.setFont( new Font("Mv Boli",Font.BOLD, 20));
        SolveMazeButton.setPreferredSize(new Dimension(300, 40));
        SolveMazeButton.setBackground(new Color(0x003BAE));
        SolveMazeButton.setForeground(Color.WHITE);

        //Przycisk do zaznaczania pozycji startowych w labiryncie + trzeba dodac metody potem
        JButton SelectPosinMaze = new JButton("Wybierz Start i Koniec!");
        SelectPosinMaze.setFont( new Font("Mv Boli",Font.BOLD, 20));
        SelectPosinMaze.setPreferredSize(new Dimension(300, 40));
        SelectPosinMaze.setBackground(new Color(0x003BAE));
        SelectPosinMaze.setForeground(Color.WHITE);

        //Menu Na Gorze
        JPanel SetupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        SetupPanel.setBackground(new Color(0x000C18));
        SetupPanel.setBounds(0,0,DEFAULTWIDTH,DEFAULTHEIGHT/16);
        SetupPanel.add(FileExtention);
        SetupPanel.add(BinButton);
        SetupPanel.add(TxtButton);
        SetupPanel.add(fileName);
        SetupPanel.add(fileNameTextField);
        SetupPanel.add(DrawMazeButton);
        frame.add(SetupPanel,BorderLayout.NORTH);

        //Dodajemy przyciski do Panelu dolnego
        solverPanel.add(SolveMazeButton);
        solverPanel.add(SelectPosinMaze);
        

        
        //Ustawiamy ramke na widoczna 
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppGUI());
    }
}