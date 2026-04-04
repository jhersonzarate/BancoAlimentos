// lista-distribuciones.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { DistribucionService } from '../../../services/distribucion.service';
import { Distribucion } from '../../../models/index';

@Component({
  selector: 'app-lista-distribuciones',
  standalone: true,
  imports: [CommonModule, RouterLink, TopbarComponent, DatePipe],
  templateUrl: './lista-distribuciones.component.html',
  styleUrl: './lista-distribuciones.component.scss'
})
export class ListaDistribucionesComponent implements OnInit {
  distribuciones: Distribucion[] = [];

  constructor(private distribucionService: DistribucionService) {}

  ngOnInit(): void { this.cargar(); }

  cargar(): void {
    this.distribucionService.listar().subscribe({
      next: (data) => this.distribuciones = data
    });
  }

  marcarEntregado(id: number): void {
    if (confirm('¿Confirmar entrega de esta distribución?')) {
      this.distribucionService.marcarEntregado(id).subscribe({ next: () => this.cargar() });
    }
  }

  cancelar(id: number): void {
    if (confirm('¿Cancelar esta distribución?')) {
      this.distribucionService.cancelar(id).subscribe({ next: () => this.cargar() });
    }
  }

  badgeClase(estado: string): string {
    return {
      'PENDIENTE': 'badge--pendiente',
      'ENTREGADO': 'badge--entregado',
      'CANCELADO': 'badge--cancelado'
    }[estado] ?? '';
  }
}