package logica;



import jakarta.persistence.*;
import java.math.BigDecimal; // Uso de BigDecimal para precisión en cantidades
import java.util.Objects;

@Entity
@Table(name = "detalle_receta")
public class DetalleReceta {

    /*
     * Clave primaria simple autogenerada, similar a tu clase Inscripcion.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación Muchos a 1: Múltiples detalles pertenecen a una única Receta.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;

    // Relación Muchos a 1: Múltiples detalles usan un único Ingrediente.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    // Campos específicos para la relación N:M (la cantidad necesaria)
    @Column(name = "cantidad", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @Column(name = "unidad", nullable = false)
    private String unidad; // Ej: "gramos", "cucharadas", "ml"

    // --- Constructores ---
    public DetalleReceta() {}

    public DetalleReceta(Receta receta, Ingrediente ingrediente, BigDecimal cantidad, String unidad) {
        this.receta = receta;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    // --- Getters y Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}