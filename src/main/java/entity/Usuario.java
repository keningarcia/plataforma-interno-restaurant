package entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity {

    private String nombres;

    private String apellidos;

    private String correo;

    private String password;

    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @OneToMany(mappedBy = "mesero")
    private List<Pedido> pedidos;
}
