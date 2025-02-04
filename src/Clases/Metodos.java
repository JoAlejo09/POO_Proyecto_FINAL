//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA

package Clases;

import javax.swing.*;
import java.awt.*;

public class Metodos extends JFrame {
    JFrame frame = new JFrame();

    public Metodos(JFrame frame) {
        this.frame = frame;
    }

    public void generarVentana(String titulo, JPanel panel, int w, int h) {
        frame.setTitle(titulo);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(true);
        frame.setSize(w, h);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void cerrarVentana(JPanel panel) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel); // Obtener la ventana que contiene el JPanel
        if (frame != null) {
            //frame.setVisible(false);
            frame.dispose();
        }
    }
/*     public Metodos(JPanel panel,int ancho, int alto) {
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setUndecorated(true);
         frame.setResizable(true);
         frame.setSize(ancho, alto);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
     }*/
}
