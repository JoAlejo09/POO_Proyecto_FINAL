package Interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal_Invitado {
    public JPanel JPanelP;
    private JMenuItem mCatalogo;
    private JMenuItem mCategorias;
    private JButton iniciarSesionButton;
    private JButton registrarseButton;
    private JButton salirButton;
    private JTextField textField1;
    JFrame frame = new JFrame();

    public Principal_Invitado(){
        iniciarSesionButton.setBorder(null); // Quita el borde
        iniciarSesionButton.setContentAreaFilled(false); // Evita el fondo del botón
        iniciarSesionButton.setFocusPainted(false);
        registrarseButton.setBorder(null); // Quita el borde
        registrarseButton.setContentAreaFilled(false); // Evita el fondo del botón
        registrarseButton.setFocusPainted(false);
        salirButton.setBorder(null); // Quita el borde
        salirButton.setContentAreaFilled(false); // Evita el fondo del botón
        salirButton.setFocusPainted(false);

        mCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Login().JPanelL);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 350);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Registrarse().JPanelR);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 300);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}
