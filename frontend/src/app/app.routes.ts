import { Routes } from '@angular/router';
import { LayoutComponent } from './shared/layout/layout.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { MesaListComponent } from './features/mesas/mesa-list.component';
import { MesaFormComponent } from './features/mesas/mesa-form.component';
import { PedidoListComponent } from './features/pedidos/pedido-list.component';
import { PedidoFormComponent } from './features/pedidos/pedido-form.component';
import { PedidoDetailComponent } from './features/pedidos/pedido-detail.component';
import { UsuarioListComponent } from './features/usuarios/usuario-list.component';
import { UsuarioFormComponent } from './features/usuarios/usuario-form.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'mesas', component: MesaListComponent },
      { path: 'mesas/nuevo', component: MesaFormComponent },
      { path: 'mesas/:id', component: MesaFormComponent },
      { path: 'pedidos', component: PedidoListComponent },
      { path: 'pedidos/pendientes', component: PedidoListComponent },
      { path: 'pedidos/nuevo', component: PedidoFormComponent },
      { path: 'pedidos/:id', component: PedidoDetailComponent },
      { path: 'usuarios', component: UsuarioListComponent },
      { path: 'usuarios/nuevo', component: UsuarioFormComponent },
      { path: 'usuarios/:id', component: UsuarioFormComponent },
    ]
  }
];
