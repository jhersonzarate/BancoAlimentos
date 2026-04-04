// src/app/services/auth.service.ts
import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private url = `${environment.apiUrl}/auth`;

  // Signal reactivo para el usuario actual
  readonly usuario = signal<AuthResponse | null>(this.cargarSesion());

  constructor(private http: HttpClient, private router: Router) {}

  login(req: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.url}/login`, req).pipe(
      tap(res => this.guardarSesion(res))
    );
  }

  register(req: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.url}/register`, req).pipe(
      tap(res => this.guardarSesion(res))
    );
  }

  logout(): void {
    localStorage.removeItem('bap_auth');
    this.usuario.set(null);
    this.router.navigate(['/login']);
  }

  estaAutenticado(): boolean {
    return !!this.usuario();
  }

  getToken(): string | null {
    return this.usuario()?.token ?? null;
  }

  private guardarSesion(res: AuthResponse): void {
    localStorage.setItem('bap_auth', JSON.stringify(res));
    this.usuario.set(res);
  }

  private cargarSesion(): AuthResponse | null {
    try {
      const raw = localStorage.getItem('bap_auth');
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }
}