// src/app/models/index.ts

// ── Donación ──────────────────────────────────────────────────────────────
export interface Donacion {
  id?: number;
  donante: string;
  tipoAlimento: string;
  cantidad: number;
  unidad: string;
  fechaDonacion?: string;
  fechaVencimiento?: string;
  estado?: 'PENDIENTE' | 'EN_PROCESO' | 'DISTRIBUIDO' | 'CANCELADO';
  observaciones?: string;
  createdAt?: string;
}

// ── Organización ──────────────────────────────────────────────────────────
export interface Organizacion {
  id?: number;
  nombre: string;
  ruc?: string;
  tipo: string;
  direccion?: string;
  telefono?: string;
  responsable?: string;
  activo?: boolean;
  createdAt?: string;
}

// ── Distribución ──────────────────────────────────────────────────────────
export interface Distribucion {
  id?: number;
  donacionId: number;
  donanteDonacion?: string;
  tipoAlimento?: string;
  organizacionId: number;
  nombreOrganizacion?: string;
  cantidadEntregada: number;
  unidad?: string;
  fechaEntrega?: string;
  estado?: 'PENDIENTE' | 'ENTREGADO' | 'CANCELADO';
  notas?: string;
  createdAt?: string;
}

// ── Dashboard ─────────────────────────────────────────────────────────────
export interface DashboardResumen {
  totalDonaciones: number;
  donacionesPendientes: number;
  donacionesEnProceso: number;
  donacionesDistribuidas: number;
  totalOrganizaciones: number;
  organizacionesActivas: number;
  totalDistribuciones: number;
  distribucionesPendientes: number;
  distribucionesEntregadas: number;
}

// ── Usuario ───────────────────────────────────────────────────────────────
export interface Usuario {
  id?: number;
  nombre: string;
  email: string;
  rol: 'USUARIO' | 'ADMIN';
  activo?: boolean;
  createdAt?: string;
}

export interface UsuarioActualizarRequest {
  nombre: string;
  email: string;
  rol: 'USUARIO' | 'ADMIN';
  activo: boolean;
}

// ── Inventario ────────────────────────────────────────────────────────────
export interface Inventario {
  id?: number;
  tipoAlimento: string;
  stockDisponible: number;
  unidad: string;
  descripcion?: string;
  stockMinimo?: number;
  bajoStock?: boolean;
  createdAt?: string;
  updatedAt?: string;
}