// src/app/guards/admin.guard.ts
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Guard que permite el acceso solo a usuarios con rol ADMIN.
 * Si el usuario no tiene ese rol, lo redirige al dashboard.
 */
export const adminGuard: CanActivateFn = () => {
  const auth   = inject(AuthService);
  const router = inject(Router);

  const usuario = auth.usuario();

  if (!usuario) {
    router.navigate(['/login']);
    return false;
  }

  if (usuario.rol !== 'ADMIN') {
    router.navigate(['/dashboard']);
    return false;
  }

  return true;
};