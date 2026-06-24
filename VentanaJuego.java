import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

// JFrame es la "ventana" típica de Windows/Mac
public class VentanaJuego extends JFrame {

    // Constructor de nuestra ventana
    public VentanaJuego() {
        // Configuramos la ventana básica
        this.setTitle("Crazy Snack Rush TEC");
        this.setSize(800, 600); // Ancho x Alto en píxeles
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Que se cierre el programa al darle a la 'X'
        this.setResizable(false); // Para que el jugador no pueda cambiar el tamaño y arruinar la cuadrícula
        
        // Agregamos nuestro "lienzo" (el panel donde dibujaremos) a la ventana
        PanelCocina panel = new PanelCocina();
        this.add(panel);
        
        // Hacemos que la ventana sea visible
        this.setVisible(true);
    }

    // Un JPanel es como un lienzo en blanco dentro de la ventana donde podemos dibujar
    // Lo hacemos como una "clase interna" (inner class) por ahora para mantenerlo simple
    private class PanelCocina extends JPanel {
        
        // Este método paintComponent es de Java. Se llama automáticamente cada vez que la pantalla necesita dibujarse
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Siempre hay que llamar al super

            // 1. DIBUJAR EL FONDO
            g.setColor(new Color(200, 200, 200)); // Color gris claro
            g.fillRect(0, 0, 800, 600); // Rellenamos todo el fondo

            // 2. DIBUJAR LA CUADRÍCULA (Simulando el piso de la cocina)
            g.setColor(Color.BLACK); // Color de las líneas
            int tamanoCelda = 50; // Cada cuadrito será de 50x50 píxeles

            // Dibujamos las líneas verticales
            for (int x = 0; x <= 800; x += tamanoCelda) {
                g.drawLine(x, 0, x, 600);
            }

            // Dibujamos las líneas horizontales
            for (int y = 0; y <= 600; y += tamanoCelda) {
                g.drawLine(0, y, 800, y);
            }
        }
    }
}