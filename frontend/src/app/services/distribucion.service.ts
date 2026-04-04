// src/app/services/distribucion.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Distribucion } from '../models/index';

@Injectable({ providedIn: 'root' })
export class DistribucionService {
  private url = `${environment.apiUrl}/distribuciones`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Distribucion[]> {
    return this.http.get<Distribucion[]>(this.url);
  }

  buscarPorId(id: number): Observable<Distribucion> {
    return this.http.get<Distribucion>(`${this.url}/${id}`);
  }

  crear(dist: Distribucion): Observable<Distribucion> {
    return this.http.post<Distribucion>(this.url, dist);
  }

  marcarEntregado(id: number): Observable<Distribucion> {
    return this.http.patch<Distribucion>(`${this.url}/${id}/entregar`, null);
  }

  cancelar(id: number): Observable<Distribucion> {
    return this.http.patch<Distribucion>(`${this.url}/${id}/cancelar`, null);
  }
}