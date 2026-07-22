import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { UsuarioService } from '../../core/services/usuario.service';
import { UsuarioRequest } from '../../core/models/usuario.model';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <h2>{{ editando ? 'Editar Usuario' : 'Nuevo Usuario' }}</h2>
    <form (ngSubmit)="guardar()" #forma="ngForm" class="row g-3" style="max-width: 600px">
      <div class="col-6">
        <label class="form-label">Nombres</label>
        <input type="text" class="form-control" name="nombres" [(ngModel)]="request.nombres" required>
      </div>
      <div class="col-6">
        <label class="form-label">Apellidos</label>
        <input type="text" class="form-control" name="apellidos" [(ngModel)]="request.apellidos" required>
      </div>
      <div class="col-6">
        <label class="form-label">Correo</label>
        <input type="email" class="form-control" name="correo" [(ngModel)]="request.correo" required>
      </div>
      <div class="col-6">
        <label class="form-label">Contraseña</label>
        <input type="password" class="form-control" name="password" [(ngModel)]="request.password" [required]="!editando">
      </div>
      <div class="col-6">
        <label class="form-label">Rol ID</label>
        <input type="number" class="form-control" name="rolId" [(ngModel)]="request.rolId" required min="1">
      </div>
      <div class="col-6">
        <label class="form-label">Activo</label>
        <select class="form-select" name="activo" [(ngModel)]="request.activo">
          <option [value]="true">Sí</option>
          <option [value]="false">No</option>
        </select>
      </div>
      <div class="col-12 d-flex gap-2">
        <button type="submit" class="btn btn-primary">Guardar</button>
        <a routerLink="/usuarios" class="btn btn-secondary">Cancelar</a>
      </div>
    </form>
  `
})
export class UsuarioFormComponent implements OnInit {
  editando = false;
  request: UsuarioRequest = { nombres: '', apellidos: '', correo: '', password: '', rolId: 0, activo: true };
  private id?: number;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.id) {
      this.editando = true;
      this.usuarioService.obtenerPorId(this.id).subscribe(u => {
        this.request = { nombres: u.nombres, apellidos: u.apellidos, correo: u.correo, password: '', rolId: 0, activo: u.activo };
      });
    }
  }

  guardar() {
    if (this.editando && this.id) {
      this.usuarioService.actualizar(this.id, this.request).subscribe(() => this.router.navigate(['/usuarios']));
    } else {
      this.usuarioService.crear(this.request).subscribe(() => this.router.navigate(['/usuarios']));
    }
  }
}
