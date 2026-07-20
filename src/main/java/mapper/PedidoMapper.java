package mapper;

import dto.request.DetalleRequest;
import dto.request.PedidoRequest;
import dto.response.DetalleResponse;
import dto.response.PedidoResponse;
import entity.DetallePedido;
import entity.EstadoDetalle;
import entity.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setCodigo(generarCodigo());
        pedido.setFecha(LocalDate.now());
        pedido.setHora(LocalTime.now());
        return pedido;
    }

    public PedidoResponse toResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setCodigo(pedido.getCodigo());
        response.setFecha(pedido.getFecha());
        response.setHora(pedido.getHora());
        response.setEstado(pedido.getEstado() != null ? pedido.getEstado().name() : null);

        if (pedido.getMesa() != null) {
            response.setMesaNumero(String.valueOf(pedido.getMesa().getNumero()));
        }
        if (pedido.getMesero() != null) {
            response.setMeseroNombre(pedido.getMesero().getNombres()
                    + " " + pedido.getMesero().getApellidos());
        }
        if (pedido.getDetalles() != null) {
            response.setDetalles(pedido.getDetalles().stream()
                    .map(this::toDetalleResponse)
                    .collect(Collectors.toList()));
        }
        response.setTotal(calcularTotal(pedido.getDetalles()));
        return response;
    }

    public DetalleResponse toDetalleResponse(DetallePedido detalle) {
        DetalleResponse response = new DetalleResponse();
        response.setId(detalle.getId());
        if (detalle.getPlato() != null) {
            response.setPlatoNombre(detalle.getPlato().getNombre());
        }
        response.setCantidad(detalle.getCantidad());
        response.setPrecio(detalle.getPrecio());
        response.setSubtotal(detalle.getSubtotal());
        response.setEstado(detalle.getEstado() != null ? detalle.getEstado().name() : null);
        return response;
    }

    public DetallePedido toDetalleEntity(DetalleRequest request) {
        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(request.getCantidad());
        detalle.setEstado(EstadoDetalle.PENDIENTE);
        return detalle;
    }

    private String generarCodigo() {
        return "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BigDecimal calcularTotal(List<DetallePedido> detalles) {
        if (detalles == null) return BigDecimal.ZERO;
        return detalles.stream()
                .map(DetallePedido::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
