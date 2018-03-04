import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBeerComponent } from './edit-beer.component';

describe('EditBeerComponent', () => {
  let component: EditBeerComponent;
  let fixture: ComponentFixture<EditBeerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditBeerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditBeerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
