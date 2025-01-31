package Clases;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;

public class MetodosBase {
    //Metodo especificado para agregar conexion a base de datos y metodos sobre la base de datos

    private String url="jdbc:mysql://localhost:3306/tienda_db";
    private String user="root";
    private String password="root";
    Connection cn;
    ResultSet rs;
    public MetodosBase(){ //Inicializacion de la conexion
        try {
            cn = DriverManager.getConnection(url,user,password);
            /*if(cn.isValid(5)){
                System.out.println("Conexion Establecida");
            }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int validarLogin(String rol,String correo, String password){
        String sql="";
        int val = rol.compareTo("Cliente");
        if(val<0||val>0){
            sql ="SELECT COUNT(*) FROM Administrador WHERE usuario = ? AND contrasena = ?";
        }else{
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

}
