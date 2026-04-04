// src/app/app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },

  // Donaciones
  {
    path: 'donaciones',
    loadComponent: () =>
      import('./pages/donaciones/lista-donaciones/lista-donaciones.component')
        .then(m => m.ListaDonacionesComponent)
  },
  {
    path: 'donaciones/nueva',
    loadComponent: () =>
      import('./pages/donaciones/formulario-donacion/formulario-donacion.component')
        .then(m => m.FormularioDonacionComponent)
  },
  {
    path: 'donaciones/editar/:id',
    loadComponent: () =>
      import('./pages/donaciones/formulario-donacion/formulario-donacion.component')
        .then(m => m.FormularioDonacionComponent)
  },

  // Organizaciones
  {
    path: 'organizaciones',
    loadComponent: () =>
      import('./pages/organizaciones/lista-organizaciones/lista-organizaciones.component')
        .then(m => m.ListaOrganizacionesComponent)
  },
  {
    path: 'organizaciones/nueva',
    loadComponent: () =>
      import('./pages/organizaciones/formulario-organizacion/formulario-organizacion.component')
        .then(m => m.FormularioOrganizacionComponent)
  },
  {
    path: 'organizaciones/editar/:id',
    loadComponent: () =>
      import('./pages/organizaciones/formulario-organizacion/formulario-organizacion.component')
        .then(m => m.FormularioOrganizacionComponent)
  },

  // Distribuciones
  {
    path: 'distribuciones',
    loadComponent: () =>
      import('./pages/distribuciones/lista-distribuciones/lista-distribuciones.component')
        .then(m => m.ListaDistribucionesComponent)
  },
  {
    path: 'distribuciones/nueva',
    loadComponent: () =>
      import('./pages/distribuciones/formulario-distribucion/formulario-distribucion.component')
        .then(m => m.FormularioDistribucionComponent)
  },

  { path: '**', redirectTo: 'dashboard' }
];