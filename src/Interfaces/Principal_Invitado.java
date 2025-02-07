//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA

package Interfaces;
import Clases.Metodos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de usuario sin registrarse
 */
public class Principal_Invitado {
    public JPanel JPanelP;
    private JMenuItem mCatalogo;
    private JMenuItem mCategorias;
    private JButton iniciarSesionButton;
    private JButton registrarseButton;
    private JButton salirButton;
    private JLabel LIcono;
    private JLabel LLogo;
    JFrame frame = new JFrame();
    Metodos met = new Metodos(frame);
    JPanel panel;
    private int estado;
    public Principal_Invitado(int estado){
        this.estado = estado;
        modificarBoton(iniciarSesionButton);
        modificarBoton(registrarseButton);
        modificarBoton(salirButton);
        anadirIcono();
        //Acciones del menu
        mCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new CatalogoProductos(0,0).panel1;
                met.generarVentana("",panel,900,350);
                met.cerrarVentana(JPanelP);
            }
        });
        mCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new CatalogoProductos(0,0).panel1;
                met.generarVentana("",panel,800,350);
                met.cerrarVentana(JPanelP);
            }
        });
        //acciones de los botones
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Login(0).JPanelL;
                met.generarVentana("",panel,400,350);
                met.cerrarVentana(JPanelP);
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Registrarse(0).JPanelR;
                met.generarVentana("",panel,550,350);
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

        /*LIcono.setIcon(resizedIcon);
        LIcono.setText("");
        LIcono.setHorizontalAlignment(JLabel.CENTER);
        LIcono.setBounds(10, 10, 40, 40);*/
    }
}
