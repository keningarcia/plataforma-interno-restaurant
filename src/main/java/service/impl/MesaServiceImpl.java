package service.impl;

import dto.request.MesaRequest;
import dto.response.MesaResponse;
import entity.EstadoMesa;
import entity.Mesa;
import exception.BusinessException;
import exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.MesaRepository;
import service.MesaService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MesaResponse> listar() {
        return mesaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MesaResponse obtenerPorId(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada", id));
        return toResponse(mesa);
    }

    @Override
    public MesaResponse crear(MesaRequest request) {
        if (mesaRepository.existsByNumero(request.getNumero())) {
            throw new BusinessException("Ya existe una mesa con el número " + request.getNumero());
        }

        Mesa mesa = new Mesa();
        mesa.setNumero(request.getNumero());
        mesa.setCapacidad(request.getCapacidad());
        mesa.setEstado(EstadoMesa.DISPONIBLE);

        Mesa guardada = mesaRepository.save(mesa);
        log.info("Mesa {} creada", guardada.getId());
        return toResponse(guardada);
    }

    @Override
    public MesaResponse actualizar(Long id, MesaRequest request) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada", id));

        mesa.setNumero(request.getNumero());
        mesa.setCapacidad(request.getCapacidad());

        Mesa actualizada = mesaRepository.save(mesa);
        log.info("Mesa {} actualizada", actualizada.getId());
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada", id));
        mesaRepository.delete(mesa);
        log.info("Mesa {} eliminada", id);
    }

    @Override
    public MesaResponse cambiarEstado(Long id, EstadoMesa estado) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada", id));
        mesa.setEstado(estado);
        Mesa actualizada = mesaRepository.save(mesa);
        log.info("Mesa {} cambiada a estado {}", id, estado);
        return toResponse(actualizada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MesaResponse> obtenerPorEstado(EstadoMesa estado) {
        return mesaRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private MesaResponse toResponse(Mesa mesa) {
        MesaResponse response = new MesaResponse();
        response.setId(mesa.getId());
        response.setNumero(mesa.getNumero());
        response.setCapacidad(mesa.getCapacidad());
        response.setEstado(mesa.getEstado() != null ? mesa.getEstado().name() : null);
        return response;
    }
}
