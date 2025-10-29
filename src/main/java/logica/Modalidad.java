package logica;

public enum Modalidad {
    PRINCIPAL("Plato Fuerte"),
    ENTRANTE("Aperitivo/Tapa"),
    POSTRE("Dulce"),
    BEBIDA("Bebida sin alcohol"),
    COCTEL("Bebida alcohólica"),
    GUARNICION("Acompañamiento");

    private final String descripcion;

    Modalidad(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        // Devuelve la descripción completa para mostrar en la vista
        return this.descripcion;
    }
}