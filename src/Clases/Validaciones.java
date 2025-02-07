//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA


package Clases;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase Validaciones que valida que se cumpla los criterios
 */
public class Validaciones {
    /**
     * Metodo que valida que sea la estructura de un correo electronico
     * @param correo String del correo a validar
     * @return  boolean true si cumple con la validacion del correo y false si no
     */
    public boolean validarCorreo(String correo){
        String emailRegex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(correo);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo que valida que el password tenga minimo 8 caracteres
     * @param password  Contraseña a validar
     * @return true si la contraseña es valida y false si no
     */
    public boolean validarPassword(JPasswordField password){
        char[] pass = password.getPassword();
        if(pass.length<8){
            return false;
        }else{
            return true;
        }

    }
}
