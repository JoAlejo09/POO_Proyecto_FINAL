//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
package Clases;
import Interfaces.Principal_Invitado;

import javax.swing.*;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame();
        //MetodosBase base = new MetodosBase();
        Metodos met = new Metodos(frame);
        JPanel panel;
        //panel = new GenerarFactura(1,22.33).JPanelGF;
        panel = new Principal_Invitado(0).JPanelP;
//        panel= new AgregarModificarProducto(2).JPanelAP;
        met.generarVentana("",panel,725,450);

    }
}