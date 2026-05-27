// src/app/pages/usuarios/lista-usuarios/lista-usuarios.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from '../../../models/index';

@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [CommonModule, RouterLink, TopbarComponent],
  templateUrl: './lista-usuarios.component.html',
  styleUrl: './lista-usuarios.component.scss'
})
export class ListaUsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];
  cargando = true;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.usuarioService.listar().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.cargando = false;
      },
      error: () => { this.cargando = false; }
    });
  }

  desactivar(id: number): void {
    if (confirm('¿Desactivar esta cuenta de usuario?')) {
      this.usuarioService.desactivar(id).subscribe({
        next: () => this.cargar()
      });
    }
  }

  activar(id: number): void {
    if (confirm('¿Activar esta cuenta de usuario?')) {
      this.usuarioService.activar(id).subscribe({
        next: () => this.cargar()
      });
    }
  }

  badgeRol(rol: string): string {
    return rol === 'ADMIN' ? 'badge--en-proceso' : 'badge--distribuido';
  }

  badgeEstado(activo: boolean | undefined): string {
    return activo ? 'badge--distribuido' : 'badge--cancelado';
  }
}