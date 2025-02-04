//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
package Clases;
import Interfaces.Principal_Invitado;

import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Metodos met = new Metodos(frame);
        JPanel panel = new Principal_Invitado().JPanelP;
        met.generarVentana("",panel,600,350);
    }
}