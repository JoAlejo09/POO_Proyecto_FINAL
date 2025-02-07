package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase Carrito que simula un carrito de compras donde el usuario agrega productos que pueda que compre
 */
public class Carrito {
    public JPanel JPanelCR;
    private JButton quitarDelCarritoButton;
    private JButton vaciarCarritoButton;
    private JButton realizarLaCompraButton;
    private JButton volverButton;
    private JTable tCarrito;
    private JLabel LTotal;
    MetodosBase base = new MetodosBase();
    DefaultTableModel modelo;
    ResultSet rs;
    JFrame frame = new JFrame();
    Metodos metodos = new Metodos(frame);

    /**
     * Metodo Carrito que permite visualizar los productos que estan agregados al carrito de compras
     * @param id            Id del cliente del cual es la informacion del carrito
     * @throws SQLException Validacion que permita acceder a la base de datos
     */
    public Carrito(int id) throws SQLException {
        rs = base.mostrarCarrito();
        llenarTabla(rs);
        calcularTotal();
        /**
         *quitarDelCarritoButton - Permite seleccionar un elemento del carrito y quitarlo
         */
        quitarDelCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_carrito = obtenerSeleccionado();
                int filaSeleccionada = tCarrito.getSelectedRow();
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este producto?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    base.eliminarRegistro(id, "CARRITO_DROP");// Llamar metodo para eliminar el registro de la BD
                    ((DefaultTableModel) tCarrito.getModel()).removeRow(filaSeleccionada); // Eliminar la fila de la tabla
                    JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                    calcularTotal();
                }

            }
        });
        /**
         * vaciarCarritoButton - Permite eliminar todos los productos registrados en el carrito
         */
        vaciarCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres vaciar todos los producto?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    base.resetearTabla("CARRITO_DROP");
                    DefaultTableModel modelo = (DefaultTableModel) tCarrito.getModel();
                    modelo.setRowCount(0);
                    calcularTotal();
                }
            }
        });
        /**
         * volverButton - Permite cancelar cualquier accion del carrito y vuelve a la ventana
         * anterior
         */
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodos.generarVentana("",new Principal_Cliente(1,id).JPanelPC,725,350);
                metodos.cerrarVentana(JPanelCR);
            }
        });
        /**
         * Genera la factura tomando los productos que se encuentran en el carrito
         */
        realizarLaCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = calcularTotal();
                try {
                    metodos.generarVentana("",new GenerarFactura(id,total).JPanelGF,600,350);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     *
     * @param rs ResultSet que contiene la informacion de la Tabla CARRITO_DROP
     * @throws SQLException Excepcion que permite leer la informacion del ResultSet
     */
    public void llenarTabla(ResultSet rs) throws SQLException {
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre del Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio Unitario");
        modelo.addColumn("Total");
        while (rs.next()) {
            int id = rs.getInt("Id");
            String nombre = rs.getString("Nombre");
            int cantidad = rs.getInt("Cantidad");
            double precio = rs.getDouble("Precio");
            double total = rs.getDouble("Total");

            modelo.addRow(new Object[]{id, nombre, cantidad, precio, total});
        }
        tCarrito.setModel(modelo);
        tCarrito.setColumnSelectionAllowed(false);
        tCarrito.setRowSelectionAllowed(true);
        tCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       tCarrito.getColumnModel().getColumn(0).setMinWidth(0);
        tCarrito.getColumnModel().getColumn(0).setMaxWidth(0);
        tCarrito.getColumnModel().getColumn(0).setWidth(0);
    }

    /**
     * Metodo que calcula el total de valor de los productos que estan en el carrito
     * @return total de pagar de los productos del carrito
     */
    public double calcularTotal() {
        double total = 0.0;
        if(!(tCarrito.getRowCount() == 0)){
            for (int i = 0; i < tCarrito.getRowCount(); i++) {
                double valor = (double) tCarrito.getValueAt(i, 4); // Columna "Total" (índice 3)
                total += valor;
            }
        }
        LTotal.setText("TOTAL FACTURA: $"+total);
        if(total==0.0){
            realizarLaCompraButton.setEnabled(false);
        }
        return total;
    }

    /**
     * Metodo que permite obtener el id del producto que ha sido seleccionado
     * @return int que muestre el id del producto seleccionado
     */
    public int obtenerSeleccionado(){
        int id_carrito=-1;
        int filaSeleccionada = tCarrito.getSelectedRow();
            if (filaSeleccionada != -1) { // Verifica que hay una fila seleccionada
            id_carrito = (int) tCarrito.getValueAt(filaSeleccionada, 0); // Suponiendo que el ID está en la columna 0
            System.out.println("ID seleccionado: " + id_carrito);
        } else {
            JOptionPane.showMessageDialog(null, "No hay fila seleccionada.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
            return id_carrito;
    }
}
