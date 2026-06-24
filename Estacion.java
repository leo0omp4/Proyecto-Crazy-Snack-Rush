import java.util.ArrayList;
import java.util.List;

public class Estacion {
    private String nombre;
    private List<Ingrediente> ingredientesAceptados;
    private List<Receta> recetasDisponibles;

    // Constructor
    public Estacion(String nombre) {
        this.nombre = nombre;
        this.ingredientesAceptados = new ArrayList<>();
        this.recetasDisponibles = new ArrayList<>();
    }

    // Método que pide el UML
    public Receta generarReceta() {
        return null; 
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Ingrediente> getIngredientesAceptados() { return ingredientesAceptados; }
    public void setIngredientesAceptados(List<Ingrediente> ingredientesAceptados) { this.ingredientesAceptados = ingredientesAceptados; }

    public List<Receta> getRecetasDisponibles() { return recetasDisponibles; }
    public void setRecetasDisponibles(List<Receta> recetasDisponibles) { this.recetasDisponibles = recetasDisponibles; }
}