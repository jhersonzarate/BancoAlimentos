// src/app/shared/topbar/topbar.component.ts
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-topbar',
  standalone: true,
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent {
  @Input() titulo: string = 'Sistema de Donaciones';
}