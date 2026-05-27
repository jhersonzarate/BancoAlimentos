// src/app/services/inventario.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Inventario } from '../models/index';

@Injectable({ providedIn: 'root' })
export class InventarioService {
  private url = `${environment.apiUrl}/inventario`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Inventario[]> {
    return this.http.get<Inventario[]>(this.url);
  }

  buscarPorId(id: number): Observable<Inventario> {
    return this.http.get<Inventario>(`${this.url}/${id}`);
  }

  crear(item: Inventario): Observable<Inventario> {
    return this.http.post<Inventario>(this.url, item);
  }

  actualizar(id: number, item: Inventario): Observable<Inventario> {
    return this.http.put<Inventario>(`${this.url}/${id}`, item);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}