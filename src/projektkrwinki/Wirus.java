package projektkrwinki;
/**
 * Klasa reprezentująca obiekt typu Wirus, dziedzicząca po klasie Obiekt.
 * Wirus jest obiektem, który porusza się na mapie i może zostać usunięty.
 */
public class Wirus extends Obiekt
{
    private Mapa mapa;  // Pole przechowujące referencję do mapy, na której znajduje się wirus
    
    public Wirus(int x, int y, Mapa mapa)
    {
        super(x, y);
        this.mapa=mapa;
        
    }
      /**
     * Metoda wykonująca akcję wirusa.
     * W tej implementacji wirus zostaje usunięty z mapy.
     * 
     * @param oldy Stara współrzędna Y przed wykonaniem akcji.
     * @param oldx Stara współrzędna X przed wykonaniem akcji.
     */
    public void akcja(int oldy, int oldx)
    {   
        mapa.usunWirusa(this); //usuwanie wirusa
    }
}
