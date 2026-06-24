import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaJuego extends JFrame {

    private Cocina cocinaActual; // Lógica del juego

    // Constructor de la ventana para recibir la cocina
    public VentanaJuego(Cocina cocina) {
        this.cocinaActual = cocina;

        // Ventana básica
        this.setTitle("Crazy Snack Rush TEC");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        PanelCocina panel = new PanelCocina();
        this.add(panel);
        
        this.setVisible(true);
    }

    // Clase interna para manejar los dibujos
    private class PanelCocina extends JPanel {
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 

            // FONDO
            g.setColor(new Color(200, 200, 200)); 
            g.fillRect(0, 0, 800, 600); 

            // CUADRÍCULA
            g.setColor(Color.BLACK); 
            int tamanoCelda = 50; 

            for (int x = 0; x <= 800; x += tamanoCelda) {
                g.drawLine(x, 0, x, 600);
            }
            for (int y = 0; y <= 600; y += tamanoCelda) {
                g.drawLine(0, y, 800, y);
            }

            // CHEFS
            if (cocinaActual != null && cocinaActual.getChefs() != null) {
                List<Chef> chefs = cocinaActual.getChefs();
                
                // posiciones fijas temporalmente para testear
                for (int i = 0; i < chefs.size(); i++) {
                    if (i == 0) {
                        g.setColor(Color.RED);
                        g.fillOval(3 * tamanoCelda, 5 * tamanoCelda, tamanoCelda, tamanoCelda);
                    } else if (i == 1) {
                        g.setColor(Color.GREEN);
                        g.fillOval(5 * tamanoCelda, 5 * tamanoCelda, tamanoCelda, tamanoCelda);
                    }
                }
            }
        }
    }
}