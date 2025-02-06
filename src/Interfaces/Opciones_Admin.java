package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Opciones_Admin {
    public JPanel Pantalla_Admin;
    private JTabbedPane tabbedPane1;
    private JButton cerrarSesiónButton;
    private JTable Mostrar_Usuarios;  // Cambiado de JTextPane a JTable
    private JTextField Id_Usuario;
    private JButton eliminarButton;

    public Opciones_Admin() {
        // Configurar la acción del botón cerrar sesión
        cerrarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(cerrarSesiónButton);
                ventanaActual.dispose(); // Cierra la ventana actual

                // Crear una nueva ventana para el panel de invitado
                JFrame frame = new JFrame();
                Metodos met = new Metodos(frame);
                JPanel panel = new Principal_Invitado(0).JPanelP;
                met.generarVentana("", panel, 725, 350);
            }
        });

        // Acción del botón eliminar
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
}
