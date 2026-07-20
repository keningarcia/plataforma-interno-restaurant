package service.impl;

import dto.request.UsuarioRequest;
import dto.response.UsuarioResponse;
import entity.Rol;
import entity.Usuario;
import exception.BusinessException;
import exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.RolRepository;
import repository.UsuarioRepository;
import service.UsuarioService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado", id));
        return toResponse(usuario);
    }

    @Override
    public UsuarioResponse crear(UsuarioRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new BusinessException("Ya existe un usuario con el correo " + request.getCorreo());
        }

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado", request.getRolId()));

        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(request.getPassword());
        usuario.setRol(rol);
        usuario.setActivo(request.getActivo() != null && request.getActivo());

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario {} creado", guardado.getId());
        return toResponse(guardado);
    }

    @Override
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado", id));

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado", request.getRolId()));

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(request.getPassword());
        usuario.setRol(rol);
        usuario.setActivo(request.getActivo() != null && request.getActivo());

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario {} actualizado", actualizado.getId());
        return toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado", id));
        usuarioRepository.delete(usuario);
        log.info("Usuario {} eliminado", id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con correo: " + correo));
        return toResponse(usuario);
    }

    private UsuarioResponse toResponse(Usuario usuario) {
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
