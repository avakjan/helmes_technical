import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AppComponent],
    }).compileComponents();
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should load sectors and validate form', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const req = httpMock.expectOne('/api/sectors');
    req.flush([{ id: 1, name: 'Manufacturing', parentId: null, sortOrder: 1 }]);

    const req2 = httpMock.expectOne('/api/submissions/me');
    req2.flush({}, { status: 200, statusText: 'OK' });

    fixture.detectChanges();
    expect(app.sectors().length).toBeGreaterThan(0);

    expect(app.form.valid).toBeFalse();
    app.form.patchValue({ name: 'Alice', sectorIds: [1], agreeToTerms: true });
    expect(app.form.valid).toBeTrue();
  });

  it('should compute depths based on parentId (root=0, child=1, grandchild=2)', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    const req = httpMock.expectOne('/api/sectors');
    req.flush([
      { id: 1, name: 'Root', parentId: null, sortOrder: 1 },
      { id: 2, name: 'Child', parentId: 1, sortOrder: 2 },
      { id: 3, name: 'Grandchild', parentId: 2, sortOrder: 3 },
    ]);

    const req2 = httpMock.expectOne('/api/submissions/me');
    req2.flush({}, { status: 200, statusText: 'OK' });

    fixture.detectChanges();

    const list = app.sectors();
    const byId = new Map(list.map(s => [s.id, s]));

    expect(byId.get(1)?.depth).toBe(0);
    expect(byId.get(2)?.depth).toBe(1);
    expect(byId.get(3)?.depth).toBe(2);
  });
});
