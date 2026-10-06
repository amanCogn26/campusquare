import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../../core/services/auth.service';
import { InteractionwebsocketService } from '../../../core/services/websocket/interactionwebsocket.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {

  private auth = inject(AuthService);
  private router = inject(Router)
  private interactionwebsocket = inject(InteractionwebsocketService);

  loginFormData !: FormGroup;

  ngOnInit(): void {
    this.loginFormData = new FormGroup({
      collegeEmail: new FormControl(),
      password: new FormControl(),
    })
  }

  formSubmit(): void {
    this.auth.login(this.loginFormData.value).subscribe({
      next: (response) => {
        this.auth.saveUser(response);
        this.interactionwebsocket.connect();

        if(response.role==='ADMIN'){
          this.router.navigate(['/admin/dashboard']);
        }else{
          this.router.navigate(['/post']);
        }
        
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
