import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionCanceledComponent } from './subscription-canceled.component';

describe('SubscriptionCanceledComponent', () => {
  let component: SubscriptionCanceledComponent;
  let fixture: ComponentFixture<SubscriptionCanceledComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubscriptionCanceledComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscriptionCanceledComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
