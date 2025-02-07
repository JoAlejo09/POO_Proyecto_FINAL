//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
package Clases;
import Interfaces.Principal_Administrador;
import Interfaces.Principal_Cliente;
import Interfaces.Principal_Invitado;

import javax.swing.*;
import java.sql.SQLException;

public class Main{
    /**
     * Main Ejecuta la ventana Principal donde iniciara el programa la cual es Invitado.
     * @param args
     * @author Jose Pila, Xavier Ochoa, Sebastian Betancourt
     * @version 1.0
     * @since 2025
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