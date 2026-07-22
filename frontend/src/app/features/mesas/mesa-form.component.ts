import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { MesaService } from '../../core/services/mesa.service';
import { MesaRequest } from '../../core/models/mesa.model';

@Component({
  selector: 'app-mesa-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <h2>{{ editando ? 'Editar Mesa' : 'Nueva Mesa' }}</h2>
    <form (ngSubmit)="guardar()" #forma="ngForm" class="row g-3" style="max-width: 500px">
      <div class="col-12">
        <label class="form-label">Número</label>
        <input type="number" class="form-control" name="numero" [(ngModel)]="request.numero" required min="1">
      </div>
      <div class="col-12">
        <label class="form-label">Capacidad</label>
        <input type="number" class="form-control" name="capacidad" [(ngModel)]="request.capacidad" required min="1">
      </div>
      <div class="col-12 d-flex gap-2">
        <button type="submit" class="btn btn-primary">Guardar</button>
        <a routerLink="/mesas" class="btn btn-secondary">Cancelar</a>
      </div>
    </form>
  `
})
export class MesaFormComponent implements OnInit {
  editando = false;
  request: MesaRequest = { numero: 0, capacidad: 0 };
  private id?: number;

  constructor(
    private mesaService: MesaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.id) {
      this.editando = true;
      this.mesaService.obtenerPorId(this.id).subscribe(m => {
        this.request = { numero: m.numero, capacidad: m.capacidad };
      });
    }
  }

  guardar() {
    if (this.editando && this.id) {
      this.mesaService.actualizar(this.id, this.request).subscribe(() => this.router.navigate(['/mesas']));
    } else {
      this.mesaService.crear(this.request).subscribe(() => this.router.navigate(['/mesas']));
    }
  }
}
