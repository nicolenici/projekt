package projektkrwinki;
/**
 * Klasa reprezentująca białą krwinkę w systemie.
 * Dziedziczy po klasie Obiekt i wykonuje akcję czyszczenia pola oraz usuwania obiektu.
 */
public class BialaKrwinka extends Obiekt 
{
    private Mapa mapa; // Odniesienie do mapy, na której znajduje się biała krwinka
   
    public BialaKrwinka(int x, int y, Mapa mapa)
    {
        super(x, y);
        this.mapa=mapa;
    }
/**
     * Akcja wykonywana przez białą krwinkę.
     * Polega na czyszczeniu pola, na którym znajduje się krwinka,
     * a następnie usunięciu obiektu białej krwinki z mapy.
     *
     * @param oldy poprzednia współrzędna Y
     * @param oldx poprzednia współrzędna X
     */
    @Override
public void akcja(int oldy, int oldx)
    {
        mapa.mapka[this.y][this.x] = 2; //czyszczenie pola
        mapa.revalidate();
        mapa.repaint();
        mapa.mapka[oldy][oldx] = 2; //czyszczenie pola
        mapa.usunKrwinkeBiala(this); //usuwanie obiektu
    }
}
