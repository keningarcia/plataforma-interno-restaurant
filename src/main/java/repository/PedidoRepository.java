package repository;

import entity.EstadoPedido;
import entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByMesaId(Long mesaId);

    List<Pedido> findByMeseroId(Long meseroId);

    List<Pedido> findByFecha(LocalDate fecha);

}