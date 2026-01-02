package nauespaial;

public class FilNau implements Runnable {

    Nau nau;
    PanelNau panell;
    int partidaId;

    public FilNau(Nau n, PanelNau p, int partidaId) {
        nau = n;
        panell = p;
        this.partidaId = partidaId;
    }

    @Override
    public void run() {
        while (!panell.isGameOver() && panell.getPartidaId() == partidaId) {
            nau.moure();

            if (nau.y >= 450) {
                panell.acabarPartida();
                break;
            }

            panell.repaint();

            try {
                Thread.sleep(nau.velocitat);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
