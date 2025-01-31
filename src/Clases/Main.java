package Clases;

import Interfaces.Principal_Invitado;

import javax.swing.*;
import java.security.Principal;

public class Main{
    public static void main(String[] args) {
        Metodos met = new Metodos(new Principal_Invitado().JPanelP,600,350);
    }
}