import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckoutBtnComponent } from './checkout-btn.component';

describe('CheckoutBtnComponent', () => {
  let component: CheckoutBtnComponent;
  let fixture: ComponentFixture<CheckoutBtnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CheckoutBtnComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckoutBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
