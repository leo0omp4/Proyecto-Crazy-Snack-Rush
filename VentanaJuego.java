import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class VentanaJuego extends JFrame implements KeyListener {

    private Cocina cocinaActual;
    private PanelCocina panel;
    private final int columnas = 16;
    private final int filas = 12;
    private Timer gameLoopTimer;
    private Timer movimientoTimer;
    private boolean juegoTerminado = false;
    private boolean dosJugadores;

    // Teclas actualmente presionadas
    private final Set<Integer> teclasPresionadas = new HashSet<>();

    public VentanaJuego(Cocina cocina, boolean dosJugadores) {
        this.cocinaActual = cocina;
        this.dosJugadores = dosJugadores;

        this.setTitle("Crazy Snack Rush TEC");
        this.setSize(800, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        panel = new PanelCocina();
        this.add(panel);

        this.addKeyListener(this);
        this.setFocusable(true);

        // Limpiar teclas si la ventana pierde el foco para evitar que queden pegadas
        this.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {}
            @Override
            public void windowLostFocus(WindowEvent e) {
                teclasPresionadas.clear();
            }
        });

        this.setVisible(true);
        this.requestFocusInWindow();

        // Timer de logica de juego: cada segundo
        gameLoopTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cocinaActual != null) {
                    for (Estacion est : cocinaActual.getEstaciones()) {
                        est.actualizarEstadoIngrediente();
                    }
                }

                if (juegoTerminado) return;

                int tiempoRestante = cocinaActual.getTiempo();
                if (tiempoRestante > 0) {
                    cocinaActual.setTiempo(tiempoRestante - 1);
                } else {
                    juegoTerminado = true;
                    teclasPresionadas.clear();
                    gameLoopTimer.stop();
                    movimientoTimer.stop();
                    System.out.println("Fin de la partida.");
                }

                List<Receta> ordenes = cocinaActual.getOrdenes();
                for (int i = ordenes.size() - 1; i >= 0; i--) {
                    ordenes.get(i).actualizarTiempo();
                    if (ordenes.get(i).isCaducada()) {
                        ordenes.remove(i);
                        ordenes.add(cocinaActual.generarReceta());
                        System.out.println("Orden caducada reemplazada.");
                    }
                }

                for (Estacion est : cocinaActual.getEstaciones()) {
                    if (est.getTipo().equals("COCINAR") && est.getIngredienteActual() != null) {
                        Ingrediente ing = est.getIngredienteActual();
                        if (ing.getNombre().equals("Carne Cruda")) {
                            est.setProgreso(est.getProgreso() + 20);
                            if (est.getProgreso() >= 100) {
                                ing.setNombre("Carne Cocinada");
                                System.out.println("La carne se ha cocinado.");
                            }
                        }
                    }
                }

                panel.repaint();
            }
        });
        gameLoopTimer.start();

        // Timer de movimiento: 100ms para respuesta fluida sin pegarse
        movimientoTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juegoTerminado) return;
                procesarMovimiento();
                panel.repaint();
            }
        });
        movimientoTimer.start();

        panel.repaint();
    }

    // --- MOVIMIENTO ---

    private void procesarMovimiento() {
        List<Chef> chefs = cocinaActual.getChefs();
        if (chefs.isEmpty()) return;

        Chef chef1 = chefs.get(0);
        int dx1 = 0, dy1 = 0;
        if (teclasPresionadas.contains(KeyEvent.VK_W)) dy1--;
        if (teclasPresionadas.contains(KeyEvent.VK_S)) dy1++;
        if (teclasPresionadas.contains(KeyEvent.VK_A)) dx1--;
        if (teclasPresionadas.contains(KeyEvent.VK_D)) dx1++;
        // Si se presionan dos diagonales a la vez, priorizar vertical
        if (dx1 != 0 && dy1 != 0) dx1 = 0;
        moverChef(chef1, dx1, dy1);

        if (dosJugadores && chefs.size() > 1) {
            Chef chef2 = chefs.get(1);
            int dx2 = 0, dy2 = 0;
            if (teclasPresionadas.contains(KeyEvent.VK_UP))    dy2--;
            if (teclasPresionadas.contains(KeyEvent.VK_DOWN))  dy2++;
            if (teclasPresionadas.contains(KeyEvent.VK_LEFT))  dx2--;
            if (teclasPresionadas.contains(KeyEvent.VK_RIGHT)) dx2++;
            if (dx2 != 0 && dy2 != 0) dx2 = 0;
            moverChef(chef2, dx2, dy2);
        }
    }

    private void moverChef(Chef chef, int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        int nuevaX = chef.getPosX() + dx;
        int nuevaY = chef.getPosY() + dy;

        if (nuevaX < 0 || nuevaX >= columnas || nuevaY < 2 || nuevaY >= filas) return;

        for (Estacion est : cocinaActual.getEstaciones()) {
            if (est.getPosX() == nuevaX && est.getPosY() == nuevaY) return;
        }

        for (Chef otro : cocinaActual.getChefs()) {
            if (otro != chef && otro.getPosX() == nuevaX && otro.getPosY() == nuevaY) return;
        }

        chef.setPosX(nuevaX);
        chef.setPosY(nuevaY);
    }

    // --- TECLAS ---

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado) return;

        int key = e.getKeyCode();
        teclasPresionadas.add(key);

        List<Chef> chefs = cocinaActual.getChefs();
        if (chefs.isEmpty()) return;

        // Interaccion chef 1
        if (key == KeyEvent.VK_E) {
            interactuar(chefs.get(0));
            panel.repaint();
        }
        // Limpiar mesa adyacente chef 1
        if (key == KeyEvent.VK_Q) {
            limpiarMesaAdyacente(chefs.get(0));
            panel.repaint();
        }

        // Interaccion chef 2
        if (dosJugadores && chefs.size() > 1) {
            if (key == KeyEvent.VK_ENTER) {
                interactuar(chefs.get(1));
                panel.repaint();
            }
            // Limpiar mesa adyacente chef 2
            if (key == KeyEvent.VK_SHIFT) {
                limpiarMesaAdyacente(chefs.get(1));
                panel.repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclasPresionadas.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // --- LIMPIAR MESA ---

    private void limpiarMesaAdyacente(Chef chef) {
        for (Estacion est : cocinaActual.getEstaciones()) {
            int dx = Math.abs(est.getPosX() - chef.getPosX());
            int dy = Math.abs(est.getPosY() - chef.getPosY());
            if (dx + dy == 1 && est.getTipo().equals("ARMAR")) {
                est.vaciarMesa();
                est.setIngredienteActual(null);
                System.out.println("Mesa de armado limpiada.");
                return;
            }
        }
    }

    // --- INTERACCIONES ---

    private void interactuar(Chef chef) {
        for (Estacion est : cocinaActual.getEstaciones()) {
            int dx = Math.abs(est.getPosX() - chef.getPosX());
            int dy = Math.abs(est.getPosY() - chef.getPosY());

            if (dx + dy != 1) continue;

            switch (est.getTipo()) {
                case "DESPENSA":  interactuarDespensa(chef, est);  break;
                case "COCINAR":   interactuarCocinar(chef, est);   break;
                case "CORTAR":    interactuarCortar(chef, est);    break;
                case "ARMAR":     interactuarArmar(chef, est);     break;
                case "ENTREGA":   interactuarEntrega(chef, est);   break;
                case "BASURERO":  interactuarBasurero(chef);       break;
            }

            panel.repaint();
            return;
        }
    }

    private void interactuarDespensa(Chef chef, Estacion est) {
        if (chef.getIngredienteActual() != null) {
            System.out.println("Tienes las manos ocupadas.");
            return;
        }

        String nombreEst = est.getNombre();
        if (nombreEst.contains("Pan")) {
            chef.setIngredienteActual(new PanesYBases("Pan"));
            System.out.println("Tomaste: Pan");
        } else if (nombreEst.contains("Carne")) {
            chef.setIngredienteActual(new Proteina("Carne Cruda"));
            System.out.println("Tomaste: Carne Cruda");
        } else if (nombreEst.contains("Lechuga")) {
            chef.setIngredienteActual(new VegetalesYFrutas("Lechuga Cruda"));
            System.out.println("Tomaste: Lechuga Cruda");
        } else if (nombreEst.contains("Tomate")) {
            chef.setIngredienteActual(new Tomate("Tomate Crudo"));
            System.out.println("Tomaste: Tomate Crudo");
        } else if (nombreEst.contains("Queso")) {
            chef.setIngredienteActual(new Queso("Queso"));
            System.out.println("Tomaste: Queso");
        }
    }

    private void interactuarCocinar(Chef chef, Estacion est) {
        if (chef.getIngredienteActual() != null && est.getIngredienteActual() == null) {
            Ingrediente ing = chef.getIngredienteActual();
            if (ing.getNombre().equals("Carne Cruda")) {
                est.setIngredienteActual(ing);
                est.setProgreso(0);
                chef.setIngredienteActual(null);
                System.out.println("Colocaste carne cruda en la estufa.");
            }
        } else if (chef.getIngredienteActual() == null && est.getIngredienteActual() != null) {
            chef.setIngredienteActual(est.getIngredienteActual());
            est.setIngredienteActual(null);
            est.setProgreso(0);
            System.out.println("Retiraste el ingrediente de la estufa.");
        }
    }

    private void interactuarCortar(Chef chef, Estacion est) {
        if (chef.getIngredienteActual() != null && est.getIngredienteActual() == null) {
            Ingrediente ing = chef.getIngredienteActual();
            String nombre = ing.getNombre();
            if (nombre.equals("Lechuga Cruda") || nombre.equals("Tomate Crudo")) {
                est.setIngredienteActual(ing);
                est.setProgreso(0);
                chef.setIngredienteActual(null);
                System.out.println("Colocaste " + nombre + " en la tabla.");
            }
        } else if (chef.getIngredienteActual() == null && est.getIngredienteActual() != null) {
            Ingrediente ing = est.getIngredienteActual();
            String nombre = ing.getNombre();
            boolean enProgreso = (nombre.equals("Lechuga Cruda") || nombre.equals("Tomate Crudo"))
                    && est.getProgreso() < 100;
            if (enProgreso) {
                est.setProgreso(est.getProgreso() + 25);
                System.out.println("Cortando " + nombre + "... " + est.getProgreso() + "%");
                if (est.getProgreso() >= 100) {
                    ing.setNombre(nombre.equals("Lechuga Cruda") ? "Lechuga Cortada" : "Tomate Cortado");
                    System.out.println(ing.getNombre() + " listo.");
                }
            } else {
                chef.setIngredienteActual(ing);
                est.setIngredienteActual(null);
                est.setProgreso(0);
                System.out.println("Recogiste: " + ing.getNombre());
            }
        }
    }

    private void interactuarArmar(Chef chef, Estacion est) {
        if (chef.getIngredienteActual() != null && est.getIngredienteActual() == null) {
            Ingrediente ing = chef.getIngredienteActual();

            if (ing.getNombre().equals("Plato Listo")) {
                est.setIngredienteActual(ing);
                chef.setIngredienteActual(null);
                System.out.println("Devolviste el plato a la mesa.");
                return;
            }

            est.getIngredientesEnMesa().add(ing);
            chef.setIngredienteActual(null);
            System.out.println("Dejaste " + ing.getNombre() + " en la mesa.");
            intentarArmar(est);

        } else if (chef.getIngredienteActual() == null && est.getIngredienteActual() != null) {
            chef.setIngredienteActual(est.getIngredienteActual());
            est.setIngredienteActual(null);
            System.out.println("Recogiste el plato listo.");
        }
    }

    private void intentarArmar(Estacion est) {
        List<Ingrediente> mesa = est.getIngredientesEnMesa();

        int cantPan = 0, cantCarne = 0, cantLechuga = 0, cantTomate = 0, cantQueso = 0;
        for (Ingrediente i : mesa) {
            switch (i.getNombre()) {
                case "Pan":             cantPan++;     break;
                case "Carne Cocinada":  cantCarne++;   break;
                case "Lechuga Cortada": cantLechuga++; break;
                case "Tomate Cortado":  cantTomate++;  break;
                case "Queso":           cantQueso++;   break;
            }
        }

        List<Receta> ordenes = cocinaActual.getOrdenes();
        for (Receta receta : ordenes) {
            if (receta.isCaducada()) continue;

            List<String> requeridos = receta.getIngredientesRequeridos();
            int rPan = 0, rCarne = 0, rLechuga = 0, rTomate = 0, rQueso = 0;
            for (String req : requeridos) {
                switch (req) {
                    case "Pan":             rPan++;     break;
                    case "Carne Cocinada":  rCarne++;   break;
                    case "Lechuga Cortada": rLechuga++; break;
                    case "Tomate Cortado":  rTomate++;  break;
                    case "Queso":           rQueso++;   break;
                }
            }

            boolean coincide = cantPan == rPan && cantCarne == rCarne
                    && cantLechuga == rLechuga && cantTomate == rTomate
                    && cantQueso == rQueso;

            if (coincide) {
                est.vaciarMesa();
                Ingrediente plato = new Ingrediente("Plato Listo");
                plato.setEstado(receta.getNombre());
                est.setIngredienteActual(plato);
                System.out.println("Plato armado: " + receta.getNombre());
                return;
            }
        }
    }

    private void interactuarEntrega(Chef chef, Estacion est) {
        if (chef.getIngredienteActual() == null) return;

        Ingrediente ing = chef.getIngredienteActual();
        if (!ing.getNombre().equals("Plato Listo")) {
            System.out.println("Solo puedes entregar un plato terminado.");
            return;
        }

        String nombreRecetaPlato = ing.getEstado();
        List<Receta> ordenes = cocinaActual.getOrdenes();

        for (int i = 0; i < ordenes.size(); i++) {
            Receta receta = ordenes.get(i);
            if (!receta.isCaducada() && receta.getNombre().equals(nombreRecetaPlato)) {
                int puntos = receta.getPuntosActuales();
                cocinaActual.sumarPuntos(puntos);
                chef.setIngredienteActual(null);
                ordenes.remove(i);
                ordenes.add(cocinaActual.generarReceta());
                System.out.println("Pedido entregado: " + nombreRecetaPlato + " +" + puntos + " pts");
                return;
            }
        }

        System.out.println("No hay orden activa que coincida con " + nombreRecetaPlato);
    }

    private void interactuarBasurero(Chef chef) {
        if (chef.getIngredienteActual() != null) {
            System.out.println("Botaste: " + chef.getIngredienteActual().getNombre());
            chef.setIngredienteActual(null);
        }
    }

    // --- DIBUJO ---

    private class PanelCocina extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int tam = 50;

            // Fondo cuadricula
            g.setColor(new Color(220, 220, 220));
            g.fillRect(0, 100, 800, 500);
            g.setColor(new Color(180, 180, 180));
            for (int x = 0; x <= 800; x += tam) g.drawLine(x, 100, x, 600);
            for (int y = 100; y <= 600; y += tam) g.drawLine(0, y, 800, y);

            // HUD superior
            g.setColor(new Color(40, 42, 54));
            g.fillRect(0, 0, 800, 100);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 15));
            g.drawString("TIEMPO: " + cocinaActual.getTiempo() + "s", 20, 35);
            g.drawString("PUNTOS: " + cocinaActual.getPuntosTotales(), 20, 65);

            // Ordenes activas
            List<Receta> ordenes = cocinaActual.getOrdenes();
            int xOrden = 200;
            for (Receta r : ordenes) {
                if (r.isCaducada()) continue;
                g.setColor(r.getTiempoRestante() <= 15 ? Color.RED : Color.YELLOW);
                g.setFont(new Font("SansSerif", Font.BOLD, 12));
                g.drawString(r.getNombre(), xOrden, 25);
                g.drawString(r.getPuntosActuales() + "pts  " + r.getTiempoRestante() + "s", xOrden, 42);
                g.setFont(new Font("SansSerif", Font.PLAIN, 10));
                g.setColor(Color.LIGHT_GRAY);
                StringBuilder listaIng = new StringBuilder();
                for (String req : r.getIngredientesRequeridos()) {
                    listaIng.append(req).append(", ");
                }
                if (listaIng.length() > 2) listaIng.setLength(listaIng.length() - 2);
                g.drawString(listaIng.toString(), xOrden, 57);
                xOrden += 210;
                if (xOrden > 640) break;
            }

            // Controles
            g.setColor(new Color(180, 180, 255));
            g.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g.drawString("C1: WASD mover | E interactuar | Q limpiar mesa", 20, 90);
            if (dosJugadores) g.drawString("C2: Flechas | Enter | Shift limpiar", 440, 90);

            // Estaciones
            if (cocinaActual != null && cocinaActual.getEstaciones() != null) {
                for (Estacion est : cocinaActual.getEstaciones()) {
                    switch (est.getTipo()) {
                        case "DESPENSA":  g.setColor(new Color(41, 128, 185));  break;
                        case "COCINAR":   g.setColor(new Color(192, 57, 43));   break;
                        case "CORTAR":    g.setColor(new Color(241, 196, 15));  break;
                        case "ARMAR":     g.setColor(new Color(230, 126, 34));  break;
                        case "ENTREGA":   g.setColor(new Color(39, 174, 96));   break;
                        case "BASURERO":  g.setColor(new Color(80, 80, 80));    break;
                        default:          g.setColor(Color.DARK_GRAY);          break;
                    }

                    int px = est.getPosX() * tam;
                    int py = est.getPosY() * tam;
                    g.fillRect(px + 4, py + 4, tam - 8, tam - 8);

                    g.setColor(Color.WHITE);
                    g.setFont(new Font("SansSerif", Font.BOLD, 9));
                    g.drawString(est.getNombre(), px + 4, py + 16);

                    // Tapa del basurero
                    if (est.getTipo().equals("BASURERO")) {
                        g.setColor(new Color(50, 50, 50));
                        g.fillRect(px + 8, py + 20, tam - 16, tam - 26);
                        g.setColor(new Color(150, 150, 150));
                        g.drawRect(px + 8, py + 20, tam - 16, tam - 26);
                    }

                    if (est.getTipo().equals("ARMAR") && !est.getIngredientesEnMesa().isEmpty()) {
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SansSerif", Font.BOLD, 11));
                        g.drawString("x" + est.getIngredientesEnMesa().size(), px + 10, py + 35);
                    }

                    if (est.getIngredienteActual() != null) {
                        g.setColor(new Color(155, 89, 182));
                        g.fillOval(px + 15, py + 25, 20, 20);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("SansSerif", Font.BOLD, 8));
                        String label = est.getIngredienteActual().getNombre();
                        g.drawString(label.substring(0, Math.min(3, label.length())), px + 18, py + 37);
                    }

                    if ((est.getTipo().equals("COCINAR") || est.getTipo().equals("CORTAR"))
                            && est.getIngredienteActual() != null) {
                        g.setColor(Color.BLACK);
                        g.fillRect(px + 5, py + 40, tam - 10, 6);
                        g.setColor(Color.CYAN);
                        g.fillRect(px + 5, py + 40, (int)((tam - 10) * (est.getProgreso() / 100.0)), 6);
                    }
                }
            }

            // Chefs
            if (cocinaActual != null && cocinaActual.getChefs() != null) {
                List<Chef> chefs = cocinaActual.getChefs();
                for (int i = 0; i < chefs.size(); i++) {
                    Chef c = chefs.get(i);
                    int px = c.getPosX() * tam;
                    int py = c.getPosY() * tam;

                    g.setColor(i == 0 ? Color.WHITE : Color.ORANGE);
                    g.fillOval(px + 6, py + 6, tam - 12, tam - 12);

                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SansSerif", Font.BOLD, 12));
                    g.drawString("C" + (i + 1), px + 18, py + 30);

                    if (c.getIngredienteActual() != null) {
                        g.setColor(new Color(231, 76, 60));
                        g.fillOval(px + 30, py + 30, 16, 16);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("SansSerif", Font.BOLD, 8));
                        String label = c.getIngredienteActual().getNombre();
                        g.drawString(label.substring(0, Math.min(1, label.length())), px + 35, py + 42);
                    }
                }
            }

            // Pantalla de fin
            if (juegoTerminado) {
                g.setColor(new Color(0, 0, 0, 220));
                g.fillRect(0, 0, 800, 650);
                g.setColor(Color.RED);
                g.setFont(new Font("SansSerif", Font.BOLD, 50));
                g.drawString("TIEMPO AGOTADO", 160, 280);
                g.setColor(Color.WHITE);
                g.setFont(new Font("SansSerif", Font.BOLD, 25));
                g.drawString("Puntuacion Final: " + cocinaActual.getPuntosTotales(), 260, 340);
                g.setFont(new Font("SansSerif", Font.PLAIN, 15));
                g.drawString("Cierra la ventana para salir.", 300, 390);
            }
        }
    }
}
