// src/app/shared/topbar/topbar.component.ts — CON AUTH
import { Component, Input } from '@angular/core';
import { SidebarService } from '../../services/sidebar.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-topbar',
  standalone: true,
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent {
  @Input() titulo: string = 'Sistema de Donaciones';

  constructor(
    private sidebarService: SidebarService,
    public authService: AuthService
  ) {}

  toggleSidebar(): void {
    this.sidebarService.toggle();
  }

  get nombreUsuario(): string {
    return this.authService.usuario()?.nombre ?? 'Administrador';
  }
}