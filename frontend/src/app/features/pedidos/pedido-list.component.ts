import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { PedidoService } from '../../core/services/pedido.service';
import { PedidoResponse } from '../../core/models/pedido.model';
import { EstadoPedido } from '../../core/models/enums';

@Component({
  selector: 'app-pedido-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2>{{ soloPendientes ? 'Pedidos Pendientes' : 'Pedidos' }}</h2>
      <a routerLink="/pedidos/nuevo" class="btn btn-success">Nuevo Pedido</a>
    </div>

    <ul class="nav nav-tabs mb-3">
      <li class="nav-item">
        <a class="nav-link" routerLink="/pedidos" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">Todos</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" routerLink="/pedidos/pendientes" routerLinkActive="active">Pendientes</a>
      </li>
    </ul>

    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Código</th>
            <th>Fecha</th>
            <th>Mesa</th>
            <th>Mesero</th>
            <th>Total</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          @for (p of pedidos; track p.id) {
            <tr>
              <td>{{ p.codigo }}</td>
              <td>{{ p.fecha }} {{ p.hora }}</td>
              <td>{{ p.mesaNumero }}</td>
              <td>{{ p.meseroNombre }}</td>
              <td>S/ {{ p.total | number:'1.2-2' }}</td>
              <td><span class="badge" [class]="badgeClass(p.estado)">{{ p.estado }}</span></td>
              <td>
                <a [routerLink]="['/pedidos', p.id]" class="btn btn-sm btn-outline-info">Ver</a>
                @if (p.estado === 'PENDIENTE') {
                  <button class="btn btn-sm btn-outline-warning" (click)="enviarCocina(p.id)">Cocina</button>
                }
                @if (p.estado === 'EN_PREPARACION') {
                  <button class="btn btn-sm btn-outline-success" (click)="marcarListo(p.id)">Listo</button>
                }
                @if (p.estado === 'LISTO') {
                  <button class="btn btn-sm btn-outline-primary" (click)="entregar(p.id)">Entregar</button>
                }
                @if (p.estado !== 'PAGADO' && p.estado !== 'CANCELADO') {
                  <button class="btn btn-sm btn-outline-danger" (click)="cancelar(p.id)">Cancelar</button>
                }
              </td>
            </tr>
          }
        </tbody>
      </table>
    </div>
  `
})
export class PedidoListComponent implements OnInit {
  pedidos: PedidoResponse[] = [];
  soloPendientes = false;

  constructor(
    private pedidoService: PedidoService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.soloPendientes = this.route.snapshot.url.some(s => s.path === 'pendientes');
    if (this.soloPendientes) {
      this.pedidoService.obtenerPedidosPendientes().subscribe(p => this.pedidos = p);
    } else {
      this.pedidoService.listar().subscribe(p => this.pedidos = p);
    }
  }

  badgeClass(estado: string): string {
    const map: Record<string, string> = {
      PENDIENTE: 'bg-warning',
      EN_PREPARACION: 'bg-info',
      LISTO: 'bg-success',
      ENTREGADO: 'bg-primary',
      PAGADO: 'bg-secondary',
      CANCELADO: 'bg-danger'
    };
    return map[estado] || 'bg-secondary';
  }

  enviarCocina(id: number) { this.pedidoService.enviarACocina(id).subscribe(() => this.ngOnInit()); }
  marcarListo(id: number) { this.pedidoService.marcarListo(id).subscribe(() => this.ngOnInit()); }
  entregar(id: number) { this.pedidoService.entregarPedido(id).subscribe(() => this.ngOnInit()); }
  cancelar(id: number) { if (confirm('Cancelar pedido?')) this.pedidoService.cancelarPedido(id).subscribe(() => this.ngOnInit()); }
}
