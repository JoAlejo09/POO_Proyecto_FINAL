package Clases;

import Interfaces.Principal_Invitado;

import javax.swing.*;
import java.security.Principal;

public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        frame.setContentPane(new Principal_Invitado().JPanelP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}