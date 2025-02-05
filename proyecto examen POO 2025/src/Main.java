
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Crear y mostrar la ventana en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pantalla de Productos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(800, 600));

            // Crear una instancia de Pantalla_p y añadir su panel a la ventana
            Pantalla_p pantalla = new Pantalla_p();
            frame.add(pantalla.panel1);

            // Ajustar el tamaño de la ventana y hacerla visible
            frame.pack();
            frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
            frame.setVisible(true);
        });
    }
}