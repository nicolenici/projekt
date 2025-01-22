package projektkrwinki;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
/**
 * Klasa Mapa rozszerza JPanel i reprezentuje mapę gry, na której rozgrywają się wydarzenia.
 * Mapa zawiera informacje o różnych obiektach, takich jak krwinki, wirusy, bakterie, płytki krwi,
 * a także różne zmienne kontrolujące stan gry w zależności od poziomu trudności.
 */
public class Mapa extends JPanel
{
     // Stałe określające wymiary mapy
    public static final int SZEROKOSC = 49;
    public static final int WYSOKOSC = 30;
    
    public int[][] mapka;  // Dwuwymiarowa tablica mapki przechowująca dane dotyczące lokalizacji obiektów
   
    // Tablice przechowujące obiekty
    private CzerwonaKrwinka[] czkrwinki;
    private BialaKrwinka[] bkrwinki;
    private PlytkaKrwi[] plytkakrwi;
    public Wirus[] wirus;
    private Bakteria[] bakteria;
    //zmienne przechowujące liczbę różnych obiektów
    private int liczbaKrwinekCzerwonych;
    private int liczbaKrwinekCzerwonychzO;
    private int liczbaKrwinekBialych;
    private int liczbaPlytekKrwi;
    public int liczbaWirusow;
    private int liczbaBakterii;
    private int liczbaRan;
    private int liczbaBrakTlenu;
    public int liczbaruchów;
    public int currentLevel;
    private static Random RANDOM=new Random();
    private static int liczbaw;
    
    /**
     * Konstruktor klasy Mapa.
     * Inicjalizuje mapę, ustawia poziom trudności i generuje odpowiednią liczbę obiektów na mapie,
     * w zależności od wybranego poziomu.
     */
    public Mapa()
    {   
        PanelStart panelStart = new PanelStart();
        panelStart.stopBackgroundMusic();
        this.currentLevel = panelStart.getLevel();
        setOpaque(false);// Ustawienie przezroczystości panelu
        System.out.println(currentLevel);
        mapka = new int[WYSOKOSC][SZEROKOSC];
        inicjalizujMape();
       
        //dane w zaleznosci od poziomu trudnosci
        if (currentLevel == 0) 
        {
            liczbaKrwinekCzerwonych = 24;
            liczbaKrwinekCzerwonychzO = 10;
            liczbaKrwinekBialych=12;
            liczbaPlytekKrwi=8;
            liczbaWirusow=1;
            liczbaBakterii=1;
            liczbaRan=1;
            liczbaBrakTlenu=1;
            liczbaruchów=100;
        }
        else if (currentLevel == 1) 
        {
            liczbaKrwinekCzerwonych = 20;
            liczbaKrwinekCzerwonychzO = 8;
            liczbaKrwinekBialych=10;
            liczbaPlytekKrwi=9;
            liczbaWirusow=2;
            liczbaBakterii=1;
            liczbaRan=2;
            liczbaBrakTlenu=1;
            liczbaruchów=85;
        }
        else if (currentLevel == 2) 
        {
            liczbaKrwinekCzerwonych = 26; 
            liczbaKrwinekCzerwonychzO = 14;
            liczbaKrwinekBialych=16;
            liczbaPlytekKrwi=8;
            liczbaWirusow=2;
            liczbaBakterii=2;
            liczbaRan=2;
            liczbaBrakTlenu=2;
            liczbaruchów=80;
        }
        liczbaw=liczbaWirusow;
        inicjalizujObiekty(liczbaKrwinekCzerwonych,liczbaKrwinekCzerwonychzO, liczbaKrwinekBialych, liczbaPlytekKrwi, liczbaWirusow,liczbaBakterii, liczbaRan, liczbaBrakTlenu);
         
    }
    /**
    * Klasa odpowiedzialna za inicjalizację oraz renderowanie mapy gry.
    */
    private void inicjalizujMape() {
        int[][] mapaDane={
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,1,1,1,1,1,1,14,0,0,0,0},
            {0,0,0,14,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0},
            {0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,14,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,1,0,0,0,0},
            {0,0,0,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0},
            {0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0},
            {0,0,0,14,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,14,1,1,1,1,1,1,2,2,2,2,2,2,1,1,1,1,1,1,14,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0},
            {0,14,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,14,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0},
            {0,14,4,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,5,14,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0},
            {0,14,1,1,1,1,1,2,2,2,2,2,2,2,2,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,1,1,1,1,1,14,0},
            {0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,14,1,1,1,1,1,2,2,2,2,2,2,2,2,1,1,1,1,14,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,3,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,14,1,1,1,1,14,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,14,1,1,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,14,0,0,0,1,2,2,2,2,1,0,0,0,1,2,2,2,2,2,2,2,2,2,3,0,0,0,0,0,0},
            {0,0,0,1,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,1,0,0,0,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,0,0,1,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,1,1,1,1,1,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,0,0,1,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,0,0,3,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,0,0,14,1,1,1,1,1,14,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,14,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        for (int y = 0; y < WYSOKOSC; y++) {
            for (int x = 0; x < SZEROKOSC; x++) {
                mapka[y][x] = mapaDane[y][x]; // Przypisanie danych mapy
                
            }
        }
    }
    /**
     * Metoda wywoływana przy każdym odświeżeniu ekranu, odpowiada za rysowanie mapy.
     * @param g Obiekt grafiki, na którym zostanie narysowana mapa.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        rysujMape(g);// Rysowanie mapy
    }
     /**
     * Rysuje mapę na ekranie na podstawie danych przechowywanych w tablicy mapka.
     * Każdemu typowi komórki mapy przypisywany jest odpowiedni kolor lub obrazek.
     * @param g Obiekt grafiki, na którym zostanie narysowana mapa.
     */
    public void rysujMape(Graphics g) 
    {
        int szerokoscKomorki = 20;  //szerokość komórki
        int wysokoscKomorki = 20; //wysokość komórki
        for (int y = 0; y < WYSOKOSC; y++) {
            for (int x = 0; x < SZEROKOSC; x++) {
                Color kolor = getKolorDlaKomorki(mapka[y][x]);
                g.setColor(kolor);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                
               //przyppisywanie zdjec do wartosci pól
                if (mapka[y][x] == 4) 
         { 
                ImageIcon imageIcon = new ImageIcon("src/files/dopluc.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
                if (mapka[y][x] == 5) 
         {      
                ImageIcon imageIcon = new ImageIcon("src/files/zpluc.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
                 if (mapka[y][x] == 6) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/krwinkac.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
                if (mapka[y][x] == 7) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/krwinkabiala.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
               if (mapka[y][x] == 8) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/plytkakrwi.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
               if (mapka[y][x] == 9) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/wirus.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
                if (mapka[y][x] == 10) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/bakteria.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
                    if (mapka[y][x] == 11) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/rana.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
                      if (mapka[y][x] == 12) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/braktlenu.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
                           if (mapka[y][x] == 13) 
         {
                ImageIcon imageIcon = new ImageIcon("src/files/krwinkaco.png");
                Image krwinkaImage = imageIcon.getImage();
                g.setColor(Color.RED);
                g.fillRect(x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki);
                g.drawImage(krwinkaImage, x * szerokoscKomorki, y * wysokoscKomorki, szerokoscKomorki, wysokoscKomorki, this);
         }
         }
        }
         
    }
   /**
    * Ustawianie kolorów dla danych wartości komórek
    * @param wartosc
    * @return 
    */
    private Color getKolorDlaKomorki(int wartosc) {
        switch (wartosc) {
            case 0: return new Color(0,0,0,0);           // 0 - czerwona
            case 1: return new Color(139, 0, 0); // 1 - ciemny czerwony
            case 2: return Color.RED;
            case 14: return new Color(139, 0, 0);// 2 - jasny czerwony
            default: return new Color(139,0,0);         // domyślny kolor
        }
    }
    /**
 * Inicjalizuje wszystkie byty (krwinki czerwone, krwinki białe, płytki krwi, wirusy, bakterie, rany 
 * i brak tlenu) na podstawie podanych liczb oraz bieżącego poziomu. Losowo umieszcza byty na mapie, 
 * w miejscach, które są dostępne do ich umiejscowienia.
 * 
 * Mapa jest reprezentowana przez określone wartości, np.:
 *  - 2 oznacza pustą przestrzeń, w którą można umieścić dowolny obiekt.
 *  - Inne wartości reprezentują już zainicjowane obiekty na mapie.
 *
 * @param liczbaKrwinekCzerwonych liczba krwinek czerwonych do umieszczenia na mapie.
 * @param liczbaKrwinekCzerwonychzO liczba krwinek czerwonych z tlenem.
 * @param liczbaKrwinekBialych liczba krwinek białych do umieszczenia na mapie.
 * @param liczbaPlytekKrwi liczba płytek krwi do umieszczenia na mapie.
 * @param liczbaWirusow liczba wirusów do umieszczenia na mapie.
 * @param liczbaBakterii liczba bakterii do umieszczenia na mapie.
 * @param liczbaRan liczba ran do umieszczenia na mapie.
 * @param liczbaBrakTlenu liczba miejsc z brakiem tlenu na mapie.
 */
    private void inicjalizujObiekty(int liczbaKrwinekCzerwonych, int liczbaKrwinekCzerwonychzO,int liczbaKrwinekBialych, int liczbaPlytekKrwi, int liczbaWirusow, int liczbaBakterii, int liczbaRan, int liczbaBrakTlenu) 
    {  
        czkrwinki = new CzerwonaKrwinka[liczbaKrwinekCzerwonych+liczbaKrwinekCzerwonychzO];
        bkrwinki= new BialaKrwinka [liczbaKrwinekBialych];
        plytkakrwi= new PlytkaKrwi [liczbaPlytekKrwi];
        switch (currentLevel)
        {
            case 0:
                wirus = new Wirus[liczbaw + 1];  //wieksza tablica, w celu namnazania się bakterii/wirusów
                bakteria=new Bakteria [liczbaBakterii+1];
                break;
            case 1:
                wirus = new Wirus[liczbaw + 2];
                bakteria=new Bakteria [liczbaBakterii+1];
                break;
            case 2:
                wirus = new Wirus[liczbaw + 2];
                 bakteria=new Bakteria [liczbaBakterii+2];
                break;
            default:

                break;
        }
       
        Random rand = new Random();
            
        for (int i = 0; i <(liczbaKrwinekCzerwonych+liczbaKrwinekCzerwonychzO); i++) {
            int x = rand.nextInt(SZEROKOSC);
            int y = rand.nextInt(WYSOKOSC);
            
            if (mapka[y][x] == 2 && i<=liczbaKrwinekCzerwonychzO) 
            {
                mapka[y][x] = 13;
                czkrwinki[i] = new CzerwonaKrwinka(x, y,this ); //tworzenie obiektów i zapisywanie ich w tablicy
                
            } 
            else if (mapka[y][x] == 2 && i>liczbaKrwinekCzerwonychzO) 
            {
                mapka[y][x] = 6;
                czkrwinki[i] = new CzerwonaKrwinka(x, y ,this);
            }
            else 
            {
                i--;
            }
        }
        for (int i = 0; i < liczbaKrwinekBialych; i++) {
            
            int x = rand.nextInt(SZEROKOSC);
            int y = rand.nextInt(WYSOKOSC);
             
            if (mapka[y][x] == 2) 
            {
                mapka[y][x] = 7;
                bkrwinki[i]=new BialaKrwinka(x,y,this);
            } 
            else 
            {
                i--;
            }
        }
        for (int i = 0; i < liczbaPlytekKrwi; i++) {
            
            int x = rand.nextInt(SZEROKOSC);
            int y = rand.nextInt(WYSOKOSC);
             
            if (mapka[y][x] == 2) 
            {
                mapka[y][x] = 8;
                plytkakrwi[i] = new PlytkaKrwi(x, y, this );
            } 
            else 
            {
                i--;
            }
        }
         for (int i = 0; i < liczbaWirusow; i++) {
            
            int x = rand.nextInt(SZEROKOSC);
            int y = rand.nextInt(WYSOKOSC);
             
            if (mapka[y][x] == 2) 
            {
                mapka[y][x] = 9;
                wirus[i] = new Wirus(x, y, this );
            } 
            else 
            {
                i--;
            }
        }
         for (int i = 0; i < liczbaBakterii; i++) {
            
            int x = rand.nextInt(SZEROKOSC);
            int y = rand.nextInt(WYSOKOSC);
             
            if (mapka[y][x] == 2) 
            {
                mapka[y][x] = 10;
                bakteria[i]=new Bakteria(x,y,this);
            } 
            else 
            {
                i--;
            }
        }
           for (int i = 0; i < liczbaRan; i++) {
            
            int x = rand.nextInt(SZEROKOSC);
            int y = rand.nextInt(WYSOKOSC);
             
            if (mapka[y][x] == 2) 
            {
                mapka[y][x] = 11;
            } 
            else 
            {
                i--;
            }
        }
          for (int i = 0; i < liczbaBrakTlenu; i++) {
            
            int x = rand.nextInt(SZEROKOSC);
            int y = rand.nextInt(WYSOKOSC);
             
            if (mapka[y][x] == 3) 
            {
                mapka[y][x] = 12;
                
            } 
            else 
            {
                i--;
            }
        }
    }
    /**
 * Metoda zwraca obiekt PlytkaKrwi na podstawie współrzędnych (x, y).
 * Szuka płytki krwi, której współrzędne odpowiadają podanym wartościom szukany_x oraz szukany_y.
 * 
 * @param szukany_x współrzędna X punktu, którego obiekt ma zostać znaleziony.
 * @param szukany_y współrzędna Y punktu, którego obiekt ma zostać znaleziony.
 * @return obiekt PlytkaKrwi, jeśli zostanie znaleziony obiekt w podanych współrzędnych, w przeciwnym razie null.
 */
   public Obiekt getCzerwonaKrwinkaPoXY(int szukany_x, int szukany_y) 
   {
       //bierzemy obiekt o podanych spolrzednych (podane przez klikniecie myszką)
    for (int i = 0; i < liczbaKrwinekCzerwonych+liczbaKrwinekCzerwonychzO; i++) 
    {
        if (czkrwinki[i].x == szukany_x && czkrwinki[i].y == szukany_y) 
        {
            return (Obiekt) czkrwinki[i]; // Rzutowanie na typ Obiekt
        }
    }
    return null; 
}
   /**
 * Metoda zwraca obiekt BialaKrwinka na podstawie współrzędnych (x, y).
 * Szuka krwinki białej, której współrzędne odpowiadają podanym wartościom szukany_x oraz szukany_y.
 * 
 * @param szukany_x współrzędna X punktu, którego obiekt ma zostać znaleziony.
 * @param szukany_y współrzędna Y punktu, którego obiekt ma zostać znaleziony.
 * @return obiekt BialaKrwinka, jeśli zostanie znaleziony obiekt w podanych współrzędnych, w przeciwnym razie null.
 */
    public Obiekt getPlytkaKrwiPoXY(int szukany_x, int szukany_y) {
    for (int i = 0; i < liczbaPlytekKrwi; i++) {
        if(plytkakrwi[i]!=null){
        if (plytkakrwi[i].x == szukany_x && plytkakrwi[i].y == szukany_y) {
            return (Obiekt) plytkakrwi[i]; // Rzutowanie na typ Obiekt
        }}
    }
    return null; 
}
   /**
    * Metoda zwraca obiekt BialaKrwinka na podstawie współrzędnych x,y
    * @param szukany_x
    * @param szukany_y
    * @return 
    */
    public Obiekt getBialaKrwinkaPoXY(int szukany_x, int szukany_y) {
    for (int i = 0; i < liczbaKrwinekBialych; i++) {
        if(bkrwinki[i]!=null){
        if (bkrwinki[i].x == szukany_x && bkrwinki[i].y == szukany_y) {
            return (Obiekt) bkrwinki[i]; // Rzutowanie na typ Obiekt
        }}
    }
    return null; 
}
    /**
    * Metoda zwraca obiekt Wirus na podstawie współrzędnych x,y
    * @param szukany_x
    * @param szukany_y
    * @return 
    */
    public Obiekt getWirusXY(int szukany_x, int szukany_y) {
    for (int i = 0; i < liczbaWirusow; i++) {
        if(wirus[i]!=null){
          
        if (wirus[i].x == szukany_x && wirus[i].y == szukany_y) {
            return (Obiekt) wirus[i]; // Rzutowanie na typ Obiekt
        }
        
    }}
    return null; 
}
    /**
    * Metoda zwraca obiekt Bakteria na podstawie współrzędnych x,y
    * @param szukany_x
    * @param szukany_y
    * @return 
    */
    public Obiekt getBakteriaXY(int szukany_x, int szukany_y) {
    for (int i = 0; i < liczbaBakterii; i++) {
        if(bakteria[i]!=null){
          
        if (bakteria[i].x == szukany_x && bakteria[i].y == szukany_y) {
            return (Obiekt) bakteria[i]; // Rzutowanie na typ Obiekt
        }
        
    }}
    return null; 
}
/**
 * Metoda zapisuje aktualny stan mapy do pliku o podanej ścieżce.
 * Zapisuje dane liczby różnych obiektów 
 * oraz stan mapy WYSOKOSC x SZEROKOSC do pliku tekstowego.
 * 
 * @param sciezkaDoPliku Ścieżka do pliku, w którym ma zostać zapisana mapa.
 */
    public void zapiszMapeDoPliku(String sciezkaDoPliku) 
    {
        try (FileWriter writer = new FileWriter(sciezkaDoPliku)) 
        {
            writer.write(liczbaKrwinekCzerwonych+ " "+ liczbaKrwinekCzerwonychzO+" " +liczbaKrwinekBialych+" "+ liczbaPlytekKrwi+" "+liczbaWirusow+" "+ liczbaBakterii+" "+ liczbaRan+" "+ liczbaBrakTlenu+" "+currentLevel+" "+liczbaruchów+'\n');
            for (int y = 0; y < WYSOKOSC; y++) {
                for (int x = 0; x < SZEROKOSC; x++) {
                    writer.write(mapka[y][x] + (x == SZEROKOSC - 1 ? "" : " "));
                }
                writer.write("\n");
            }
            System.out.println("Mapa została zapisana do pliku: " + sciezkaDoPliku);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania mapy: " + e.getMessage());
        }
    }
/**
 * Metoda wczytuje stan mapy z pliku o podanej ścieżce.
 * Z pliku wczytane są dane dotyczące liczby obiektów oraz stan mapy.
 * Wczytywane dane są analizowane i na ich podstawie tworzone są nowe obiekty.
 * 
 * @param sciezkaDoPliku Ścieżka do pliku, z którego ma zostać wczytana mapa.
 */
    public void wczytajMapeZPliku(String sciezkaDoPliku) {
        try (BufferedReader br = new BufferedReader(new FileReader(sciezkaDoPliku))) {
            String linia = br.readLine();
            String[] dane = linia.split(" ");
            // usuniecie poprzednich obiektów, dodanie nowych obiektów do tablic z ich polozeniem zgodnie z wczytanymi danymi - pozycje oraz ilosci
            
            for (int i = 0; i < (liczbaKrwinekCzerwonychzO+liczbaKrwinekCzerwonych); i++) {
                czkrwinki[i] = null;
            }
            czkrwinki = null;

            for (int i = 0; i < liczbaKrwinekBialych; i++) {
                bkrwinki[i] = null;
            }
            bkrwinki = null;

            for (int i = 0; i < liczbaPlytekKrwi; i++) {
                plytkakrwi[i] = null;
            }
            plytkakrwi = null;

            for (int i = 0; i < liczbaWirusow; i++) {
                wirus[i] = null;
            }
            wirus = null;

            for (int i = 0; i < liczbaBakterii; i++) {
                bakteria[i] = null;
            }
            bakteria = null;

            // wczytanie danych liczbowych

            liczbaKrwinekCzerwonych = Integer.parseInt(dane[0]);
            int liczbaKrwinekCzerwonych_licznik = 0;

            liczbaKrwinekCzerwonychzO = Integer.parseInt(dane[1]);
            czkrwinki = new CzerwonaKrwinka[liczbaKrwinekCzerwonych+liczbaKrwinekCzerwonychzO];
            
            liczbaKrwinekBialych = Integer.parseInt(dane[2]);
            int liczbaKrwinekBialych_licznik = 0;
            bkrwinki = new BialaKrwinka[liczbaKrwinekBialych];

            liczbaPlytekKrwi = Integer.parseInt(dane[3]);
            int liczbaPlytekKrwi_licznik = 0;
            plytkakrwi = new PlytkaKrwi[liczbaPlytekKrwi];

            liczbaWirusow = Integer.parseInt(dane[4]);
            int liczbaWirusow_licznik = 0;

            liczbaBakterii = Integer.parseInt(dane[5]);
            int liczbaBakterii_licznik = 0;
           
            switch (currentLevel)
        {
            case 0:
                wirus = new Wirus[liczbaw + 1];
                bakteria=new Bakteria [liczbaBakterii+1];
                break;
            case 1:
                wirus = new Wirus[liczbaw + 2];
                bakteria=new Bakteria [liczbaBakterii+1];
                break;
            case 2:
                wirus = new Wirus[liczbaw + 2];
                 bakteria=new Bakteria [liczbaBakterii+2];
                break;
            default:

                break;
        }
            
            liczbaRan = Integer.parseInt(dane[6]);
            liczbaBrakTlenu = Integer.parseInt(dane[7]);
            currentLevel = Integer.parseInt(dane[8]);
            liczbaruchów = Integer.parseInt(dane[9]);
            // Wczytywanie mapy
            for (int y = 0; y < WYSOKOSC; y++) {
                linia = br.readLine();
                dane = linia.split(" ");
                for (int x = 0; x < SZEROKOSC; x++) {
                    mapka[y][x] = Integer.parseInt(dane[x]);

                    // sprawdzamy co to i ewentualnie dodajemy nowy obiekt do tablicy
                    if (mapka[y][x] == 13 || mapka[y][x] == 6) {
                       
                        czkrwinki[liczbaKrwinekCzerwonych_licznik] = new CzerwonaKrwinka(x, y, this); //tworzymy obiekty i zapisujemy w tablicy
                        liczbaKrwinekCzerwonych_licznik++;
                    }
                    else if (mapka[y][x] == 7) {
                        bkrwinki[liczbaKrwinekBialych_licznik] = new BialaKrwinka(x, y, this);
                        liczbaKrwinekBialych_licznik++;
                    }
                    else if (mapka[y][x] == 8) {
                        plytkakrwi[liczbaPlytekKrwi_licznik] = new PlytkaKrwi(x, y, this);
                        liczbaPlytekKrwi_licznik++;
                    }
                    else if (mapka[y][x] == 9) {
                        wirus[liczbaWirusow_licznik] = new Wirus(x, y, this);
                        liczbaWirusow_licznik++;
                    }
                    else if (mapka[y][x] == 10) {
                        bakteria[liczbaBakterii_licznik] = new Bakteria(x, y, this);
                        liczbaBakterii_licznik++;
                    }

                }
            }





            System.out.println("Mapa została wczytana z pliku: " + sciezkaDoPliku);
        } catch (FileNotFoundException e) {
            System.err.println("Plik nie został znaleziony.");
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu mapy: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Błąd formatu danych w pliku.");
        }
    }
/**
 * Metoda zapisuje mapę do domyślnego pliku "mapa.txt".
 * Jest to skrócona wersja metody `zapiszMapeDoPliku`, w której plik zapisywany jest zawsze w tym samym miejscu.
 */
    public void zapiszMape() 
    {
        String sciezka = "mapa.txt"; //zawsze zapisujemy mape w tym samym pliku, mozemy miec jeden zapis
        zapiszMapeDoPliku(sciezka);
    }

    /**
 * Metoda usuwa z tablicy `plytkakrwi` obiekt typu `PlytkaKrwi`.
 * Przeszukuje tablicę płytek krwi w celu znalezienia obiektu, który ma zostać usunięty.
 * Po znalezieniu obiektu, ustawia odpowiedni element tablicy na `null`.
 * 
 * @param plytka Obiekt typu `PlytkaKrwi`, który ma zostać usunięty.
 */
    public void usunPlytkeKrwi(PlytkaKrwi plytka) 
    {

      for (int i = 0; i < plytkakrwi.length; i++) {
        if (plytkakrwi[i] == plytka) 
        { 
            plytkakrwi[i] = null; 
            break;    
        }
      }  
    }
    /**
 * Metoda usuwa z tablicy `czkrwinki` obiekt typu `CzerwonaKrwinka`.
 * Przeszukuje tablicę czerwonych krwinek w celu znalezienia obiektu, który ma zostać usunięty.
 * Po znalezieniu obiektu, ustawia odpowiedni element tablicy na `null`.
 * 
 * @param krwinka Obiekt typu `CzerwonaKrwinka`, który ma zostać usunięty.
 */
    public void usunKrwinkeCzerwona(CzerwonaKrwinka krwinka) {

      for (int i = 0; i < plytkakrwi.length; i++) {
        if (czkrwinki[i] == krwinka) 
        { 
            czkrwinki[i] = null; 
            break;    
        }
      }  
    }
    /**
     * Metoda usuwa z tablicy bkriwnki obiekt typu BialaKrwinka
     * @param krwinka 
     */
       public void usunKrwinkeBiala(BialaKrwinka krwinka) {

      for (int i = 0; i < bkrwinki.length; i++) {
        if (bkrwinki[i] == krwinka) { 
            bkrwinki[i] = null; 
            break;    
            
        }
    }
}
       /**
        * Metoda usuwa z tablicy wirus obiekt typu Wirus
        * @param wirusy 
        */
       public void usunWirusa(Wirus wirusy)
       {for (int i = 0; i < wirus.length; i++)
       {
        if (wirus[i] == wirusy)
        { 
            wirus[i] = null; 
            break; 
        }
       }
      }
       /**
        * Metoda usuwa z tablicy bakteria obiekt typu Bakteria
        * @param bakterie 
        */
        public void usunBakterie(Bakteria bakterie)
       {for (int i = 0; i < bakteria.length; i++)
       {
        if (bakteria[i] == bakterie)
        { 
            bakteria[i] = null; 
            break; 
        }
       }
      }
/**
 * Metoda sprawdza liczbę ruchów w grze i w zależności od poziomu oraz liczby ruchów 
 * wykonuje różne akcje, takie jak dodawanie wirusów, bakterii lub braku tlenu 
 * oraz poruszanie obiektami w grze.
 *
 * @param x Współrzędna x dla obiektów, które mają się poruszyć.
 * @param y Współrzędna y dla obiektów, które mają się poruszyć.
 */
    public void sprawdzLiczbeRuchow(int x, int y)
    {
        //dynamika gry, poprzez namnazanie sie niebezpieczenstw
      if(currentLevel==0 && liczbaruchów%40==0){DodawanieWirusa();}
      if(currentLevel==1 && liczbaruchów%35==0){DodawanieWirusa();}
      if(currentLevel==2 && liczbaruchów%30==0){DodawanieWirusa();}
      if(currentLevel==0 && liczbaruchów%40==0){DodawanieBakterii();}
      if(currentLevel==1 && liczbaruchów%35==0){DodawanieBakterii();}
      if(currentLevel==2 && liczbaruchów%30==0){DodawanieBakterii();}
      if(liczbaruchów%2==0){Ruszobiektami(x, y);}
      if(currentLevel==0 && liczbaruchów%70==0){DodawanieBrakuTlenu();}
      if(currentLevel==1 && liczbaruchów%65==0){DodawanieBrakuTlenu();}
      if(currentLevel==2 && liczbaruchów%60==0){DodawanieBrakuTlenu();}
      else{}
    }
    /**
 * Metoda dodaje brak tlenu do losowej komórki w mapie.
 * Wyszukuje losową komórkę na ścianie naczynia (gdzie mapka[y][x] == 1)
 * i przypisuje jej wartość 12 (oznaczając brak tlenu).
 */
    public void DodawanieBrakuTlenu()
    {
        for(int i=0; i<50; i++)
        {
            int x = RANDOM.nextInt(SZEROKOSC);
            int y = RANDOM.nextInt(WYSOKOSC);
            //losowe miejsce na sciance naczynia
            if(mapka[y][x]==1)
            {  
               mapka[y][x]=12; //dodawanie pola brak tlenu
               break;
            }
        }
    }
 /**
 * Metoda porusza obiektami (wirusami, bakteriami, płytkami krwi, krwinkami) 
 * w losowych kierunkach na mapie, z uwzględnieniem szansy na ruch.
 *
 * @param dany_x Współrzędna x obiektu, który ma się poruszyć.
 * @param dany_y Współrzędna y obiektu, który ma się poruszyć.
 */
    public void Ruszobiektami(int dany_x, int dany_y)
    {
        //dodanie dynamiki poprzez wybieranie randomowych obiektow i ruszanie nimi w wolne miejsca
        for(int i=0; i<liczbaWirusow; i++)
        {
            int czyruszamy = RANDOM.nextInt(3); //szansa na ruszenie 
            if(czyruszamy == 0 && wirus[i]!=null) //jesli mamy obiekt 
            {  
                int x=wirus[i].x;
                int y=wirus[i].y;
                if(x == dany_x && y == dany_y)
                {
                    continue;
                }
                int kierunek=RANDOM.nextInt(4); //losujemy kierunek
                if(kierunek==0 && mapka[y+1][x]==2 )
                { 
                    wirus[i].y=y+1;mapka[y+1][x] = 9;
                    mapka[y][x]=2;
                } 
                else if(kierunek==1 && mapka[y-1][x]==2)
                {
                    wirus[i].y=y-1;mapka[y-1][x] = 9;
                    mapka[y][x]=2;
                    
                }
                else if(kierunek==2 && mapka[y][x-1]==2)
                {
                    wirus[i].x=x-1;mapka[y][x-1] = 9;
                    mapka[y][x]=2;
                }
                else if(kierunek==3 && mapka[y][x+1]==2)
                {
                    wirus[i].x=x+1;mapka[y][x+1] = 9;
                    mapka[y][x]=2;
                }

            }
        }
        for(int i=0; i<liczbaBakterii; i++)
        {
            int czyruszamy = RANDOM.nextInt(3);
            if(czyruszamy == 0 && bakteria[i]!=null)
            {  
                int x=bakteria[i].x;
                int y=bakteria[i].y;
                if(x == dany_x && y == dany_y)
                {
                    continue;
                }
                int kierunek=RANDOM.nextInt(4);
                if(kierunek==0 && mapka[y+1][x]==2 )
                { 
                    bakteria[i].y=y+1;mapka[y+1][x] = 10;
                    mapka[y][x]=2;
                } 
                else if(kierunek==1 && mapka[y-1][x]==2)
                {
                    bakteria[i].y=y-1;mapka[y-1][x] = 10;
                    mapka[y][x]=2;
                    
                }
                else if(kierunek==2 && mapka[y][x-1]==2)
                {
                    bakteria[i].x=x-1;mapka[y][x-1] = 10;
                    mapka[y][x]=2;
                }
                else if(kierunek==3 && mapka[y][x+1]==2)
                {
                    bakteria[i].x=x+1;mapka[y][x+1] = 10;
                    mapka[y][x]=2;
                }

            }
        }
        for(int i=0; i<liczbaPlytekKrwi; i++)
        {
            int czyruszamy = RANDOM.nextInt(3);
            if(czyruszamy == 0 && plytkakrwi[i]!=null)
            {  
                int x=plytkakrwi[i].x;
                int y=plytkakrwi[i].y;
                if(x == dany_x && y == dany_y)
                {
                    continue;
                }
                int kierunek=RANDOM.nextInt(4);
                if(kierunek==0 && mapka[y+1][x]==2 )
                { 
                    plytkakrwi[i].y=y+1;mapka[y+1][x] = 8;
                    mapka[y][x]=2;
                } 
                else if(kierunek==1 && mapka[y-1][x]==2)
                {
                    plytkakrwi[i].y=y-1;mapka[y-1][x] = 8;
                    mapka[y][x]=2;
                    
                }
                else if(kierunek==2 && mapka[y][x-1]==2)
                {
                    plytkakrwi[i].x=x-1;mapka[y][x-1] = 8;
                    mapka[y][x]=2;
                }
                else if(kierunek==3 && mapka[y][x+1]==2)
                {
                    plytkakrwi[i].x=x+1;mapka[y][x+1] = 8;
                    mapka[y][x]=2;
                }

            }
        }
        for(int i=0; i<liczbaKrwinekCzerwonych; i++)
        {
            int czyruszamy = RANDOM.nextInt(3);
            if(czyruszamy == 0 && czkrwinki[i]!=null)
            {  
                int x=czkrwinki[i].x;
                int y=czkrwinki[i].y;
                if(x == dany_x && y == dany_y)
                {
                    continue;
                }
                int kierunek=RANDOM.nextInt(4);
                if(kierunek==0 && mapka[y+1][x]==2 )
                { 
                    czkrwinki[i].y=y+1;
                    if(mapka[y][x]==6){mapka[y+1][x] = 6;}
                    else{mapka[y+1][x] = 13;}
                    mapka[y][x]=2;
                } 
                else if(kierunek==1 && mapka[y-1][x]==2)
                {
                    czkrwinki[i].y=y-1;
                    if(mapka[y][x]==6){mapka[y-1][x] = 6;}
                    else{mapka[y-1][x] = 13;}
                    mapka[y][x]=2;
                    
                }
                else if(kierunek==2 && mapka[y][x-1]==2)
                {
                    czkrwinki[i].x=x-1;
                    if(mapka[y][x]==6){mapka[y][x-1] = 6;}
                    else{mapka[y][x-1] = 13;}
                    mapka[y][x]=2;
                }
                else if(kierunek==3 && mapka[y][x+1]==2)
                {
                    czkrwinki[i].x=x+1;
                    if(mapka[y][x]==6){mapka[y][x+1] = 6;}
                    else{mapka[y][x+1] = 13;}
                    mapka[y][x]=2;
                }
                
            }
        }
        for(int i=0; i<liczbaKrwinekBialych; i++)
        {
            int czyruszamy = RANDOM.nextInt(3);
            if(czyruszamy == 0 && bkrwinki[i]!=null)
            {  
                int x=bkrwinki[i].x;
                int y=bkrwinki[i].y;
                if(x == dany_x && y == dany_y)
                {
                    continue;
                }
                int kierunek=RANDOM.nextInt(4);
                if(kierunek==0 && mapka[y+1][x]==2 )
                { 
                    bkrwinki[i].y=y+1;mapka[y+1][x] = 7;
                    mapka[y][x]=2;
                } 
                else if(kierunek==1 && mapka[y-1][x]==2)
                {
                    bkrwinki[i].y=y-1;mapka[y-1][x] = 7;
                    mapka[y][x]=2;
                    
                }
                else if(kierunek==2 && mapka[y][x-1]==2)
                {
                    bkrwinki[i].x=x-1;mapka[y][x-1] = 7;
                    mapka[y][x]=2;
                }
                else if(kierunek==3 && mapka[y][x+1]==2)
                {
                    bkrwinki[i].x=x+1;mapka[y][x+1] = 7;
                    mapka[y][x]=2;
                }

            }
        }
        
    }
 /**
 * Metoda odpowiedzialna za dodanie nowej bakterii na mapie gry.
 * Bakteria jest rozmnażana losowo w jednym z czterech możliwych kierunków (góra, dół, lewo, prawo),
 * w przypadku, gdy w tym kierunku znajduje się wolne miejsce (oznaczone jako 2).
 * Po rozmnożeniu, nowa bakteria jest dodawana do tablicy `bakteria`, a mapa jest aktualizowana.
 */
    public void DodawanieBakterii()
    {
       int z = RANDOM.nextInt(liczbaBakterii);  //wybieramy bakterie
       for(int i=0; i<liczbaBakterii;i++)
       { 
        if(bakteria[z]!=null && liczbaBakterii<bakteria.length) //jesli bakteria w tablicy istnieje, bierzemy jej dane i szukamy wolnego miejsca
         {     
                int x=bakteria[z].x;
                int y=bakteria[z].y;
                
                if(mapka[y][x+1]==2)
                {  
                    liczbaBakterii++;
                    bakteria[liczbaBakterii-1]=new Bakteria(x+1, y, this); //tworzenie i dodawanie bakterii do tablicy
                    mapka[y][x+1] = 10;
                    repaint();
                    break;
                }
                if(mapka[y][x-1]==2)
                {
                    liczbaBakterii++;
                    bakteria[liczbaBakterii-1]=new Bakteria(x-1, y, this);
                    mapka[y][x-1] = 10;
                    repaint();
                    break;
                }
                if(mapka[y-1][x]==2)
                {
                    liczbaBakterii++;
                    bakteria[liczbaBakterii-1]=new Bakteria(x, y-1, this);
                    mapka[y-1][x] = 10;
                    repaint();
                    break;
                }
                if(mapka[y+1][x]==2)
                {
                    liczbaBakterii++;
                    bakteria[liczbaBakterii-1]=new Bakteria(x, y+1, this);
                    mapka[y+1][x] = 10;
                    repaint();
                    break;
                }
                else{}
               
         }
        else{}
    }
    }
    
    /**
     * Metoda dodaje Krwinke czerwoną na najbliższe pole obok ,,drogi do pluc", sprawdzając, które najbliższe pole jest wolne
     * @param krwinka
     * @return 
     */
    public boolean dodajKrwinkeCzerwona(CzerwonaKrwinka krwinka) {
    boolean czyMoznaWstawic = false;
    int startX = 46; //dane wyjscia z pluc
    int startY = 12;
    
    Queue<int[]> kolejka = new LinkedList<>();
    Set<String> odwiedzone = new HashSet<>(); // Unikamy powtarzania pozycji

    // Dodaj pozycję startową do kolejki
    kolejka.add(new int[]{startY, startX});
    odwiedzone.add(startY + "," + startX);

    // Kierunki: góra, dół, lewo, prawo, lewo-góra, lewo-dół, prawo-góra, prawo-dół
    int[][] kierunki = 
    {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {1, -1}, {-1, 1}, {1, 1}
    };

    while (!kolejka.isEmpty()) {
        int[] pozycja = kolejka.poll();
        int y = pozycja[0];
        int x = pozycja[1];

        // sprawdzamy czy obecna pozycja jest wolna
        if (mapka[y][x] == 2) 
        {
            mapka[y][x] = 13;
            krwinka.y = y;
            krwinka.x = x;
            czyMoznaWstawic = true;
            repaint();
            break;
        }

        // dodajemy sąsiednie pozycje do kolejki
        for (int[] kierunek : kierunki) {
            int nowyY = y + kierunek[0];
            int nowyX = x + kierunek[1];
            String klucz = nowyY + "," + nowyX;

            //czy nie wychodzimy poza mapę i czy pozycja nie była odwiedzona
            if (nowyY >= 0 && nowyY < mapka.length && nowyX >= 0 && nowyX < mapka[0].length && !odwiedzone.contains(klucz)) {
                kolejka.add(new int[]{nowyY, nowyX});
                odwiedzone.add(klucz);
            }
        }
    }

    return czyMoznaWstawic;
}
    /**
     * Dodawanie nowego wirusa do tablicy, namnażanie się wirusów
     */
    public void DodawanieWirusa()
    {  
       int z = RANDOM.nextInt(liczbaWirusow);
       for(int i=0; i<liczbaw;i++)
       { 
        if(wirus[z]!=null && liczbaWirusow<wirus.length)
         {      int x=wirus[z].x;
                int y=wirus[z].y;
                
                if(mapka[y][x+1]==2)
                {  
                    liczbaWirusow++;
                    wirus[liczbaWirusow-1]=new Wirus(x+1, y, this);
                    mapka[y][x+1] = 9;
                    repaint();
                    break;
                }
                if(mapka[y][x-1]==2)
                {
                    liczbaWirusow++;
                    wirus[liczbaWirusow-1]=new Wirus(x-1, y, this);
                    mapka[y][x-1] = 9;
                    repaint();
                    break;
                }
                if(mapka[y-1][x]==2)
                {
                    liczbaWirusow++;
                    wirus[liczbaWirusow-1]=new Wirus(x, y-1, this);
                    mapka[y-1][x] = 9;
                    repaint();
                    break;
                }
                if(mapka[y+1][x]==2)
                {
                    liczbaWirusow++;
                    wirus[liczbaWirusow-1]=new Wirus(x, y+1, this);
                    mapka[y+1][x] = 9;
                    repaint();
                    break;
                }
                else{}
               
         }
        else{}
        
    }
   }

}
