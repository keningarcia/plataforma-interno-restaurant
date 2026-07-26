package mapper;

import dto.request.MesaRequest;
import dto.response.MesaResponse;
import entity.EstadoMesa;
import entity.Mesa;
import org.springframework.stereotype.Component;

@Component
public class MesaMapper {

    public Mesa toEntity(MesaRequest request) {
        Mesa mesa = new Mesa();
        mesa.setNumero(request.getNumero());
        mesa.setCapacidad(request.getCapacidad());
        mesa.setEstado(EstadoMesa.DISPONIBLE);
        return mesa;
    }

    public MesaResponse toResponse(Mesa mesa) {
        MesaResponse response = new MesaResponse();
        response.setId(mesa.getId());
        response.setNumero(mesa.getNumero());
        response.setCapacidad(mesa.getCapacidad());
        response.setEstado(mesa.getEstado() != null ? mesa.getEstado().name() : null);
        return response;
    }
}
