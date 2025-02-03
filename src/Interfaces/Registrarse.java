package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;
import Clases.Validaciones;

import javax.swing.*;
import java.awt.event.*;

public class Registrarse {
    private JTextField tNombre;
    private JTextField tApellido;
    private JPasswordField pContrasena;
    private JTextField tCedula;
    private JTextField tDireccion;
    private JButton bRegistrarse;
    private JButton cancelarButton;
    public JPanel JPanelR;
    private JButton bLogin;
    private JTextField tCorreo;
    Metodos met;
    MetodosBase bas = new MetodosBase();
    public Registrarse(){
        bLogin.setBorder(null); // Quita el borde
        bLogin.setContentAreaFilled(false);
        bLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 met = new Metodos(new Login().JPanelL,400,350);

            }
        });
        tDireccion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                habilitarBoton();
            }
        });
        tNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    e.consume(); // Evita que el carácter se escriba en el JTextField
                    //lblMensaje.setText("⚠ Solo se permiten letras");
                } else {
//                    lblMensaje.setText("✓ Correcto");
                }
                super.keyTyped(e);
            }
        });
        bRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean rs = bas.insertarCliente(tNombre.getText(),tApellido.getText(),tCorreo.getText(), new String(pContrasena.getPassword()),tCedula.getText(),
                        tDireccion.getText());
                if(rs){
                    JOptionPane.showMessageDialog(null,"USUARIO INGRESADO CORRECTAMENTE","",0);
                    met = new Metodos(new Login().JPanelL,400,350);
                }else{
                    JOptionPane.showMessageDialog(null,"NO SE HA PODIDO REGISTRAR EL USUARIO","",1);
                }



            }
        });
        tCorreo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                Validaciones val = new Validaciones();
                if (!val.validarCorreo(tCorreo.getText())){
                    JOptionPane.showMessageDialog(null,"CORREO INGRESADO INCORRECTAMENTE","",1);
                    tCorreo.setText("");
                }

            }
        });
    }
    public void habilitarBoton(){
        if(!tNombre.getText().isEmpty()&&!tApellido.getText().isEmpty()&&
                pContrasena.getPassword().length>0&&!tCedula.getText().isEmpty()&&
                !tDireccion.getText().isEmpty()){
            bRegistrarse.setEnabled(true);
        }
    }
}
