package service;

import dto.request.AgregarPlatoRequest;
import dto.request.PedidoRequest;
import dto.response.PedidoResponse;

import java.util.List;

public interface PedidoService {

    PedidoResponse crearPedido(PedidoRequest request);

    PedidoResponse obtenerPorId(Long id);

    List<PedidoResponse> listar();

    PedidoResponse actualizar(Long id, PedidoRequest request);

    void eliminar(Long id);

    PedidoResponse agregarPlato(Long pedidoId, AgregarPlatoRequest request);

    PedidoResponse actualizarCantidad(Long detalleId, Integer cantidad);

    void eliminarPlato(Long detalleId);

    PedidoResponse enviarACocina(Long pedidoId);

    PedidoResponse marcarEnPreparacion(Long pedidoId);

    PedidoResponse marcarListo(Long pedidoId);

    PedidoResponse entregarPedido(Long pedidoId);

    PedidoResponse cancelarPedido(Long pedidoId);

    List<PedidoResponse> obtenerPedidosPendientes();

    List<PedidoResponse> obtenerPedidosPorMesa(Long mesaId);

    List<PedidoResponse> obtenerPedidosPorMesero(Long meseroId);

}