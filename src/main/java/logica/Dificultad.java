package logica;

public enum Dificultad {
    FACIL(1, "Sencillo y rápido"),
    MEDIA(2, "Requiere algo de experiencia"),
    DIFICIL(3, "Solo para chefs expertos");

    private final int nivel;
    private final String descripcion;

    Dificultad(int nivel, String descripcion) {
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    public int getNivel() {
        return nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        // Muestra el nombre con la primera letra mayúscula (Ej: Facil)
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}