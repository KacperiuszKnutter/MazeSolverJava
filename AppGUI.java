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



public class AppGUI extends JFrame {

    final static int DEFAULTWIDTH = 1920;
    final static int DEFAULTHEIGHT = DEFAULTWIDTH - 940;
    private int mazeRows = 0;
    private int mazeCols = 0;
    private int x;
    private int y;
    public String InputFile = "";
    public ArrayList<String> messages = new ArrayList<>();
    private FileRead czytaczpliku;
    private char[][] maze;
    private int CELLSIZE = 10;
    private String selectedFile;
    public JButton SelectPosinMaze;
    public JButton SolveMazeButton;
    public Border messagepadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    public JPanel mazeMessages;
    public String lastMessage;
    public Font largeFont = new Font("Arial", Font.BOLD, 30);
    public Font mediumFont = new Font("Arial", Font.BOLD, 18);
    public Font smallFont = new Font("Arial", Font.BOLD, 12);
    public Border titlepadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    private JPanel sidePanel;
    private JPanel mazeInfo;
    private JLabel mazepropslabel;
    private JLabel mazeprops;
    private JLabel startRowField;
    private JLabel startColumnField;
    private JLabel endRowField;
    private JLabel endColumnField;
    private JLabel startLabel;
    private JPanel centerPanel;
    private MazePanel mazePanel;
    private BufferedImage mazeImage;

    public int startRowValue = 0;
    public int startColValue = 0;
    public int endRowValue = 0;
    public int endColValue = 0;
    private int clickMode = 0;

    private Maze mazeData;

    public void printMessageToGui() {
        if (!messages.isEmpty()) {
            lastMessage = messages.get(messages.size() - 1);
        } else {
            lastMessage = "The messages list is empty";
        }
        JLabel message = new JLabel(lastMessage);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        message.setForeground(Color.WHITE);
        message.setFont(smallFont);
        message.setBorder(messagepadding);
        mazeMessages.add(message);
        mazeMessages.revalidate();
        mazeMessages.repaint();
    }

    private void createMaze(int cellSize, char[][] maze) {
        mazeData.updateMaze(czytaczpliku.getWidth(), czytaczpliku.getHeight(), cellSize, maze);
        createMazeImage();

        mazePanel = new MazePanel();
        JScrollPane scrollPane = new JScrollPane(mazePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerPanel.removeAll();
        centerPanel.add(scrollPane);
        mazeChanged();
    }

    private void createMazeImage() {
        int imageWidth = mazeData.cols * mazeData.cellSize;
        int imageHeight = mazeData.rows * mazeData.cellSize;
        mazeImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        updateMazeImage();
    }

    private class MazePanel extends JPanel {
        public MazePanel() {
            System.out.println(mazeData.cols);
            System.out.println(mazeData.rows);
            int panelWidth = mazeData.cols * mazeData.cellSize;
            int panelHeight = mazeData.rows * mazeData.cellSize;
            setPreferredSize(new Dimension(panelWidth, panelHeight));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    x = event.getX() / mazeData.cellSize;
                    y = event.getY() / mazeData.cellSize;

                    if (x < mazeData.cols && y < mazeData.rows) {
                        if (mazeData.maze[y][x] == ' ' && clickMode == 1) {
                            mazeData.maze[y][x] = 'P';
                            clickMode = 2;
                            startRowValue = y;
                            startColValue = x;
                            updateMazeImage();
                            messages.add("Wybrano Początek x - "+ x + "  ||  y - " + y);
                            printMessageToGui();
                            mazeChanged();
                        } else if (mazeData.maze[y][x] == ' ' && clickMode == 2) {
                            mazeData.maze[y][x] = 'K';
                            clickMode = 0;
                            endRowValue = y;
                            endColValue = x;
                            updateMazeImage();
                            messages.add("Wybrano Koniec x - "+ x + "  ||  y - " + y);
                            printMessageToGui();
                            mazeChanged();
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(mazeImage, 0, 0, this);
        }
    }

    private void updateMazeImage() {
        Graphics2D g2d = mazeImage.createGraphics();

        for (int i = 0; i < mazeData.maze.length; i++) {
            for (int j = 0; j < mazeData.maze[0].length; j++) {
                switch (mazeData.maze[i][j]) {
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
                g2d.fillRect(j * mazeData.cellSize, i * mazeData.cellSize, mazeData.cellSize, mazeData.cellSize);
            }
        }
        g2d.dispose();
    }

    public void mazeChanged() {
        mazePanel.revalidate();
        mazePanel.repaint();
        sidePanel.revalidate();
        sidePanel.repaint();
        refreshMazeInfo();
    }

    public void refreshMazeInfo() {
        mazeRows = czytaczpliku.getHeight();
        mazeCols = czytaczpliku.getWidth();
        mazeprops.setText("Rows: " + mazeRows + " Cols: " + mazeCols);
        startRowField.setText(Integer.toString(startRowValue));
        startColumnField.setText(Integer.toString(startColValue));
        endRowField.setText(Integer.toString(endRowValue));
        endColumnField.setText(Integer.toString(endColValue));
        mazeInfo.revalidate();
        mazeInfo.repaint();
    }

    public AppGUI() {
        mazeData = new Maze();

        mazeMessages = new JPanel(new FlowLayout());
        mazeMessages.setBackground(new Color(0x000C18));
        mazeMessages.setPreferredSize(new Dimension(DEFAULTWIDTH / 6, 1000));
        mazeMessages.setLayout(new BoxLayout(mazeMessages, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel("Messages");
        messageLabel.setBorder(titlepadding);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(largeFont);

        messages.add("Welcome to our Program!");
        printMessageToGui();
        messages.add("Please load a Maze!");
        printMessageToGui();

        JFrame frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new BorderLayout());

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton drawMazeButton = new JButton("Wczytaj Labirynt");
        drawMazeButton.setFont(new Font("Arial", Font.BOLD, 20));
        drawMazeButton.setPreferredSize(new Dimension(300, 40));
        drawMazeButton.setBackground(new Color(0x000C18));
        drawMazeButton.setForeground(Color.WHITE);
        drawMazeButton.setMargin(new Insets(0, 10, 0, 0));
        drawMazeButton.addActionListener(e -> {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setCurrentDirectory(new File("C:\\Users\\"));

            int result = filechooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = filechooser.getSelectedFile().getAbsolutePath();
                try {
                    czytaczpliku = new FileRead();
                    messages.add("Maze Loaded Successfully!");
                    printMessageToGui();
                    maze = czytaczpliku.wczytajLabirynt(selectedFile);
                    createMaze(CELLSIZE, maze);
                    refreshMazeInfo();
                    SelectPosinMaze.setEnabled(true);
                } catch (IOException exception) {
                    messages.add("Something went wrong while reading the text file!");
                    printMessageToGui();
                }
            }
        });

        SolveMazeButton = new JButton("Rozwiaz Labirynt!");
        SolveMazeButton.setFont(new Font("Arial", Font.BOLD, 20));
        SolveMazeButton.setBackground(new Color(0x000C18));
        SolveMazeButton.setForeground(Color.WHITE);
        SolveMazeButton.setPreferredSize(new Dimension(300, 60));
        SolveMazeButton.setEnabled(false);

        SelectPosinMaze = new JButton("Wybierz Start i Koniec!");
        SelectPosinMaze.setFont(new Font("Arial", Font.BOLD, 20));
        SelectPosinMaze.setPreferredSize(new Dimension(300, 60));
        SelectPosinMaze.setBackground(new Color(0x000C18));
        SelectPosinMaze.setForeground(Color.WHITE);
        SelectPosinMaze.setEnabled(false);

        SelectPosinMaze.addActionListener(e -> {
           
            mazeData.maze[startRowValue][startColValue] = ' ';
            mazeData.maze[endRowValue][endColValue] = ' ';
            clickMode = 1;
            updateMazeImage();
            mazeChanged();
            messages.add("Picking Start and finish...");
            mazeMessages.removeAll();
            printMessageToGui();
            
        });

        JPanel setupPanel = new JPanel(new BorderLayout());
        setupPanel.setBorder(new EmptyBorder(DEFAULTHEIGHT / 30, DEFAULTWIDTH / 30, DEFAULTHEIGHT / 30, DEFAULTWIDTH / 30));
        setupPanel.setBackground(new Color(0x003BAE));
        setupPanel.add(SolveMazeButton, BorderLayout.CENTER);
        setupPanel.add(drawMazeButton, BorderLayout.WEST);
        setupPanel.add(SelectPosinMaze, BorderLayout.EAST);

        setupPanel.setPreferredSize(new Dimension(DEFAULTWIDTH - DEFAULTWIDTH / 6, DEFAULTHEIGHT / 8));
        frame.add(setupPanel, BorderLayout.NORTH);

        mazeInfo = new JPanel(new FlowLayout());
        mazeInfo.setBackground(new Color(0x000C18));
        mazeInfo.setPreferredSize(new Dimension(DEFAULTWIDTH / 6, DEFAULTHEIGHT / 3));
        mazeInfo.setLayout(new BoxLayout(mazeInfo, BoxLayout.Y_AXIS));

        startRowField = new JLabel();
        startRowField.setText(startRowValue + "");
        startRowField.setPreferredSize(new Dimension(30, 20));
        startColumnField = new JLabel();
        startColumnField.setText(startColValue + "");
        startColumnField.setPreferredSize(new Dimension(100, 20));

        endRowField = new JLabel();
        endRowField.setText(endRowValue + "");
        endRowField.setPreferredSize(new Dimension(100, 20));
        endColumnField = new JLabel();
        endColumnField.setText(endColValue + "");
        endColumnField.setPreferredSize(new Dimension(100, 20));

        mazepropslabel = new JLabel("Maze Properties");
        mazepropslabel.setFont(largeFont);
        mazepropslabel.setBorder(titlepadding);
        mazepropslabel.setForeground(Color.WHITE);
        mazepropslabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mazeInfo.add(mazepropslabel);

        mazeprops = new JLabel("Rows: " + mazeRows + " Cols: " + mazeCols);
        mazeprops.setAlignmentX(Component.CENTER_ALIGNMENT);
        Border propspadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        mazeprops.setBorder(propspadding);
        mazeprops.setForeground(Color.WHITE);
        mazeprops.setFont(mediumFont);
        mazeInfo.add(mazeprops);

        startLabel = new JLabel("Start Point");
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startLabel.setForeground(Color.WHITE);
        startLabel.setFont(mediumFont);
        mazeInfo.add(startLabel);

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

        mazeInfo.add(startRow);

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

        mazeInfo.add(startCol);

        JLabel endLabel = new JLabel("End Point");
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setForeground(Color.WHITE);
        endLabel.setFont(mediumFont);
        mazeInfo.add(endLabel);

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

        mazeInfo.add(endRow);

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

        mazeInfo.add(endCol);

        sidePanel = new JPanel(new FlowLayout());
        sidePanel.setBackground(new Color(0x000C18));
        sidePanel.setPreferredSize(new Dimension(DEFAULTWIDTH / 6, DEFAULTHEIGHT));
        sidePanel.setBorder(new EmptyBorder(DEFAULTHEIGHT / 30, 0, DEFAULTHEIGHT / 30, 0));
        sidePanel.add(mazeInfo, BorderLayout.NORTH);

        frame.add(sidePanel, BorderLayout.EAST);

        mazeMessages.setBorder(propspadding);
        JScrollPane scrollPanelMessages = new JScrollPane(mazeMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelMessages.setPreferredSize(new Dimension(DEFAULTWIDTH / 6, DEFAULTHEIGHT / 3));

        messageLabel.setBorder(propspadding);
        sidePanel.add(messageLabel);

        sidePanel.add(scrollPanelMessages, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppGUI());
    }
}
