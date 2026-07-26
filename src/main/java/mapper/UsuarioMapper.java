package mapper;

import dto.request.UsuarioRequest;
import dto.response.UsuarioResponse;
import entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombres(usuario.getNombres());
        response.setApellidos(usuario.getApellidos());
        response.setCorreo(usuario.getCorreo());
        response.setActivo(usuario.getActivo());
        if (usuario.getRol() != null) {
            response.setRol(usuario.getRol().getNombre());
        }
        return response;
    }
}
