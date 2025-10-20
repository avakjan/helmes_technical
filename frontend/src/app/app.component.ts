import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  private http = inject(HttpClient);
  private fb = inject(FormBuilder);

  title = 'Helmes Technical';

  sectors = signal<Array<{id:number,name:string,parentId:number|null,sortOrder:number,depth:number}>>([]);
  loading = signal<boolean>(false);
  saving = signal<boolean>(false);
  error = signal<string | null>(null);
  success = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(200)]],
    sectorIds: this.fb.nonNullable.control<number[]>([], { validators: [Validators.required]}),
    agreeToTerms: this.fb.nonNullable.control<boolean>(false, { validators: [Validators.requiredTrue]})
  });

  constructor() {
    this.loadSectors();
    this.loadMySubmission();
  }

  onToggleSector(sectorId: number, checked: boolean) {
    const current = this.form.controls.sectorIds.value ?? [];
    const set = new Set<number>(current);
    if (checked) {
      set.add(sectorId);
    } else {
      set.delete(sectorId);
    }
    this.form.controls.sectorIds.setValue(Array.from(set));
    this.form.controls.sectorIds.markAsDirty();
    this.form.controls.sectorIds.updateValueAndValidity();
  }

  private loadSectors() {
    this.loading.set(true);
    this.http.get<Array<{id:number,name:string,parentId:number|null,sortOrder:number}>>('/api/sectors')
      .subscribe({
        next: data => {
          const withDepth = this.computeDepths(data);
          this.sectors.set(withDepth);
          this.loading.set(false);
        },
        error: err => {
          this.error.set('Failed to load sectors');
          this.loading.set(false);
        }
      });
  }

  private computeDepths(items: Array<{id:number,name:string,parentId:number|null,sortOrder:number}>) {
    const parentMap = new Map<number, number | null>();
    items.forEach(i => parentMap.set(i.id, i.parentId ?? null));
    const memo = new Map<number, number>();
    const depthOf = (id: number): number => {
      if (memo.has(id)) return memo.get(id)!;
      const parent = parentMap.get(id);
      const d = parent ? 1 + depthOf(parent) : 0;
      memo.set(id, d);
      return d;
    };
    return items.map(i => ({ ...i, depth: depthOf(i.id) }));
  }

  private loadMySubmission() {
    this.http.get<{id:number,name:string,agreeToTerms:boolean,sectorIds:number[]}>('/api/submissions/me')
      .subscribe({
        next: data => {
          if (data) {
            this.form.patchValue({
              name: data.name,
              agreeToTerms: data.agreeToTerms,
              sectorIds: data.sectorIds ?? []
            });
          }
        },
        error: _ => {
        }
      });
  }

  save() {
    this.error.set(null);
    this.success.set(null);
    if (this.form.invalid) {
      this.error.set('Please fill all fields');
      this.form.markAllAsTouched();
      return;
    }
    this.saving.set(true);
    const payload = this.form.getRawValue();
    this.http.post<{id:number,name:string,agreeToTerms:boolean,sectorIds:number[]}>('/api/submissions', payload)
      .subscribe({
        next: _ => {
          this.success.set('Saved');
          this.saving.set(false);
        },
        error: _ => {
          this.error.set('Failed to save');
          this.saving.set(false);
        }
      });
  }
}
