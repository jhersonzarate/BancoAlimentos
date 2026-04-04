// src/app/pages/donaciones/formulario-donacion/formulario-donacion.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { DonacionService } from '../../../services/donacion.service';

@Component({
  selector: 'app-formulario-donacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, TopbarComponent],
  templateUrl: './formulario-donacion.component.html',
  styleUrl: './formulario-donacion.component.scss'
})
export class FormularioDonacionComponent implements OnInit {
  form!: FormGroup;
  esEdicion = false;
  idEdicion?: number;
  guardando = false;

  constructor(
    private fb: FormBuilder,
    private donacionService: DonacionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      donante:         ['', Validators.required],
      tipoAlimento:    ['', Validators.required],
      cantidad:        [null, [Validators.required, Validators.min(0.01)]],
      unidad:          ['kg', Validators.required],
      fechaDonacion:   [new Date().toISOString().split('T')[0]],
      fechaVencimiento:[null],
      observaciones:   ['']
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.esEdicion = true;
      this.idEdicion = +id;
      this.donacionService.buscarPorId(this.idEdicion).subscribe({
        next: (d) => this.form.patchValue(d)
      });
    }
  }

  guardar(): void {
    if (this.form.invalid) return;
    this.guardando = true;

    const datos = this.form.value;

    const accion = this.esEdicion
      ? this.donacionService.actualizar(this.idEdicion!, datos)
      : this.donacionService.crear(datos);

    accion.subscribe({
      next: () => this.router.navigate(['/donaciones']),
      error: () => { this.guardando = false; }
    });
  }
}