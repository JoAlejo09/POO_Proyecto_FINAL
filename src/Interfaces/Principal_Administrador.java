package Interfaces;

import Clases.Metodos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal_Administrador {
    private JButton cerrarSesionButton;
    private JButton salirButton;
    private JMenuItem mCatalogo;
    private JMenuItem mCategorias;
    public JPanel JPanelAD;
    private JMenuItem mAgregarProducto;
    private JMenuItem mEliminarRegistro;
    private JMenuItem mEditarProducto;
    private JMenuItem mGenerarReporte;
    JFrame frame = new JFrame();
    Metodos met = new Metodos(frame);
    JPanel panel;
    public Principal_Administrador(){
        modificarBoton(cerrarSesionButton);
        modificarBoton(salirButton);
        //ACCIONES BOTONES
        mCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new CatalogoProductos(3,0).panel1;
                met.generarVentana("",panel,900,350);
                met.cerrarVentana(JPanelAD);
            }
        });
        mAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new AgregarModificarProducto().JPanelAP;
                met.generarVentana("",panel,650,400);
                met.cerrarVentana(JPanelAD);
            }
        });
        mEditarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new CatalogoProductos(2,0).panel1;
                met.generarVentana("",panel,900,350);
                met.cerrarVentana(JPanelAD);
            }
        });
        mCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new CatalogoProductos(3,0).panel1;
                met.generarVentana("",panel,900,350);
                met.cerrarVentana(JPanelAD);
            }
        });
        mEliminarRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new EliminarRegistro().Pantalla_Admin;
                met.generarVentana("",panel,900,350);
                met.cerrarVentana(JPanelAD);

            }
        });
        mGenerarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Reportes().JPanelRP;
                met.generarVentana("",panel,600,350);
                met.cerrarVentana(JPanelAD);
            }
        });
        //BOTONES ADICIONALES

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"CERRANDO SESION.....","",1);
                panel = new Principal_Invitado(0).JPanelP;
                met.generarVentana("",panel,600,350);
                met.cerrarVentana(JPanelAD);
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"GRACIAS POR USAR LA TIENDA EN LINEA","",1);
                System.exit(0);
            }
        });
    }


    //INICIALIZAR VENTANA
    public void modificarBoton(JButton boton){
        boton.setBorder(null); // Quita el borde
        boton.setContentAreaFilled(false); // Evita el fondo del botón
        boton.setFocusPainted(false);
    }
}
