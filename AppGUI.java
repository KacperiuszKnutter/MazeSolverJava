import javax.swing.*;
import java.awt.*;

import javax.swing.border.Border;



public class AppGUI extends JFrame {


    final static int DEFAULTWIDTH = 1920;
    final static int DEFAULTHEIGHT = DEFAULTWIDTH - 940;

    

    public Font largeFont = new Font("Arial", Font.BOLD, 30);
    public Font mediumFont = new Font("Arial", Font.BOLD, 18);
    public Font smallFont = new Font("Arial", Font.BOLD, 12);
    public Border titlepadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    
    



    public AppGUI() {

        

        Observers observers = new Observers();

        Observers.MazePanel mazePanel = observers.new MazePanel();
        Observers.MazeSidePanel mazeSidePanel = observers.new MazeSidePanel();
        Observers.MazeNavbar mazeNavbar = observers.new MazeNavbar();


        JScrollPane scrollPane = new JScrollPane(mazePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      
        //STWORZENIE FRAME'A I NADANIE POCZATKOWYCH WARTOŚCI
        JFrame frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new BorderLayout());



        // CenterPanel zawiera gui labiryntu
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.add(scrollPane);


        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(mazeNavbar, BorderLayout.NORTH);
        frame.add(mazeSidePanel, BorderLayout.EAST);

       

        frame.setVisible(true);




    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppGUI());
    }
    
}
