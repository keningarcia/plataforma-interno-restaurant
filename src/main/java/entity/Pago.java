package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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