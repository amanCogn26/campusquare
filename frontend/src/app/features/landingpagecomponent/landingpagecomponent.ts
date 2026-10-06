import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-landingpagecomponent',
  imports: [RouterLink],
  templateUrl: './landingpagecomponent.html',
  styleUrl: './landingpagecomponent.css',
})
export class Landingpagecomponent implements OnInit {

  private authService = inject(AuthService);
  private router = inject(Router);


  ngOnInit(): void {

    console.log("landing")

    if (this.authService.getToken()) {

      if (this.authService.getUserRole() === 'ADMIN') {
        this.router.navigate(['/admin/dashboard']);
      } else {
        this.router.navigate(['/post']);
      }

    }

  }

}
