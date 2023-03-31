import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FornecedoresEditComponent } from './fornecedores-edit.component';

describe('FornecedoresEditComponent', () => {
  let component: FornecedoresEditComponent;
  let fixture: ComponentFixture<FornecedoresEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FornecedoresEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FornecedoresEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
