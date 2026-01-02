package nauespaial;

import java.awt.*;
import javax.swing.ImageIcon;

public class Nau {

    int x, y, dx, dy, velocitat;
    int ample = 64;
    int alt = 64;
    Image img;

    public Nau(int x, int y, int dx, int dy, int velocitat) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.velocitat = velocitat;

        img = new ImageIcon("src/Imatges/papanoel.png").getImage();
    }

    public void setMida(int ample, int alt) {
        this.ample = ample;
        this.alt = alt;
    }


    public void moure() {
        x += dx;

        if (x <= 0) {
            x = 0;
            dx = -dx;
            y += dy;
        }

        if (x >= 450 - ample) {
            x = 450 - ample;
            dx = -dx;
            y += dy;
        }
    }


    public void pinta(Graphics g) {
        g.drawImage(img, x, y, ample, alt, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ample, alt);
    }
}
