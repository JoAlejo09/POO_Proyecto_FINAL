//proyecto poo
//OCHOA,BETANCOURT,CARDENAS,PILA
package Interfaces;

import Clases.Metodos;
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
    private JComboBox cbRol;
    JFrame frame = new JFrame();
    JPanel panel;
    Metodos met = new Metodos(frame);
    MetodosBase metodos = new MetodosBase();
    Validaciones val = new Validaciones();
    public int est;
    public Login (int estado){
        bRegistrarse.setBorder(null); // Quita el borde
        bRegistrarse.setContentAreaFilled(false); // Evita el fondo del botón
        bRegistrarse.setFocusPainted(false);
        iniciarSesionButton.setEnabled(false);
        cbRol.addItem("Administrador");
        cbRol.addItem("Cliente");
        bRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel=new Registrarse(0).JPanelR;
                met.generarVentana("",panel,500,300);
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
        tUsuario.addComponentListener(new ComponentAdapter() {
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rol = (String) cbRol.getSelectedItem();
                System.out.println(rol);
                int rl = rol.compareTo("Cliente");
                int log = metodos.validarLogin(rol,tUsuario.getText(),new String(pPassword.getPassword()));
                if((rl>0||rl<0)&log>0) {
                    JOptionPane.showMessageDialog(null,"Bienvenido Administrador","",1);
                    //Se envia a ventana Administrador
                    est=1;
                }else if(rl==0 && log>0){
                    JOptionPane.showMessageDialog(null,"Bienvenido Cliente","",1);
                    est=1;
                    panel = new Principal_Cliente(est,log).JPanelPC;
                    met.generarVentana("",panel,800,350);
                    met.cerrarVentana(JPanelL);
                }else{
                    JOptionPane.showMessageDialog(null,"Correo o Contraseña incorrectos.","",0);
                    tUsuario.setText("");
                    pPassword.setText("");
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new Principal_Invitado(est).JPanelP;
                met = new Metodos(frame);
                met.generarVentana("",panel,600,350);
                met.cerrarVentana(JPanelL);
            }
        });
    }
}
