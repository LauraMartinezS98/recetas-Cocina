package logica;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ingrediente;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad", nullable = false)
    private Unidad unidad;

    // --- RELACIÓN INVERSA (Uno a Muchos con DetalleReceta) ---
    @OneToMany(mappedBy = "ingrediente", fetch = FetchType.LAZY)
    private List<DetalleReceta> detallesRecetasDondeSeUsa;

    // --- Constructores ---
    public Ingrediente() {
    }

    public Ingrediente(String nombre, Unidad unidad) {
        this.nombre = nombre;
        this.unidad = unidad;
    }

    // --- Getters y Setters ---

    public Unidad getUnidad() { return unidad; }

    public void setUnidad(Unidad unidad) { this.unidad = unidad; }
    public Integer getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(Integer id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<DetalleReceta> getDetallesRecetasDondeSeUsa() {
        return detallesRecetasDondeSeUsa;
    }

    public void setDetallesRecetasDondeSeUsa(List<DetalleReceta> detallesRecetasDondeSeUsa) {
        this.detallesRecetasDondeSeUsa = detallesRecetasDondeSeUsa;
    }
}