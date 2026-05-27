// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { authGuard }  from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [

  // ── Pública ───────────────────────────────────────────────────────────
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/auth/auth.component').then(m => m.AuthComponent)
  },

  // Redirección raíz
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  // ── Rutas autenticadas ────────────────────────────────────────────────
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },

  // Donaciones
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

  // Organizaciones
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

  // Distribuciones
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

  // Inventario (todos los usuarios autenticados)
  {
    path: 'inventario',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/inventario/lista-inventario/lista-inventario.component')
        .then(m => m.ListaInventarioComponent)
  },
  {
    path: 'inventario/nuevo',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/inventario/formulario-inventario/formulario-inventario.component')
        .then(m => m.FormularioInventarioComponent)
  },
  {
    path: 'inventario/editar/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/inventario/formulario-inventario/formulario-inventario.component')
        .then(m => m.FormularioInventarioComponent)
  },

  // Gestión de Usuarios (solo ADMIN)
  {
    path: 'usuarios',
    canActivate: [adminGuard],
    loadComponent: () =>
      import('./pages/usuarios/lista-usuarios/lista-usuarios.component')
        .then(m => m.ListaUsuariosComponent)
  },
  {
    path: 'usuarios/editar/:id',
    canActivate: [adminGuard],
    loadComponent: () =>
      import('./pages/usuarios/editar-usuario/editar-usuario.component')
        .then(m => m.EditarUsuarioComponent)
  },

  // Fallback
  { path: '**', redirectTo: 'dashboard' }
];