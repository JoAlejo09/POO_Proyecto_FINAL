//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
package Clases;
import Interfaces.GenerarFactura;
import Interfaces.Principal_Invitado;
import Interfaces.Reportes;

import javax.swing.*;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame();
        Metodos met = new Metodos(frame);
        //JPanel panel = new GenerarFactura(1,22.33).JPanelGF;
        JPanel panel = new Principal_Invitado(0).JPanelP;
        met.generarVentana("",panel,725,350);
    }
}