import { Component, inject } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { RouterOutlet } from '@angular/router';
import { AdminSideBar } from "../../admin/admin-side-bar/admin-side-bar";
import { LoaderService } from '../../core/services/loader.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-layout',
  imports: [Navbar, RouterOutlet, AdminSideBar,CommonModule],
  templateUrl: './admin-layout.html',
  styleUrl: './admin-layout.css',
})
export class AdminLayout {

  private loaderService = inject(LoaderService);

  loading$ = this.loaderService.loadingSub$;
}
