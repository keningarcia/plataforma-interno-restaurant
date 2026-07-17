package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria extends BaseEntity {

    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private List<Plato> platos;
}