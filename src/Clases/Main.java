//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
package Clases;
import Interfaces.Principal_Invitado;
import Interfaces.Reportes;

import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Metodos met = new Metodos(frame);
//        JPanel panel = new Principal_Invitado(0).JPanelP;
        JPanel panel = new Reportes(1).JPanelRP;
        met.generarVentana("",panel,700,350);
    }
}