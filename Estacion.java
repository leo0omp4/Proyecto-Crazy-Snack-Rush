import java.util.ArrayList;
import java.util.List;

public class Estacion {
    private String nombre;
    private String tipo;
    private int posX, posY;
    private List<Ingrediente> ingredientesAceptados;
    private Ingrediente ingredienteActual; // Para estufas, tablas o despensas
    private List<Ingrediente> ingredientesEnMesa; // Especial para la mesa de ARMAR
    private int progreso; // Progreso actual de cocción o corte (0 a 100)

    public Estacion(String nombre, String tipo, int posX, int posY) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posX = posX;
        this.posY = posY;
        this.ingredientesAceptados = new ArrayList<>();
        this.ingredienteActual = null;
        this.ingredientesEnMesa = new ArrayList<>();
        this.progreso = 0;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }

    public Ingrediente getIngredienteActual() { return ingredienteActual; }
    public void setIngredienteActual(Ingrediente ingredienteActual) { this.ingredienteActual = ingredienteActual; }

    public List<Ingrediente> getIngredientesEnMesa() { return ingredientesEnMesa; }
    public void vaciarMesa() { this.ingredientesEnMesa.clear(); }

    public int getProgreso() { return progreso; }
    public void setProgreso(int progreso) { 
        this.progreso = Math.max(0, Math.min(100, progreso)); 
    }
}