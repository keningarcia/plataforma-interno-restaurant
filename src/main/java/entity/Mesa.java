package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "mesas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mesa extends BaseEntity {

    private Integer numero;

    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    private EstadoMesa estado;

    @OneToMany(mappedBy = "mesa")
    private List<Pedido> pedidos;
}