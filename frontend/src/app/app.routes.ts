// src/app/app.routes.ts — VERSIÓN CON AUTH GUARD
import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  // Ruta pública de login/registro
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/auth/auth.component').then(m => m.AuthComponent)
  },

  // Redirigir raíz a dashboard (protegido)
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  // ── Rutas protegidas ──
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'donaciones',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/donaciones/lista-donaciones/lista-donaciones.component')
        .then(m => m.ListaDonacionesComponent)
  },
  {
    path: 'donaciones/nueva',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/donaciones/formulario-donacion/formulario-donacion.component')
        .then(m => m.FormularioDonacionComponent)
  },
  {
    path: 'donaciones/editar/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/donaciones/formulario-donacion/formulario-donacion.component')
        .then(m => m.FormularioDonacionComponent)
  },
  {
    path: 'organizaciones',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/organizaciones/lista-organizaciones/lista-organizaciones.component')
        .then(m => m.ListaOrganizacionesComponent)
  },
  {
    path: 'organizaciones/nueva',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/organizaciones/formulario-organizacion/formulario-organizacion.component')
        .then(m => m.FormularioOrganizacionComponent)
  },
  {
    path: 'organizaciones/editar/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/organizaciones/formulario-organizacion/formulario-organizacion.component')
        .then(m => m.FormularioOrganizacionComponent)
  },
  {
    path: 'distribuciones',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/distribuciones/lista-distribuciones/lista-distribuciones.component')
        .then(m => m.ListaDistribucionesComponent)
  },
  {
    path: 'distribuciones/nueva',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/distribuciones/formulario-distribucion/formulario-distribucion.component')
        .then(m => m.FormularioDistribucionComponent)
  },

  { path: '**', redirectTo: 'dashboard' }
];