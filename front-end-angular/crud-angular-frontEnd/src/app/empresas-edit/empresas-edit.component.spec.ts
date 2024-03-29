import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpresasEditComponent } from './empresas-edit.component';

describe('EmpresasEditComponent', () => {
  let component: EmpresasEditComponent;
  let fixture: ComponentFixture<EmpresasEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmpresasEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmpresasEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
