import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuInicio extends JFrame implements KeyListener {

    private int opcionSeleccionada = 0;
    private PanelMenu panel;

    public MenuInicio() {
        this.setTitle("Crazy Snack Rush TEC - Menu");
        this.setSize(800, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        panel = new PanelMenu();
        this.add(panel);

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W
                || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            opcionSeleccionada = (opcionSeleccionada + 1) % 2;
            panel.repaint();
        } else if (key == KeyEvent.VK_ENTER) {
            iniciarJuego();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    private void iniciarJuego() {
        boolean dosJugadores = opcionSeleccionada == 1;

        Cocina cocina = new Cocina();
        cocina.setTiempo(120);

        Chef chef1 = new Chef("Chef 1", 7, 6);
        cocina.agregarChef(chef1);

        if (dosJugadores) {
            Chef chef2 = new Chef("Chef 2", 8, 6);
            cocina.agregarChef(chef2);
        }

        cocina.agregarOrden(cocina.generarReceta());
        cocina.agregarOrden(cocina.generarReceta());

        this.dispose();
        VentanaJuego ventana = new VentanaJuego(cocina, dosJugadores);
        ventana.requestFocusInWindow();
    }

    private class PanelMenu extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(new Color(30, 30, 40));
            g.fillRect(0, 0, 800, 650);

            g.setColor(new Color(255, 200, 0));
            g.setFont(new Font("SansSerif", Font.BOLD, 42));
            g.drawString("Crazy Snack Rush TEC", 120, 140);

            g.setColor(new Color(200, 200, 200));
            g.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g.drawString("Selecciona el modo de juego", 280, 190);

            String[] opciones = {
                "1 Jugador  (WASD + E)",
                "2 Jugadores  (WASD + E  |  Flechas + Enter)"
            };
            for (int i = 0; i < opciones.length; i++) {
                if (i == opcionSeleccionada) {
                    g.setColor(new Color(255, 200, 0));
                    g.fillRoundRect(150, 255 + i * 100, 500, 60, 20, 20);
                    g.setColor(new Color(30, 30, 40));
                } else {
                    g.setColor(new Color(60, 60, 80));
                    g.fillRoundRect(150, 255 + i * 100, 500, 60, 20, 20);
                    g.setColor(new Color(220, 220, 220));
                }
                g.setFont(new Font("SansSerif", Font.BOLD, 18));
                g.drawString(opciones[i], 190, 293 + i * 100);
            }

            g.setColor(new Color(140, 140, 160));
            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g.drawString("W/S para navegar    Enter para confirmar", 270, 520);

            g.setColor(new Color(100, 160, 255));
            g.setFont(new Font("SansSerif", Font.PLAIN, 13));
            g.drawString("Ingredientes: Pan, Carne, Lechuga, Tomate, Queso", 220, 570);
            g.drawString("Recetas: Hamburguesa (variantes)  |  Ensalada (lechuga + tomate)", 165, 590);
            g.drawString("Las ordenes caducan en 60 segundos.", 290, 610);
        }
    }
}
