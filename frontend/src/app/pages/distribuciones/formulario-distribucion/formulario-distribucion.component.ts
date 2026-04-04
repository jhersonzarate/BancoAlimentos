// formulario-distribucion.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { TopbarComponent } from '../../../shared/topbar/topbar.component';
import { DistribucionService } from '../../../services/distribucion.service';
import { DonacionService } from '../../../services/donacion.service';
import { OrganizacionService } from '../../../services/organizacion.service';
import { Donacion, Organizacion } from '../../../models/index';

@Component({
  selector: 'app-formulario-distribucion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, TopbarComponent],
  templateUrl: './formulario-distribucion.component.html',
  styleUrl: './formulario-distribucion.component.scss'
})
export class FormularioDistribucionComponent implements OnInit {
  form!: FormGroup;
  donaciones: Donacion[] = [];
  organizaciones: Organizacion[] = [];
  guardando = false;

  constructor(
    private fb: FormBuilder,
    private distribucionService: DistribucionService,
    private donacionService: DonacionService,
    private organizacionService: OrganizacionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      donacionId:        ['', Validators.required],
      organizacionId:    ['', Validators.required],
      cantidadEntregada: [null, [Validators.required, Validators.min(0.01)]],
      fechaEntrega:      [new Date().toISOString().split('T')[0]],
      notas:             ['']
    });

    // Cargar solo donaciones en estado disponible
    this.donacionService.listar().subscribe({
      next: (data) => {
        this.donaciones = data.filter(d =>
          d.estado === 'PENDIENTE' || d.estado === 'EN_PROCESO'
        );
      }
    });

    this.organizacionService.listar().subscribe({
      next: (data) => this.organizaciones = data
    });
  }

  guardar(): void {
    if (this.form.invalid) return;
    this.guardando = true;

    const payload = {
      ...this.form.value,
      donacionId:     +this.form.value.donacionId,
      organizacionId: +this.form.value.organizacionId
    };

    this.distribucionService.crear(payload).subscribe({
      next: () => this.router.navigate(['/distribuciones']),
      error: () => { this.guardando = false; }
    });
  }
}