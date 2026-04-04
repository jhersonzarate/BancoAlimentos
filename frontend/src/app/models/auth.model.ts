// src/app/models/auth.model.ts

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  nombre: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  id: number;
  nombre: string;
  email: string;
  rol: string;
  token: string;
  createdAt: string;
}