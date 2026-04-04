// formulario-organizacion.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { OrganizacionService } from '../../../services/organizacion.service';

@Component({
  selector: 'app-formulario-organizacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, TopbarComponent],
  templateUrl: './formulario-organizacion.component.html',
  styleUrl: './formulario-organizacion.component.scss'
})
export class FormularioOrganizacionComponent implements OnInit {
  form!: FormGroup;
  esEdicion = false;
  idEdicion?: number;
  guardando = false;

  constructor(
    private fb: FormBuilder,
    private organizacionService: OrganizacionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre:      ['', Validators.required],
      tipo:        ['', Validators.required],
      ruc:         [''],
      telefono:    [''],
      direccion:   [''],
      responsable: ['']
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.esEdicion = true;
      this.idEdicion = +id;
      this.organizacionService.buscarPorId(this.idEdicion).subscribe({
        next: (o) => this.form.patchValue(o)
      });
    }
  }

  guardar(): void {
    if (this.form.invalid) return;
    this.guardando = true;

    const accion = this.esEdicion
      ? this.organizacionService.actualizar(this.idEdicion!, this.form.value)
      : this.organizacionService.crear(this.form.value);

    accion.subscribe({
      next: () => this.router.navigate(['/organizaciones']),
      error: () => { this.guardando = false; }
    });
  }
}