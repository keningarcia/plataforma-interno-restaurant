package dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {

    private Long id;
    private String codigo;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private String mesaNumero;
    private String meseroNombre;
    private List<DetalleResponse> detalles;
    private BigDecimal total;
}
