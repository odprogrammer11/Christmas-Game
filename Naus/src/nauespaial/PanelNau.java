package nauespaial;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class PanelNau extends JPanel implements Runnable {

    private boolean gameOver = false;
    private boolean win = false;

    private volatile int partidaId = 0;

    Thread[] filsNau;
    Nau[] nau;

    NauAmiga nauAmiga;

    ArrayList<Dispar> dispars = new ArrayList<>();
    ArrayList<Explosio> explosions = new ArrayList<>();
    ArrayList<DisparEnemic> disparsEnemics = new ArrayList<>();

    private Thread filEnemicDispara;

    int numNaus = 8;

    public PanelNau() {
        setFocusable(true);
        requestFocus();

        iniciarEnemics();

        nauAmiga = new NauAmiga(220, 400);

        iniciarFilEnemicDispara();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (gameOver) {
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        reiniciar();
                    }
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    nauAmiga.moureDreta();

                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    nauAmiga.moureEsquerra();

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Dispar d = new Dispar(nauAmiga.x + 20, nauAmiga.y - 10);
                    synchronized (dispars) {
                        dispars.add(d);
                    }

                    Thread fd = new Thread(new FilDispar(d, PanelNau.this, partidaId));
                    fd.start();
                }

                repaint();
            }
        });

        new Thread(this).start();
    }

    public int getPartidaId() {
        return partidaId;
    }

    private void iniciarFilEnemicDispara() {
        filEnemicDispara = new Thread(new FilEnemicDispara(this, partidaId));
        filEnemicDispara.start();
    }

    private void iniciarEnemics() {
        filsNau = new Thread[numNaus];
        nau = new Nau[numNaus];

        Random rand = new Random();

        for (int i = 0; i < numNaus; i++) {
            int velocitat = rand.nextInt(40) + 10;
            int posX = rand.nextInt(300) + 30;
            int posY = 30;
            int dX = rand.nextInt(3) + 1;

            nau[i] = new Nau(posX, posY, dX, 15, velocitat);

            filsNau[i] = new Thread(new FilNau(nau[i], this, partidaId));
            filsNau[i].start();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void acabarPartida() {
        gameOver = true;
        win = false;
        repaint();
    }

    private void reiniciar() {
        partidaId++;

        gameOver = false;
        win = false;

        synchronized (dispars) { dispars.clear(); }
        synchronized (explosions) { explosions.clear(); }
        synchronized (disparsEnemics) { disparsEnemics.clear(); }

        nauAmiga = new NauAmiga(220, 400);

        iniciarEnemics();
        iniciarFilEnemicDispara();

        repaint();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {}
        }
    }

    public void comprovarVictoria() {
        for (Nau n : nau) {
            if (n != null) return;
        }
        win = true;
        gameOver = true;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        nauAmiga.pinta(g);

        for (int i = 0; i < nau.length; i++) {
            if (nau[i] != null)
                nau[i].pinta(g);
        }

        ArrayList<Dispar> copiaDispars;
        synchronized (dispars) {
            copiaDispars = new ArrayList<>(dispars);
        }
        for (Dispar d : copiaDispars) {
            if (d.actiu)
                d.pinta(g);
        }

        Color old = g.getColor();
        g.setColor(Color.RED);

        ArrayList<DisparEnemic> copiaEnemics;
        synchronized (disparsEnemics) {
            disparsEnemics.removeIf(d -> !d.actiu);
            copiaEnemics = new ArrayList<>(disparsEnemics);
        }
        for (DisparEnemic d : copiaEnemics) {
            if (d.actiu)
                d.pinta(g);
        }

        g.setColor(old);

        synchronized (explosions) {
            explosions.removeIf(Explosio::acabada);
            for (Explosio ex : explosions)
                ex.pinta(g);
        }

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString(win ? "YOU WIN" : "GAME OVER", 140, 250);

            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Pulsa R para reiniciar", 170, 280);
        }
    }
}
