import java.util.ArrayList;
import java.util.List;

public class Cocina {
    private int tiempo;
    private List<Chef> chefs;
    private List<Receta> ordenes;
    private List<Estacion> estaciones;

    public Cocina() {
        this.tiempo = 0; 
        this.chefs = new ArrayList<>();
        this.ordenes = new ArrayList<>();
        this.estaciones = new ArrayList<>(); 
        
        // --- ESTACIONES DISTRIBUIDAS EN EL MAPA ---
        
        // Zona Izquierda (Azul): Despensas para recoger ingredientes
        this.estaciones.add(new Estacion("Despensa Pan", "DESPENSA", 1, 2));
        this.estaciones.add(new Estacion("Despensa Carne", "DESPENSA", 1, 6));
        
        // Zona Superior: Preparación y Cocción
        this.estaciones.add(new Estacion("Tabla Picar", "CORTAR", 5, 1));  // Amarillo
        this.estaciones.add(new Estacion("Estufa 1", "COCINAR", 9, 1));    // Rojo
        this.estaciones.add(new Estacion("Estufa 2", "COCINAR", 11, 1));   // Rojo

        // Zona Central/Inferior (Naranja): Mesas para armar los platos
        this.estaciones.add(new Estacion("Mesa Armado 1", "ARMAR", 5, 9));
        this.estaciones.add(new Estacion("Mesa Armado 2", "ARMAR", 9, 9));

        // Zona Derecha (Verde): Mostrador de Entrega
        this.estaciones.add(new Estacion("Mostrador", "ENTREGA", 14, 4));
        this.estaciones.add(new Estacion("Mostrador", "ENTREGA", 14, 5));
    }

    public Receta generarReceta() {
        return null;
    }

    // Getters y Setters
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    public List<Chef> getChefs() { return chefs; }
    public void setChefs(List<Chef> chefs) { this.chefs = chefs; }

    public List<Receta> getOrdenes() { return ordenes; }
    public void setOrdenes(List<Receta> ordenes) { this.ordenes = ordenes; }

    public List<Estacion> getEstaciones() { return estaciones; }
}