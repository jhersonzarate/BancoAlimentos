// src/app/pages/auth/auth.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule, FormBuilder, FormGroup, Validators, AbstractControl
} from '@angular/forms';
import { Router } from '@angular/router';
import {
  trigger, transition, style, animate, query, group
} from '@angular/animations';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss',
  animations: [
    trigger('fadeSlide', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(12px)' }),
        animate('220ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ]),
      transition(':leave', [
        animate('150ms ease-in', style({ opacity: 0, transform: 'translateY(-8px)' }))
      ])
    ])
  ]
})
export class AuthComponent implements OnInit {
  modo: 'login' | 'register' = 'login';

  loginForm!: FormGroup;
  registerForm!: FormGroup;

  verPasswordLogin = false;
  verPasswordReg   = false;
  cargando         = false;
  errorMsg         = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Si ya está autenticado, ir al dashboard
    if (this.authService.estaAutenticado()) {
      this.router.navigate(['/dashboard']);
      return;
    }

    this.loginForm = this.fb.group({
      email:    ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    this.registerForm = this.fb.group({
      nombre:   ['', Validators.required],
      email:    ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });

    // Resetear error al cambiar valores
    this.loginForm.valueChanges.subscribe(() => { this.errorMsg = ''; });
    this.registerForm.valueChanges.subscribe(() => { this.errorMsg = ''; });
  }

  cambiarModo(m: 'login' | 'register'): void {
    this.modo = m;
    this.errorMsg = '';
    this.verPasswordLogin = false;
    this.verPasswordReg   = false;
  }

  iniciarSesion(): void {
    if (this.loginForm.invalid) { this.loginForm.markAllAsTouched(); return; }
    this.cargando = true;
    this.errorMsg = '';

    this.authService.login(this.loginForm.value).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => {
        this.cargando = false;
        this.errorMsg = err?.error?.mensaje ?? 'Error al iniciar sesión. Intenta de nuevo.';
      }
    });
  }

  registrar(): void {
    if (this.registerForm.invalid) { this.registerForm.markAllAsTouched(); return; }
    this.cargando = true;
    this.errorMsg = '';

    this.authService.register(this.registerForm.value).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => {
        this.cargando = false;
        this.errorMsg = err?.error?.mensaje ?? 'Error al registrarse. Intenta de nuevo.';
      }
    });
  }

  // ── Fortaleza de contraseña ──
  get fortalezaPassword(): number {
    const pass = this.registerForm.get('password')?.value ?? '';
    let score = 0;
    if (pass.length >= 8)  score += 25;
    if (pass.length >= 12) score += 15;
    if (/[A-Z]/.test(pass)) score += 20;
    if (/[0-9]/.test(pass)) score += 20;
    if (/[^A-Za-z0-9]/.test(pass)) score += 20;
    return Math.min(score, 100);
  }

  get etiquetaFortaleza(): string {
    const f = this.fortalezaPassword;
    if (f < 40) return 'Débil';
    if (f < 75) return 'Media';
    return 'Fuerte';
  }
}