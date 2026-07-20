package dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    private String nombres;
    private String apellidos;
    private String correo;
    private String password;
    private Long rolId;
    private Boolean activo;
}
