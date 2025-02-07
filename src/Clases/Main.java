//Proyecto poo
//OCHOA,BETANCOURT,PILA,CARDENAS
package Clases;
import Interfaces.Principal_Invitado;
import javax.swing.*;
/**
 * Main Ejecuta la ventana Principal donde iniciara el programa la cual es Invitado.
 * @author Jose Pila, Xavier Ochoa, Sebastian Betancourt, Mateo Cardenas
 * @version 1.0
 * @since 2025
 */
public class Main{
    /**
     * Main Ejecuta la ventana Principal donde iniciara el programa la cual es Invitado.
     * @param args
     */
    public static void main(String[] args){
        /**
         * Metodos de llamado para inicializar la ventana Inicial
         */
        JFrame frame = new JFrame();
        Metodos met = new Metodos(frame);
        JPanel panel;
        panel = new Principal_Invitado(0).JPanelP;
        met.generarVentana("",panel,725,450);
    }
}