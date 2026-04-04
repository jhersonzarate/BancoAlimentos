// src/app/services/donacion.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Donacion } from '../models/index';

@Injectable({ providedIn: 'root' })
export class DonacionService {
  private url = `${environment.apiUrl}/donaciones`;

  constructor(private http: HttpClient) {}

  listar(estado?: string): Observable<Donacion[]> {
    let params = new HttpParams();
    if (estado) params = params.set('estado', estado);
    return this.http.get<Donacion[]>(this.url, { params });
  }

  buscarPorId(id: number): Observable<Donacion> {
    return this.http.get<Donacion>(`${this.url}/${id}`);
  }

  crear(donacion: Donacion): Observable<Donacion> {
    return this.http.post<Donacion>(this.url, donacion);
  }

  actualizar(id: number, donacion: Donacion): Observable<Donacion> {
    return this.http.put<Donacion>(`${this.url}/${id}`, donacion);
  }

  cambiarEstado(id: number, estado: string): Observable<Donacion> {
    return this.http.patch<Donacion>(`${this.url}/${id}/estado`, null, {
      params: new HttpParams().set('estado', estado)
    });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}