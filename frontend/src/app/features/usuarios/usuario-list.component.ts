import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UsuarioService } from '../../core/services/usuario.service';
import { UsuarioResponse } from '../../core/models/usuario.model';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2>Usuarios</h2>
      <a routerLink="/usuarios/nuevo" class="btn btn-primary">Nuevo Usuario</a>
    </div>

    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombres</th>
            <th>Apellidos</th>
            <th>Correo</th>
            <th>Rol</th>
            <th>Activo</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          @for (u of usuarios; track u.id) {
            <tr>
              <td>{{ u.id }}</td>
              <td>{{ u.nombres }}</td>
              <td>{{ u.apellidos }}</td>
              <td>{{ u.correo }}</td>
              <td>{{ u.rol }}</td>
              <td>
                <span class="badge" [class.bg-success]="u.activo" [class.bg-danger]="!u.activo">
                  {{ u.activo ? 'Sí' : 'No' }}
                </span>
              </td>
              <td>
                <a [routerLink]="['/usuarios', u.id]" class="btn btn-sm btn-outline-primary">Editar</a>
                <button class="btn btn-sm btn-outline-danger" (click)="eliminar(u.id)">Eliminar</button>
              </td>
            </tr>
          }
        </tbody>
      </table>
    </div>
  `
})
export class UsuarioListComponent implements OnInit {
  usuarios: UsuarioResponse[] = [];

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.usuarioService.listar().subscribe(u => this.usuarios = u);
  }

  eliminar(id: number) {
    if (confirm('Eliminar usuario?')) {
      this.usuarioService.eliminar(id).subscribe(() => this.ngOnInit());
    }
  }
}
