package Interfaces;

import Clases.Metodos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal_Cliente {
    private JTextField textField1;
    private JMenuItem mCatalogo;
    private JMenuItem mCategorias;
    public JPanel JPanelPC;
    private JMenuItem mFacturas;
    private JMenuItem mCarrito;
    private JButton cerrarSesionButton;
    private JButton cambiarUsuarioButton;
    private JButton salirButton;
    private JLabel LIcono;
    private JLabel LLogo;
    JFrame frame = new JFrame();
    Metodos met = new Metodos(frame);
    JPanel panel;

    public Principal_Cliente(){
        anadirIcono();
        modificarBoton(cambiarUsuarioButton);
        modificarBoton(cerrarSesionButton);
        modificarBoton(salirButton);
        mCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"CERRANDO SESION.....","",0);
                panel = new Principal_Invitado().JPanelP;
                met.generarVentana("",panel,600,350);
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"GRACIAS POR USAR LA TIENDA EN LINEA","",0);
                System.exit(0);
            }
        });
        cambiarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"CERRANDO SESION.....","",0);
                panel = new Login().JPanelL;
                met.generarVentana("",panel,400,350);
            }
        });
    }
    public void modificarBoton(JButton boton){
        boton.setBorder(null); // Quita el borde
        boton.setContentAreaFilled(false); // Evita el fondo del botón
        boton.setFocusPainted(false);
    }
    public void añadirLogo(){
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/recursos/logo2.png"));
        LLogo.setIcon(originalIcon);
        LLogo.setText("");
        LLogo.setHorizontalAlignment(JLabel.CENTER);
        LLogo.setBounds(10, 10, 50, 50);
    }
    public void anadirIcono(){
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/recursos/buscar.png"));
        Image img = originalIcon.getImage(); // Obtener la imagen
        Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImg);

        LIcono.setIcon(resizedIcon);
        LIcono.setText("");
        LIcono.setHorizontalAlignment(JLabel.CENTER);
        LIcono.setBounds(10, 50, 10, 10);
    }
}
