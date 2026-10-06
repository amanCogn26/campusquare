import { Component, inject } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { LeftSidebar } from "../../shared/left-sidebar/left-sidebar";
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoaderService } from '../../core/services/loader.service';

@Component({
  selector: 'app-student-layout',
  imports: [Navbar, LeftSidebar, RouterOutlet, CommonModule],
  templateUrl: './student-layout.html',
  styleUrl: './student-layout.css',
})
export class StudentLayout {

  private loaderService = inject(LoaderService);

  loading$ = this.loaderService.loadingSub$;
}
