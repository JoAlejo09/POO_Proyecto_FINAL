//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
/**
 * Clase que contiene metodos y validaciones de la base de datos
 */
package Clases;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
/**
 * Clase que implementa las consultas hacia la base de datos
 */
public class MetodosBase {
    //DIRECCIONAMIENTO DE LA BASE DE DATOS PARA CONEXION EN LA NUBE*/

    /*private static final String HOST="bdbsjb7v8o8wa0pot4lt-mysql.services.clever-cloud.com";
    private static final String DB="bdbsjb7v8o8wa0pot4lt";
    private static final String USER="uspitlplqxwpi1ft";
    private static final String PORT="3306";
    private static final String PASSWORD="vDVAQEeEPO9pvXHzs0ih";
    private final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB;
    //Metodo especificado para agregar conexion a base de datos y metodos sobre la base de datos*/

    /**
     * @param URL       Direccion de la base de datos
     * @param USER      Parametro de conexion a la base de datos
     * @param PASSWORD  Parametro de conexion a la base de datos
     * @param cn        Conector con la base de datos
     * @param rs        Resultado de la ejecucion de la sentencia en la base de datos
     */
    private String URL = "jdbc:mysql://localhost:3306/tienda_db";
    private String USER = "root";
    private String PASSWORD = "root";
    Connection cn;
    ResultSet rs;

    /**
     * Metodo que establece la conexion con la base de datos
     */
    public MetodosBase() {
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
    /**
     * Metodo que valida si el usuario esta registrado en la base de datos
     * @param rol       Que privilegios quiere el usuario que se registra(Cliente, Administrador)
     * @param correo    Almacena el correo del usuario  para la consulta
     * @param password  Almacena la contraseña del usuario para la consulta
     * @return 0 si el usuario no esta registrado y un valor >0 si el usuario esta registrado
     */
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

    /**
     * Añade un nuevo registro del Cliente
     *
     * @param nombre        nombre del cliente
     * @param apellido      apellido del cliente
     * @param correo        correo de registro del cliente
     * @param contrasena    password del registro del cliente
     * @param cedula        identificion del cliente
     * @param direccion     direccion de entrega del cliente
     * @return true si se inserto el cliente en la base de datos y false si no se logro agregar
     */
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
    /**
     * Consulta un producto por el id de Producto
     * @param id
     * @return  null si no coincide con ningun valor
     *         o devuelve el registro del Producto (Id, Nombre, Marca, Categoria,Stock)
     */
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
    /**
     * Consulta un producto en la base de datos por la categoria del Producto
     * @param categoria     categoria a la que pertenece el producto
     * @return un ResultSet con el registro de Producto (Id, Nombre, Marca, Categoria,Stock)
     */
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
    /**
     * Metodo que busca productos acorde a un criterio/condicion y en funcion de
     * @param criterioBusqueda
     * @param valorBusqueda
     * @return un ResultSet con registro de Productos (Id, Nombre, Marca, Categoria,Stock)
     */
    public ResultSet realizarBusqueda(String criterioBusqueda, String valorBusqueda) {
        String query = "SELECT Id, Nombre, Precio, Marca, Descripcion, Categoria, Imagen, Stock FROM PRODUCTOS WHERE " + criterioBusqueda + " LIKE '%" + valorBusqueda + "%'";
        try {
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {

        }
        return rs;
    }
    /**
     * Metodo que obtiene informacion Nombre del Cliente
     * @param id       id del cliente de la Tabla CLIENTE en la base de datos
     * @return devuelve un String con el nombre del Cliente
     */
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

    /**
     * Metodo que inserta un nuevo registro en la Tabla PRODUCTOS
     * @param nombre        Nombre del producto - String
     * @param precio        Precio del producto - Double
     * @param marca         Marca del producto - String
     * @param descripcion   Descripcion del producto - String
     * @param categoria     Categoria del producto - String
     * @param stock         Cantidad de productos en oferta - Int
     * @param imagen        Imagen del producto - Image/BLOB
     * @return devuelve true si se inserta el producto en la tabla PRODUCTOS y false si no se ingresa
     */
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

    /**
     * Metodo que actualiza la información de un producto en la tabla PRODUCTOS
     * @param id            Id del producto - Int
     * @param nombre        Nombre del producto - String
     * @param precio        Precio del producto - Double
     * @param marca         Marca del producto - String
     * @param descripcion   Descripcion del producto - String
     * @param categoria     Categoria del producto - String
     * @param stock         Cantidad de productos ofertado - Int
     * @param imagen        Imagen del producto - BLOB/Imagen
     * @return devuelve true si se actualiza el producto en la Tabla PRODUCTOS y false si no se actualiza
     */
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
    /**
     * Metodo que consulta todos los reportes de facturas y pagos realizados para Administrador
     * @param opcion    opcion de seleccion de facturas o pagos
     * @return ResultSet con todos los registros de facturas o pagos
     */
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
    /**
     * Metodo que consulta todos los reportes de facturas y pagos realizados de un Cliente
     * @param opcion    opcion de seleccion de reporte (facturas o pagos)
     * @param id        id del cliente de la Tabla CLIENTES para la busqueda
     * @return ResultSet con los registros de facturas o pagos para un cliente con un determinado id
     */
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
    /**
     * Metodo que obtiene informacion de un producto del Carrito
     * @param producto      nombre del Producto para busqueda
     * @return ResultSet con el registro del Producto que esta en el Carrito
     */
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
    /**
     * Metodo que agrega un producto con stock existente al carrito de compras (Tabla CARRITO_DROP)
     * @param id_producto       Id del producto para agregar al Carrito
     * @param cantidad           Cantidad del producto agregado al Carrito
     * @return true si se ha agregado correctamente el registro del producto a Tabla CARRITO_DROP
     */
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
    /**
     * Metodo que consulta el id del producto para editarlo a partir del nombre
     * @param producto      Nombre del producto para obtener el id
     * @deprecated
     * @return un entero con el id del producto para obtener su información
     */
    public int consultarProductoEditar(String producto){
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Metodo que consulta todos los registros que estan agregados en la Tabla CARRITO_DROP
     * @return ResultSet con los registros que se encuentran en la tabla CARRITO_DROP
     */
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
    /**
     * Metodo que elemina registro de una Tabla de la base de datos
     * @param id        identificador del elemento para eliminar de una Tabla
     * @param tabla     Nombre de la tabla de la base de datos donde se eliminaran registros
     */
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
    /**
     * Metodo que elimina todos los registros de una Tabla de la base de datos
     * @param tabla     Nombre de la tabla de la cual se eliminaran todos los registros
     */
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
    /**
     * Metodo que consulta la informacion de un Cliente con un id especifico
     * @param id    Identificador del Cliente
     * @return ResultSet con todos los valores del registro consultado
     */
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
    /**
     * Metodo que me permite obtener el id de la ultima factura creada
     * @return Int con el valor de id de la ultima factura creada
     */
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
    /**
     * Metodo que permite agregar un registro en la Tabla Factura
     * @param nombre        Nombre del Cliente
     * @param valor         Valor de la factura
     * @param estado        Estado de la factura(Pagado, Pendiente)
     * @param id_cliente    Id del cliente para enlazar la factura
     * @return boolean true si se ha creado la factura o false si no
     * @throws SQLException Excepcion en caso de que no se obtenga nada o no se realice la insercion de la factura
     */
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
    /**
     * Metodo que consulta la ultima factura creada para un cliente
     * @param id_cliente    Id del cliente para buscar su factura
     * @return  Int con el id de la ultima factura del usuario
     */
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
    /**
     * Metodo que agrega un pago realizado de un cliente para una factura
     * @param id_factura    Id de la factura a pagar
     * @param id_cliente    Id del cliente de la factura a pagar
     * @param valor         Valor a Pagar
     * @param nombre        Nombre del Cliente
     */
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
    /**
     * Metodo que actualiza el stock de los productos despues de una compra (Factura)
     * @return boolean true si se realizo la actualizacion o false si no se actualizo
     */
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
    /**
     * Metodo que consulta todos los clientes que estan en la Tabla CLIENTES de la base de datos
     * @return ResultSet con todos los registros de Clientes
     */
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
    /**
     * Metodo que elimina un cliente de la Tabla CLIENTE de la base de datos
     * @param id    id del Cliente a eliminar
     */
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
    /**
     * Metodo que obtiene todos los registros de Producto de la Tabla PRODUCTO
     * @return ResultSet con todos los registros de Productos
     */
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
    /**
     *Metodo que elimina un producto de la Tabla PRODUCTOS de la base de datos
     * @param id    id del Producto a eliminar
     */
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
