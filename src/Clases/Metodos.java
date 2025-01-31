package Clases;

import javax.swing.*;

public class Metodos extends JFrame {
    JFrame frame;
     public Metodos(JPanel panel,int ancho, int alto) {
         frame = new JFrame("");
         frame.setContentPane(panel);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(ancho, alto);
         frame.setVisible(true);
         frame.setLocationRelativeTo(null);
     }
}
