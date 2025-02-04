//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA

package Interfaces;
import Clases.Metodos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal_Invitado {
    public JPanel JPanelP;
    private JMenuItem mCatalogo;
    private JMenuItem mCategorias;
    private JButton iniciarSesionButton;
    private JButton registrarseButton;
    private JButton salirButton;
    private JTextField labelTextField;
    private JLabel LIcono;
    private JLabel LLogo;
    JFrame frame = new JFrame();
    Metodos met = new Metodos(frame);
    JPanel panel;

    public Principal_Invitado(){
        modificarBoton(iniciarSesionButton);
        modificarBoton(registrarseButton);
        modificarBoton(salirButton);
        anadirIcono();
        //Acciones del menu
        mCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Pantalla_p().panel1;
                met.generarVentana("",panel,300,600);
                met.cerrarVentana(JPanelP);
            }
        });
        mCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Pantalla_p().panel1;
                met.generarVentana("",panel,300,600);
            }
        });
        //acciones de los botones
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Login().JPanelL;
                met.generarVentana("",panel,400,350);
                met.cerrarVentana(JPanelP);
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Registrarse().JPanelR;
                met.generarVentana("",panel,500,300);
                met.cerrarVentana(JPanelP);
            }
        });
    }
    public void modificarBoton(JButton boton){
        boton.setBorder(null); // Quita el borde
        boton.setContentAreaFilled(false); // Evita el fondo del botón
        boton.setFocusPainted(false);

    }
    public void anadirIcono(){
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/recursos/buscar.png"));
        Image img = originalIcon.getImage(); // Obtener la imagen
        Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImg);

        LIcono.setIcon(resizedIcon);
        LIcono.setText("");
        LIcono.setHorizontalAlignment(JLabel.CENTER);
        LIcono.setBounds(10, 10, 40, 40);
    }
}
