package entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "platos")
public class Plato extends BaseEntity {

    private String nombre;

    @Column(length = 500)
    private String descripcion;

    private BigDecimal precio;

    private String imagen;

    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "plato")
    private List<DetallePedido> detalles;
}