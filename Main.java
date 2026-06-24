import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Cocina principal
        Cocina miCocina = new Cocina();
        miCocina.setTiempo(120); // darle 120 segundos al juego

        // 2 chefs
        Chef chef1 = new Chef("Chef 1", 2, 3);
        Chef chef2 = new Chef("Chef 2", 6, 4);

        //Añadir los chefs a la lista de la cocina
        miCocina.getChefs().add(chef1);
        miCocina.getChefs().add(chef2);

        // Crear proteína
        Proteina carne = new Proteina("Carne de Res");

        // Crear lista de ingredientes que se necesitan para una receta
        ArrayList<Ingrediente> ingredientesHamburguesa = new ArrayList<>();
        ingredientesHamburguesa.add(new Proteina("Carne de Res"));
        ingredientesHamburguesa.add(new PanesYBases("Pan de Hamburguesa"));
        
        // Crear la receta: Lista de ingredientes, 50 puntos, 60 segundos de límite
        Receta hamburguesa = new Receta(ingredientesHamburguesa, 50, 60);
        
        // Añadir la orden a la cocina
        miCocina.getOrdenes().add(hamburguesa);
        // System.out.println("Nueva orden recibida. Puntos posibles: " + miCocina.getOrdenes().get(0).getPuntosReceta());

        // Mostrar la ventana del juego
        VentanaJuego ventana = new VentanaJuego(miCocina);
        ventana.requestFocusInWindow();

    }
}