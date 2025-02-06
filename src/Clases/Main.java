//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
package Clases;
import Interfaces.Principal_Administrador;
import Interfaces.Principal_Cliente;
import Interfaces.Principal_Invitado;

import javax.swing.*;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame();
        Metodos met = new Metodos(frame);
        JPanel panel;
        panel = new Principal_Invitado(0).JPanelP;
        met.generarVentana("",panel,725,450);

    }
}