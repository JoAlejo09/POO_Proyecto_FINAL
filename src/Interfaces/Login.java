package Interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton bRegistrarse;
    private JButton iniciarSesionButton;
    private JCheckBox mantenerLaSesionIniciadaCheckBox;
    private JButton cancelarButton;
    public JPanel JPanelL;
    public JFrame frame;
    public Login (){
        bRegistrarse.setBorder(null); // Quita el borde
        bRegistrarse.setContentAreaFilled(false); // Evita el fondo del botón
        bRegistrarse.setFocusPainted(false);

        bRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Registrarse().JPanelR);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 300);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}
