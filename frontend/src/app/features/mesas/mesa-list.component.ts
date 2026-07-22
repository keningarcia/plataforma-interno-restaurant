import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MesaService } from '../../core/services/mesa.service';
import { MesaResponse } from '../../core/models/mesa.model';
import { EstadoMesa } from '../../core/models/enums';

@Component({
  selector: 'app-mesa-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2>Mesas</h2>
      <a routerLink="/mesas/nuevo" class="btn btn-primary">Nueva Mesa</a>
    </div>

    <div class="row g-3">
      @for (mesa of mesas; track mesa.id) {
        <div class="col-md-3">
          <div class="card" [class.border-success]="mesa.estado === 'DISPONIBLE'" [class.border-danger]="mesa.estado === 'OCUPADA'">
            <div class="card-body text-center">
              <h5 class="card-title">Mesa #{{ mesa.numero }}</h5>
              <p class="card-text">Capacidad: {{ mesa.capacidad }}</p>
              <span class="badge" [class.bg-success]="mesa.estado === 'DISPONIBLE'" [class.bg-danger]="mesa.estado === 'OCUPADA'" [class.bg-warning]="mesa.estado === 'RESERVADA'" [class.bg-secondary]="mesa.estado === 'LIMPIEZA'">
                {{ mesa.estado }}
              </span>
              <div class="mt-2 d-flex gap-1 justify-content-center">
                <a [routerLink]="['/mesas', mesa.id]" class="btn btn-sm btn-outline-primary">Editar</a>
                <button class="btn btn-sm btn-outline-danger" (click)="eliminar(mesa.id)">Eliminar</button>
              </div>
              <div class="mt-2">
                <select class="form-select form-select-sm" [value]="mesa.estado" (change)="cambiarEstado(mesa.id, $event)">
                  @for (estado of estadosMesa; track estado) {
                    <option [value]="estado">{{ estado }}</option>
                  }
                </select>
              </div>
            </div>
          </div>
        </div>
      }
    </div>
  `
})
export class MesaListComponent implements OnInit {
  mesas: MesaResponse[] = [];
  estadosMesa = Object.values(EstadoMesa);

  constructor(private mesaService: MesaService) {}

  ngOnInit() {
    this.cargarMesas();
  }

  cargarMesas() {
    this.mesaService.listar().subscribe(m => this.mesas = m);
  }

  eliminar(id: number) {
    if (confirm('Eliminar mesa?')) {
      this.mesaService.eliminar(id).subscribe(() => this.cargarMesas());
    }
  }

  cambiarEstado(id: number, event: Event) {
    const estado = (event.target as HTMLSelectElement).value as EstadoMesa;
    this.mesaService.cambiarEstado(id, estado).subscribe(() => this.cargarMesas());
  }
}
