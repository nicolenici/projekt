package projektkrwinki;
import javax.swing.*;
import java.awt.*;
/**
 * Klasa reprezentująca przycisk z zaokrąglonymi rogami oraz efektami hover i kliknięcia.
 * Dziedziczy po JButton i modyfikuje sposób rysowania oraz zachowanie przycisku.
 */
public class ZaokraglanieBorder extends JButton {

    private static final int ARC_WIDTH = 30; // Szerokość promienia zaokrąglenia
    private static final int ARC_HEIGHT = 30; // Wysokość promienia zaokrąglenia

    public ZaokraglanieBorder(String text)
    {
        super(text);
        setOpaque(false); // Ustawienie tła na przezroczyste
        setContentAreaFilled(false); 
        setFocusPainted(false); // Wyłączenie efektu zaznaczenia
        setBorder(BorderFactory.createEmptyBorder()); // Usunięcie standardowej ramki
        addButtonHoverAndClickEffects(this); // Dodanie efektów najechania i kliknięcia
    }

     /**
     * Nadpisana metoda do rysowania przycisku z zaokrąglonymi rogami.
     * Zmienia kolor tła przycisku w zależności od jego stanu.
     *
     * @param g obiekt Graphics do rysowania
     */
    @Override
    protected void paintComponent(Graphics g) 
    {   
        //zmiana koloru tła w zależności od stanu przycisku
        if (getModel().isPressed()) 
        {
            g.setColor(getBackground().darker()); // Kolor przycisku po kliknięciu
        } 
        else if (getModel().isRollover()) 
        {
            g.setColor(getBackground().brighter()); // Kolor przycisku przy najechaniu myszką
        } 
        else 
        {
            g.setColor(getBackground()); 
        }

        g.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        super.paintComponent(g); 
    }
    
   /**
    * Dodaje efekty wizualne przycisku np zmiana koloru tła
    * @param button przycisk, do którego mają byc dodawane efekty
    */
    private void addButtonHoverAndClickEffects(JButton button) 
    {
        button.setBackground((Color.WHITE));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  //margines
        button.setFocusPainted(false); 
        
        //obsługa zdarzeń myszki
        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {   //zmiana koloru przycisku po najechaniu myszką
                button.setBackground(new Color(255, 160, 174)); 
                button.setForeground(new Color(139,0,0));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {   //zmiana koloru na pierwotny po wyjechaniu myszką
                button.setBackground(Color.WHITE);
                button.setForeground(Color.RED); 
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) 
            {   //zmiana koloru tła po naciśnięciu
                button.setBackground(new Color(255, 105, 97) ); 
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) 
            {   //Przywrócenie pierwotnego koloru tła po zwolnieniu przycisku
                button.setBackground(Color.WHITE); 
            }
         });
     }
}
