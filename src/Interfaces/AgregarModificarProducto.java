package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgregarModificarProducto extends Component {
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
    //Parametros para modificar
    JFrame frame = new JFrame();
    Metodos metodos = new Metodos(frame);
    File file;
    MetodosBase base = new MetodosBase();
    //PARA AGREGAR PRODUCTO USAR ESTA INSTANCIA SIN PARAMETROS

    /**
     * Constructor que permite agregar un producto
     */
    public AgregarModificarProducto(){
        SStock.setModel(new SpinnerNumberModel(5,1,100,1));
        /**
         * BCargarImagen permite seleccionar una imagen
         */
        BCargarImagen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seleccionarImagen();
                }
            });
        /**
         * agregarButton - Permite añadir un nuevo Producto una vez este el formulario lleno
         */
        agregarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean insertado = false;
                    if(validarDatos()){
                        insertado = base.insertarProducto(tNombreP.getText(),Double.parseDouble(tPrecioP.getText()),tMarca.getText(),tDescripcionP.getText(),tCategoria.getText(),(int) SStock.getValue(),imageBytes);
                    }
                    if(insertado){
                        JOptionPane.showMessageDialog(null,"PRODUCTO INSERTADO CORRECTAMENTE","",0);
                        metodos.generarVentana("", new Principal_Administrador().JPanelAD,350,600);
                        metodos.cerrarVentana(JPanelAP);
                    }else{
                        JOptionPane.showMessageDialog(null,"NO SE HA PODIDO INGRESAR EL PRODUCTO","",1);
                    }

                }
            });
        /**
         * cancelarButton - Cancela la creacion de un nuevo producto y regresa a la ventana anterior
         */
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodos.generarVentana("", new Principal_Administrador().JPanelAD,725,450);
                metodos.cerrarVentana(JPanelAP);

            }
        });
    }
    //PARA MODIFICAR DATOS USAR ESTA INSTANCIA YA QUE SE REQUIRE EL ID DEL PRODUCTO A MODIFICAR SUS VALORES

    /**
     * Constructor que permite modificar un producto de la Tabla PRODUCTOS dado un Id
     * @param id_producto   Id del producto a modificar
     */
    public AgregarModificarProducto(int id_producto){
        ResultSet rs = base.consultarProductosId(id_producto);
        agregarButton.setText("Actualizar");
        int stock=0;
        try {
            if(rs.next())
            {
                tNombreP.setText(rs.getString("Nombre"));
                tPrecioP.setText(String.valueOf(rs.getDouble("Precio")));
                tMarca.setText(rs.getString("Marca"));
                tDescripcionP.setText(rs.getString("Descripcion"));
                tCategoria.setText(rs.getString("Categoria"));
                stock=rs.getInt("Stock");
                imageBytes = rs.getBytes("Imagen");
            }
            SStock.setModel(new SpinnerNumberModel(stock,1,100,1));
            BufferedImage img = cargarImagen(imageBytes);
            Image escalado =img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(escalado);
            LImagen.setIcon(icon);
            LImagen.setText("");
            tImagenP.setText("Imagen Base de Datos.jpeg");
            /**
             * BCargarImagen - Permite cambiar la imagen existente en el registro de Producto por una nueva
             */
            BCargarImagen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seleccionarImagen();
                }
            });
            /**
             * agregarButton - Actualiza los valores del producto seleccionado por los del formulario
             */
            agregarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean insertado = false;
                    if(validarDatos()){
                        insertado = base.actualizarProducto(id_producto, tNombreP.getText(),Double.parseDouble(tPrecioP.getText()),tMarca.getText(),tDescripcionP.getText(),tCategoria.getText(),(int) SStock.getValue(),imageBytes);
                    }
                    if(insertado){
                        JOptionPane.showMessageDialog(null,"PRODUCTO ACTUALIZADO CORRECTAMENTE","",1);
                        metodos.cerrarVentana(JPanelAP);
                    }else {
                        JOptionPane.showMessageDialog(null, "NO SE HA PODIDO ACTUALIZAR EL PRODUCTO", "", 0);
                    }
                }
            });
            /**
             * cancelarButton - Cancela la accion de modificar producto y regresa a la ventana anterior
             */
            cancelarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    metodos.cerrarVentana(JPanelAP);

                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //FUNCIONES PARA VALIDACION Y EJECUCION

    /**
     * Clase que valida que las casillas de texto para poder ejercer la accion(agregar o modificar)
     * @return true si los datos ingresados son validos o false si hay errores
     */
    public boolean validarDatos(){
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

    /**
     * Clase que permite cargar una imagen desde el directorio del computador y convertirla en bytes
     * para almacenar en la base de datos
     */
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

    /**
     * Clase que convierte la informacion recibida del atributo Imagen de la tabla PRODUCTOS
     * de la base de datos en una Imagen.
     * @param imageBytes    Atributo que recibe la imagen almacenada en la base de datos
     * @return imagen procesada desde los bytes que se recibieron del atributo Imagen de la Tabla PRODUCTOS
     *         de la base de datos
     */
    public BufferedImage cargarImagen(byte[] imageBytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
