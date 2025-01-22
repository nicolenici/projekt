package projektkrwinki;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.IOException;
/**
 * Klasa PanelStart reprezentuje główny panel startowy aplikacji,
 * który wyświetla tło, przyciski do rozpoczęcia gry, wyboru poziomu trudności,
 * wyświetlania zasad oraz wczytania zapisanej gry.
 */
public class PanelStart extends JPanel{
    
    private static int level;
    private BufferedImage backgroundImage;
    private static final Dimension BUTTON_SIZE = new Dimension(300, 50); // Rozmiar przycisków 
    private Clip backgroundMusicClip;
    private boolean isMuted = false; // Zmienna kontrolująca, czy muzyka jest wyciszona

    /**
     * Konstruktor klasy PanelStart.
     * Ustawia layout, ładuje tło, tworzy przyciski oraz dodaje je do panelu.
     */
    PanelStart()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        
        try 
        {
           // Ładowanie obrazu tła
           backgroundImage = ImageIO.read(new File("src/files/31215.jpg"));

        } catch (Exception e)
        {
            System.out.println("Nie udało się załadować obrazu: " + e.getMessage());
        }
        
        playBackgroundMusic(); // Odtwarzanie muzyki w tle
        
        ImageIcon obrazek = new ImageIcon("src/files/FONT5.png");
        
        Image img = obrazek.getImage(); 
        int newWidth = 700;  
        int newHeight = 261;
        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);  // Skalowanie obrazu
        ImageIcon scaledImageIcon = new ImageIcon(scaledImg);

        JLabel nazwa = new JLabel(scaledImageIcon);
        nazwa.setAlignmentX(Component.CENTER_ALIGNMENT);
        nazwa.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        nazwa.setForeground(Color.WHITE);
        // Tworzenie przycisków
        ZaokraglanieBorder przyciskstart = new ZaokraglanieBorder("Rozpocznij gre"); //korzystamy z klasy w celu ozdobienia przycisków
        ustawRozmiarPrzycisku(przyciskstart);
        
        ZaokraglanieBorder pokazzasady  = new ZaokraglanieBorder("Pokaż elementy gry");
        ustawRozmiarPrzycisku(pokazzasady);
        
        ZaokraglanieBorder wczytajgre  = new ZaokraglanieBorder("Wczytaj gre");
        ustawRozmiarPrzycisku(wczytajgre);
        
        ZaokraglanieBorder wybierzpoziom  = new ZaokraglanieBorder("Wybierz poziom trudnosci");
        ustawRozmiarPrzycisku(wybierzpoziom);

        add(nazwa);
        add(Box.createVerticalStrut(20));
        add(przyciskstart);
        add(Box.createVerticalStrut(10)); 
        add(wybierzpoziom);
        add(Box.createVerticalStrut(10)); //przerwy
        add(pokazzasady);
        add(Box.createVerticalStrut(10));
        add(wczytajgre);
        add(Box.createVerticalStrut(10));
        
        //akcje dla przycisków
        przyciskstart.addActionListener(e -> setLevel(0));
        wybierzpoziom.addActionListener(e -> pokazOknoPoziomow());
        pokazzasady.addActionListener(e -> pokazZasady());
        wczytajgre.addActionListener(e -> {Gra gra = new Gra("Gra: Komórki krwi na ratunek!");stopBackgroundMusic(); gra.wczytajStanGryZPliku();});
    }
    /**
     * Metoda do rysowania tła w panelu.
     * 
     * @param g Obiekt Graphics do rysowania na panelu.
     */
     protected void paintComponent(Graphics g) 
     {
        super.paintComponent(g);

        if (backgroundImage != null) 
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } 
        else 
        {
            System.out.println("Obraz tła nie został załadowany.");
        }
    }
     /**
     * Metoda ustawia rozmiar przycisku oraz jego właściwości, takie jak kolor i czcionka.
     * 
     * @param button Przycisk, którego właściwości mają zostać ustawione.
     */
    private void ustawRozmiarPrzycisku(JButton button) 
    {
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setMinimumSize(BUTTON_SIZE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); 
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 14));  // Czerwona czcionka
        
        if (button instanceof ZaokraglanieBorder)
        {
        button.setPreferredSize(BUTTON_SIZE);
        }
        
    }
       /**
     * Metoda do rozpoczęcia gry.
     * 
     * @param level Poziom trudności do ustawienia w grze.
     */
    public void startGame(int level)
    { 
        stopBackgroundMusic(); //zatrzymanie muzyki w tle
        Window window = SwingUtilities.windowForComponent(this);
        if (window != null) 
        {
            window.dispose();  //zamykanie okna
        }
        
        Gra gra = new Gra("Gra: Komórki krwi na ratunek!"); //tworzenie gry
    }
     /**
     * Metoda wyświetla okno do wyboru poziomu trudności.
     */
    private void pokazOknoPoziomow()
    {
        //tworzenie okna z wyborem poziomów
        JDialog dialog = new JDialog((Frame) null, "Wybierz poziom trudności", true);
        ImageIcon icon = new ImageIcon("src/files/ikonka.jpg");
        dialog.setIconImage(icon.getImage());
       
        JPanel panelZtlem = new JPanel() 
        {
           private BufferedImage backgroundImage;
           {
                try 
                {
                    backgroundImage = ImageIO.read(new File("src/files/tapetalevel3.png"));
                } 
                catch (Exception e) 
                {
                    System.out.println("Nie udało się załadować obrazu tła: " + e.getMessage());
                }
            }
           
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                if (backgroundImage != null) 
                {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelZtlem.setLayout(new BoxLayout(panelZtlem, BoxLayout.Y_AXIS));
        panelZtlem.setOpaque(true);
        
        ImageIcon tytuloknapoziomow=new ImageIcon("src/files/wybierzpoziom1.png");
        JLabel wybor = new JLabel();
        wybor.setIcon(tytuloknapoziomow); 
        wybor.setAlignmentX(Component.CENTER_ALIGNMENT);
        wybor.setFont(new Font("Dialog", Font.BOLD, 24));
        wybor.setForeground(Color.WHITE); 
        // Tworzenie panelu z przyciskami
        JPanel przyciskiPanel = new JPanel();
        przyciskiPanel.setLayout(new BoxLayout(przyciskiPanel, BoxLayout.X_AXIS)); 
        przyciskiPanel.setOpaque(false); 
        JPanel panelzobrazkami=new JPanel();
        panelzobrazkami.setOpaque(false); 
        
        ZaokraglanieBorder latwy=new ZaokraglanieBorder("Łatwy");
        ustawRozmiarPrzycisku(latwy);
        ZaokraglanieBorder sredni=new ZaokraglanieBorder("Średni");
        ustawRozmiarPrzycisku(sredni);
        ZaokraglanieBorder trudny=new ZaokraglanieBorder("Trudny");
        ustawRozmiarPrzycisku(trudny);
        
        przyciskiPanel.add(latwy);
        przyciskiPanel.add(Box.createHorizontalStrut(10)); 
        przyciskiPanel.add(sredni);
        przyciskiPanel.add(Box.createHorizontalStrut(10)); 
        przyciskiPanel.add(trudny);
        
        ImageIcon zdjecie1=new ImageIcon("src/files/poziom1.png");
        ImageIcon zdjecie2=new ImageIcon("src/files/poziom2.png");
        ImageIcon zdjecie3=new ImageIcon("src/files/poziom3.png");
        Image img1 = zdjecie1.getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
        Image img2 = zdjecie2.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
        Image img3 = zdjecie3.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);

        zdjecie1 = new ImageIcon(img1);
        zdjecie2 = new ImageIcon(img2);
        zdjecie3 = new ImageIcon(img3);
        JLabel label1 = new JLabel(zdjecie1);
        JLabel label2 = new JLabel(zdjecie2);
        JLabel label3 = new JLabel(zdjecie3);
         
        panelzobrazkami.add(label1);
        panelzobrazkami.add(Box.createHorizontalStrut(180));
        panelzobrazkami.add(label2);
        panelzobrazkami.add(Box.createHorizontalStrut(170));
        panelzobrazkami.add(label3);
        panelzobrazkami.add(Box.createHorizontalStrut(0));
        
        panelZtlem.add(Box.createVerticalStrut(20)); // Odstęp od góry
        panelZtlem.add(wybor);
        panelZtlem.add(Box.createVerticalStrut(20));
        panelZtlem.add(panelzobrazkami);// Odstęp między etykietą a przyciskami
        panelZtlem.add(Box.createVerticalStrut(0));
        panelZtlem.add(przyciskiPanel);
        panelZtlem.add(Box.createVerticalStrut(70));
        
        //Akcje dla przycisków wyboru poziomu
        latwy.addActionListener(e -> setLevelandClose(0,dialog)); // Ustawienie poziomu na 0 (łatwy)
        sredni.addActionListener(e -> setLevelandClose(1,dialog)); // Ustawienie poziomu na 1 (średni)
        trudny.addActionListener(e -> setLevelandClose(2,dialog));
        
        dialog.setContentPane(panelZtlem);
        dialog.setSize(1000, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    };
    /**
     * Metoda wyświetla zasady gry, grafike.
     */
    private void pokazZasady()
    {
        JDialog dialog = new JDialog((Frame) null, "Elementy gry", true);
        ImageIcon icon = new ImageIcon("src/files/ikonka.jpg");
        dialog.setIconImage(icon.getImage());
       
        JPanel panelZtlem = new JPanel() 
        {
           private BufferedImage backgroundImage;
           {
                try 
                {
                    backgroundImage = ImageIO.read(new File("src/files/tutorial.png"));
                } 
                catch (Exception e) 
                {
                    System.out.println("Nie udało się załadować obrazu tła: " + e.getMessage());
                }
            }
           
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            if (backgroundImage != null) 
            {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
        };
        panelZtlem.setLayout(new BoxLayout(panelZtlem, BoxLayout.Y_AXIS));
        panelZtlem.setOpaque(true);
        
        dialog.setContentPane(panelZtlem);
        dialog.setSize(600, 338);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    };
    /**
 * Ustawia poziom gry i zamyka podany dialog.
 * 
 * @param nowylevel poziom gry do ustawienia (0 - łatwy, 1 - średni, 2 - trudny)
 * @param dialog    dialog, który zostanie zamknięty
 */
   private void setLevelandClose(int nowylevel, JDialog dialog)
   {
     dialog.dispose();
     setLevel(nowylevel);
   }
   /**
 * Ustawia poziom gry i rozpoczyna grę od wybranego poziomu.
 * 
 * @param nowylevel poziom gry do ustawienia:
 *               łatwy, średni, trudny
 */
    private void setLevel(int nowylevel) 
    { 
        //przekazywanie wybranych poziomów do startGame()
        switch (nowylevel) 
        {
            case 0:
                System.out.println(" Wybrano level latwy");
                level=nowylevel;
                startGame(0);
                break;
            case 1:
                System.out.println(" Wybrano level sredni");
                level=nowylevel;
                startGame(1);
                break;
            case 2:
                System.out.println(" Wybrano level trudny");
                level=nowylevel;
                startGame(2);
                break;
            
        }
    }
     /**
     * Zwraca obecny poziom trudności gry.
     * 
     * @return Obecny poziom trudności.
     */
    public int getLevel()
    {
        System.out.println(" Wybrano ...+"+level);
        return level;
    }

      /**
     * Uruchamia muzykę w tle.
     */
    private void playBackgroundMusic() 
    {
        try 
        {
            File soundFile = new File("src/files/TremLoadingloopl.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            backgroundMusicClip = AudioSystem.getClip(); 
            backgroundMusicClip.open(audioStream);

            FloatControl gainControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float volume = 0.1f; // Wartość od 0.0 (cisza) do 1.0 (maksymalna głośność)
            float dB = 20f * (float) Math.log10(volume);
            gainControl.setValue(dB);

            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusicClip.start();
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            System.out.println("Błąd przy ładowaniu dźwięku: " + e.getMessage());
        }
    }
     /**
     * Zatrzymuje odtwarzanie muzyki w tle.
     */
    public void stopBackgroundMusic()
    {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning())
        {
            backgroundMusicClip.stop(); 
            backgroundMusicClip.close();
        }
    }
/**
 * Przełącza stan wyciszenia muzyki w tle.
 * 
 * Jeśli muzyka jest obecnie wyciszona, metoda włącza odtwarzanie muzyki
 * i ustawia ją na ciągłe odtwarzanie w pętli. Jeśli muzyka jest włączona, 
 * metoda ją zatrzymuje i wycisza.
 * 
 * Wynik działania metody jest także logowany w konsoli za pomocą komunikatów
 * informujących o zmianie stanu muzyki.
 */
    private void toggleMute() 
    {
        if (isMuted) {
            // Jeśli muzyka jest wyciszona, włącz ją
            if (backgroundMusicClip != null) {
                backgroundMusicClip.start();
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            isMuted = false;
            System.out.println("Muzyka została włączona");
        } else 
        {
            // Jeśli muzyka gra, wycisz ją
            if (backgroundMusicClip != null)
            {
                backgroundMusicClip.stop();
            }
            isMuted = true;
            System.out.println("Muzyka została wyciszona");
        }
    }


}
