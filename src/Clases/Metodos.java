//proyecto poo
//OCHOA,BETANCOURT,PILA
package Clases;
import javax.swing.*;
/**
 * Clase que gestiona las acciones con las ventanas (creacion, cierre)
 */
public class Metodos extends JFrame {
    JFrame frame = new JFrame();
    /**
     * Metodos es el constructor que llama al Frame que recibira las componentes de la siguiente ventana
     *
     * @param frame Frame recibe las componentes para inicializar la ventana
     *              dependiendo de los paramet
     */
    public Metodos(JFrame frame) {
        this.frame = frame;
    }
    /**
     * Metodo que crea la ventana y la muestra en pantalla
     * @param titulo    Nombre de la Ventana
     * @param panel     Contenedor de componentes a mostrar en la ventana (botones, paneles, texto, etc)
     * @param w         Ancho de la ventana
     * @param h         Longitud de la ventana
     */
    public void generarVentana(String titulo, JPanel panel, int w, int h) {
        frame.setTitle(titulo);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(true);
        frame.setSize(w, h);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metodo que cierra una ventana que no se va a usar
     * @param panel     Contenedor de componentes a mostrar en la ventana (botones, paneles, texto, etc)
     */
    public void cerrarVentana(JPanel panel) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel); // Obtener la ventana que contiene el JPanel
        if (frame != null) {
            //frame.setVisible(false);
            frame.dispose();
        }
    }
}
