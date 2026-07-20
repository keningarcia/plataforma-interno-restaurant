# Contexto del proyecto — Plataforma Interno Restaurant

## Stack técnico
- Java 21, Spring Boot 4.1.0, Maven
- PostgreSQL, Spring Data JPA, Lombok
- Spring Security (declarado pero sin configurar)
- Sin MapStruct — los mappers son clases manuales con `@Component`

## Convenios importantes

### Paquetes planos
Todas las clases (entity, service, controller, dto, mapper, repository, exception) usan paquetes planos (`package entity;`, `package service;`, etc.) en lugar de estar bajo `com.keningarcia.plantaforma_interno_restaurant`. El escaneo se resuelve con `scanBasePackages` en la clase principal.

### Mappers manuales
No usar MapStruct. Los mappers son clases `@Component` con métodos de conversión manual. Ejemplo: `PedidoMapper.toEntity()`, `PedidoMapper.toResponse()`.

### Lombok en entidades
Todas las entidades usan `@Getter @Setter @NoArgsConstructor @AllArgsConstructor`. No se usa `@Data` para evitar problemas con proxies JPA.

### Flujo de estados de Pedido
```
PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO → PAGADO
CANCELADO (desde cualquier estado excepto PAGADO)
```

### Excepciones
- `ResourceNotFoundException` — para recursos no encontrados
- `BusinessException` — para violaciones de reglas de negocio
Ambas extienden `RuntimeException`.

### Enums
- `EstadoPedido`: PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, PAGADO, CANCELADO
- `EstadoDetalle`: PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO
- `EstadoMesa`: DISPONIBLE, OCUPADA, RESERVADA, LIMPIEZA
- `MetodoPago`: EFECTIVO, YAPE, PLIN, VISA, MASTERCARD

### Servicios
Siguen el patrón interfaz + implementación. Ejemplo: `service.MesaService` ← `service.impl.MesaServiceImpl`. Las implementaciones usan `@Service @RequiredArgsConstructor @Slf4j @Transactional`.

### DTOs
- Request: `dto/request/*Request.java`
- Response: `dto/response/*Response.java`

## Checklist de capas
Para cada nueva entidad, crear en orden:
1. `entity/` — clase JPA con Lombok
2. `repository/` — interfaz extends JpaRepository
3. `dto/request/*Request.java`
4. `dto/response/*Response.java`
5. `mapper/*Mapper.java` — clase @Component
6. `service/*Service.java` — interfaz
7. `service/impl/*ServiceImpl.java` — implementación
8. `controller/*Controller.java` — endpoints REST

## Lo que falta implementar
- Controladores (PedidoController, MesaController, UsuarioController — todos vacíos)
- Configuración de seguridad (Spring Security sin uso)
- Auditoría completa (falta bean AuditorAware)
- Handler global de excepciones (@ControllerAdvice)
