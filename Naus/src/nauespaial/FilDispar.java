package nauespaial;

public class FilDispar implements Runnable {

    Dispar d;
    PanelNau panell;
    int partidaId;

    public FilDispar(Dispar d, PanelNau p, int partidaId) {
        this.d = d;
        this.panell = p;
        this.partidaId = partidaId;
    }

    @Override
    public void run() {
        while (d.actiu && !panell.isGameOver() && panell.getPartidaId() == partidaId) {

            d.mou();

            for (int i = 0; i < panell.nau.length; i++) {
                if (panell.nau[i] != null &&
                        panell.nau[i].getBounds().intersects(d.getBounds())) {

                    synchronized (panell.explosions) {
                        panell.explosions.add(new Explosio(
                                panell.nau[i].x,
                                panell.nau[i].y
                        ));
                    }

                    panell.nau[i] = null;
                    d.actiu = false;

                    panell.comprovarVictoria();

                    break;
                }
            }

            panell.repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {}
        }
    }
}
