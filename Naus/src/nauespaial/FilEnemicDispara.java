package nauespaial;

import java.util.Random;

public class FilEnemicDispara implements Runnable {

    private final PanelNau panell;
    private final int partidaId;
    private final Random rnd = new Random();

    public FilEnemicDispara(PanelNau p, int partidaId) {
        this.panell = p;
        this.partidaId = partidaId;
    }

    @Override
    public void run() {
        while (!panell.isGameOver() && panell.getPartidaId() == partidaId) {

            try {
                Thread.sleep(600 + rnd.nextInt(800));
            } catch (InterruptedException e) {
                break;
            }

            if (panell.isGameOver() || panell.getPartidaId() != partidaId) break;

            Nau n = null;
            for (int tries = 0; tries < 30; tries++) {
                int i = rnd.nextInt(panell.nau.length);
                if (panell.nau[i] != null) { n = panell.nau[i]; break; }
            }
            if (n == null) continue;

            DisparEnemic d = new DisparEnemic(n.x + (n.ample / 2), n.y + n.alt);

            synchronized (panell.disparsEnemics) {
                panell.disparsEnemics.add(d);
            }

            new Thread(new FilDisparEnemic(d, panell, partidaId)).start();
        }
    }
}
