package Interfaces;

import Clases.Metodos;

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
    Metodos met;

    public Principal_Invitado(){
        modificarBoton(iniciarSesionButton);
        modificarBoton(registrarseButton);
        modificarBoton(salirButton);
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
                met= new Metodos(new Login().JPanelL,400,350);
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                met= new Metodos(new Registrarse().JPanelR,500,300);
            }
        });
    }
    public void modificarBoton(JButton boton){
        boton.setBorder(null); // Quita el borde
        boton.setContentAreaFilled(false); // Evita el fondo del botón
        boton.setFocusPainted(false);

    }
}
