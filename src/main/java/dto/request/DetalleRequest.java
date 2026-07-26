package dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRequest {

    @NotNull
    private Long platoId;

    @NotNull
    @Min(1)
    private Integer cantidad;
}
