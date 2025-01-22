package projektkrwinki;
/**
 * Klasa reprezentująca bakterie w systemie.
 * Dziedziczy po klasie Obiekt i dodaje funkcjonalność związaną z mapą.
 */
public class Bakteria extends Obiekt
{
     private Mapa mapa; // Odniesienie do mapy, na której znajduje się bakteria
     
     public Bakteria(int x, int y, Mapa mapa)
    {
        super(x,y);
        this.mapa=mapa;
        
    }
     /**
     * Akcja wykonywana przez bakterię.
     * W tym przypadku usuwa bakterie z mapy na podstawie ich pozycji.
     *
     * @param oldy poprzednia współrzędna Y
     * @param oldx poprzednia współrzędna X
     */
    public void akcja(int oldy, int oldx)
    {  
        mapa.usunBakterie(this); //usuwanie obiketu
    }
}
