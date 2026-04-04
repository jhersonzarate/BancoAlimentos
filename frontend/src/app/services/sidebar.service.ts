// src/app/services/sidebar.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SidebarService {
  private _abierto = new BehaviorSubject<boolean>(false);
  readonly abierto$ = this._abierto.asObservable();

  toggle(): void {
    this._abierto.next(!this._abierto.value);
  }

  cerrar(): void {
    this._abierto.next(false);
  }

  abrir(): void {
    this._abierto.next(true);
  }
}