package Interfaces;

import Clases.Metodos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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

    public Registrarse(){
        bLogin.setBorder(null); // Quita el borde
        bLogin.setContentAreaFilled(false);
        bLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Metodos met = new Metodos(new Login().JPanelL,400,350);

            }
        });
        tDireccion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                habilitarBoton();
            }
        });
    }
    public void habilitarBoton(){
        if(!tNombre.getText().isEmpty()&&
                !tApellido.getText().isEmpty()&&
                pContrasena.getPassword().length>0&&
                !tCedula.getText().isEmpty()&&
                !tDireccion.getText().isEmpty()){
            bRegistrarse.setEnabled(true);
        }
    }
}
