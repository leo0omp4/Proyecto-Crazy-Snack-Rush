// Proteina.java
public class Proteina extends Ingrediente {
    private boolean cocinada;

    public Proteina(String nombre) {
        super(nombre);
        this.cocinada = false;
    }

    public boolean isCocinada() { return cocinada; }
    public void setCocinada(boolean cocinada) { this.cocinada = cocinada; }
}