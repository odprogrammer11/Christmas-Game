package nauespaial;

import javax.swing.JFrame;

public class NauEspaial extends JFrame {

    public NauEspaial() {
        setTitle("Naus Espaials");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new PanelNau());
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NauEspaial();
    }
}
