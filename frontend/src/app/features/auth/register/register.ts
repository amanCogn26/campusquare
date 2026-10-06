import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { FormControl, FormGroup, ReactiveFormsModule, ɵInternalFormsSharedModule } from "@angular/forms";
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register implements OnInit{

  constructor(private authService:AuthService,private router:Router){

  }

  registerFormData !: FormGroup;

  ngOnInit(): void {
    this.registerFormData = new FormGroup({
      name:new FormControl(),
      collegeEmail:new FormControl(),
      password:new FormControl(),
      studentId:new FormControl()
    })
  }

  formSubmit() {
    this.authService.register(this.registerFormData.value).subscribe({
      next:(response)=>{
        console.log(response);
        this.router.navigate(['/login']);
      },
      error:(err)=>{
        console.log(err);
      }
    })
  }
}
