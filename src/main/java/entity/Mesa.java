package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa extends BaseEntity {

    private Integer numero;

    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    private EstadoMesa estado;

    @OneToMany(mappedBy = "mesa")
    private List<Pedido> pedidos;
}