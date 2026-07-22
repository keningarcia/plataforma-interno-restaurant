import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { PedidoService } from '../../core/services/pedido.service';
import { PedidoResponse, AgregarPlatoRequest } from '../../core/models/pedido.model';
import { EstadoPedido, EstadoDetalle } from '../../core/models/enums';

@Component({
  selector: 'app-pedido-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2>Pedido {{ pedido?.codigo }}</h2>
      <a routerLink="/pedidos" class="btn btn-secondary">Volver</a>
    </div>

    @if (pedido) {
      <div class="row">
        <div class="col-md-6">
          <div class="card mb-3">
            <div class="card-header">Información del Pedido</div>
            <div class="card-body">
              <p><strong>Código:</strong> {{ pedido.codigo }}</p>
              <p><strong>Fecha:</strong> {{ pedido.fecha }} {{ pedido.hora }}</p>
              <p><strong>Mesa:</strong> {{ pedido.mesaNumero }}</p>
              <p><strong>Mesero:</strong> {{ pedido.meseroNombre }}</p>
              <p><strong>Estado:</strong> <span class="badge" [class]="badgeClass">{{ pedido.estado }}</span></p>
              <p><strong>Total:</strong> S/ {{ pedido.total | number:'1.2-2' }}</p>
            </div>
          </div>

          <div class="card mb-3">
            <div class="card-header">Acciones</div>
            <div class="card-body d-flex gap-2 flex-wrap">
              @if (pedido.estado === 'PENDIENTE') {
                <button class="btn btn-warning" (click)="enviarCocina()">Enviar a Cocina</button>
              }
              @if (pedido.estado === 'EN_PREPARACION') {
                <button class="btn btn-success" (click)="marcarListo()">Marcar Listo</button>
              }
              @if (pedido.estado === 'LISTO') {
                <button class="btn btn-primary" (click)="entregar()">Entregar</button>
              }
              @if (pedido.estado !== 'PAGADO' && pedido.estado !== 'CANCELADO') {
                <button class="btn btn-danger" (click)="cancelar()">Cancelar</button>
              }
            </div>
          </div>
        </div>

        <div class="col-md-6">
          <div class="card mb-3">
            <div class="card-header d-flex justify-content-between align-items-center">
              <span>Detalles</span>
              <button class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#agregarPlatoModal">+ Agregar Plato</button>
            </div>
            <div class="card-body">
              <table class="table table-sm">
                <thead>
                  <tr>
                    <th>Plato</th>
                    <th>Cant</th>
                    <th>Precio</th>
                    <th>Subtotal</th>
                    <th>Estado</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  @for (d of pedido.detalles; track d.id) {
                    <tr>
                      <td>{{ d.platoNombre }}</td>
                      <td>
                        @if (editandoCantidad === d.id) {
                          <div class="input-group input-group-sm" style="width: 100px">
                            <input type="number" class="form-control" [(ngModel)]="nuevaCantidad" min="1">
                            <button class="btn btn-success" (click)="guardarCantidad(d.id)">OK</button>
                          </div>
                        } @else {
                          <span (click)="iniciarEditarCantidad(d.id, d.cantidad)" style="cursor:pointer">{{ d.cantidad }}</span>
                        }
                      </td>
                      <td>S/ {{ d.precio | number:'1.2-2' }}</td>
                      <td>S/ {{ d.subtotal | number:'1.2-2' }}</td>
                      <td><span class="badge bg-info">{{ d.estado }}</span></td>
                      <td>
                        <button class="btn btn-sm btn-outline-danger" (click)="eliminarPlato(d.id)">X</button>
                      </td>
                    </tr>
                  }
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <div class="modal fade" id="agregarPlatoModal" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header"><h5>Agregar Plato</h5></div>
            <div class="modal-body">
              <div class="mb-3">
                <label class="form-label">Plato ID</label>
                <input type="number" class="form-control" [(ngModel)]="nuevoPlato.platoId">
              </div>
              <div class="mb-3">
                <label class="form-label">Cantidad</label>
                <input type="number" class="form-control" [(ngModel)]="nuevoPlato.cantidad" min="1">
              </div>
            </div>
            <div class="modal-footer">
              <button class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
              <button class="btn btn-primary" data-bs-dismiss="modal" (click)="agregarPlato()">Agregar</button>
            </div>
          </div>
        </div>
      </div>
    }
  `
})
export class PedidoDetailComponent implements OnInit {
  pedido?: PedidoResponse;
  editandoCantidad: number | null = null;
  nuevaCantidad = 1;
  nuevoPlato: AgregarPlatoRequest = { platoId: 0, cantidad: 1 };

  constructor(
    private pedidoService: PedidoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.pedidoService.obtenerPorId(id).subscribe(p => this.pedido = p);
  }

  get badgeClass(): string {
    const map: Record<string, string> = {
      PENDIENTE: 'bg-warning', EN_PREPARACION: 'bg-info', LISTO: 'bg-success',
      ENTREGADO: 'bg-primary', PAGADO: 'bg-secondary', CANCELADO: 'bg-danger'
    };
    return map[this.pedido?.estado || ''] || 'bg-secondary';
  }

  enviarCocina() { this.pedidoService.enviarACocina(this.pedido!.id).subscribe(p => this.pedido = p); }
  marcarListo() { this.pedidoService.marcarListo(this.pedido!.id).subscribe(p => this.pedido = p); }
  entregar() { this.pedidoService.entregarPedido(this.pedido!.id).subscribe(p => this.pedido = p); }
  cancelar() { if (confirm('Cancelar pedido?')) this.pedidoService.cancelarPedido(this.pedido!.id).subscribe(p => this.pedido = p); }

  iniciarEditarCantidad(detalleId: number, cantidad: number) {
    this.editandoCantidad = detalleId;
    this.nuevaCantidad = cantidad;
  }

  guardarCantidad(detalleId: number) {
    this.pedidoService.actualizarCantidad(detalleId, this.nuevaCantidad).subscribe(p => {
      this.pedido = p;
      this.editandoCantidad = null;
    });
  }

  eliminarPlato(detalleId: number) {
    if (confirm('Eliminar plato?')) {
      this.pedidoService.eliminarPlato(detalleId).subscribe(() => this.ngOnInit());
    }
  }

  agregarPlato() {
    this.pedidoService.agregarPlato(this.pedido!.id, this.nuevoPlato).subscribe(p => {
      this.pedido = p;
      this.nuevoPlato = { platoId: 0, cantidad: 1 };
    });
  }
}
