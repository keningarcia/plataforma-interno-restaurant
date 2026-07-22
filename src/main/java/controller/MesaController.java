package controller;

import dto.request.MesaRequest;
import dto.response.MesaResponse;
import entity.EstadoMesa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MesaService;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
@Slf4j
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public ResponseEntity<List<MesaResponse>> listar() {
        return ResponseEntity.ok(mesaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mesaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<MesaResponse> crear(@RequestBody MesaRequest request) {
        log.info("Creando mesa número {}", request.getNumero());
        MesaResponse response = mesaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaResponse> actualizar(@PathVariable Long id, @RequestBody MesaRequest request) {
        return ResponseEntity.ok(mesaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MesaResponse> cambiarEstado(@PathVariable Long id, @RequestParam EstadoMesa estado) {
        log.info("Cambiando estado de mesa {} a {}", id, estado);
        return ResponseEntity.ok(mesaService.cambiarEstado(id, estado));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MesaResponse>> obtenerPorEstado(@PathVariable EstadoMesa estado) {
        return ResponseEntity.ok(mesaService.obtenerPorEstado(estado));
    }
}
