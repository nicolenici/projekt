package projektkrwinki;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.*;

/**
     * Klasa Gra- zawiera planszę oraz panel z przyciskami
     */
public class Gra extends JFrame
{
    Mapa mapa = new Mapa();  // Obiekt reprezentujący mapę gry
    ZaokraglanieBorder zapiszgre=new ZaokraglanieBorder("Zapisz gre");
    ZaokraglanieBorder zakonczgre=new ZaokraglanieBorder("Zakoncz gre");
    JLabel licznik=new JLabel(" Liczba kroków: " +mapa.liczbaruchów);
    JPanel panelgry=new JPanel();
    JPanel panelikonek=new JPanel();
    private static final Dimension BUTTON_SIZE = new Dimension(200, 50); 
    private Obiekt aktywnyObiekt= null;
    PanelStart panelStart = new PanelStart();
    int currentLevel;  // Aktualny poziom gry
    
    /**
     * Konstruktor klasy Gra, tworzy główne okno gry oraz ustawia odpowiednie komponenty.
     * @param nazwa Tytuł okna gry.
     */
    public Gra(String nazwa)
    {  
        super(nazwa);
        // Zatrzymywanie muzyki tła z panelu startowego i ustawienie poziomu
        panelStart.stopBackgroundMusic();
        currentLevel = panelStart.getLevel();
        
        setResizable(false);
        setLayout(new FlowLayout());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        // setSize(screenSize);
        setSize(1920, 1080); //Ustawienie rozmiaru okna
        ImageIcon icon = new ImageIcon("src/files/ikonka.jpg");
        setIconImage(icon.getImage());
        panelikonek.setLayout(new BoxLayout(panelikonek, BoxLayout.Y_AXIS));
        panelgry.setLayout(new BorderLayout());
        
        // Dostosowanie rozmiarów elementów
        ustawRozmiarElementu(zapiszgre);
        ustawRozmiarElementu(zakonczgre);
        ustawRozmiarElementu(licznik);
        
        panelikonek.setOpaque(false); //brak tła
        panelgry.setOpaque(false);
        
        setFocusable(true); 
        requestFocusInWindow(); 
        
        JPanel panelZtlem = new JPanel() 
        {
           private BufferedImage backgroundImage;
           {
                try 
                {   // Ładowanie odpowiedniego obrazu tła 
                    if(currentLevel==0 || currentLevel==1)
                    {
                        backgroundImage = ImageIO.read(new File("src/files/ptapetalevel5.png"));
                    }
                    else if(currentLevel==2)
                    {
                        backgroundImage = ImageIO.read(new File("src/files/tapetalevel3.png"));
                    }
                } 
                catch (Exception e) 
                {
                    System.out.println("Nie udało się załadować obrazu tła: " + e.getMessage());
                }
            }
           
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                if (backgroundImage != null) 
                {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
                
            }
        };
        
        licznik.setFont(new Font("Arial", Font.BOLD, 20));
        licznik.setForeground(new Color(139, 0, 0)); 
        panelZtlem.setPreferredSize(screenSize);
        panelikonek.add(zapiszgre);
        panelikonek.add(Box.createVerticalStrut(20));
        panelikonek.add(zakonczgre);
        panelikonek.add(Box.createVerticalStrut(20));
        panelikonek.add(licznik);
        panelikonek.add(Box.createVerticalStrut(20));
        panelgry.setPreferredSize(new Dimension(1000, 600));   // Dodanie mapy do panelu gry
        panelgry.add(mapa, BorderLayout.CENTER);
       // Akcja przycisku "Zapisz grę"
        zapiszgre.addActionListener(e -> 
        {
            try 
            {
                mapa.zapiszMapeDoPliku("src/save_files/mapka.txt");
                JOptionPane.showMessageDialog(this, "Mapa została zapisana pomyślnie!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } 
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(this, "Błąd podczas zapisywania mapy: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            
            requestFocusInWindow(); //fokus
        });
        //wybieranie obkietu za pomocą kliknięcia myszką
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int x = e.getX()/20-2;
                int y = e.getY()/20-2;
               
                System.out.println("Value at (" + x + ", " + y + ") is: " );
                
                if (aktywnyObiekt != null) 
                {
                    aktywnyObiekt.deactivate();
                             
                }
                //pobranie obiektu
                if (mapa.mapka[y][x] == 6 || mapa.mapka[y][x] == 13) 
                {
                    System.out.println("Czerwona Krwinka");
                    aktywnyObiekt=mapa.getCzerwonaKrwinkaPoXY(x,y);
                } 
                else if (mapa.mapka[y][x] == 7) 
                {
                    System.out.println("Biała Krwinka");
                    aktywnyObiekt = mapa.getBialaKrwinkaPoXY(x, y);
                } 
                else if (mapa.mapka[y][x] == 8) 
                {
                    System.out.println("Płytka Krwi");
                    aktywnyObiekt = mapa.getPlytkaKrwiPoXY(x,y);
                } 
                else 
                {
                    aktywnyObiekt=null;
                    System.out.println("Brak obiektu");
                    return; 
                }
                for (KeyListener kl : getKeyListeners())
                {
                   removeKeyListener(kl);
                }
                  //poruszanie obiektem za pomocą wsad i zapis
                 addKeyListener(new KeyAdapter()
                {
                    @Override
                    public void keyPressed(KeyEvent e) 
                    {
                        // jesli kliknieto L, wczytujemy gre z pliku
                        if(e.getKeyCode() == KeyEvent.VK_L) 
                        {
                            wczytajStanGryZPliku();
                            aktywnyObiekt=null;
                            mapa.repaint();
                        }
                    
                        if(aktywnyObiekt!=null)
                        {
                            aktywnyObiekt.move(e);
                        }
                        else{}
                    }
                });  
                 
                aktywnyObiekt.setPositionChangeListener((oldX, oldY, newX, newY) -> 
                {
                    //akcje np. spotkanie bakteri z kriwnka białą
                    if (mapa.mapka[newY][newX] == 11 && aktywnyObiekt instanceof PlytkaKrwi)
                    {   
                        mapa.sprawdzLiczbeRuchow(aktywnyObiekt.x, aktywnyObiekt.y);
                        aktywnyObiekt.akcja(oldY,oldX);
                        aktywnyObiekt=null; 
                        mapa.liczbaruchów--;
                        licznik.setText("Liczba kroków: " + mapa.liczbaruchów);
                        mapa.repaint();
                    }
                    else if (mapa.mapka[newY][newX] == 9 && aktywnyObiekt instanceof BialaKrwinka)
                    {     
                        mapa.sprawdzLiczbeRuchow(aktywnyObiekt.x, aktywnyObiekt.y);
                        aktywnyObiekt.akcja(oldY,oldX);
                        aktywnyObiekt=null; 
                        aktywnyObiekt=mapa.getWirusXY(newX,newY); //pobieramy wirusa by go usunąć
                        aktywnyObiekt.akcja(newY,newX);
                        aktywnyObiekt=null;
                        mapa.liczbaruchów--;
                        licznik.setText("Liczba kroków: " + mapa.liczbaruchów);
                        mapa.repaint();                       
                    }
                    else if (mapa.mapka[newY][newX] == 10 && aktywnyObiekt instanceof BialaKrwinka)
                    {   
                        mapa.sprawdzLiczbeRuchow(aktywnyObiekt.x, aktywnyObiekt.y);
                        aktywnyObiekt.akcja(oldY,oldX);
                        aktywnyObiekt=null; 
                        aktywnyObiekt=mapa.getBakteriaXY(newX,newY);
                        aktywnyObiekt.akcja(newY,newX);
                        aktywnyObiekt=null;
                        mapa.liczbaruchów--;
                        licznik.setText("Liczba kroków: " + mapa.liczbaruchów);
                        mapa.repaint();
                    }
                    else if (mapa.mapka[newY][newX] == 12 && mapa.mapka[oldY][oldX]==13)
                    {   
                        aktywnyObiekt.akcja(oldY,oldX);
                        mapa.liczbaruchów--;
                        licznik.setText("Liczba kroków: " + mapa.liczbaruchów);
                        mapa.repaint();
                        mapa.sprawdzLiczbeRuchow(aktywnyObiekt.x, aktywnyObiekt.y);
                    }
                    else if (mapa.mapka[newY][newX] == 4 && aktywnyObiekt instanceof CzerwonaKrwinka)
                    {   
                        mapa.sprawdzLiczbeRuchow(aktywnyObiekt.x, aktywnyObiekt.y);
                        aktywnyObiekt.akcja(oldY,oldX);
                        aktywnyObiekt=null;
                        mapa.liczbaruchów--;
                        licznik.setText("Liczba kroków: " + mapa.liczbaruchów);
                        mapa.repaint();
                    }
                    else if (mapa.mapka[newY][newX] != 2) 
                    {
                        //jeśli nie możemy się ruszyć zostajemy w miejscu
                        System.out.println("liczba ruchów" +mapa.liczbaruchów);
                        newX = oldX;
                        newY = oldY;
                        aktywnyObiekt.setPosition(newX, newY);
                    } 
                    else
                    {  //ruszamy się bez akcji 
                        mapa.mapka[newY][newX] = mapa.mapka[oldY][oldX];
                        mapa.mapka[oldY][oldX] = 2;
                        mapa.liczbaruchów--;
                        licznik.setText("Liczba kroków: " + mapa.liczbaruchów);
                        mapa.repaint();
                        mapa.sprawdzLiczbeRuchow(aktywnyObiekt.x, aktywnyObiekt.y);
                        //aktywnyObiekt.setPosition(newX, newY);
                    }
                    
                    SprawdzCzyKoniec(); //sprawdz czy nastąpiła już wygrana
                    mapa.invalidate();
                    System.out.println("Map updated at: (" + newX + ", " + newY + ")");
                
                });
            }
            
        });
        
        panelZtlem.add(panelgry);
        panelZtlem.add(panelikonek);
        add(panelZtlem);
        zakonczgre.addActionListener(e -> zakonczGre());
        
        revalidate();
        repaint();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    /**
 * Sprawdza, czy gra dobiegła końca, tzn. czy skończyły się ruchy, 
 * a mapa jest czysta od zagrożeń (lub odwrotnie, czy są jeszcze zagrożenia).
 * W zależności od wyniku, wyświetla odpowiedni dialog informujący o końcu gry.
 * Pozwala również na ponowne rozpoczęcie gry lub powrót do menu głównego.
 */
    public void SprawdzCzyKoniec()
    { 
        if(mapa.liczbaruchów==0 && Przeszukajmape()==false)
      {  
          //jesli skonczyly sie ruchy i mamy nadal zagrozenia(nie jest czysto)
          JDialog dialog = new JDialog((JFrame) null, "Koniec gry", true);
          dialog.setLayout(new BorderLayout());
          dialog.setSize(600, 300);
          dialog.setResizable(false);
          dialog.setLocationRelativeTo(null);
          dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
          dialog.getContentPane().setBackground(new Color(255, 182, 193));
          dialog.addWindowListener(new java.awt.event.WindowAdapter()
          {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) 
            {
                zakonczGre();
            }
          });
          ImageIcon imageIcon = new ImageIcon("src/files/przegrana2.png");
          Image img = imageIcon.getImage(); 
          int newWidth = 600;  
          int newHeight = 300;
          Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);  // Skalowanie obrazu
          ImageIcon scaledImageIcon = new ImageIcon(scaledImg);
          JLabel imageLabel = new JLabel(scaledImageIcon, SwingConstants.CENTER);
          dialog.add(imageLabel, BorderLayout.CENTER);
          
          //zagraj ponownie lub wroc do menu 
          
          ZaokraglanieBorder gameButton = new ZaokraglanieBorder("Zagraj ponownie");
          gameButton.addActionListener(e -> {
          dialog.dispose();
          this.dispose();
          panelStart.startGame(currentLevel);
        });
          ZaokraglanieBorder menuButton = new ZaokraglanieBorder("Powrót do menu");
          menuButton.addActionListener(e -> {
          dialog.dispose();
         
          zakonczGre();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 182, 193)); 
        buttonPanel.add(menuButton);
        buttonPanel.add(gameButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
      }
      else if(Przeszukajmape()==true)
      {    
          //jesli mapa jest czysta od zagrozen
           JDialog dialog = new JDialog((JFrame) null, "Koniec gry", true);
          dialog.setLayout(new BorderLayout());
          dialog.setSize(600, 300);
          dialog.setResizable(false);
          dialog.setLocationRelativeTo(null);
          dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
          dialog.getContentPane().setBackground(new Color(255, 182, 193));
          dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                zakonczGre();
            }
            });
          ImageIcon imageIcon = new ImageIcon("src/files/wygrana.png");  // Załadowanie obrazu "wygranej" i jego skalowanie
          Image img = imageIcon.getImage(); 
          int newWidth = 600;  
          int newHeight = 300;
          Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);  // Skalowanie obrazu
          ImageIcon scaledImageIcon = new ImageIcon(scaledImg);
          JLabel imageLabel = new JLabel(scaledImageIcon, SwingConstants.CENTER);
          dialog.add(imageLabel, BorderLayout.CENTER);
          //mozliwosc wejscia do menu lub ponownego zagrania
          ZaokraglanieBorder menuButton = new ZaokraglanieBorder("Powrót do menu");
          ZaokraglanieBorder gameButton = new ZaokraglanieBorder("Zagraj ponownie");
           gameButton.addActionListener(e -> {
          dialog.dispose(); // Zamknięcie okna
          this.dispose(); // Zamknięcie głównego okna gry
          panelStart.startGame(currentLevel);});
          menuButton.addActionListener(e -> {
          dialog.dispose();
          zakonczGre();
        }
          );
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 182, 193)); 
        buttonPanel.add(menuButton);
        buttonPanel.add(gameButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
      }
    }
    /**
 * Przeszukuje mapę w celu sprawdzenia, czy wszystkie zagrożenia zostały usunięte.
 * Jeżeli na mapie znajdują się zagrożenia (reprezentowane przez wartości 9, 10, 11, 12), 
 * metoda zwraca `false`, w przeciwnym przypadku zwraca `true`.
 * @return true, jeśli mapa jest czysta od zagrożeń, false, jeśli są jeszcze zagrożenia.
 */
    public boolean Przeszukajmape()
    {   boolean czyCzystoOdZla=true;
        for (int y = 0; y < mapa.WYSOKOSC; y++) {
            for (int x = 0; x < mapa.SZEROKOSC; x++) {
                if(mapa.mapka[y][x] ==9 || mapa.mapka[y][x] ==10 || mapa.mapka[y][x] ==11 || mapa.mapka[y][x] ==12 )
                { 
                    //jesli na mapce mamy pole z zagrozeniem -> false
                    czyCzystoOdZla=false;
                    break;
                }
                if(czyCzystoOdZla==false)
                {
                    break;
                }
            }
      }
        return czyCzystoOdZla;
    } 
    /**
 * Ustawia rozmiar komponentu  na stałą wartość.
 * @param component Komponent (np. przycisk), którego rozmiar ma zostać ustawiony.
 */
    private void ustawRozmiarElementu(JComponent component) 
{
    component.setPreferredSize(BUTTON_SIZE);
    component.setMaximumSize(BUTTON_SIZE);
    component.setMinimumSize(BUTTON_SIZE);
    component.setAlignmentX(Component.CENTER_ALIGNMENT); 
    component.setFont(new Font("Arial", Font.BOLD, 14));
}
 /**
 * Zakończenie gry i zamknięcie okna.
 * Używa `dispose()`, aby zamknąć aktualne okno, 
 * a następnie uruchamia nową instancję gry.
 */
    private void zakonczGre() 
    {
        dispose();
        SwingUtilities.invokeLater(() -> new Projekt()); 
    }
/**
 * Wczytuje stan gry z pliku i aktualizuje mapę oraz licznik ruchów.
 * Jeśli wystąpi błąd podczas wczytywania pliku, zostaje wyświetlony komunikat o błędzie.
 */
    public void wczytajStanGryZPliku()
    {
        try 
        {
            mapa.wczytajMapeZPliku("src/save_files/mapka.txt");


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd podczas wczytywania mapy: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        
        licznik.setText("Liczba kroków: " + mapa.liczbaruchów);
    }
}
