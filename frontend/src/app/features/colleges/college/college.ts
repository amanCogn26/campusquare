import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CollegeService } from '../../../core/services/college.service';
import { CollegeResponseDto } from '../../../core/models/college/college-response-dto';
import { CreateCollege } from "../create-college/create-college";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-college',
  imports: [CreateCollege, CommonModule],
  templateUrl: './college.html',
  styleUrl: './college.css',
})
export class College implements OnInit {

  private collegeService = inject(CollegeService);
  private cdr = inject(ChangeDetectorRef);

  createCollege: boolean = false;

  collegeResponseDataArray: CollegeResponseDto[] = [];
  collegeResponseData!: CollegeResponseDto;

  ngOnInit(): void {
    this.collegeService.getAllColleges().subscribe({
      next: (response) => {
        this.collegeResponseDataArray = response;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  deleteCollege(collegeId: number) {
    this.collegeService.deleteCollege(collegeId).subscribe({
      next: (response) => {
        this.collegeResponseDataArray = this.collegeResponseDataArray.filter(
          (colleges) => colleges.id !== collegeId
        )
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
