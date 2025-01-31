package Interfaces;

import Clases.MetodosBase;
import Clases.Validaciones;

import javax.swing.*;
import java.awt.event.*;

public class Login {
    private JTextField tUsuario;
    private JPasswordField pPassword;
    private JButton bRegistrarse;
    private JButton iniciarSesionButton;
    private JCheckBox mantenerLaSesionIniciadaCheckBox;
    private JButton cancelarButton;
    public JPanel JPanelL;
    JFrame frame;
    MetodosBase metodos = new MetodosBase();
    Validaciones val = new Validaciones();

    public Login (){
        bRegistrarse.setBorder(null); // Quita el borde
        bRegistrarse.setContentAreaFilled(false); // Evita el fondo del botón
        bRegistrarse.setFocusPainted(false);
        iniciarSesionButton.setEnabled(false);
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
        tUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(tUsuario.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"No puede estar el campo vacio","",1);
                }else if(!val.validarCorreo(tUsuario.getText())){
                    JOptionPane.showMessageDialog(null,"Correo electronico no valido","",1);
                }else{
                    iniciarSesionButton.setEnabled(true);
                }
            }
        });
        pPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(pPassword.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"No puede estar el campo vacio","",0);
                }else if(!val.validarPassword(pPassword)){
                    JOptionPane.showMessageDialog(null,"Contraseña Incorrecta","",0);
                }else{
                    iniciarSesionButton.setEnabled(true);
                }

            }
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tUsuario.addComponentListener(new ComponentAdapter() {
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int log = metodos.validarLogin(tUsuario.getText(),new String(pPassword.getPassword()));
                if(log==1) {
                    //Se envia a ventana de Usuario
                }else if(log==2){
                    //Se envia a ventana de Administrador
                }else{
                    JOptionPane.showMessageDialog(null,"Correo o Contraseña incorrectos.","",0);
                    tUsuario.setText("");
                    pPassword.setText("");
                }
            }
        });
    }
}
