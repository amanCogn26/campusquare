import { Component, inject, OnInit } from '@angular/core';
import { NotificationService } from '../../core/services/notification.service';
import { NotificationDto } from '../../core/models/notification/notification-dto';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { NotificationwebsocketService } from '../../core/services/websocket/notificationwebsocket.service';


@Component({
  selector: 'app-notification',
  imports: [CommonModule],
  templateUrl: './notification.html',
  styleUrl: './notification.css',
})
export class Notification implements OnInit {

  private notificationService = inject(NotificationService);
  private notificationWebsocketService = inject(NotificationwebsocketService);
  private authService = inject(AuthService);

  userName = this.authService.getUser()?.name;
  notificationDto: NotificationDto[] = [];

  ngOnInit(): void {
    this.notificationService.getAllNotification().subscribe({
      next: (response) => {

        this.notificationDto = response.sort(
          (a, b) =>
            new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
        );

        const unreadCount = response.filter(
          notification => !notification.isRead
        ).length;

        this.notificationWebsocketService.setUnReadCount(unreadCount);
      },
      error: (err) => {
        console.log(err);
      }
    })

    this.notificationWebsocketService.notificationSub$
      .subscribe((notification) => {
        this.notificationDto.unshift(notification);
      });
  }

  markAllAsRead() {
    this.notificationService.markAllAsRead().subscribe({
      next: (response) => {
        this.notificationWebsocketService.resetUnReadCount();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}