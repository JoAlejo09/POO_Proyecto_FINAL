package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgregarCarrito {
    public JPanel JPanelAC;
    private JSpinner SStock;
    private JButton agregarCarritoButton;
    private JButton cancelarButton;
    private JLabel LNombre;
    private JLabel lblImagen;
    private JLabel LPrecio;
    private JLabel LMarca;
    private JLabel LCategoria;
    ResultSet rs;
    JFrame frame = new JFrame();
    Metodos met = new Metodos(frame);
    MetodosBase base = new MetodosBase();
    int id = 0;
    String nombre="",marca="",categoria="";
    double precio = 0;
    int stock=0;
    byte[] imagenBytes;
    public AgregarCarrito(String producto) throws SQLException {
        rs = base.obtenerProductoCarrito(producto);
        if (rs.next()){
            id = rs.getInt("Id");
            nombre = rs.getString("Nombre");
            precio = rs.getDouble("Precio");
            marca = rs.getString("Marca");
            categoria = rs.getString("Categoria");
            stock = rs.getInt("Stock");
            imagenBytes = rs.getBytes("Imagen");
            if (imagenBytes != null) {
                ImageIcon icon = new ImageIcon(imagenBytes);
                Image image = icon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(image));
                lblImagen.setText("");
            } else {
                lblImagen.setText("Sin imagen");
            }
        }
        LNombre.setText(nombre);
        LPrecio.setText("Precio:  $"+precio);
        LMarca.setText("\nMarca:  "+marca);
        LCategoria.setText("Categoria: "+categoria);
        SStock.setModel(new SpinnerNumberModel(1,1,stock,1));

        agregarCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,"Desea agregar al carrito el producto?","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (opcion == JOptionPane.YES_OPTION){
                    boolean agrega = base.agregarCarrito(id,(int) SStock.getValue());
                    if(agrega){
                        JOptionPane.showMessageDialog(null,"PRODUCTO AGREGADO","",1);
                        met.cerrarVentana(JPanelAC);
                    }else{
                        JOptionPane.showMessageDialog(null,"PRODUCTO NO AGREGADO","",0);
                    }
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                met.cerrarVentana(JPanelAC);
            }
        });
    }
}
