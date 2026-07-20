package dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaResponse {

    private Long id;
    private Integer numero;
    private Integer capacidad;
    private String estado;
}
