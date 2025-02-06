//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA

package Clases;


import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;

public class MetodosBase {
//PRUEBA DE OTRA BASE DE DATOS
 /*   private String HOST="bx7mgr834vhwqndq7pjj-mysql.services.clever-cloud.com";
    private final String DB="bx7mgr834vhwqndq7pjj";
    private final String USER="uqtw41jnukntizp7";
    private final String PORT="3306";
    private final String PASSWORD="aPYwHRDWLool6PYHCY7C";
    private final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB; //DIRECCIONAMIENTO DE LA BASE DE DATOS PARA CONEXION EN LA NUBE*/

    //DIRECCIONAMIENTO DE LA BASE DE DATOS PARA CONEXION EN LA NUBE*/

    /*private static final String HOST="bdbsjb7v8o8wa0pot4lt-mysql.services.clever-cloud.com";
    private static final String DB="bdbsjb7v8o8wa0pot4lt";
    private static final String USER="uspitlplqxwpi1ft";
    private static final String PORT="3306";
    private static final String PASSWORD="vDVAQEeEPO9pvXHzs0ih";
    private final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB;
    //Metodo especificado para agregar conexion a base de datos y metodos sobre la base de datos*/

  private String URL = "jdbc:mysql://localhost:3306/tienda_db";
    private String USER = "root";
    private String PASSWORD = "root";
    Connection cn;
    ResultSet rs;

    public MetodosBase() { //Inicializacion de la conexion
        try {
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
            if(cn==null){
                System.out.println("NO SE HA CONECTADO A LA BASE");
            }else{
                System.out.println("CONECTADO A LA BASE");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int validarLogin(String rol, String correo, String password) {
        String sql = "";
        int val = rol.compareTo("Cliente");
        if (val < 0 || val > 0) {
            sql = "SELECT Id FROM ADMINISTRADOR WHERE Usuario = ? AND Contrasena = ?";
        } else {
            sql = "SELECT Id FROM CLIENTE WHERE CorreoElectronico = ? AND Contrasena = ?";
        }
        try {
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // Retorna el número de usuarios que coinciden
            } else {
                return 0;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertarCliente(String nombre, String apellido, String correo, String contrasena, String cedula, String direccion) {
        String sql = "INSERT INTO CLIENTE (NombreCompleto, CorreoElectronico, Contrasena, Cedula, Direccion) VALUES (?,?,?,?,?)";
        boolean rt = false;
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, nombre + " " + apellido);
            pstmt.setString(2, correo);
            pstmt.setString(3, contrasena);
            pstmt.setString(4, cedula);
            pstmt.setString(5, direccion);
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                rt = true;
            }
        } catch (SQLException e) {
            System.out.println("ERROR");
        }
        return rt;
    }
    public ResultSet consultarProductosId(int id) {
        String query = "SELECT Nombre, Precio, Marca, Descripcion, Categoria, Imagen, Stock FROM PRODUCTOS WHERE Id = ?";
        ResultSet rs = null;
        try {
            if (cn == null) {
                throw new SQLException("Error: La conexión a la base de datos es nula.");
            }
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery(); // 🔹 CORRECCIÓN: No pasar 'query' aquí
        } catch (SQLException e) {
            e.printStackTrace(); // 🔹 Ahora imprimimos el error para depuración
        }
        return rs;
    }
    public ResultSet consultarProductos(String categoria) {
        String query = "SELECT Id, Nombre, Precio, Marca, Descripcion, Categoria, Imagen, Stock FROM PRODUCTOS";
        if (!categoria.equals("TODOS")) {
            query += " WHERE Categoria = '" + categoria + "'";  // Filtrar por categoría
        }
        try {
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {

        }
        return rs;
    }
    public ResultSet realizarBusqueda(String criterioBusqueda, String valorBusqueda) {
        String query = "SELECT Id, Nombre, Precio, Marca, Descripcion, Categoria, Imagen, Stock FROM PRODUCTOS WHERE " + criterioBusqueda + " LIKE '%" + valorBusqueda + "%'";
        try {
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {

        }
        return rs;
    }
    public String hallarNombre(int id) {
        String query = "SELECT NombreCompleto FROM CLIENTE WHERE Id = ?";
        try {
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean insertarProducto(String nombre, double precio, String marca, String descripcion, String categoria, int stock, byte[] imagen) {
        String sql = "INSERT INTO PRODUCTOS(Nombre, Precio, Marca, Descripcion, Categoria, Stock, Imagen )VALUES(?,?,?,?,?,?,?)";
        boolean rt = false;
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
            pstmt.setString(3, marca);
            pstmt.setString(4, descripcion);
            pstmt.setString(5, categoria);
            pstmt.setInt(6, stock);
            if (imagen != null) {
                pstmt.setBytes(7, imagen);
            }
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                rt = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
    public boolean actualizarProducto(int id, String nombre,double precio,String marca,String descripcion,String categoria,int stock, byte[] imagen){
        String sql = "UPDATE PRODUCTOS SET Nombre = ?, Precio = ?, Marca = ?, Descripcion = ?, Categoria = ?, Stock = ?, Imagen = ? WHERE Id = ?";
        boolean rt = false;
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setString(1,nombre);
            pstmt.setDouble(2,precio);
            pstmt.setString(3,marca);
            pstmt.setString(4,descripcion);
            pstmt.setString(5,categoria);
            pstmt.setInt(6,stock);
            if (imagen != null) {
                pstmt.setBytes(7, imagen);
            }
            pstmt.setInt(8, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet consultarReportes(String opcion) {
        String sql = "";
        if (opcion.compareTo("Facturas") == 0) {
            sql = "SELECT * FROM FACTURAS";
        } else {
            sql = "SELECT * FROM PAGOS";
        }
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public ResultSet consultarReportesClientes(String opcion, int id) {
        String sql = "";
        if (opcion.compareTo("Facturas") == 0) {
            sql = "SELECT * FROM FACTURAS WHERE Id_cliente = ?";
        } else {
            sql = "SELECT * FROM PAGOS WHERE Id_cliente = ?";
        }
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public ResultSet obtenerProductoCarrito(String producto) {
        String sql = "SELECT Id, Nombre, Precio, Marca, Categoria, Imagen, Stock FROM PRODUCTOS WHERE Nombre = ?";
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, producto);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public boolean agregarCarrito(int id_producto, int cantidad) {
        String sql = "INSERT INTO CARRITO_DROP(Id_producto, Cantidad) VALUES(?,?)";
        boolean rt = false;
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(sql);
            pstmt.setInt(1, id_producto);
            pstmt.setInt(2, cantidad);
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                rt = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
    public int consultarProductoEditar(String producto)throws SQLException{
        String sql = "SELECT Id FROM PRODUCTOS WHERE Nombre = ?";
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, producto);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id");
            }
            else{
                return 0;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet mostrarCarrito() {
        String sql = "SELECT c.Id, p.Nombre, c.Cantidad, p.Precio, (c.Cantidad * p.Precio) AS Total " +
                "FROM CARRITO_DROP c " +
                "JOIN PRODUCTOS p ON c.Id_Producto = p.Id";
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public void eliminarRegistro(int id, String tabla) {
        String sql = "DELETE FROM " + tabla + " WHERE Id = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetearTabla(String tabla) {
        String sql = "TRUNCATE TABLE "+tabla;
        try {
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Carrito vaciado y reiniciado correctamente.");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ResultSet consultarDatosCliente(int id){
        String sql = "SELECT NombreCompleto, Cedula FROM CLIENTE WHERE Id = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public ResultSet consultarNroFactura(){
        String sql = "SELECT MAX(Id) AS UltimoId FROM FACTURAS";
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;

    }
    public boolean generarFactura(String nombre,double valor,String estado, int id_cliente)throws SQLException{
        boolean rt=false;
        String sql = "INSERT INTO FACTURAS(Fecha, Nombre, Valor, Estado, Id_cliente) VALUES (?,?,?,?,?)";
        LocalDateTime now = LocalDateTime.now();
        Timestamp tiempo = Timestamp.valueOf(now);

        PreparedStatement pstmt = cn.prepareStatement(sql);
        pstmt.setTimestamp(1,tiempo);
        pstmt.setString(2,nombre);
        pstmt.setDouble(3,valor);
        pstmt.setString(4,estado);
        pstmt.setInt(5,id_cliente);
        int filasInsertadas = pstmt.executeUpdate();
        if (filasInsertadas > 0) {
            rt = true;
        }
        return rt;
    }
    public int obtenerIdFactura(int id_cliente) {
        String sql = "SELECT Id FROM FACTURAS WHERE Id_cliente = ? ORDER BY Id DESC LIMIT 1;";
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(sql);
            pstmt.setInt(1,id_cliente);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("Id");
            }else{
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void generarPago(int id_factura, int id_cliente, double valor,String nombre){
        String sql = "INSERT INTO PAGOS (Fecha, Nombre, Valor, Id_cliente, Id_factura) VALUES (NOW(), ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1,nombre);
            pstmt.setDouble(2,valor);
            pstmt.setInt(3,id_cliente);
            pstmt.setInt(4,id_factura);
            int filas_insertadas = pstmt.executeUpdate();
            if(filas_insertadas>0){
                JOptionPane.showMessageDialog(null,"FACTURA Y PAGOS REGISTRADOS CON EXITO","",1);
            }else{
                JOptionPane.showMessageDialog(null,"NO SE HA REGISTRADO EL PAGO","",0);
            }
        }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean actualizarStockProductos(){
        boolean rt = false;
        String sql = "UPDATE PRODUCTOS p JOIN CARRITO_DROP c ON p.Id = c.Id_Producto SET p.Stock = GREATEST(p.Stock - c.Cantidad, 0);";
        try {
            PreparedStatement pstmt = cn.prepareStatement(sql);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                rt = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
    public ResultSet consultarTodosClientes() {
        String query = "SELECT Id, NombreCompleto, CorreoElectronico, Contrasena, Cedula, Direccion FROM CLIENTE";
        try {
            Statement stmt = cn.createStatement(); 
            rs = stmt.executeQuery(query);  
        } catch (SQLException e) {
            throw new RuntimeException(e); 
        }
        return rs;  
    }
    public void eliminarCliente(int id) {
        String sqlCheck = "SELECT COUNT(*) FROM Cliente WHERE Id = ?";
        String sqlDelete = "DELETE FROM Cliente WHERE Id = ?";
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtDelete = null;
        try {
            // Verificar si el ID existe en la base de datos
            pstmtCheck = cn.prepareStatement(sqlCheck);
            pstmtCheck.setInt(1, id);
            ResultSet rsCheck = pstmtCheck.executeQuery();

            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                // El ID existe, proceder con la eliminación
                pstmtDelete = cn.prepareStatement(sqlDelete);
                pstmtDelete.setInt(1, id);
                pstmtDelete.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
            } else {
                // El ID no existe
                JOptionPane.showMessageDialog(null, "No existe un cliente con ese ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet consultarTodosProductos() {
        String query = "SELECT Id, Nombre, Precio, Marca, Descripcion , Categoria, Stock FROM PRODUCTOS";
        try {
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public void eliminarProducto(int id) {
        String sqlCheck = "SELECT COUNT(*) FROM PRODUCTOS WHERE Id = ?";
        String sqlDelete = "DELETE FROM PRODUCTOS WHERE Id = ?";
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtDelete = null;
        try {
            // Verificar si el ID existe en la base de datos
            pstmtCheck = cn.prepareStatement(sqlCheck);
            pstmtCheck.setInt(1, id);
            ResultSet rsCheck = pstmtCheck.executeQuery();

            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                // El ID existe, proceder con la eliminación
                pstmtDelete = cn.prepareStatement(sqlDelete);
                pstmtDelete.setInt(1, id);
                pstmtDelete.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
            } else {
                // El ID no existe
                JOptionPane.showMessageDialog(null, "No existe un cliente con ese ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
