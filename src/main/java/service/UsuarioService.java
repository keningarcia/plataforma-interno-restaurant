package service;

import dto.request.UsuarioRequest;
import dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponse> listar();

    UsuarioResponse obtenerPorId(Long id);

    UsuarioResponse crear(UsuarioRequest request);

    UsuarioResponse actualizar(Long id, UsuarioRequest request);

    void eliminar(Long id);

    UsuarioResponse obtenerPorCorreo(String correo);
}
