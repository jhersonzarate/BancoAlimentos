// src/app/shared/sidebar/sidebar.component.ts — CON AUTH
import { Component, computed } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SidebarService } from '../../services/sidebar.service';
import { AuthService } from '../../services/auth.service';
import { AuthResponse } from '../../models/auth.model';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  constructor(
    public sidebarService: SidebarService,
    private authService: AuthService
  ) {}

  get usuario(): AuthResponse | null {
    return this.authService.usuario();
  }

  cerrar(): void {
    this.sidebarService.cerrar();
  }

  cerrarAlNavegar(): void {
    if (window.innerWidth <= 768) {
      this.sidebarService.cerrar();
    }
  }

  cerrarSesion(): void {
    this.authService.logout();
  }
}