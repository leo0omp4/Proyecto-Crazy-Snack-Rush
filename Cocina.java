import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cocina {
    private int tiempo;
    private int puntosTotales;
    private List<Chef> chefs;
    private List<Estacion> estaciones;
    private List<Receta> ordenes;
    private Random random;

    public Cocina() {
        this.tiempo = 120;
        this.puntosTotales = 0;
        this.chefs = new ArrayList<>();
        this.ordenes = new ArrayList<>();
        this.estaciones = new ArrayList<>();
        this.random = new Random();

        this.estaciones.add(new Estacion("Despensa Pan",     "DESPENSA", 1, 3));
        this.estaciones.add(new Estacion("Despensa Carne",   "DESPENSA", 1, 5));
        this.estaciones.add(new Estacion("Despensa Lechuga", "DESPENSA", 1, 7));
        this.estaciones.add(new Estacion("Despensa Tomate",  "DESPENSA", 1, 9));
        this.estaciones.add(new Estacion("Despensa Queso",   "DESPENSA", 1, 11));

        this.estaciones.add(new Estacion("Tabla Picar",  "CORTAR",  5,  2));
        this.estaciones.add(new Estacion("Estufa 1",     "COCINAR", 9,  2));
        this.estaciones.add(new Estacion("Estufa 2",     "COCINAR", 11, 2));

        this.estaciones.add(new Estacion("Mesa Armado 1", "ARMAR", 5, 9));
        this.estaciones.add(new Estacion("Mesa Armado 2", "ARMAR", 9, 9));

        this.estaciones.add(new Estacion("Mostrador 1", "ENTREGA", 14, 5));
        this.estaciones.add(new Estacion("Mostrador 2", "ENTREGA", 14, 6));

        this.estaciones.add(new Estacion("Basurero", "BASURERO", 14, 9));
    }

    public Receta generarReceta() {
        int tipo = random.nextInt(2);
        if (tipo == 0) {
            return generarHamburguesa();
        } else {
            return generarEnsalada();
        }
    }

    private Receta generarHamburguesa() {
        List<String> opcionales = new ArrayList<>();
        opcionales.add("Queso");
        opcionales.add("Lechuga Cortada");
        opcionales.add("Tomate Cortado");

        int cantOpcionales = random.nextInt(3);
        List<String> elegidos = new ArrayList<>();
        while (elegidos.size() < cantOpcionales && !opcionales.isEmpty()) {
            int idx = random.nextInt(opcionales.size());
            elegidos.add(opcionales.remove(idx));
        }

        StringBuilder nombreReceta = new StringBuilder("Hamburguesa");
        if (!elegidos.isEmpty()) {
            nombreReceta.append(" con");
            for (int i = 0; i < elegidos.size(); i++) {
                if (i > 0) nombreReceta.append(" y");
                nombreReceta.append(" ").append(elegidos.get(i).split(" ")[0]);
            }
        }

        Receta receta = new Receta(nombreReceta.toString(), 300, 30);
        receta.agregarIngredienteRequerido("Pan");
        receta.agregarIngredienteRequerido("Carne Cocinada");
        for (String extra : elegidos) {
            receta.agregarIngredienteRequerido(extra);
        }
        return receta;
    }

    private Receta generarEnsalada() {
        Receta receta = new Receta("Ensalada", 200, 30);
        receta.agregarIngredienteRequerido("Lechuga Cortada");
        receta.agregarIngredienteRequerido("Tomate Cortado");
        return receta;
    }

    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    public int getPuntosTotales() { return puntosTotales; }
    public void sumarPuntos(int puntos) { this.puntosTotales += puntos; }

    public List<Chef> getChefs() { return chefs; }
    public List<Receta> getOrdenes() { return ordenes; }
    public List<Estacion> getEstaciones() { return this.estaciones; }

    public void agregarChef(Chef c) { chefs.add(c); }
    public void agregarOrden(Receta r) { ordenes.add(r); }
}
