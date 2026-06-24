import java.util.ArrayList;
import java.util.List;

public class Cocina {
    private int tiempo;
    private int puntosTotales;
    private List<Chef> chefs;
    private List<Estacion> estaciones;
    private List<Receta> ordenes;

    public Cocina() {
        this.tiempo = 120; // Tiempo total de juego
        this.puntosTotales = 0;
        this.chefs = new ArrayList<>();
        this.ordenes = new ArrayList<>();
        this.estaciones = new ArrayList<>(); 
        
        // --- ESTACIONES (Layout original sin modificaciones) ---
        this.estaciones.add(new Estacion("Despensa Pan", "DESPENSA", 1, 3));
        this.estaciones.add(new Estacion("Despensa Carne", "DESPENSA", 1, 6));
        this.estaciones.add(new Estacion("Despensa Lechuga", "DESPENSA", 1, 9)); 
        
        this.estaciones.add(new Estacion("Tabla Picar", "CORTAR", 5, 2));  
        this.estaciones.add(new Estacion("Estufa 1", "COCINAR", 9, 2));    
        this.estaciones.add(new Estacion("Estufa 2", "COCINAR", 11, 2));  

        this.estaciones.add(new Estacion("Mesa Armado 1", "ARMAR", 5, 9));
        this.estaciones.add(new Estacion("Mesa Armado 2", "ARMAR", 9, 9));

        this.estaciones.add(new Estacion("Mostrador 1", "ENTREGA", 14, 5));
        this.estaciones.add(new Estacion("Mostrador 2", "ENTREGA", 14, 6));
    }

    // Genera la receta usando la nueva estructura de Receta
    public Receta generarReceta() {
        // Nombre de la receta, puntos base (300), tiempo para empezar a degradar (30s)
        return new Receta("Hamburguesa Especial", 300, 30);
    }

    // --- MÉTODOS DE GESTIÓN ---
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    public int getPuntosTotales() { return puntosTotales; }
    public void sumarPuntos(int puntos) { this.puntosTotales += puntos; }

    public List<Chef> getChefs() { return chefs; }
    public List<Receta> getOrdenes() { return ordenes; }
    public List<Estacion> getEstaciones() { return estaciones; }

    public void agregarChef(Chef c) { chefs.add(c); }
    public void agregarOrden(Receta r) { ordenes.add(r); }
}