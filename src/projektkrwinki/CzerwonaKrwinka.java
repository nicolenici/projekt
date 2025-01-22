package projektkrwinki;
/**
 * Klasa reprezentująca czerwoną krwinkę w systemie.
 * Dziedziczy po klasie Obiekt i dodaje specyficzną logikę dla czerwonych krwinek, 
 * takich jak dodawanie krwinki do płuc lub dostarczanie tlenu.
 */
public class CzerwonaKrwinka extends Obiekt {

    private Mapa mapa; // Odniesienie do mapy, na której znajduje się czerwona krwinka

   
    public CzerwonaKrwinka(int x, int y, Mapa mapa)
    {
        super(x, y);
        this.mapa=mapa;
    }
   /**
     * Akcja wykonywana przez czerwoną krwinkę.
     * Wykonuje jedną z dwóch operacji w zależności od pozycji:
     * 1. Dodaje krwinkę do płuc, jeśli znajduje się na określonych pozycjach.
     * 2. Dostarcza tlen, jeśli znajduje się poza pozycjami docelowymi.
     *
     * @param oldy poprzednia współrzędna Y
     * @param oldx poprzednia współrzędna X
     */
    @Override
public void akcja(int oldy, int oldx)
    {    // Sprawdzenie, czy krwinka znajduje się na jednej z pozycji docelowych
         if((oldy==13 && oldx==2) || (oldy==11 && oldx==2) || (oldy==12 && oldx==3))
         {
          if(mapa.dodajKrwinkeCzerwona(this))  // Próba dodania krwinki do płuc
          {
              mapa.mapka[oldy][oldx] = 2;
          }
         }
         else //dawanie tlenu
         {
          // Krwinka dostarcza tlen, gdy nie znajduje się na pozycjach docelowych, wykonywana jest druga akcja, czyli dostarczanie tlenu
          mapa.mapka[this.y][this.x] = 1; //czyszczenie scianki
          mapa.mapka[oldy][oldx] = 6;
          this.x=oldx;
          this.y=oldy;
          mapa.revalidate(); // Odświeżenie wizualizacji mapy
          mapa.repaint();
          
         }
    }

}
