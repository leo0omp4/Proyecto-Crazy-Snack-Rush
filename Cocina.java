import java.util.ArrayList;
import java.util.List;

public class Cocina {
    private int tiempo;
    private List<Chef> chefs;
    private List<Receta> ordenes;
    private List<Estacion> estaciones;
    private int puntosTotales; // Puntos globales acumulados en la partida

    // Constructor
    public Cocina() {
        this.tiempo = 120; // 120 segundos por defecto para el juego
        this.chefs = new ArrayList<>();
        this.ordenes = new ArrayList<>();
        this.estaciones = new ArrayList<>(); 
        this.puntosTotales = 0;
        
        // --- ESTACIONES DISTRIBUIDAS EN LA CUADRÍCULA ---
        
        // Zona Izquierda (Azul): Despensas para recoger ingredientes básicos
        this.estaciones.add(new Estacion("Despensa Pan", "DESPENSA", 1, 3));
        this.estaciones.add(new Estacion("Despensa Carne", "DESPENSA", 1, 6));
        this.estaciones.add(new Estacion("Despensa Lechuga", "DESPENSA", 1, 9)); 
        
        // Zona Superior: Preparación (Amarillo) y Cocción (Rojo)
        this.estaciones.add(new Estacion("Tabla Picar", "CORTAR", 5, 2));  
        this.estaciones.add(new Estacion("Estufa 1", "COCINAR", 9, 2));    
        this.estaciones.add(new Estacion("Estufa 2", "COCINAR", 11, 2));   

        // Zona Inferior (Naranja): Mesas para armar los platos finales
        this.estaciones.add(new Estacion("Mesa Armado 1", "ARMAR", 5, 9));
        this.estaciones.add(new Estacion("Mesa Armado 2", "ARMAR", 9, 9));

        // Zona Derecha (Verde): Mostrador para entregar las órdenes terminadas
        this.estaciones.add(new Estacion("Mostrador 1", "ENTREGA", 14, 5));
        this.estaciones.add(new Estacion("Mostrador 2", "ENTREGA", 14, 6));
    }

    // Generar una nueva receta aleatoria para mantener activo el menú del juego
    public Receta generarReceta() {
        ArrayList<Ingrediente> ing = new ArrayList<>();
        ing.add(new PanesYBases("Pan"));
        ing.add(new Proteina("Carne Cocinada"));
        // Se puede añadir "Vegetal" (Lechuga Cortada) para más complejidad
        return new Receta(ing, 100, 60);
    }

    // Getters y Setters
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    public List<Chef> getChefs() { return chefs; }
    public void setChefs(List<Chef> chefs) { this.chefs = chefs; }

    public List<Receta> getOrdenes() { return ordenes; }
    public void setOrdenes(List<Receta> ordenes) { this.ordenes = ordenes; }

    public List<Estacion> getEstaciones() { return estaciones; }

    public int getPuntosTotales() { return puntosTotales; }
    public void setPuntosTotales(int puntosTotales) { this.puntosTotales = puntosTotales; }
    public void sumarPuntos(int puntos) { this.puntosTotales += puntos; }
}