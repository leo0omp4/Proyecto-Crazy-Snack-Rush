import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaJuego extends JFrame implements KeyListener {

    private Cocina cocinaActual; 
    private PanelCocina panel;
    private int chefSeleccionado = 0; 

    public VentanaJuego(Cocina cocina) {
        this.cocinaActual = cocina;
        this.setTitle("Crazy Snack Rush TEC");
        this.setSize(800, 600); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setResizable(false); 
        
        panel = new PanelCocina();
        this.add(panel);
        
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
        
        // Forzamos el repintado inicial para asegurar que las estaciones aparezcan
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (cocinaActual == null || cocinaActual.getChefs().isEmpty()) return;

        Chef chefActivo = cocinaActual.getChefs().get(chefSeleccionado);
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) chefActivo.mover(0, -1);
        else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) chefActivo.mover(0, 1);
        else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) chefActivo.mover(-1, 0);
        else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) chefActivo.mover(1, 0);
        else if (key == KeyEvent.VK_SPACE) {
            chefSeleccionado = (chefSeleccionado + 1) % cocinaActual.getChefs().size();
        }
        panel.repaint(); 
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private class PanelCocina extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            g.setColor(new Color(200, 200, 200)); 
            g.fillRect(0, 0, 800, 600); 
            
            // Dibujar cuadrícula
            g.setColor(Color.BLACK); 
            int tamanoCelda = 50; 
            for (int x = 0; x <= 800; x += tamanoCelda) g.drawLine(x, 0, x, 600);
            for (int y = 0; y <= 600; y += tamanoCelda) g.drawLine(0, y, 800, y);

            // 3. DIBUJAR A LOS CHEFS
            if (cocinaActual != null && cocinaActual.getChefs() != null) {
                List<Chef> chefs = cocinaActual.getChefs();
                for (int i = 0; i < chefs.size(); i++) {
                    Chef c = chefs.get(i);
                    g.setColor(i == 0 ? Color.RED : Color.GREEN);
                    g.fillOval(c.getPosX() * tamanoCelda + 5, c.getPosY() * tamanoCelda + 5, tamanoCelda - 10, tamanoCelda - 10);
                    if(i == chefSeleccionado) {
                        g.setColor(Color.YELLOW);
                        g.drawOval(c.getPosX() * tamanoCelda + 2, c.getPosY() * tamanoCelda + 2, tamanoCelda - 4, tamanoCelda - 4);
                    }
                }
            }

            // 4. DIBUJAR LAS ESTACIONES
            if (cocinaActual != null && cocinaActual.getEstaciones() != null) {
                for (Estacion est : cocinaActual.getEstaciones()) {
                    System.out.println("Dibujando estación: " + est.getNombre());
                    if (est.getTipo().equals("DESPENSA")) g.setColor(Color.BLUE);
                    else if (est.getTipo().equals("TRABAJO")) g.setColor(Color.ORANGE);
                    else if (est.getTipo().equals("ENTREGA")) g.setColor(Color.GREEN);
                    else g.setColor(Color.DARK_GRAY);
                    
                    g.fillRect(est.getPosX() * tamanoCelda + 5, est.getPosY() * tamanoCelda + 5, tamanoCelda - 10, tamanoCelda - 10);
                }
            }
        }
    }
}