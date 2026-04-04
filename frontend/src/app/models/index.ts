// src/app/models/donacion.model.ts
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

// src/app/models/organizacion.model.ts
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

// src/app/models/distribucion.model.ts
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

// src/app/models/dashboard.model.ts
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