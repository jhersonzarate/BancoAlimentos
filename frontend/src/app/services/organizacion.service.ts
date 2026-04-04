// src/app/services/organizacion.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Organizacion } from '../models/index';

@Injectable({ providedIn: 'root' })
export class OrganizacionService {
  private url = `${environment.apiUrl}/organizaciones`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Organizacion[]> {
    return this.http.get<Organizacion[]>(this.url);
  }

  buscarPorId(id: number): Observable<Organizacion> {
    return this.http.get<Organizacion>(`${this.url}/${id}`);
  }

  crear(org: Organizacion): Observable<Organizacion> {
    return this.http.post<Organizacion>(this.url, org);
  }

  actualizar(id: number, org: Organizacion): Observable<Organizacion> {
    return this.http.put<Organizacion>(`${this.url}/${id}`, org);
  }

  desactivar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}