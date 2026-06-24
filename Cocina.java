import java.util.ArrayList;
import java.util.List;

public class Cocina {
    private int tiempo;
    private List<Chef> chefs;
    private List<Receta> ordenes;

    // Constructor
    public Cocina() {
        this.tiempo = 0; // El temporizador inicia en 0
        this.chefs = new ArrayList<>();
        this.ordenes = new ArrayList<>();
    }

    public Receta generarReceta() {
        // retorna null, generación aleatoria más adelante
        return null;
    }

    // Getters y Setters
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    public List<Chef> getChefs() { return chefs; }
    public void setChefs(List<Chef> chefs) { this.chefs = chefs; }

    public List<Receta> getOrdenes() { return ordenes; }
    public void setOrdenes(List<Receta> ordenes) { this.ordenes = ordenes; }
}