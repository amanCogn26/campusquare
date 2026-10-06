import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { UpperCasePipe } from '@angular/common';
import { LoginResponse } from '../../core/models/auth/login-response';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-left-sidebar',
  imports: [UpperCasePipe, RouterLink],
  templateUrl: './left-sidebar.html',
  styleUrl: './left-sidebar.css',
})
export class LeftSidebar implements OnInit{

  private authService = inject(AuthService);

  user:LoginResponse | null = null;

  ngOnInit(): void {
    this.user=this.authService.getUser();
  }
}
