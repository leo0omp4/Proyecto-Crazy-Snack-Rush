import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaJuego extends JFrame implements KeyListener {

    private Cocina cocinaActual; 
    private PanelCocina panel;
    private int chefSeleccionado = 0; 
    private final int columnas = 16; // 800px / 50px por celda
    private final int filas = 12;    // 600px / 50px por celda (más espacio extra para el HUD)
    private Timer gameLoopTimer;     // Reloj del juego que corre en segundo plano
    private boolean juegoTerminado = false;

    public VentanaJuego(Cocina cocina) {
        this.cocinaActual = cocina;
        this.setTitle("Crazy Snack Rush TEC");
        this.setSize(800, 650); // Altura extendida para mostrar el panel HUD
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setResizable(false); 
        
        panel = new PanelCocina();
        this.add(panel);
        
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
        
        // BUCLE DE JUEGO EN TIEMPO REAL
        gameLoopTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juegoTerminado) return;

                // Decrementar temporizador de la partida
                int tiempoRestante = cocinaActual.getTiempo();
                if (tiempoRestante > 0) {
                    cocinaActual.setTiempo(tiempoRestante - 1);
                } else {
                    juegoTerminado = true;
                    gameLoopTimer.stop();
                    System.out.println("¡Se acabó el tiempo! Fin de la partida.");
                }

                // Procesamiento cocción en estufas
                for (Estacion est : cocinaActual.getEstaciones()) {
                    if (est.getTipo().equals("COCINAR") && est.getIngredienteActual() != null) {
                        Ingrediente ing = est.getIngredienteActual();
                        if (ing.getNombre().equals("Carne Cruda")) {
                            est.setProgreso(est.getProgreso() + 20); // 20% cada segundo (se cocina en 5s)
                            if (est.getProgreso() >= 100) {
                                ing.setNombre("Carne Cocinada");
                                System.out.println("¡La carne se ha cocinado!");
                            }
                        }
                    }
                }

                panel.repaint();
            }
        });
        gameLoopTimer.start();
        
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado) return;
        if (cocinaActual == null || cocinaActual.getChefs().isEmpty()) return;

        Chef chefActivo = cocinaActual.getChefs().get(chefSeleccionado);
        int key = e.getKeyCode();
        
        // Interacción con estaciones (Tecla E)
        if (key == KeyEvent.VK_E) {
            interactuar(chefActivo);
            return;
        }

        int nuevaX = chefActivo.getPosX();
        int nuevaY = chefActivo.getPosY();

        // Movimiento básico de cuadrícula
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) nuevaY--;
        else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) nuevaY++;
        else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) nuevaX--;
        else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) nuevaX++;
        else if (key == KeyEvent.VK_SPACE) {
            // Intercambiar selección de Chef activo
            chefSeleccionado = (chefSeleccionado + 1) % cocinaActual.getChefs().size();
            panel.repaint();
            return;
        }

        // El movimiento se limita para no pisar la zona superior reservada al HUD (filas 0 y 1)
        if (nuevaX < 0 || nuevaX >= columnas || nuevaY < 2 || nuevaY >= filas) return;

        // Comprobación de colisiones con estaciones
        boolean hayColision = false;
        for (Estacion est : cocinaActual.getEstaciones()) {
            if (est.getPosX() == nuevaX && est.getPosY() == nuevaY) {
                hayColision = true;
                break;
            }
        }

        if (!hayColision) {
            chefActivo.setPosX(nuevaX);
            chefActivo.setPosY(nuevaY);
        }

        panel.repaint(); 
    }

    private void interactuar(Chef chef) {
        for (Estacion est : cocinaActual.getEstaciones()) {
            int dx = Math.abs(est.getPosX() - chef.getPosX());
            int dy = Math.abs(est.getPosY() - chef.getPosY());
            
            // Distancia de exactamente 1 bloque para interactuar
            if (dx + dy == 1) { 
                
                // INTERACCIÓN CON DESPENSAS (Suministran ingredientes crudos)
                if (est.getTipo().equals("DESPENSA")) {
                    if (chef.getIngredienteActual() == null) {
                        if (est.getNombre().contains("Pan")) {
                            chef.setIngredienteActual(new PanesYBases("Pan")); 
                            System.out.println("Tomaste: Pan");
                        } else if (est.getNombre().contains("Carne")) {
                            chef.setIngredienteActual(new Proteina("Carne Cruda")); 
                            System.out.println("Tomaste: Carne Cruda");
                        } else if (est.getNombre().contains("Lechuga")) {
                            // Ahora se utiliza correctamente la clase VegetalesYFrutas
                            chef.setIngredienteActual(new VegetalesYFrutas("Lechuga Cruda")); 
                            System.out.println("Tomaste: Lechuga Cruda");
                        }
                    } else {
                        System.out.println("¡Tienes las manos ocupadas!");
                    }
                }
                
                // INTERACCIÓN CON ESTUFAS (Se cocinan solas con el tiempo)
                else if (est.getTipo().equals("COCINAR")) {
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

                // INTERACCIÓN CON LA TABLA DE PICAR
                else if (est.getTipo().equals("CORTAR")) {
                    if (chef.getIngredienteActual() != null && est.getIngredienteActual() == null) {
                        Ingrediente ing = chef.getIngredienteActual();
                        if (ing.getNombre().equals("Lechuga Cruda")) {
                            est.setIngredienteActual(ing);
                            est.setProgreso(0);
                            chef.setIngredienteActual(null);
                            System.out.println("Colocaste la lechuga en la tabla.");
                        }
                    } else if (chef.getIngredienteActual() == null && est.getIngredienteActual() != null) {
                        Ingrediente ing = est.getIngredienteActual();
                        if (ing.getNombre().equals("Lechuga Cruda") && est.getProgreso() < 100) {
                            // Avanzar corte manualmente
                            est.setProgreso(est.getProgreso() + 25);
                            System.out.println("¡Picando! Progreso: " + est.getProgreso() + "%");
                            if (est.getProgreso() >= 100) {
                                ing.setNombre("Lechuga Cortada");
                                System.out.println("¡La lechuga ha sido picada!");
                            }
                        } else {
                            // Recoger el vegetal picado
                            chef.setIngredienteActual(ing);
                            est.setIngredienteActual(null);
                            est.setProgreso(0);
                            System.out.println("Recogiste el ingrediente procesado.");
                        }
                    }
                }

                // INTERACCIÓN CON MESAS DE ARMADO
                else if (est.getTipo().equals("ARMAR")) {
                    if (chef.getIngredienteActual() != null && est.getIngredienteActual() == null) {
                        Ingrediente ing = chef.getIngredienteActual();
                        est.getIngredientesEnMesa().add(ing);
                        chef.setIngredienteActual(null);
                        System.out.println("Dejaste " + ing.getNombre() + " en la mesa de armado.");

                        int cantPan = 0;
                        int cantCarne = 0;
                        int cantLechuga = 0;

                        for (Ingrediente i : est.getIngredientesEnMesa()) {
                            if (i.getNombre().equals("Pan")) cantPan++;
                            if (i.getNombre().equals("Carne Cocinada")) cantCarne++;
                            if (i.getNombre().equals("Lechuga Cortada")) cantLechuga++;
                        }

                        // Verificación para combinar: requiere 2 Panes, 1 Carne Cocinada y 1 Lechuga Cortada
                        if (cantPan >= 2 && cantCarne >= 1 && cantLechuga >= 1) {
                            est.vaciarMesa();
                            est.setIngredienteActual(new Proteina("Hamburguesa Completa"));
                            System.out.println("¡Hamburguesa armada con éxito con tapa superior!");
                        }
                    } else if (chef.getIngredienteActual() == null && est.getIngredienteActual() != null) {
                        chef.setIngredienteActual(est.getIngredienteActual());
                        est.setIngredienteActual(null);
                        System.out.println("Recogiste la Hamburguesa Completa.");
                    }
                }

                // INTERACCIÓN CON MOSTRADORES DE ENTREGA
                else if (est.getTipo().equals("ENTREGA")) {
                    if (chef.getIngredienteActual() != null && chef.getIngredienteActual().getNombre().equals("Hamburguesa Completa")) {
                        chef.setIngredienteActual(null); // Quitar de las manos del chef
                        cocinaActual.sumarPuntos(150); // Recompensa de puntos
                        System.out.println("¡Pedido completado con éxito! +150 puntos.");
                        
                        // Renovar el menú de órdenes
                        if (!cocinaActual.getOrdenes().isEmpty()) {
                            cocinaActual.getOrdenes().remove(0);
                        }
                        cocinaActual.getOrdenes().add(cocinaActual.generarReceta());
                    } else {
                        System.out.println("¡Solo puedes entregar una Hamburguesa Completa terminada!");
                    }
                }
                
                panel.repaint();
                return; 
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private class PanelCocina extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            int tamanoCelda = 50; 

            // Cuadrícula
            g.setColor(new Color(220, 220, 220)); 
            g.fillRect(0, 100, 800, 500); 
            
            g.setColor(new Color(180, 180, 180)); 
            for (int x = 0; x <= 800; x += tamanoCelda) g.drawLine(x, 100, x, 600);
            for (int y = 100; y <= 600; y += tamanoCelda) g.drawLine(0, y, 800, y);

            // Panel de información superior
            g.setColor(new Color(40, 42, 54)); 
            g.fillRect(0, 0, 800, 100);

            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 15));
            g.drawString("TIEMPO: " + cocinaActual.getTiempo() + "s", 20, 35);
            g.drawString("PUNTOS: " + cocinaActual.getPuntosTotales(), 20, 65);

            // Orden activa
            g.drawString("ORDEN DE COCINA:", 220, 35);
            g.setColor(Color.YELLOW);
            g.drawString("Hamburguesa Especial", 220, 65);
            g.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("(Pan + Carne Cocinada + Lechuga Cortada + Pan)", 410, 65);

            // Estaciones
            if (cocinaActual != null && cocinaActual.getEstaciones() != null) {
                for (Estacion est : cocinaActual.getEstaciones()) {
                    switch (est.getTipo()) {
                        case "DESPENSA": g.setColor(new Color(41, 128, 185)); break; // Azul
                        case "COCINAR": g.setColor(new Color(192, 57, 43)); break;   // Rojo
                        case "CORTAR": g.setColor(new Color(241, 196, 15)); break;   // Amarillo
                        case "ARMAR": g.setColor(new Color(230, 126, 34)); break;    // Naranja
                        case "ENTREGA": g.setColor(new Color(39, 174, 96)); break;   // Verde
                        default: g.setColor(Color.DARK_GRAY); break;
                    }
                    
                    int px = est.getPosX() * tamanoCelda;
                    int py = est.getPosY() * tamanoCelda;
                    g.fillRect(px + 4, py + 4, tamanoCelda - 8, tamanoCelda - 8);
                    
                    // Texto descriptivo de la estación
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("SansSerif", Font.BOLD, 10));
                    g.drawString(est.getNombre(), px + 6, py + 18);

                    // Contador de ingredientes en la mesa de armado
                    if (est.getTipo().equals("ARMAR") && !est.getIngredientesEnMesa().isEmpty()) {
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SansSerif", Font.BOLD, 12));
                        g.drawString("Cant: " + est.getIngredientesEnMesa().size(), px + 10, py + 35);
                    }

                    // Dibujar el ingrediente sobre la superficie de la mesa
                    if (est.getIngredienteActual() != null) {
                        g.setColor(new Color(155, 89, 182)); // Color púrpura para elementos en mesa
                        g.fillOval(px + 15, py + 25, 20, 20);
                        
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("SansSerif", Font.BOLD, 8));
                        g.drawString(est.getIngredienteActual().getNombre().substring(0, 3), px + 18, py + 37);
                    }

                    // Barras de progreso de trabajo
                    if ((est.getTipo().equals("COCINAR") || est.getTipo().equals("CORTAR")) && est.getIngredienteActual() != null) {
                        g.setColor(Color.BLACK);
                        g.fillRect(px + 5, py + 40, tamanoCelda - 10, 6);
                        g.setColor(Color.CYAN);
                        g.fillRect(px + 5, py + 40, (int)((tamanoCelda - 10) * (est.getProgreso() / 100.0)), 6);
                    }
                }
            }

            // Chefs y sus manos ocupadas
            if (cocinaActual != null && cocinaActual.getChefs() != null) {
                List<Chef> chefs = cocinaActual.getChefs();
                for (int i = 0; i < chefs.size(); i++) {
                    Chef c = chefs.get(i);
                    int px = c.getPosX() * tamanoCelda;
                    int py = c.getPosY() * tamanoCelda;

                    // Círculo base del chef (Blanco para Chef 1, Gris oscuro para Chef 2)
                    g.setColor(i == 0 ? Color.WHITE : Color.DARK_GRAY);
                    g.fillOval(px + 6, py + 6, tamanoCelda - 12, tamanoCelda - 12);
                    
                    // Texto identificador (C1 / C2)
                    g.setColor(i == 0 ? Color.BLACK : Color.WHITE);
                    g.setFont(new Font("SansSerif", Font.BOLD, 12));
                    g.drawString("C" + (i + 1), px + 20, py + 30);
                    
                    // Indicador de chef seleccionado
                    if(i == chefSeleccionado) {
                        g.setColor(Color.YELLOW);
                        g.drawOval(px + 2, py + 2, tamanoCelda - 4, tamanoCelda - 4);
                    }
                    
                    // Ingrediente sostenido en manos (representado en rojo brillante)
                    if (c.getIngredienteActual() != null) {
                        g.setColor(new Color(231, 76, 60)); 
                        g.fillOval(px + 30, py + 30, 16, 16);
                        
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("SansSerif", Font.BOLD, 8));
                        g.drawString(c.getIngredienteActual().getNombre().substring(0, 1), px + 35, py + 42);
                    }
                }
            }

            // Pantalla final (Game Over)
            if (juegoTerminado) {
                g.setColor(new Color(0, 0, 0, 220));
                g.fillRect(0, 0, 800, 650);
                
                g.setColor(Color.RED);
                g.setFont(new Font("SansSerif", Font.BOLD, 50));
                g.drawString("¡TIEMPO AGOTADO!", 180, 280);
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("SansSerif", Font.BOLD, 25));
                g.drawString("Puntuación Final: " + cocinaActual.getPuntosTotales(), 285, 340);
                g.setFont(new Font("SansSerif", Font.PLAIN, 15));
                g.drawString("Cierra el juego para volver a empezar.", 290, 390);
            }
        }
    }
}