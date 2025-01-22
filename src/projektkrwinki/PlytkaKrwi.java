package projektkrwinki;
/**
 * Klasa PlytkaKrwi reprezentuje obiekt płytki krwi, który porusza się po mapie.
 * Dziedziczy po klasie Obiekt i odpowiada za akcje związane z płytką krwi na mapie.
 */
public class PlytkaKrwi extends Obiekt {
    
   private Mapa mapa; // Pole przechowujące referencję do mapy, na której znajduje się płytka krwi
   
    public PlytkaKrwi(int x, int y , Mapa mapa)
    {
        super(x, y);
        this.mapa=mapa;
    }
    /**
     * Metoda wykonująca akcję dla płytki krwi.
     * W tej implementacji, płytka krwi zostaje usunięta z mapy oraz wyczyszczone zostają odpowiednie pola.
     * 
     * @param oldy Stara współrzędna Y przed wykonaniem akcji.
     * @param oldx Stara współrzędna X przed wykonaniem akcji.
     */
    public void akcja(int oldy, int oldx)
    {
          mapa.mapka[this.y][this.x] = 2; //czyszczenie pola
          mapa.revalidate();
          mapa.repaint();
          mapa.mapka[oldy][oldx] = 2; //czyszczenie pola
          mapa.usunPlytkeKrwi(this); //usuwanie obiektu
         
   
    }
}
