package nauespaial;

import java.awt.*;
import javax.swing.ImageIcon;

public class Dispar {

    int x, y;
    boolean actiu = true;
    int ample = 55;
    int alt = 55;
    Image img;

    public Dispar(int x, int y) {
        this.x = x;
        this.y = y;
        img = new ImageIcon("src/Imatges/barretina.png").getImage();
    }

    public void mou() {
        y -= 8;
        if (y < 0)
            actiu = false;
    }

    public void setMida(int ample, int alt) {
        this.ample = ample;
        this.alt = alt;
    }

    public void pinta(Graphics g) {
        g.drawImage(img, x, y, ample, alt, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ample, alt);
    }
}
