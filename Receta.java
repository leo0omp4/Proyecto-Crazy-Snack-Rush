import java.util.List;

public class Receta {
    private List<Ingrediente> listaIngredientes;
    private int puntosReceta;
    private int maxTimeReceta;

    // Constructor
    public Receta(List<Ingrediente> listaIngredientes, int puntosReceta, int maxTimeReceta) {
        this.listaIngredientes = listaIngredientes;
        this.puntosReceta = puntosReceta;
        this.maxTimeReceta = maxTimeReceta;
    }

    // Método para comparar si los ingredientes entregados coinciden con la receta
    public boolean compararReceta(Receta recetaEntregada) {
        return false; 
    }

    // Getters y Setters
    public List<Ingrediente> getListaIngredientes() { return listaIngredientes; }
    public void setListaIngredientes(List<Ingrediente> listaIngredientes) { this.listaIngredientes = listaIngredientes; }

    public int getPuntosReceta() { return puntosReceta; }
    public void setPuntosReceta(int puntosReceta) { this.puntosReceta = puntosReceta; }

    public int getMaxTimeReceta() { return maxTimeReceta; }
    public void setMaxTimeReceta(int maxTimeReceta) { this.maxTimeReceta = maxTimeReceta; }
}