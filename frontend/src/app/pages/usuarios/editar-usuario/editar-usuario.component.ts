// src/app/pages/usuarios/editar-usuario/editar-usuario.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { UsuarioService } from '../../../services/usuario.service';

@Component({
  selector: 'app-editar-usuario',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, TopbarComponent],
  templateUrl: './editar-usuario.component.html',
  styleUrl: './editar-usuario.component.scss'
})
export class EditarUsuarioComponent implements OnInit {
  form!: FormGroup;
  idUsuario!: number;
  guardando = false;
  cargando  = true;

  constructor(
    private fb:             FormBuilder,
    private usuarioService: UsuarioService,
    private router:         Router,
    private route:          ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      email:  ['', [Validators.required, Validators.email]],
      rol:    ['USUARIO', Validators.required],
      activo: [true, Validators.required]
    });

    this.idUsuario = Number(this.route.snapshot.paramMap.get('id'));

    this.usuarioService.buscarPorId(this.idUsuario).subscribe({
      next: (u) => {
        this.form.patchValue({
          nombre: u.nombre,
          email:  u.email,
          rol:    u.rol,
          activo: u.activo
        });
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        this.router.navigate(['/usuarios']);
      }
    });
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.guardando = true;

    this.usuarioService.actualizar(this.idUsuario, this.form.value).subscribe({
      next: () => this.router.navigate(['/usuarios']),
      error: () => { this.guardando = false; }
    });
  }
}