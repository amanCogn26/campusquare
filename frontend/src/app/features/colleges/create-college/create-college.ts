import { Component, EventEmitter, importProvidersFrom, inject, OnInit, Output } from '@angular/core';
import { CollegeService } from '../../../core/services/college.service';
import { Router } from '@angular/router';
import { CollegeResponseDto } from '../../../core/models/college/college-response-dto';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-create-college',
  imports: [ReactiveFormsModule],
  templateUrl: './create-college.html',
  styleUrl: './create-college.css',
})
export class CreateCollege implements OnInit{

  @Output() 
  closeModal = new EventEmitter<void>();

  private collegeService = inject(CollegeService);
  private router=inject(Router)

  collegeResponseData!: CollegeResponseDto;
  collegeFormData!: FormGroup;

  ngOnInit(): void {
    this.collegeFormData = new FormGroup({
      collegeName: new FormControl(),
      emailDomain: new FormControl(),
      logo: new FormControl(),
      city: new FormControl(),
      state: new FormControl(),
    })
  }

  formSubmit() {

    this.collegeService.createCollege(this.collegeFormData.value).subscribe({
      next: (response) => {
        this.collegeResponseData=response;
        this.collegeFormData.reset();
        this.closeModal.emit();
        this.router.navigate(["/admin/college"]);
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
