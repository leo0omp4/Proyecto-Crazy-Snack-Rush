import java.util.ArrayList;
import java.util.List;

public class Receta {
    private String nombre;
    private int puntosActuales;
    private int tiempoLimite;
    private int tiempoTranscurrido;
    private List<String> ingredientesRequeridos;
    private boolean caducada;
    private static final int TIEMPO_CADUCIDAD = 60;

    public Receta(String nombre, int puntosBase, int tiempoLimite) {
        this.nombre = nombre;
        this.puntosActuales = puntosBase;
        this.tiempoLimite = tiempoLimite;
        this.tiempoTranscurrido = 0;
        this.ingredientesRequeridos = new ArrayList<>();
        this.caducada = false;
    }

    public void actualizarTiempo() {
        if (caducada) return;

        tiempoTranscurrido++;

        if (tiempoTranscurrido >= TIEMPO_CADUCIDAD) {
            caducada = true;
            puntosActuales = 0;
            System.out.println("La orden " + nombre + " ha caducado.");
            return;
        }

        if (tiempoTranscurrido >= tiempoLimite && puntosActuales > 0) {
            puntosActuales /= 2;
            tiempoTranscurrido = 0;
        }
    }

    public boolean isCaducada() { return caducada; }

    public int getTiempoRestante() { return Math.max(0, TIEMPO_CADUCIDAD - tiempoTranscurrido); }

    public void agregarIngredienteRequerido(String nombreIngrediente) {
        ingredientesRequeridos.add(nombreIngrediente);
    }

    public List<String> getIngredientesRequeridos() { return ingredientesRequeridos; }

    public String getNombre() { return nombre; }
    public int getPuntosActuales() { return puntosActuales; }
}
