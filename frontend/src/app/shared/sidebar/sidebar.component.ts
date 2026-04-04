// src/app/shared/sidebar/sidebar.component.ts
import { Component, HostListener } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  constructor(public sidebarService: SidebarService) {}

  cerrar(): void {
    this.sidebarService.cerrar();
  }

  cerrarAlNavegar(): void {
    // Solo cerrar en móvil
    if (window.innerWidth <= 768) {
      this.sidebarService.cerrar();
    }
  }
}