import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { NotificationwebsocketService } from '../../core/services/websocket/notificationwebsocket.service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar implements OnInit {

  private authService = inject(AuthService);
  private notificationwebsocketService = inject(NotificationwebsocketService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef)

  unReadCount = 0;
  isProfilePage: boolean = false;

  ngOnInit(): void {
    if (this.router.url === '/admin/profile' || this.router.url === '/profile') {
      this.isProfilePage = true;
    } else {
      this.isProfilePage = false;
    }

    this.notificationwebsocketService.unreadCountSub$.subscribe({
      next: (response) => {
        this.unReadCount = response;
        this.cdr.detectChanges();
      }
    })
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/'])
  }

  goToProfile() {
    if (this.authService.getUserRole() === "ADMIN") {
      this.router.navigate(['/admin/profile']);
    } else {
      this.router.navigate(['/profile']);
    }
  }
  goToHome() {
    if (this.authService.getUserRole() === "ADMIN") {
      this.router.navigate(['/admin/dashboard']);
    } else {
      this.router.navigate(['/post']);
    }
  }
  goToNotification() {
    if (this.authService.getUserRole() === "ADMIN") {
      this.router.navigate(['/admin/notification']);
    } else {
      this.router.navigate(['/notification']);
    }
  }
}
