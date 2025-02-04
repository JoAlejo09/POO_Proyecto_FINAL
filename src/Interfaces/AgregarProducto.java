package Interfaces;

import Clases.MetodosBase;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class AgregarProducto extends Component {
    private JTextField tNombreP;
    private JTextField tPrecioP;
    private JSpinner SStock;
    private JTextArea tDescripcionP;
    private JTextField tImagenP;
    private JButton BCargarImagen;
    private JButton agregarButton;
    private JButton cancelarButton;
    public JPanel JPanelAP;
    private JTextField tCategoria;
    private JTextField tMarca;
    private JLabel LImagen;
    private byte[] imageBytes;
    File file;
    MetodosBase base = new MetodosBase();


    public AgregarProducto(){
        SStock.setModel(new SpinnerNumberModel(5,1,100,1));
        BCargarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();

            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean insertado = false;
                if(validarNombre()){
                    insertado = base.insertarProducto(tNombreP.getText(),Double.parseDouble(tPrecioP.getText()),tMarca.getText(),tDescripcionP.getText(),tCategoria.getText(),(int) SStock.getValue(),imageBytes);
                }
                if(insertado){
                    JOptionPane.showMessageDialog(null,"PRODUCTO INSERTADO CORRECTAMENTE","",0);
                }else{
                    JOptionPane.showMessageDialog(null,"NO SE HA PODIDO INGRESAR EL PRODUCTO","",1);
                }

            }
        });
    }
    public boolean validarNombre(){
        boolean status = true;
        if (tNombreP.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Debe Ingresar el nombre del producto","",0);
            tNombreP.requestFocus();
            status=false;
        }
        else if(tPrecioP.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Debe Ingresar el costo del producto","",0);
            tPrecioP.requestFocus();
            status=false;
        }else if(tMarca.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Debe Ingresar la marca del producto","",0);
            tMarca.requestFocus();
            status=false;
        }else if(tCategoria.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Ingresar la categoria a la que pertenece el producto", "", 0);
            tCategoria.requestFocus();
            status = false;
        }
        return status;
    }
    public void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Imagen (*.jpg, *.png, *.gif)", "jpg", "png", "gif");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            tImagenP.setText(file.getName());
            tImagenP.setEditable(false);
            ImageIcon icon = new ImageIcon(new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            LImagen.setIcon(icon);
            LImagen.setText("");
            try {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                imageBytes = bos.toByteArray();
                fis.close();
                bos.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al leer la imagen: " + ex.getMessage());
            }

        }
    }

}
