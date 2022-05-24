import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceOrderBtnComponent } from './place-order-btn.component';

describe('PlaceOrderBtnComponent', () => {
  let component: PlaceOrderBtnComponent;
  let fixture: ComponentFixture<PlaceOrderBtnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlaceOrderBtnComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaceOrderBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
