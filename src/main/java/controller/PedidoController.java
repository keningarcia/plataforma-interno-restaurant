package controller;

import dto.request.AgregarPlatoRequest;
import dto.request.PedidoRequest;
import dto.response.PedidoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@RequestBody PedidoRequest request) {
        log.info("Creando pedido para mesa {}", request.getMesaId());
        PedidoResponse response = pedidoService.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> actualizar(@PathVariable Long id, @RequestBody PedidoRequest request) {
        return ResponseEntity.ok(pedidoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pedidoId}/platos")
    public ResponseEntity<PedidoResponse> agregarPlato(@PathVariable Long pedidoId, @RequestBody AgregarPlatoRequest request) {
        return ResponseEntity.ok(pedidoService.agregarPlato(pedidoId, request));
    }

    @PatchMapping("/platos/{detalleId}/cantidad")
    public ResponseEntity<PedidoResponse> actualizarCantidad(@PathVariable Long detalleId, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(pedidoService.actualizarCantidad(detalleId, cantidad));
    }

    @DeleteMapping("/platos/{detalleId}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Long detalleId) {
        pedidoService.eliminarPlato(detalleId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{pedidoId}/enviar-cocina")
    public ResponseEntity<PedidoResponse> enviarACocina(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.enviarACocina(pedidoId));
    }

    @PatchMapping("/{pedidoId}/preparacion")
    public ResponseEntity<PedidoResponse> marcarEnPreparacion(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.marcarEnPreparacion(pedidoId));
    }

    @PatchMapping("/{pedidoId}/listo")
    public ResponseEntity<PedidoResponse> marcarListo(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.marcarListo(pedidoId));
    }

    @PatchMapping("/{pedidoId}/entregar")
    public ResponseEntity<PedidoResponse> entregarPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.entregarPedido(pedidoId));
    }

    @PatchMapping("/{pedidoId}/cancelar")
    public ResponseEntity<PedidoResponse> cancelarPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.cancelarPedido(pedidoId));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<PedidoResponse>> obtenerPedidosPendientes() {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPendientes());
    }

    @GetMapping("/mesa/{mesaId}")
    public ResponseEntity<List<PedidoResponse>> obtenerPedidosPorMesa(@PathVariable Long mesaId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorMesa(mesaId));
    }

    @GetMapping("/mesero/{meseroId}")
    public ResponseEntity<List<PedidoResponse>> obtenerPedidosPorMesero(@PathVariable Long meseroId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorMesero(meseroId));
    }
}
