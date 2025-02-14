package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que permite visualizar un catalogo de productos y realizar otras acciones sobre este
 * dependiendo del usuario
 */
public class CatalogoProductos {
    public JPanel panel1;
    private JTextField ingreso_de_busqueda;
    private JButton boton_de_volver;
    private JTable tabla_de_productos;
    private JTree categorias_de_productos;
    private JButton boton_de_busqueda;
    private JComboBox<String> buscar_segun;
    private JButton agregarCarritoButton;
    private JButton editarProductoButton;
    JFrame frame = new JFrame();
    Metodos metodos= new Metodos(frame);
    MetodosBase met = new MetodosBase();

    /**
     *
     * @param estado    Rol del usuario
     * @param id        Id en caso de que el rol sea Cliente
     */
    public CatalogoProductos(int estado, int id) {
        cargarDatosEnTabla("TODOS");  // Inicializar con todos los productos
        cargarCategoriasEnTree();
        inicializarComboBox();
        validarBotones(id,estado); //Valida que botones debe aparecer si es invitado,cliente o administrador
        /**
         *boton_de_busqueda - Realiza la busqueda del producto
         *boton_de_volver - Cancela la accion de Catalogo Productos y vuelve a la ventana anterior
         */
        // Acciones de los botones
        boton_de_busqueda.addActionListener(e -> realizarBusqueda());
        boton_de_volver.addActionListener(e -> {
            abrirFormularioRegistro(id,estado);
            metodos.cerrarVentana(panel1);  // Cerrar la ventana actual
        });
        /**
         *agregarCarritoButton - Agrega al carrito un producto seleccionado del catalogo
         */
        agregarCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String producto = obtenerProducto();
                try {
                    metodos.generarVentana("",new AgregarCarrito(producto).JPanelAC,300,350);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        /**
         *editarProductoButton - Permite editar un producto seleccionado
         */
        editarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String producto = obtenerProducto();
                int id=0;
                id = met.consultarProductoEditar(producto);
                if(id==0){
                    JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGUN PRODUCTO PARA EDITAR","",1);
                }else{
                    metodos.generarVentana("",new AgregarModificarProducto(id).JPanelAP,700,450);

                }

            }
        });
    }

    /**
     *
     * @param id - Id del cliente
     * @param est - Estado del usuario
     * @return devuelve el panel el cual se condiciona para la siguiente ventana sea para Invitado,
     * Cliente o Administrador
     */
    private JPanel validarCierreVentana(int id, int est){
        JPanel panel = null;
        if(est==1){
            panel = new Principal_Cliente(1,id).JPanelPC;
        }else if(est==0){
            panel = new Principal_Invitado(0).JPanelP;
        }else if(est==2){
            panel = new Principal_Administrador().JPanelAD;
        }else if(est==3){
            panel=panel = new Principal_Administrador().JPanelAD;
        }else{
            panel = null;
        }
        return panel;
    }

    /**
     * Metodo que valida las componentes que se muestran al usuario dependiendo de su estado
     * @param id - Id del Cliente solo para estado del usuario Cliente
     * @param est - Estado del usuario del programa
     */
    private void validarBotones(int id,int est){
        if(est==0){
            agregarCarritoButton.setVisible(false);
            editarProductoButton.setVisible(false);
        }else if(est==1){
            agregarCarritoButton.setVisible(true);
            editarProductoButton.setVisible(false);
        }else if(est==2){
            agregarCarritoButton.setVisible(false);
            editarProductoButton.setVisible(true);
        }else{
            agregarCarritoButton.setVisible(false);
            editarProductoButton.setVisible(false);
        }
    }

    /**
     * Metodo que inicializa el ComboBox para busquedas
     */
    private void inicializarComboBox() {
        buscar_segun.removeAllItems();
        buscar_segun.addItem("Nombre");
        buscar_segun.addItem("Marca");
        buscar_segun.addItem("Precio");
    }

    /**
     * Metodo que carga datos de los productos que sean de una determinada categoria
     * en la Tabla para mostrar
     * @param categoria - Parametro de busqueda de productos
     */
    private void cargarDatosEnTabla(String categoria) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Producto 1");
        modelo.addColumn("Producto 2");
        modelo.addColumn("Producto 3");

        try {
            ResultSet rs = met.consultarProductos(categoria);
            List<ProductoCelda> listaProductos = new ArrayList<>();
            // Cargar los productos en una lista
            while (rs.next()) {
                byte[] imagenBytes = rs.getBytes("Imagen");
                String nombreProducto = rs.getString("Nombre");

                ImageIcon imagenIcon = (imagenBytes != null) ? new ImageIcon(imagenBytes) : null;
                ProductoCelda producto = new ProductoCelda(imagenIcon, nombreProducto);

                listaProductos.add(producto);
            }
            // Llenar la tabla con filas de 3 productos por fila
            for (int i = 0; i < listaProductos.size(); i += 3) {
                Object[] fila = new Object[3];

                // Agregar productos a la fila asegurando que los espacios vacíos se muestren como "Vacío"
                for (int j = 0; j < 3; j++) {
                    if (i + j < listaProductos.size()) {
                        fila[j] = listaProductos.get(i + j);
                    } else {
                        fila[j] = new ProductoCelda(null, "Vacío"); // Celda vacía
                    }
                }

                modelo.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        tabla_de_productos.setModel(modelo);

        tabla_de_productos.getColumnModel().getColumn(0).setPreferredWidth(145);
        tabla_de_productos.getColumnModel().getColumn(1).setPreferredWidth(145);
        tabla_de_productos.getColumnModel().getColumn(2).setPreferredWidth(145);

        tabla_de_productos.getColumnModel().getColumn(0).setCellRenderer(new ProductoCellRenderer());
        tabla_de_productos.getColumnModel().getColumn(1).setCellRenderer(new ProductoCellRenderer());
        tabla_de_productos.getColumnModel().getColumn(2).setCellRenderer(new ProductoCellRenderer());

        tabla_de_productos.setRowHeight(150);
        tabla_de_productos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //VALIDACION QUE SE PUEDA SELECCIONAR UN SOLO ELEMENTO
        tabla_de_productos.setColumnSelectionAllowed(false);
        tabla_de_productos.setRowSelectionAllowed(false);
        tabla_de_productos.setCellSelectionEnabled(true);
        tabla_de_productos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Metodo que permite realizar busqueda de productos en funcion de categoria y nombre del producto
     */
    private void realizarBusqueda() {
        String criterioBusqueda = (String) buscar_segun.getSelectedItem();
        String valorBusqueda = ingreso_de_busqueda.getText().trim();

        // Si el campo de búsqueda está vacío, no hacer nada
        if (valorBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un valor para buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Realizar la búsqueda
        DefaultTableModel modelo = (DefaultTableModel) tabla_de_productos.getModel();

        try {
            ResultSet rs = met.realizarBusqueda(criterioBusqueda,valorBusqueda);
            List<ProductoCelda> listaProductos = new ArrayList<>();

            // Cargar los productos en una lista
            while (rs.next()) {
                byte[] imagenBytes = rs.getBytes("Imagen");
                String nombreProducto = rs.getString("Nombre");

                ImageIcon imagenIcon = (imagenBytes != null) ? new ImageIcon(imagenBytes) : null;
                ProductoCelda producto = new ProductoCelda(imagenIcon, nombreProducto);

                listaProductos.add(producto);
            }

            // Limpiar el modelo de la tabla antes de agregar nuevos resultados
            modelo.setRowCount(0);

            // Llenar la tabla con filas de 3 productos por fila
            for (int i = 0; i < listaProductos.size(); i += 3) {
                Object[] fila = new Object[3];

                // Agregar productos a la fila asegurando que los espacios vacíos se muestren como "Vacío"
                for (int j = 0; j < 3; j++) {
                    if (i + j < listaProductos.size()) {
                        fila[j] = listaProductos.get(i + j);
                    } else {
                        fila[j] = new ProductoCelda(null, "Vacío"); // Celda vacía
                    }
                }

                modelo.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar la búsqueda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo que permite cargar el listado de Categorias que estan en la base de datos
     */
    private void cargarCategoriasEnTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categorías");

        DefaultMutableTreeNode todos = new DefaultMutableTreeNode("TODOS");
        DefaultMutableTreeNode computadorasYLaptops = new DefaultMutableTreeNode("Computadoras y Laptops");
        DefaultMutableTreeNode telefonosYSmartphones = new DefaultMutableTreeNode("Teléfonos y Smartphones");
        DefaultMutableTreeNode tabletasYEReaders = new DefaultMutableTreeNode("Tabletas y E-Readers");
        DefaultMutableTreeNode televisoresYAudio = new DefaultMutableTreeNode("Televisores y Audio");
        DefaultMutableTreeNode electronicaPortatilYGadgets = new DefaultMutableTreeNode("Electrónica Portátil y Gadgets");
        DefaultMutableTreeNode redesYConectividad = new DefaultMutableTreeNode("Redes y Conectividad");
        DefaultMutableTreeNode gaming = new DefaultMutableTreeNode("Gaming");
        DefaultMutableTreeNode hogarInteligente = new DefaultMutableTreeNode("Hogar Inteligente");
        DefaultMutableTreeNode almacenamientoYBackup = new DefaultMutableTreeNode("Almacenamiento y Backup");
        DefaultMutableTreeNode perifericosYAccesorios = new DefaultMutableTreeNode("Periféricos y Accesorios");
        DefaultMutableTreeNode cuidadoYReparacion = new DefaultMutableTreeNode("Cuidado y Reparación de Dispositivos");

        root.add(todos);
        root.add(computadorasYLaptops);
        root.add(telefonosYSmartphones);
        root.add(tabletasYEReaders);
        root.add(televisoresYAudio);
        root.add(electronicaPortatilYGadgets);
        root.add(redesYConectividad);
        root.add(gaming);
        root.add(hogarInteligente);
        root.add(almacenamientoYBackup);
        root.add(perifericosYAccesorios);
        root.add(cuidadoYReparacion);

        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        categorias_de_productos.setModel(treeModel);

        // Agregar TreeSelectionListener para escuchar cuando se seleccione una categoría
        categorias_de_productos.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) categorias_de_productos.getLastSelectedPathComponent();
            if (nodoSeleccionado != null) {
                String categoriaSeleccionada = nodoSeleccionado.toString();
                cargarDatosEnTabla(categoriaSeleccionada);  // Filtrar por la categoría seleccionada
            }
        });
    }

   private void abrirFormularioRegistro(int id, int est) {
       JFrame frame = new JFrame();
        metodos = new Metodos(frame);
        metodos.generarVentana("",validarCierreVentana(id,est),600,350);
    }
    private String obtenerProducto(){
        int fila = tabla_de_productos.getSelectedRow();
        int columna = tabla_de_productos.getSelectedColumn();
        String nombreProducto="";

        if (fila != -1 && columna != -1) { // Verificar que haya una celda seleccionada
            ProductoCelda producto = (ProductoCelda) tabla_de_productos.getValueAt(fila, columna);
            nombreProducto = producto.getNombre(); // Suponiendo que ProductoCelda tiene un método getNombre()
            System.out.println("Producto seleccionado: " + nombreProducto);
        } else {
            System.out.println("No hay ninguna celda seleccionada.");
        }
        return nombreProducto;
    }
    // Método para cerrar la ventana actual

    static class ProductoCelda {
        private ImageIcon imagen;
        private String nombre;

        public ProductoCelda(ImageIcon imagen, String nombre) {
            this.imagen = imagen;
            this.nombre = nombre;
        }

        public ImageIcon getImagen() {
            return imagen;
        }

        public String getNombre() {
            return nombre;
        }
    }

    static class ProductoCellRenderer extends JPanel implements TableCellRenderer {
        private JLabel lblImagen;
        private JLabel lblNombre;

        public ProductoCellRenderer() {
            setLayout(new BorderLayout());
            lblImagen = new JLabel();
            lblNombre = new JLabel("", SwingConstants.CENTER);
            lblNombre.setFont(new Font("Arial", Font.PLAIN, 12));

            add(lblImagen, BorderLayout.CENTER);
            add(lblNombre, BorderLayout.SOUTH);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ProductoCelda) {
                ProductoCelda producto = (ProductoCelda) value;

                // Si el producto es "Vacío", no mostramos imagen y solo ponemos el texto
                if (producto.getImagen() != null) {
                    Image img = producto.getImagen().getImage();
                    Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(newImg));
                } else {
                    lblImagen.setIcon(null);
                }

                lblNombre.setText(producto.getNombre());

                if (isSelected) {
                    setBackground(new Color(200, 200, 255));
                } else {
                    setBackground(Color.WHITE);
                }
            }
            return this;
        }
    }
}
