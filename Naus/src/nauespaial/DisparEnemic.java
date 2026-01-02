package nauespaial;

import java.awt.*;

public class DisparEnemic {
    int x, y;
    boolean actiu = true;

    int ample = 8;
    int alt = 18;
    int velocitat = 6;

    public DisparEnemic(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mou() {
        y += velocitat;
        if (y > 520) actiu = false;
    }

    public void pinta(Graphics g) {
        g.fillRect(x, y, ample, alt);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ample, alt);
    }
}
