//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA


package Clases;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

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
    public boolean validarPassword(JPasswordField password){
        char[] pass = password.getPassword();
        if(pass.length<8){
            return false;
        }else{
            return true;
        }

    }
}
