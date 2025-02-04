//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA

package Clases;

import javax.swing.*;

public class Metodos extends JFrame {
    JFrame frame = new JFrame();
     public Metodos(JPanel panel,int ancho, int alto) {
         frame.setContentPane(panel);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setUndecorated(true);
         frame.setResizable(true);
         frame.setSize(ancho, alto);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
     }
}
