package entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "plato_id")
    private Plato plato;

    private Integer cantidad;

    private BigDecimal precio;

    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    private EstadoDetalle estado;
}