import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" routerLink="/">Restaurant</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link" routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/mesas" routerLinkActive="active">Mesas</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/pedidos" routerLinkActive="active">Pedidos</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/usuarios" routerLinkActive="active">Usuarios</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <main class="container-fluid mt-3">
      <router-outlet />
    </main>
  `
})
export class LayoutComponent {}
