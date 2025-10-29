package logica;

// Enum para limitar las unidades de medida disponibles
public enum Unidad {
    GRAMO("gr"),
    MILILITRO("ml"),
    UNIDAD("ud"),
    TIRA("tira"),
    CUCHARADA("cda"); // Añadimos un ejemplo más

    private final String abreviatura;

    Unidad(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    // Método para mostrar nombres legibles en la vista (Ej: Gramo, Mililitro)
    @Override
    public String toString() {
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}