package service.impl;

import dto.request.AgregarPlatoRequest;
import dto.request.PedidoRequest;
import dto.response.PedidoResponse;
import entity.*;
import exception.BusinessException;
import exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapper.PedidoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.DetallePedidoRepository;
import repository.MesaRepository;
import repository.PedidoRepository;
import repository.PlatoRepository;
import repository.UsuarioRepository;
import service.PedidoService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final PlatoRepository platoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DetallePedidoRepository detalleRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    public PedidoResponse crearPedido(PedidoRequest request) {

        Mesa mesa = mesaRepository.findById(request.getMesaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mesa no encontrada"));

        if(mesa.getEstado() != EstadoMesa.DISPONIBLE){
            throw new BusinessException(
                    "La mesa no está disponible");
        }

        Usuario mesero = usuarioRepository.findById(request.getMeseroId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mesero no encontrado"));

        Pedido pedido = pedidoMapper.toEntity(request);
        pedido.setMesa(mesa);
        pedido.setMesero(mesero);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setDetalles(crearDetalles(request, pedido));

        Pedido guardado = pedidoRepository.save(pedido);

        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);

        log.info("Pedido {} creado", guardado.getId());
        return pedidoMapper.toResponse(guardado);
    }

    private List<DetallePedido> crearDetalles(PedidoRequest request, Pedido pedido) {
        if (request.getDetalles() == null) return null;

        return request.getDetalles().stream()
                .map(detalleReq -> {
                    Plato plato = platoRepository.findById(detalleReq.getPlatoId())
                            .orElseThrow(() -> new ResourceNotFoundException("Plato no encontrado"));

                    if (!plato.getDisponible()) {
                        throw new BusinessException("El plato " + plato.getNombre() + " no está disponible");
                    }

                    DetallePedido detalle = pedidoMapper.toDetalleEntity(detalleReq);
                    detalle.setPedido(pedido);
                    detalle.setPlato(plato);
                    detalle.setPrecio(plato.getPrecio());
                    detalle.setSubtotal(plato.getPrecio()
                            .multiply(BigDecimal.valueOf(detalleReq.getCantidad())));
                    return detalle;
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponse obtenerPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado", id));
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }

    @Override
    public PedidoResponse actualizar(Long id, PedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado", id));

        Mesa mesa = mesaRepository.findById(request.getMesaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada"));

        Usuario mesero = usuarioRepository.findById(request.getMeseroId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesero no encontrado"));

        pedido.setMesa(mesa);
        pedido.setMesero(mesero);

        detalleRepository.findByPedidoId(id).forEach(detalleRepository::delete);

        pedido.getDetalles().clear();
        pedido.getDetalles().addAll(crearDetalles(request, pedido));

        Pedido actualizado = pedidoRepository.save(pedido);
        log.info("Pedido {} actualizado", actualizado.getId());
        return pedidoMapper.toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado", id));

        Mesa mesa = pedido.getMesa();
        mesa.setEstado(EstadoMesa.DISPONIBLE);
        mesaRepository.save(mesa);

        pedidoRepository.delete(pedido);
        log.info("Pedido {} eliminado", id);
    }

    @Override
    public PedidoResponse agregarPlato(Long pedidoId, AgregarPlatoRequest request) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado", pedidoId));

        Plato plato = platoRepository.findById(request.getPlatoId())
                .orElseThrow(() -> new ResourceNotFoundException("Plato no encontrado", request.getPlatoId()));

        if (!plato.getDisponible()) {
            throw new BusinessException("El plato " + plato.getNombre() + " no está disponible");
        }

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setPlato(plato);
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecio(plato.getPrecio());
        detalle.setSubtotal(plato.getPrecio()
                .multiply(BigDecimal.valueOf(request.getCantidad())));
        detalle.setEstado(EstadoDetalle.PENDIENTE);

        if (pedido.getDetalles() != null) {
            pedido.getDetalles().add(detalle);
        }

        pedidoRepository.save(pedido);
        log.info("Plato {} agregado al pedido {}", plato.getNombre(), pedidoId);
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public PedidoResponse actualizarCantidad(Long detalleId, Integer cantidad) {
        DetallePedido detalle = detalleRepository.findById(detalleId)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado", detalleId));

        detalle.setCantidad(cantidad);
        if (detalle.getPrecio() != null) {
            detalle.setSubtotal(detalle.getPrecio()
                    .multiply(BigDecimal.valueOf(cantidad)));
        }

        detalleRepository.save(detalle);
        log.info("Cantidad del detalle {} actualizada a {}", detalleId, cantidad);

        Pedido pedido = detalle.getPedido();
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public void eliminarPlato(Long detalleId) {
        DetallePedido detalle = detalleRepository.findById(detalleId)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado", detalleId));

        Pedido pedido = detalle.getPedido();
        if (pedido != null) {
            pedido.getDetalles().remove(detalle);
        }

        detalleRepository.delete(detalle);
        log.info("Detalle {} eliminado del pedido", detalleId);
    }

    @Override
    public PedidoResponse enviarACocina(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido no encontrado"));

        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public PedidoResponse marcarEnPreparacion(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado", pedidoId));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new BusinessException("El pedido debe estar en estado PENDIENTE para marcarlo en preparación");
        }

        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        pedidoRepository.save(pedido);
        log.info("Pedido {} marcado como EN_PREPARACION", pedidoId);
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public PedidoResponse marcarListo(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido no encontrado"));

        if (pedido.getEstado() != EstadoPedido.EN_PREPARACION) {
            throw new BusinessException("El pedido debe estar en preparación para marcarlo como listo");
        }

        pedido.setEstado(EstadoPedido.LISTO);
        pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public PedidoResponse entregarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado", pedidoId));

        if (pedido.getEstado() != EstadoPedido.LISTO) {
            throw new BusinessException("El pedido debe estar LISTO para entregarlo");
        }

        pedido.setEstado(EstadoPedido.ENTREGADO);
        pedidoRepository.save(pedido);
        log.info("Pedido {} entregado", pedidoId);
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public PedidoResponse cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido no encontrado"));

        if (pedido.getEstado() == EstadoPedido.PAGADO) {
            throw new BusinessException("No se puede cancelar un pedido ya pagado");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);

        Mesa mesa = pedido.getMesa();
        if (mesa != null) {
            mesa.setEstado(EstadoMesa.DISPONIBLE);
            mesaRepository.save(mesa);
        }

        pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> obtenerPedidosPendientes() {
        return pedidoRepository.findByEstado(EstadoPedido.PENDIENTE)
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> obtenerPedidosPorMesa(Long mesaId) {
        return pedidoRepository.findByMesaId(mesaId)
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> obtenerPedidosPorMesero(Long meseroId) {
        return pedidoRepository.findByMeseroId(meseroId)
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }
}
