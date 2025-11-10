package logica;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recetas")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_receta;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob // Para permitir textos largos de instrucciones
    @Column(name = "instrucciones")
    private String descripcion;

    // --- ENUMS ---
    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad", nullable = false)
    private Dificultad dificultad;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad", nullable = false)
    private Modalidad modalidad;
    // -------------
    // --- RELACIÓN INVERSA (Uno a Muchos con DetalleReceta) ---
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleReceta> detallesRecetas = new ArrayList<>();

    // --- CONSTRUCTORES ---
    public Receta() {
    }

    public Receta(String nombre, String descripcion, Dificultad dificultad, Modalidad modalidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.modalidad = modalidad;
    }

    // --- GETTERS Y SETTERS ---
    public Integer getId_receta() {
        return id_receta;
    }

    public void setId_receta(Integer id_receta) {
        this.id_receta = id_receta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // --- NUEVOS GETTERS/SETTERS PARA ENUMS ---
    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public List<DetalleReceta> getDetalleRecetas() {
        return detallesRecetas;
    }

    public void setDetalleRecetas(List<DetalleReceta> detallesRecetas) {
        this.detallesRecetas = detallesRecetas;
    }

    /**
     * Método auxiliar para la relación bidireccional
     */
    public void addDetalleReceta(DetalleReceta detalle) {
        this.detallesRecetas.add(detalle);
        detalle.setReceta(this);
    }
}