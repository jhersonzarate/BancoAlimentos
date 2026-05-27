// src/app/pages/inventario/formulario-inventario/formulario-inventario.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { InventarioService } from '../../../services/inventario.service';

@Component({
  selector: 'app-formulario-inventario',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, TopbarComponent],
  templateUrl: './formulario-inventario.component.html',
  styleUrl: './formulario-inventario.component.scss'
})
export class FormularioInventarioComponent implements OnInit {
  form!: FormGroup;
  esEdicion = false;
  idEdicion?: number;
  guardando = false;

  constructor(
    private fb:                FormBuilder,
    private inventarioService: InventarioService,
    private router:            Router,
    private route:             ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      tipoAlimento:    ['', Validators.required],
      stockDisponible: [null, [Validators.required, Validators.min(0)]],
      unidad:          ['kg', Validators.required],
      stockMinimo:     [0, Validators.min(0)],
      descripcion:     ['']
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.esEdicion = true;
      this.idEdicion = +id;
      this.inventarioService.buscarPorId(this.idEdicion).subscribe({
        next: (item) => this.form.patchValue(item)
      });
    }
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.guardando = true;

    const accion = this.esEdicion
      ? this.inventarioService.actualizar(this.idEdicion!, this.form.value)
      : this.inventarioService.crear(this.form.value);

    accion.subscribe({
      next: () => this.router.navigate(['/inventario']),
      error: () => { this.guardando = false; }
    });
  }
}