package nauespaial;

import java.awt.*;
import javax.swing.ImageIcon;

public class Explosio {

    int x, y;
    int temps = 12;
    Image img;

    public Explosio(int x, int y) {
        this.x = x;
        this.y = y;
        img = new ImageIcon("src/Imatges/explosio.png").getImage();
    }

    public void pinta(Graphics g) {
        if (temps > 0)
            g.drawImage(img, x, y, null);
        temps--;
    }

    public boolean acabada() {
        return temps <= 0;
    }
}
