package Interfaces;

import javax.swing.*;

public class Principal_Invitado {
    public JPanel JPanelP;
    private JMenuItem mCatalogo;
    private JMenuItem mCategorias;
    private JButton iniciarSesionButton;
    private JButton registrarseButton;
    private JButton salirButton;
    private JTextField textField1;

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
    }
}
