// src/app/pages/donaciones/lista-donaciones/lista-donaciones.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { DonacionService } from '../../../services/donacion.service';
import { Donacion } from '../../../models/index';

@Component({
  selector: 'app-lista-donaciones',
  standalone: true,
  imports: [CommonModule, RouterLink, TopbarComponent, DatePipe],
  templateUrl: './lista-donaciones.component.html',
  styleUrl: './lista-donaciones.component.scss'
})
export class ListaDonacionesComponent implements OnInit {
  donaciones: Donacion[] = [];
  estadoFiltro = '';

  constructor(private donacionService: DonacionService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.donacionService.listar(this.estadoFiltro || undefined).subscribe({
      next: (data) => this.donaciones = data,
      error: (err) => console.error('Error al cargar donaciones', err)
    });
  }

  filtrar(estado: string): void {
    this.estadoFiltro = estado;
    this.cargar();
  }

  eliminar(id: number): void {
    if (confirm('¿Eliminar esta donación?')) {
      this.donacionService.eliminar(id).subscribe({
        next: () => this.cargar()
      });
    }
  }

  badgeClase(estado: string): string {
    const mapa: Record<string, string> = {
      'PENDIENTE':   'badge--pendiente',
      'EN_PROCESO':  'badge--en-proceso',
      'DISTRIBUIDO': 'badge--distribuido',
      'CANCELADO':   'badge--cancelado'
    };
    return mapa[estado] ?? '';
  }

  etiquetaEstado(estado: string): string {
    const mapa: Record<string, string> = {
      'PENDIENTE':   'Pendiente',
      'EN_PROCESO':  'En proceso',
      'DISTRIBUIDO': 'Distribuido',
      'CANCELADO':   'Cancelado'
    };
    return mapa[estado] ?? estado;
  }
}