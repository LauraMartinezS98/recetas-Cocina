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

    @Override
    public String toString() {
        String name = this.name();
        //Muestra el nombre sin abreviatura
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}