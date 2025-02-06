package Interfaces;

import Clases.Metodos;
import Clases.MetodosBase;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reportes {
    private JTable taReportes;
    private JRadioButton facturasRadioButton;
    private JRadioButton pagosRadioButton;
    private JButton generarReporteButton;
    private JButton volverButton;
    private JButton realizarPagoButton;
    public JPanel JPanelRP;
    private JButton generarPDFButton;
    JFrame frame = new JFrame();
    Metodos metodos = new Metodos(frame);
    MetodosBase base = new MetodosBase();
    ButtonGroup opciones;
    ResultSet rs;
    DefaultTableModel modelo;

    //PARA ADMINISTRADOR
    public Reportes(){
        agregarBotones();
        realizarPagoButton.setVisible(false);
        generarReporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 rs = base.consultarReportes(seleccionarBotones());
                 if(seleccionarBotones().equalsIgnoreCase("Facturas")){
                     try {
                         llenarTablaFacturas();
                     } catch (SQLException ex) {
                         throw new RuntimeException(ex);
                     }
                 }else{
                     try {
                         llenarTablaPagos();
                     } catch (SQLException ex) {
                         throw new RuntimeException(ex);
                     }}}
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //metodos.generarVentana("",new Administrador().JPanelAD,300,600);
                metodos.cerrarVentana(JPanelRP);
            }
        });
        generarPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportesPDF(0);
            }
        });
    }

    //PARA CLIENTES
    public Reportes(int id){
       agregarBotones();
       generarReporteButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               rs = base.consultarReportesClientes(seleccionarBotones(),id);
               if(seleccionarBotones().equalsIgnoreCase("Facturas")){
                   try {
                       llenarTablaFacturas();
                   } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                   }
               }else{
                   try {
                       llenarTablaPagos();
                   } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                   }}
           }
       });
       volverButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               metodos.generarVentana("",new Principal_Cliente(1,id).JPanelPC,700,350);
               metodos.cerrarVentana(JPanelRP);
           }
       });
        generarPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportesPDF(id);
            }
        });

    }
    public void agregarBotones(){
        opciones = new ButtonGroup();
        opciones.add(facturasRadioButton);
        opciones.add(pagosRadioButton);
    }
    public String seleccionarBotones(){
        if(facturasRadioButton.isSelected()){
            return "Facturas";
        }else{
            return "Pagos";
        }
    }
    public void llenarTablaFacturas() throws SQLException {
            modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Fecha");
            modelo.addColumn("Nombre");
            modelo.addColumn("Valor");
            modelo.addColumn("Estado");
            modelo.addColumn("ID Cliente");

        while (rs.next()) {
            Object[] fila = new Object[6]; // 6 columnas en la tabla
            fila[0] = rs.getInt("Id");
            fila[1] = rs.getTimestamp("Fecha"); // Puede ser Timestamp o String según tu preferencia
            fila[2] = rs.getString("Nombre");
            fila[3] = rs.getDouble("Valor");
            fila[4] = rs.getString("Estado");
            fila[5] = rs.getInt("Id_cliente");

            modelo.addRow(fila);
        }

        // Asignar el modelo a la JTable
        taReportes.setModel(modelo);
    }
    public void llenarTablaPagos() throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nro Pago");
        modelo.addColumn("Fecha");
        modelo.addColumn("Nombre");
        modelo.addColumn("Valor");
        modelo.addColumn("Cliente");
        modelo.addColumn("Nro Factura");
        while (rs.next()) {
            Object[] fila = new Object[6]; // 6 columnas en la tabla
            fila[0] = rs.getInt("Id");
            fila[1] = rs.getTimestamp("Fecha"); // Puede ser Timestamp o String según tu preferencia
            fila[2] = rs.getString("Nombre");
            fila[3] = rs.getDouble("Valor");
            fila[4] = rs.getString("Id_cliente");
            fila[5] = rs.getInt("Id_factura");
            modelo.addRow(fila);
        }

        // Asignar el modelo a la JTable
        taReportes.setModel(modelo);
    }
    public void generarReportesPDF(int id){
        String encabezado="";//VALIDACION DE QUIEN ENVIA REPORTE
        if(id == 0){
            encabezado = "REPORTE PARA ADMINSTRADOR.";
        }else if(id>0){
            encabezado = "REPORTE DE CLIENTE: "+base.hallarNombre(id)+ ".";
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaStr = now.format(formatter);
        encabezado+="\nFecha: "+fechaStr;

        //ELECCION DE DONDE ALMACENAR
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("GUARDAR PDF");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // Filtro para archivos .pdf
        filechooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));
        int seleccion = filechooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = filechooser.getSelectedFile();
            String rutaArchivo = archivo.getAbsolutePath();
            if(!rutaArchivo.toLowerCase().endsWith(".pdf"))
            {
                rutaArchivo+= ".pdf";
            }
            Document documento = new Document();
            try{
                PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
                documento.open();
                documento.add(new Paragraph("REPORTE DE DATOS"));
                documento.add(new Paragraph("\n"));
                documento.add(new Paragraph(encabezado));
                documento.add(new Paragraph("\n"));
                PdfPTable tablaPDF = new PdfPTable(taReportes.getColumnCount());

                TableModel modelo = taReportes.getModel();
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    tablaPDF.addCell(new Phrase(modelo.getColumnName(i), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                }
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        tablaPDF.addCell(modelo.getValueAt(i, j).toString());
                    }
                }
                documento.add(tablaPDF);
                documento.close();
                JOptionPane.showMessageDialog(null, "PDF guardado en: " + rutaArchivo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
