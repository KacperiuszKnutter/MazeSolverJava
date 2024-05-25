import java.io.*;
import java.util.Scanner;
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
    public void read(String in) throws IOException {
        int x = 0;
        int y = 0;
        int maxWidth = 0;
        int currentChar;
        int iter=0;
        String jakikolwiek = "";

        try 
        {
            Scanner scaner=new Scanner(new File(in));
            while (scaner.hasNext()) {
                String line=scaner.nextLine();
                x=0;
                y++;
                width = line.length() + 1;
                for(char c:line.toCharArray()){
                    iter++;
                switch (c) {
                    case 'P':
                    jakikolwiek+=c;

                        startX = x;
                        
                        startY = y;
                        
                        x++;
                        break;
                    case 'K':
                    jakikolwiek+=c;

                        endX = x;
                        endY = y;
                        x++;
                        break;
                    
                    case 'X':
                    jakikolwiek+=c;

                        x++;
                        break;
                    case ' ':
                    jakikolwiek+=c;
        
                        x++;
                        break;
                    default:
                
                    System.exit(1);
                }

                }
                
            }
        }
        catch (IOException e) 
        {
                System.out.println("Failed to read file: ");
                e.printStackTrace();
        }
        height = y+1; 
    }

    public static void markVisited(char[][] lab , int x, int y){
        lab[y][x] = '+';
    }
    public static void markPath(char[][] lab , int x, int y){
        lab[y][x] = 'O';
    }
    public static boolean isInvalidPosition(char[][] lab , int x, int y) {
        return (lab[y][x] == '+' || lab[y][x] == 'X');
    }
    public static char[][] wczytajLabirynt (String sciezkaDoPliku) throws IOException{
        try (BufferedReader brCount = new BufferedReader(new FileReader(sciezkaDoPliku))) {
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
            // Utwórz dwuwymiarową tablicę na podstawie rozmiaru labiryntu
            char[][] labirynt = new char[liczbaWierszy][liczbaKolumn];
            // Wczytaj labirynt z pliku
            int wiersz = 0;
            brRead.close();
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
    public static void print(char[][] lab){
        for(int i=0; i <lab.length; i++){
            for(int j = 0; j< lab[i].length; j++){
                System.out.print(lab[i][j]);
            }
            System.out.println();
        }
    }
    
}








