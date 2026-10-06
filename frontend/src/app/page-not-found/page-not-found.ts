import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-page-not-found',
  imports: [],
  templateUrl: './page-not-found.html',
  styleUrl: './page-not-found.css',
})
export class PageNotFound {

  private authService = inject(AuthService);
  private router = inject(Router);
  
  goToHome() {
    if (this.authService.getUserRole() === "ADMIN") {
      this.router.navigate(['/admin/dashboard']);
    } else {
      this.router.navigate(['/post']);
    }
  }
}
