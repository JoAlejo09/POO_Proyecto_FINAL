package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerarFactura {
    private JTextField tNro;
    private JTextField tNombre;
    private JTextField tCedula;
    private JTextField tValor;
    private JButton pagarButton;
    private JButton generarFacturaButton;
    private JButton cancelarButton;
    public JPanel JPanelGF;
    JFrame frame = new JFrame();
    Metodos metodos = new Metodos(frame);
    ResultSet rs,rs1;
    MetodosBase base = new MetodosBase();
    public GenerarFactura(int id_cliente, double valor) throws SQLException {
        bloquearTexto();
        llenarCamposFactura();
        llenarCamposFactura2(id_cliente);
        tValor.setText(String.valueOf(valor));
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    metodos.generarVentana("",new Carrito(id_cliente).JPanelCR,600,350);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                metodos.cerrarVentana(JPanelGF);
            }
        });
        generarFacturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean estado;
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea generar la factura sin pagar?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if(confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        estado = base.generarFactura(tNombre.getText(),Double.parseDouble(tValor.getText()),"Pendiente",id_cliente);
                        if(estado){
                            JOptionPane.showMessageDialog(null,"Se ha generado la factura","",1);
                            base.actualizarStockProductos();
                            base.resetearTabla("Carrito_drop");
                        }else{
                            JOptionPane.showMessageDialog(null,"Error al generar la factura","",0);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
    public void llenarCamposFactura() throws SQLException {
        int nro = 0;
        rs = base.consultarNroFactura();
        if (rs.next()) {
            nro = rs.getInt("UltimoId") + 1;
        }
        tNro.setText("001-001-0" + String.valueOf(nro));
    }
    public void llenarCamposFactura2(int id_cliente)throws SQLException{
        String nombre = "", cedula = "";
        rs1 = base.consultarDatosCliente(id_cliente);
        while(rs1.next()){
            nombre = rs1.getString("NombreCompleto");
            cedula = rs1.getString("Cedula");
            System.out.println("Nombre:"+nombre+"\nCedula: "+cedula);
        }
        tNombre.setText(nombre);
        tCedula.setText(cedula);
    }
    public void bloquearTexto(){
        tNro.setEditable(false);
        tNombre.setEditable(false);
        tCedula.setEditable(false);
        tValor.setEditable(false);
    }
}
