import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Cocina principal
        Cocina miCocina = new Cocina();
        miCocina.setTiempo(120); // 120 segundos para el juego

        // 2 chefs
        Chef chef1 = new Chef("Chef 1", 7, 6);
        Chef chef2 = new Chef("Chef 2", 8, 6);

        // 3. Añadir los chefs a la lista de la cocina
        miCocina.agregarChef(chef1);
        miCocina.agregarChef(chef2);

        // Crear lista de ingredientes que se necesitan para la receta
        ArrayList<Ingrediente> ingredientesHamburguesa = new ArrayList<>();
        ingredientesHamburguesa.add(new Proteina("Carne de Res"));
        ingredientesHamburguesa.add(new PanesYBases("Pan de Hamburguesa"));
        
        // Crear la receta: 
        Receta hamburguesa = new Receta("Hamburguesa Especial", 50, 60);
        
        // Añadir la orden a la cocina
        miCocina.agregarOrden(hamburguesa);

        // Mostrar la ventana del juego
        VentanaJuego ventana = new VentanaJuego(miCocina);
        ventana.requestFocusInWindow();
    }
}