package projektkrwinki;
import javax.swing.*;
import java.awt.*; 
/**
 * Klasa Projekt reprezentuje główne okno aplikacji.
 * Ustawia tytuł okna, ikonę, rozmiar, layout oraz panel startowy.
 * Odpowiada za inicjalizację interfejsu użytkownika.
 */
public class Projekt extends JFrame{
    /**
     * Konstruktor klasy Projekt.
     * Inicjalizuje główne okno aplikacji, ustawia tytuł, ikonę, rozmiar, layout oraz panel startowy.
     */
    public Projekt()
    {
        setTitle("Projekt Krwinki");  // Ustawienie tytułu okna
        ImageIcon icon = new ImageIcon("src/files/ikonka.jpg");// Załadowanie ikony okna z pliku 
        setIconImage(icon.getImage());
        setSize(900, 560);   // Ustawienie rozmiaru okna
        setLayout(new BorderLayout());
        PanelStart panelStart = new PanelStart(); //panel staru dodajemy do frame
        add(panelStart, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false); // Ustawienie, by okno nie było zmienialne
    }
     /**
     * Metoda main.
     * Uruchamia aplikację i tworzy główne okno.
     * 
     * @param args Argumenty wywołania programu (nieużywane).
     */
    public static void main(String[] args) 
    {
        new Projekt(); // Utworzenie nowego obiektu klasy Projekt (główne okno aplikacji)
    }
    
}
