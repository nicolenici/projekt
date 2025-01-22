package projektkrwinki;
import java.awt.event.KeyEvent;

/**
 * Abstrakcyjna klasa bazowa reprezentująca obiekt, który może się poruszać.
 * Zapewnia mechanizmy do śledzenia pozycji, poruszania się oraz powiadamiania 
 * słuchaczy o zmianach pozycji.
 */
public abstract class Obiekt 
{
    protected int x;
    protected int y;
    protected PositionChangeListener listener;
    
    /**
     * Konstruktor inicjalizujący pozycję obiektu.
     *
     * @param x początkowa współrzędna x
     * @param y początkowa współrzędna y
     */
    public Obiekt(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
/**
     * Obsługuje ruch obiektu w zależności od naciśniętego klawisza.
     * Możliwe ruchy: W (góra), A (lewo), S (dół), D (prawo).
     * Jeśli pozycja obiektu się zmieni, powiadamiany jest słuchacz.
     *
     * @param e zdarzenie klawiatury, które inicjuje ruch
     */
    public void move(KeyEvent e) 
    {
        int oldX = this.x;
        int oldY = this.y;

        switch (e.getKeyCode()) //poruszanie
        {
            case KeyEvent.VK_W: //rusz w górę
                if (this.y > 0) this.y--;
                break;
            case KeyEvent.VK_A: //rusz w lewo
                if (this.x > 0) this.x--;
                break;
            case KeyEvent.VK_S: //rusz w dół
                this.y++;
                break;
            case KeyEvent.VK_D: //rusz w prawo
                this.x++;
                break;
        }

        if (listener != null) 
        {
            listener.onPositionChange(oldX, oldY, this.x, this.y);
        }

        System.out.println(this.getClass().getSimpleName() + " moved to: (" + this.x + ", " + this.y + ")");
    }
    /**
     * Ustawia słuchacza zmian pozycji.
     *
     * @param listener obiekt implementujący interfejs PositionChangeListener
     */
    public void setPositionChangeListener(PositionChangeListener listener) 
    {
        this.listener = listener;
    }
 /**
     * Ustawia nową pozycję obiektu.
     *
     * @param newX nowa współrzędna X
     * @param newY nowa współrzędna Y
     */
    public void setPosition(int newX, int newY) 
    {
        this.x = newX;
        this.y = newY;
    }
      /**
     * Metoda, która może być nadpisywana przez klasy dziedziczące
     * w celu wykonania dodatkowych akcji podczas zmiany pozycji.
     * Domyślnie nic nie robi.
     *
     * @param oldy poprzednia współrzędna Y
     * @param oldx poprzednia współrzędna X
     */
    public void akcja(int oldy, int oldx) 
    {
        // Domyślnie nic nie robi
    }
     /**
     * Dezaktywuje obiekt, usuwając słuchacza zmian pozycji.
     */
    public void deactivate() 
    {
        this.listener = null;
        System.out.println(this.getClass().getSimpleName() + " has been deactivated");
    }
    /**
     * Interfejs słuchacza zmian pozycji.
     * Służy do powiadamiania o zmianach pozycji obiektu.
     */
    public interface PositionChangeListener 
    {
        void onPositionChange(int oldX, int oldY, int newX, int newY);
    } 
}
