import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCollege } from './create-college';

describe('CreateCollege', () => {
  let component: CreateCollege;
  let fixture: ComponentFixture<CreateCollege>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateCollege],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateCollege);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
