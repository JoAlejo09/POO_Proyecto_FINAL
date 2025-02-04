//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA

package Clases;


import javax.swing.*;
import java.sql.*;

public class MetodosBase {
    //Metodo especificado para agregar conexion a base de datos y metodos sobre la base de datos
   /* private static final String HOST="bdbsjb7v8o8wa0pot4lt-mysql.services.clever-cloud.com";
    private static final String DB="bdbsjb7v8o8wa0pot4lt";
    private static final String USER="uspitlplqxwpi1ft";
    private static final String PORT="3306";
    private static final String PASSWORD="vDVAQEeEPO9pvXHzs0ih";
    private static final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB; //DIRECCIONAMIENTO DE LA BASE DE DATOS PARA CONEXION EN LA NUBE
*/

    private String URL = "jdbc:mysql://localhost:3306/tienda_db";
    private String USER = "root";
    private String PASSWORD = "root";
    Connection cn;
    ResultSet rs;

    public MetodosBase() { //Inicializacion de la conexion
        try {
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int validarLogin(String rol, String correo, String password) {
        String sql = "";
        int val = rol.compareTo("Cliente");
        if (val < 0 || val > 0) {
            sql = "SELECT id FROM Administrador WHERE usuario = ? AND contrasena = ?";
        } else {
            sql = "SELECT id FROM Cliente WHERE CorreoElectronico = ? AND Contrasena = ?";
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
        String sql = "INSERT INTO Cliente (NombreCompleto, CorreoElectronico, Contrasena, Cedula, Direccion) VALUES (?,?,?,?,?)";
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

        }
        return rt;
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
    public ResultSet realizarBusqueda(String criterioBusqueda,String valorBusqueda){
        String query = "SELECT Id, Nombre, Precio, Marca, Descripcion, Categoria, Imagen, Stock FROM PRODUCTOS WHERE " + criterioBusqueda + " LIKE '%" + valorBusqueda + "%'";
        try{
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(query);
        }catch (SQLException e){

        }
        return rs;
    }
    public String hallarNombre(int id){
        String query = "SELECT NombreCompleto FROM Cliente WHERE id = ?";
        try{
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
