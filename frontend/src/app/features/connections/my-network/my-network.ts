import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { UserService } from '../../../core/services/user.service';
import { UserDto } from '../../../core/models/user/user-dto';
import { CommonModule } from '@angular/common';
import { ConnectionService } from '../../../core/services/connection.service';
import { ConnectionDto } from '../../../core/models/connection/connection-dto';

@Component({
  selector: 'app-my-network',
  imports: [CommonModule],
  templateUrl: './my-network.html',
  styleUrl: './my-network.css',
})
export class MyNetwork implements OnInit {

  private userService = inject(UserService);
  private connectionService = inject(ConnectionService);
  private cdr = inject(ChangeDetectorRef);
  connectionStatus: string = "CONNECT";

  userDto: UserDto[] = [];
  connectionDto!: ConnectionDto;
  connectionMap: { [key: number]: ConnectionDto } = {};

  ngOnInit(): void {

    this.userService.getAllUsersByCollegeId().subscribe({

      next: (response) => {
        this.userDto = response;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })

    this.connectionService.getAllConnectionByUserId().subscribe({
      next: (response) => {
        response.forEach((res) => {
          this.connectionMap[res.userId] = res;
        })
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  sendConnectionRequest(receiverId: number) {

    this.connectionService.sendConnectionRequest(receiverId).subscribe({
      next: (response) => {
        this.connectionMap[response.userId] = response;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })

  }

  acceptConnectionRequest(connectionId: number) {
    this.connectionService.acceptConnectionRequest(connectionId).subscribe({
      next: (response) => {
        this.connectionMap = {
          ...this.connectionMap,
          [response.userId]: response
        };
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  rejectConnectionRequest(connectionId: number) {
    this.connectionService.rejectConnectionRequest(connectionId).subscribe({
      next: (response) => {
        this.connectionMap[response.userId] = response;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  removeConnection(connectionId: number, userId: number) {
    this.connectionService.removeConnection(connectionId).subscribe({
      next: (response) => {
        delete this.connectionMap[userId];
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
