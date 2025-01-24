package Clases;

import Interfaces.Principal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        frame.setContentPane(new Principal().JPanelP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 350);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}