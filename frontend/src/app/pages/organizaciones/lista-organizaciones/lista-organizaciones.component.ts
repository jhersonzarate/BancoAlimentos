// lista-organizaciones.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { OrganizacionService } from '../../../services/organizacion.service';
import { Organizacion } from '../../../models/index';

@Component({
  selector: 'app-lista-organizaciones',
  standalone: true,
  imports: [CommonModule, RouterLink, TopbarComponent],
  templateUrl: './lista-organizaciones.component.html',
  styleUrl: './lista-organizaciones.component.scss'
})
export class ListaOrganizacionesComponent implements OnInit {
  organizaciones: Organizacion[] = [];

  constructor(private organizacionService: OrganizacionService) {}

  ngOnInit(): void { this.cargar(); }

  cargar(): void {
    this.organizacionService.listar().subscribe({
      next: (data) => this.organizaciones = data
    });
  }

  desactivar(id: number): void {
    if (confirm('¿Desactivar esta organización?')) {
      this.organizacionService.desactivar(id).subscribe({
        next: () => this.cargar()
      });
    }
  }
}