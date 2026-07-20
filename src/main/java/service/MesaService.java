package service;

import dto.request.MesaRequest;
import dto.response.MesaResponse;
import entity.EstadoMesa;

import java.util.List;

public interface MesaService {

    List<MesaResponse> listar();

    MesaResponse obtenerPorId(Long id);

    MesaResponse crear(MesaRequest request);

    MesaResponse actualizar(Long id, MesaRequest request);

    void eliminar(Long id);

    MesaResponse cambiarEstado(Long id, EstadoMesa estado);

    List<MesaResponse> obtenerPorEstado(EstadoMesa estado);
}
