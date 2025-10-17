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
});
