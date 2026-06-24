import java.util.ArrayList;
import java.util.List;

public class Cocina {
    private int tiempo;
    private List<Chef> chefs;
    private List<Receta> ordenes;
    private List<Estacion> estaciones; // Lista de estaciones en la cocina

    // Constructor
    public Cocina() {
        this.tiempo = 0; 
        this.chefs = new ArrayList<>();
        this.ordenes = new ArrayList<>();
        this.estaciones = new ArrayList<>();
        this.estaciones.add(new Estacion("Despensa A", "DESPENSA", 1, 1));
        this.estaciones.add(new Estacion("Mesa A", "TRABAJO", 4, 2));
        this.estaciones.add(new Estacion("Salida A", "ENTREGA", 7, 5));
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

    // Getter para las estaciones
    public List<Estacion> getEstaciones() { return estaciones; }
}