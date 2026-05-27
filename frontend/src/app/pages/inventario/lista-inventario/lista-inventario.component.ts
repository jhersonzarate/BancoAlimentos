// src/app/pages/inventario/lista-inventario/lista-inventario.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { InventarioService } from '../../../services/inventario.service';
import { Inventario } from '../../../models/index';

@Component({
  selector: 'app-lista-inventario',
  standalone: true,
  imports: [CommonModule, RouterLink, TopbarComponent],
  templateUrl: './lista-inventario.component.html',
  styleUrl: './lista-inventario.component.scss'
})
export class ListaInventarioComponent implements OnInit {
  inventario: Inventario[] = [];
  cargando = true;

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.inventarioService.listar().subscribe({
      next: (data) => {
        this.inventario = data;
        this.cargando = false;
      },
      error: () => { this.cargando = false; }
    });
  }

  eliminar(id: number): void {
    if (confirm('¿Eliminar este ítem del inventario?')) {
      this.inventarioService.eliminar(id).subscribe({
        next: () => this.cargar()
      });
    }
  }
}