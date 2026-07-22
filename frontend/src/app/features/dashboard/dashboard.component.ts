import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PedidoService } from '../../core/services/pedido.service';
import { MesaService } from '../../core/services/mesa.service';
import { UsuarioService } from '../../core/services/usuario.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <h2 class="mb-4">Dashboard</h2>
    <div class="row g-3">
      <div class="col-md-3">
        <div class="card text-bg-primary">
          <div class="card-body">
            <h5 class="card-title">Total Mesas</h5>
            <p class="card-text display-6">{{ totalMesas }}</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-bg-success">
          <div class="card-body">
            <h5 class="card-title">Mesas Disponibles</h5>
            <p class="card-text display-6">{{ mesasDisponibles }}</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-bg-warning">
          <div class="card-body">
            <h5 class="card-title">Pedidos Pendientes</h5>
            <p class="card-text display-6">{{ pedidosPendientes }}</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-bg-info">
          <div class="card-body">
            <h5 class="card-title">Total Usuarios</h5>
            <p class="card-text display-6">{{ totalUsuarios }}</p>
          </div>
        </div>
      </div>
    </div>

    <div class="row mt-4">
      <div class="col-12">
        <div class="card">
          <div class="card-header d-flex justify-content-between align-items-center">
            <span>Acceso Rápido</span>
          </div>
          <div class="card-body">
            <div class="d-flex gap-2 flex-wrap">
              <a routerLink="/pedidos/nuevo" class="btn btn-success">Nuevo Pedido</a>
              <a routerLink="/mesas" class="btn btn-primary">Gestionar Mesas</a>
              <a routerLink="/pedidos/pendientes" class="btn btn-warning">Pedidos Pendientes</a>
              <a routerLink="/usuarios" class="btn btn-info">Gestionar Usuarios</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class DashboardComponent implements OnInit {
  totalMesas = 0;
  mesasDisponibles = 0;
  pedidosPendientes = 0;
  totalUsuarios = 0;

  constructor(
    private pedidoService: PedidoService,
    private mesaService: MesaService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit() {
    this.mesaService.listar().subscribe(m => {
      this.totalMesas = m.length;
      this.mesasDisponibles = m.filter(x => x.estado === 'DISPONIBLE').length;
    });
    this.pedidoService.obtenerPedidosPendientes().subscribe(p => {
      this.pedidosPendientes = p.length;
    });
    this.usuarioService.listar().subscribe(u => {
      this.totalUsuarios = u.length;
    });
  }
}
