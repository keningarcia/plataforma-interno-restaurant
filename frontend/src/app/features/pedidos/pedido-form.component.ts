import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { PedidoService } from '../../core/services/pedido.service';
import { MesaService } from '../../core/services/mesa.service';
import { UsuarioService } from '../../core/services/usuario.service';
import { PedidoRequest, DetalleRequest } from '../../core/models/pedido.model';
import { MesaResponse } from '../../core/models/mesa.model';
import { UsuarioResponse } from '../../core/models/usuario.model';

@Component({
  selector: 'app-pedido-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <h2>Nuevo Pedido</h2>
    <form (ngSubmit)="guardar()" #forma="ngForm" class="row g-3" style="max-width: 600px">
      <div class="col-6">
        <label class="form-label">Mesa</label>
        <select class="form-select" name="mesaId" [(ngModel)]="request.mesaId" required>
          <option value="">Seleccione mesa</option>
          @for (m of mesas; track m.id) {
            <option [value]="m.id">Mesa #{{ m.numero }} ({{ m.estado }})</option>
          }
        </select>
      </div>
      <div class="col-6">
        <label class="form-label">Mesero</label>
        <select class="form-select" name="meseroId" [(ngModel)]="request.meseroId" required>
          <option value="">Seleccione mesero</option>
          @for (u of usuarios; track u.id) {
            <option [value]="u.id">{{ u.nombres }} {{ u.apellidos }}</option>
          }
        </select>
      </div>

      <div class="col-12">
        <hr>
        <h5>Detalles del Pedido</h5>
        @for (det of request.detalles; track det; let i = $index) {
          <div class="row g-2 mb-2 align-items-end">
            <div class="col-6">
              <label class="form-label">Plato ID</label>
              <input type="number" class="form-control" [(ngModel)]="det.platoId" name="platoId_{{i}}" required min="1">
            </div>
            <div class="col-4">
              <label class="form-label">Cantidad</label>
              <input type="number" class="form-control" [(ngModel)]="det.cantidad" name="cantidad_{{i}}" required min="1">
            </div>
            <div class="col-2 d-flex align-items-end">
              <button type="button" class="btn btn-outline-danger btn-sm" (click)="eliminarDetalle(i)">X</button>
            </div>
          </div>
        }
        <button type="button" class="btn btn-outline-secondary btn-sm" (click)="agregarDetalle()">+ Agregar plato</button>
      </div>

      <div class="col-12 d-flex gap-2">
        <button type="submit" class="btn btn-success">Crear Pedido</button>
        <a routerLink="/pedidos" class="btn btn-secondary">Cancelar</a>
      </div>
    </form>
  `
})
export class PedidoFormComponent implements OnInit {
  request: PedidoRequest = { mesaId: 0, meseroId: 0, detalles: [] };
  mesas: MesaResponse[] = [];
  usuarios: UsuarioResponse[] = [];

  constructor(
    private pedidoService: PedidoService,
    private mesaService: MesaService,
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  ngOnInit() {
    this.mesaService.listar().subscribe(m => this.mesas = m);
    this.usuarioService.listar().subscribe(u => this.usuarios = u);
  }

  agregarDetalle() {
    this.request.detalles.push({ platoId: 0, cantidad: 1 });
  }

  eliminarDetalle(index: number) {
    this.request.detalles.splice(index, 1);
  }

  guardar() {
    this.pedidoService.crear(this.request).subscribe(() => this.router.navigate(['/pedidos']));
  }
}
