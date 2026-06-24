import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaJuego extends JFrame {

    // Constructor de la ventana
    public VentanaJuego() {
        // Configurar la ventana básica
        this.setTitle("Crazy Snack Rush TEC");
        this.setSize(800, 600); // Ancho x Alto en píxeles
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Que se cierre el programa al darle a la 'X'
        this.setResizable(false); // Para que el jugador no pueda cambiar el tamaño y arruinar la cuadrícula
        
        // Agregar lienzo a la ventana
        PanelCocina panel = new PanelCocina();
        this.add(panel);
        
        // Hacer la ventana visible
        this.setVisible(true);
    }

    // Hacer una JPanel una clase interna para simplificar
    private class PanelCocina extends JPanel {
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Siempre hay que llamar al super

            // FONDO
            g.setColor(new Color(200, 200, 200)); // Color gris claro
            g.fillRect(0, 0, 800, 600); // Rellenamos todo el fondo

            // CUADRÍCULA
            g.setColor(Color.BLACK); // Color de las líneas
            int tamanoCelda = 50; // Cada cuadrito será de 50x50 píxeles

            // Líneas verticales
            for (int x = 0; x <= 800; x += tamanoCelda) {
                g.drawLine(x, 0, x, 600);
            }

            // Líneas horizontales
            for (int y = 0; y <= 600; y += tamanoCelda) {
                g.drawLine(0, y, 800, y);
            }
        }
    }
}