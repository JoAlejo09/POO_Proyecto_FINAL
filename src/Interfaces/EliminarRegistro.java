package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EliminarRegistro {
    public JPanel Pantalla_Admin;
    private JTabbedPane tabbedPane1;
    private JButton volverButton;
    private JTable Mostrar_Usuarios;  // Cambiado de JTextPane a JTable
    private JTextField Id_Usuario;
    private JButton eliminarButton;
    private JTable mostrar_productos;
    private JTextField Id_Producto;
    private JButton eliminarProductoButton;
    JFrame frame = new JFrame();
    Metodos metodos = new Metodos(frame);
    public EliminarRegistro() {
        // Configurar la acción del botón cerrar sesión
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodos.generarVentana("",new Principal_Administrador().JPanelAD,725,350);
                metodos.cerrarVentana(Pantalla_Admin);
            }
        });
        //Accion del boton eliminar Producto
        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idProductoStr = Id_Producto.getText();
                if (idProductoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de usuario.");
                    return;
                }
                try {
                    int idProducto = Integer.parseInt(idProductoStr);  // Convertir el ID a entero

                    // Crear una instancia de MetodosBase y eliminar el cliente
                    MetodosBase metodosBase = new MetodosBase();
                    metodosBase.eliminarProducto(idProducto);  // Eliminar cliente de la base de datos


                    // Refrescar la tabla para mostrar los cambios
                    mostrarRegistrosClientes();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
                }
            }
        });
        // Acción del botón eliminar Cliente
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idUsuarioStr = Id_Usuario.getText();  // Obtener el ID del JTextField

                if (idUsuarioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de usuario.");
                    return;
                }

                try {
                    int idUsuario = Integer.parseInt(idUsuarioStr);  // Convertir el ID a entero

                    // Crear una instancia de MetodosBase y eliminar el cliente
                    MetodosBase metodosBase = new MetodosBase();
                    metodosBase.eliminarCliente(idUsuario);  // Eliminar cliente de la base de datos


                    // Refrescar la tabla para mostrar los cambios
                    mostrarRegistrosClientes();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
                }
            }
        });

        // Llamar al método para mostrar los registros de clientes en la tabla
        mostrarRegistrosClientes();
        mostrarRegistrosProductos();
    }

    private void mostrarRegistrosClientes() {
        // Crear una instancia de MetodosBase para acceder a la base de datos
        MetodosBase metodosBase = new MetodosBase();
        ResultSet rs = metodosBase.consultarTodosClientes();  // Obtener los registros de los clientes

        // Definir las columnas de la tabla
        String[] columnNames = {"ID", "Nombre Completo", "Correo", "Cédula", "Dirección"};
        // Crear un modelo de tabla con las columnas definidas
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        Mostrar_Usuarios.setModel(model);  // Establecer el modelo de la tabla

        try {
            while (rs.next()) {
                // Obtener los valores de cada registro de la base de datos
                int id = rs.getInt("Id");
                String nombreCompleto = rs.getString("NombreCompleto");
                String correo = rs.getString("CorreoElectronico");
                String cedula = rs.getString("Cedula");
                String direccion = rs.getString("Direccion");

                // Agregar los registros a la tabla
                model.addRow(new Object[]{id, nombreCompleto, correo, cedula, direccion});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void mostrarRegistrosProductos(){
        MetodosBase metodosBase = new MetodosBase();
        ResultSet rs = metodosBase.consultarTodosProductos();
        String[] columnNames = {"ID", "Nombre","Precio","Marca","Descripcion","Categoria", "Stock"};
        // Crear un modelo de tabla con las columnas definidas
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        mostrar_productos.setModel(model);  // Establecer el modelo de la tabla
        try {
            while (rs.next()) {
                int id = rs.getInt("Id");
                String nombre = rs.getString("Nombre");
                String precio = String.valueOf(rs.getDouble("Precio"));
                String marca = rs.getString("Marca");
                String descripcion = rs.getString("Descripcion");
                String categoria = rs.getString("Categoria");
                int stock = rs.getInt("Stock");

                //Agregar los registros a la tabla
                model.addRow(new Object[]{id,nombre,precio,marca,descripcion,categoria,stock});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
