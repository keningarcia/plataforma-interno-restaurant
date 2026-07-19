package service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.*;
import service.PedidoService;

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
        return null;
    }

    @Override
    public PedidoResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public List<PedidoResponse> listar() {
        return null;
    }

    @Override
    public PedidoResponse actualizar(Long id, PedidoRequest request) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public PedidoResponse agregarPlato(Long pedidoId, AgregarPlatoRequest request) {
        return null;
    }

    @Override
    public PedidoResponse actualizarCantidad(Long detalleId, Integer cantidad) {
        return null;
    }

    @Override
    public void eliminarPlato(Long detalleId) {

    }

    @Override
    public PedidoResponse enviarACocina(Long pedidoId) {
        return null;
    }

    @Override
    public PedidoResponse marcarEnPreparacion(Long pedidoId) {
        return null;
    }

    @Override
    public PedidoResponse marcarListo(Long pedidoId) {
        return null;
    }

    @Override
    public PedidoResponse entregarPedido(Long pedidoId) {
        return null;
    }

    @Override
    public PedidoResponse cancelarPedido(Long pedidoId) {
        return null;
    }

    @Override
    public List<PedidoResponse> obtenerPedidosPendientes() {
        return null;
    }

    @Override
    public List<PedidoResponse> obtenerPedidosPorMesa(Long mesaId) {
        return null;
    }

    @Override
    public List<PedidoResponse> obtenerPedidosPorMesero(Long meseroId) {
        return null;
    }
}