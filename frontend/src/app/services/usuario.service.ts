// src/app/services/usuario.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Usuario, UsuarioActualizarRequest } from '../models/index';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private url = `${environment.apiUrl}/usuarios`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.url);
  }

  buscarPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.url}/${id}`);
  }

  actualizar(id: number, datos: UsuarioActualizarRequest): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.url}/${id}`, datos);
  }

  activar(id: number): Observable<Usuario> {
    return this.http.patch<Usuario>(`${this.url}/${id}/activar`, null);
  }

  desactivar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}