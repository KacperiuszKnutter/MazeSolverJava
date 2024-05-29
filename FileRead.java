import java.io.*;

public class FileRead {


    private static int width;
    private static int height;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    
    public FileRead() {
        this.width = 0;
        this.height = 0;
        this.startX = -1;
        this.startY = -1;
        this.endX = -1;
        this.endY = -1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
    public void changestart(int x, int y){
        startX = x;
        startY = y;
    }
    public void changeend(int x, int y){
        endX = x;
        endY = y;
    }

   
    public static char[][] readMazetxt(String sciezkaDoPliku) throws IOException
    {
        try (BufferedReader brCount = new BufferedReader(new FileReader(sciezkaDoPliku))) 
        {
            int liczbaWierszy = 0;
            int liczbaKolumn = 0;

            // Pobierz liczbę wierszy i kolumn labiryntu
            while (brCount.readLine() != null) {
                liczbaWierszy++;
            }
            brCount.close();

            // Teraz użyjemy osobnego BufferedReader do wczytania labiryntu
            BufferedReader brRead = new BufferedReader(new FileReader(sciezkaDoPliku));
            String linia;
            if ((linia = brRead.readLine()) != null) {
                liczbaKolumn = linia.length();
            }
            brRead.close();

            
            char[][] labirynt = new char[liczbaWierszy][liczbaKolumn];
            int wiersz = 0;
            
            brRead = new BufferedReader(new FileReader(sciezkaDoPliku));
            while ((linia = brRead.readLine()) != null) {
                for (int kolumna = 0; kolumna < liczbaKolumn; kolumna++) {
                    labirynt[wiersz][kolumna] = linia.charAt(kolumna);
                }
                wiersz++;
            }
            width = liczbaKolumn;
            height = liczbaWierszy;


            return labirynt;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String binaryRead(String fileName) throws IOException {
        
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {

            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "r");
            int fileID = Integer.reverseBytes(randomAccessFile.readInt());

            int escape = randomAccessFile.read();
            int columns = Short.reverseBytes(randomAccessFile.readShort());
            int lines = Short.reverseBytes(randomAccessFile.readShort());
            int entryX = Short.reverseBytes(randomAccessFile.readShort());
            int entryY = Short.reverseBytes(randomAccessFile.readShort());
            int exitX = Short.reverseBytes(randomAccessFile.readShort());
            int exitY = Short.reverseBytes(randomAccessFile.readShort());

            randomAccessFile.skipBytes(12);
            int counter = Integer.reverseBytes(randomAccessFile.readInt());
            randomAccessFile.readInt();

            char separator = (char) randomAccessFile.read();
            char wall = (char) randomAccessFile.read();
            char path = (char) randomAccessFile.read();

            int newline=0;
            int stepx=3;
            int stepy=0;


            String outPutFileName = "decodedMaze.txt";
            FileOutputStream outputFile = new FileOutputStream(outPutFileName);
            for (int i = 0; i < counter; i++) {
                separator = (char) randomAccessFile.read();
                char value = (char) randomAccessFile.read();
                int count = randomAccessFile.read();
                
                for (int j = 0; j <= count; j++) {
                    if(stepx == entryX && stepy +1 == entryY){
                        outputFile.write('P');
                        newline++;
                        stepx++;
                        
                    }
                    else if(stepx == exitX && stepy +1 == exitY){
                        outputFile.write('K');
                        newline++;
                        stepx++;
                    }
     
                    else if (value == wall) {
                        outputFile.write('X');
                        newline++;
                        stepx++;
                    } else if (value == path) {
                        outputFile.write(' ');
                        newline++;
                        stepx++;
                     
                    }
                    if (newline==columns) {
                        stepy++;
                        if (stepy < lines-1)
                        outputFile.write('\n');
                        newline=0;
                        stepx=1;
                    }
                }
            }
            outputFile.close();
            System.out.println("Labirynt został zdekodowany do pliku decoded_maze.txt");
            return outPutFileName;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int checkExtension(String filePath) {
        
        if (filePath != null) {
            File file = new File(filePath);
            if (file.isFile()) {
                if (filePath.endsWith(".txt")) {
                    return 1;
                } else if (filePath.endsWith(".bin")) {
                    System.out.println("Wybralem plik binarny!!!");
                    return 2;
                } else {
                    System.out.println("Wspierane jest tylko rozszerzenie bin lub txt");
                }
            }
        } else {
            System.out.println("Sciezka pliku jest null");
        }
        return 0;
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








