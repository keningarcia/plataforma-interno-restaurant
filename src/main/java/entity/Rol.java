package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "roles")
public class Rol extends BaseEntity {

    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;
}