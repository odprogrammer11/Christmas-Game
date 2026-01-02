package nauespaial;

public class FilDisparEnemic implements Runnable {

    private final DisparEnemic d;
    private final PanelNau panell;
    private final int partidaId;

    public FilDisparEnemic(DisparEnemic d, PanelNau p, int partidaId) {
        this.d = d;
        this.panell = p;
        this.partidaId = partidaId;
    }

    @Override
    public void run() {
        while (d.actiu && !panell.isGameOver() && panell.getPartidaId() == partidaId) {

            d.mou();

            if (d.getBounds().intersects(panell.nauAmiga.getBounds())) {
                synchronized (panell.explosions) {
                    panell.explosions.add(new Explosio(panell.nauAmiga.x, panell.nauAmiga.y));
                }
                d.actiu = false;
                panell.acabarPartida();
                break;
            }

            panell.repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }

        synchronized (panell.disparsEnemics) {
            panell.disparsEnemics.remove(d);
        }
    }
}
