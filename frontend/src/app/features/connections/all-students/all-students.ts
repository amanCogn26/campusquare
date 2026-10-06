import { ChangeDetectorRef, Component, inject, Injector, OnInit } from '@angular/core';
import { UserService } from '../../../core/services/user.service';
import { UserDto } from '../../../core/models/user/user-dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-all-students',
  imports: [CommonModule],
  templateUrl: './all-students.html',
  styleUrl: './all-students.css',
})
export class AllStudents implements OnInit {

  private userService = inject(UserService);
  private cdr = inject(ChangeDetectorRef);

  users: UserDto[] = [];

  ngOnInit(): void {

    this.userService.getAllUser().subscribe({
      next: (response) => {
        this.users = response.filter(
          (user)=> user.role!=='ADMIN'
        );
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  deleteUser(userId: number): void {
    this.userService.deleteUser(userId).subscribe({
      next: (response) => {
        this.users = this.users.filter(
          (user) => userId !== user.id
        )
        this.cdr.detectChanges();
      }
    })
  }
}
