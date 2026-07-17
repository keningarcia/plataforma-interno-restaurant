package entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private BigDecimal subtotal;

    private BigDecimal descuento;

    private BigDecimal impuesto;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private LocalDateTime fechaPago;
}