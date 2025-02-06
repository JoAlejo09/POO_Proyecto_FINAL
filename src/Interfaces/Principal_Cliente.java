package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Principal_Cliente {
    private JMenuItem mCatalogo;
    private JMenuItem mCategorias;
    public JPanel JPanelPC;
    private JMenuItem mFacturas;
    private JMenuItem mCarrito;
    private JButton cerrarSesionButton;
    private JButton cambiarUsuarioButton;
    private JButton salirButton;
    private JLabel LCliente;
    private JLabel LLogo;
    JFrame frame = new JFrame();
    Metodos met = new Metodos(frame);
    MetodosBase base = new MetodosBase();
    JPanel panel;

    public Principal_Cliente(int estado,int id){
        String nombre = base.hallarNombre(id);
        LCliente.setText("Usuario: "+nombre);
        anadirIcono();
        modificarBoton(cambiarUsuarioButton);
        modificarBoton(cerrarSesionButton);
        modificarBoton(salirButton);
        mCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Pantalla_p(1,id).panel1;
                met.generarVentana("",panel,900,350);
                met.cerrarVentana(JPanelPC);
            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                base.resetearTabla("Carrito_drop");
                JOptionPane.showMessageDialog(null,"CERRANDO SESION.....","",1);
                panel = new Principal_Invitado(0).JPanelP;
                met.generarVentana("",panel,600,350);
                met.cerrarVentana(JPanelPC);
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                base.resetearTabla("Carrito_drop");
                JOptionPane.showMessageDialog(null,"GRACIAS POR USAR LA TIENDA EN LINEA","",1);
                System.exit(0);
            }
        });
        cambiarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"CERRANDO SESION.....","",1);
                base.resetearTabla("Carrito_drop");
                panel = new Login(1).JPanelL;
                met.generarVentana("",panel,400,350);
                met.cerrarVentana(JPanelPC);
            }
        });
        mFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Reportes(id).JPanelRP;
                met.generarVentana("",panel,600,350);
                met.cerrarVentana(JPanelPC);
            }
        });
        mCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    panel = new Carrito(id).JPanelCR;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                met.generarVentana("",panel,600,350);
                met.cerrarVentana(JPanelPC);
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
    }
}
