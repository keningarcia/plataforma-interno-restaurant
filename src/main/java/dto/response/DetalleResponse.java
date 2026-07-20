package dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleResponse {

    private Long id;
    private String platoNombre;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
    private String estado;
}
