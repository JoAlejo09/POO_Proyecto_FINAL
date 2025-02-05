package Interfaces;

import Clases.MetodosBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reportes {
    private JTable taReportes;
    private JRadioButton facturasRadioButton;
    private JRadioButton pagosRadioButton;
    private JButton generarReporteButton;
    private JButton volverButton;
    private JButton realizarPagoButton;
    public JPanel JPanelRP;
    private JButton generarPDFButton;
    MetodosBase base = new MetodosBase();
    ButtonGroup opciones;
    ResultSet rs;
    //PARA ADMINISTRADOR
    public Reportes(){
        agregarBotones();
        realizarPagoButton.setVisible(false);
        generarReporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 rs = base.consultarReportes(seleccionarBotones());
                 if(seleccionarBotones().equalsIgnoreCase("Facturas")){
                     try {
                         llenarTablaFacturas();
                     } catch (SQLException ex) {
                         throw new RuntimeException(ex);
                     }
                 }else{
                     try {
                         llenarTablaPagos();
                     } catch (SQLException ex) {
                         throw new RuntimeException(ex);
                     }}}
        });
    }
    //PARA CLIENTES
    public Reportes(int id){
       agregarBotones();
       generarReporteButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               rs = base.consultarReportesClientes(seleccionarBotones(),id);
               if(seleccionarBotones().equalsIgnoreCase("Facturas")){
                   try {
                       llenarTablaFacturas();
                   } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                   }
               }else{
                   try {
                       llenarTablaPagos();
                   } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                   }}
           }
       });

    }
    public void agregarBotones(){
        opciones = new ButtonGroup();
        opciones.add(facturasRadioButton);
        opciones.add(pagosRadioButton);
    }
    public String seleccionarBotones(){
        if(facturasRadioButton.isSelected()){
            return "Facturas";
        }else{
            return "Pagos";
        }
    }
    public void llenarTablaFacturas() throws SQLException {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Fecha");
            modelo.addColumn("Nombre");
            modelo.addColumn("Valor");
            modelo.addColumn("Estado");
            modelo.addColumn("ID Cliente");

        while (rs.next()) {
            Object[] fila = new Object[6]; // 6 columnas en la tabla
            fila[0] = rs.getInt("Id");
            fila[1] = rs.getTimestamp("Fecha"); // Puede ser Timestamp o String según tu preferencia
            fila[2] = rs.getString("Nombre");
            fila[3] = rs.getDouble("Valor");
            fila[4] = rs.getString("Estado");
            fila[5] = rs.getInt("Id_cliente");

            modelo.addRow(fila);
        }

        // Asignar el modelo a la JTable
        taReportes.setModel(modelo);
    }
    public void llenarTablaPagos() throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nro Pago");
        modelo.addColumn("Fecha");
        modelo.addColumn("Nombre");
        modelo.addColumn("Valor");
        modelo.addColumn("Cliente");
        modelo.addColumn("Nro Factura");
        while (rs.next()) {
            Object[] fila = new Object[6]; // 6 columnas en la tabla
            fila[0] = rs.getInt("Id");
            fila[1] = rs.getTimestamp("Fecha"); // Puede ser Timestamp o String según tu preferencia
            fila[2] = rs.getString("Nombre");
            fila[3] = rs.getDouble("Valor");
            fila[4] = rs.getString("Id_cliente");
            fila[5] = rs.getInt("Id_factura");
            modelo.addRow(fila);
        }

        // Asignar el modelo a la JTable
        taReportes.setModel(modelo);
    }
}
