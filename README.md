# Plataforma Interno Restaurant

API REST para la gestión interna de un restaurante. Permite administrar mesas, menú, pedidos, usuarios y pagos.

## Tecnologías

- **Java 21**
- **Spring Boot 4.1.0** (WebMVC, Data JPA, Security, Validation)
- **PostgreSQL**
- **Lombok**
- **Maven**

## Estructura del proyecto

```
src/main/java/
├── controller/       # Controladores REST
│   ├── MesaController.java
│   ├── PedidoController.java
│   └── UsuarioController.java
├── dto/
│   ├── request/      # DTOs de entrada
│   └── response/     # DTOs de salida
├── entity/           # Entidades JPA
├── exception/        # Excepciones personalizadas
├── mapper/           # Mappers (entidad ↔ DTO)
├── repository/       # Repositorios JPA
└── service/
    ├── impl/         # Implementaciones
    └── *.java        # Interfaces de servicio
```

## Entidades

| Entidad | Descripción |
|---------|-------------|
| `Mesa` | Mesas del restaurante con estado (DISPONIBLE, OCUPADA, RESERVADA, LIMPIEZA) |
| `Categoria` | Categorías de platos |
| `Plato` | Platos del menú con precio y disponibilidad |
| `Usuario` | Usuarios del sistema (meseros, administradores) con roles |
| `Rol` | Roles de usuario |
| `Pedido` | Pedidos con estado (PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO → PAGADO) |
| `DetallePedido` | Detalle de cada plato en un pedido |
| `Pago` | Pagos asociados a pedidos |

## Servicios disponibles

### PedidoService
- `crearPedido` — Crea un pedido con sus detalles, valida disponibilidad de mesa y platos
- `agregarPlato` / `actualizarCantidad` / `eliminarPlato` — Gestiona platos en un pedido
- `enviarACocina` / `marcarEnPreparacion` / `marcarListo` / `entregarPedido` — Flujo de estados
- `cancelarPedido` — Cancela y libera la mesa
- `obtenerPedidosPendientes` / `obtenerPedidosPorMesa` / `obtenerPedidosPorMesero`

### MesaService
- CRUD completo + `cambiarEstado` y `obtenerPorEstado`

### UsuarioService
- CRUD completo + `obtenerPorCorreo`

## Configuración

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/restaurant_db
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

## Ejecución

```bash
./mvnw spring-boot:run
```
