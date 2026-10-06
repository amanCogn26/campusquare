import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environment/environment.development';
import { Client } from '@stomp/stompjs';
import { AuthService } from '../auth.service';
import { BehaviorSubject, Subject } from 'rxjs';
import { NotificationDto } from '../../models/notification/notification-dto';

@Injectable({
  providedIn: 'root',
})
export class NotificationwebsocketService {

  private readonly notificationWsUrl = `${environment.notificationWsUrl}`;
  private authService = inject(AuthService);
  private userId = this.authService.getUserId();
  private client!: Client;
  private connected = false;

  private notificationSubject = new Subject<NotificationDto>();
  notificationSub$ = this.notificationSubject.asObservable();

  private unreadCountSubject = new BehaviorSubject<number>(0);
  unreadCountSub$ = this.unreadCountSubject.asObservable();

  setUnReadCount(count: number) {
    this.unreadCountSubject.next(count);
  }

  incrementUnReadCount() {
    this.unreadCountSubject.next(this.unreadCountSubject.value + 1);
  }

  resetUnReadCount() {
    this.unreadCountSubject.next(0);
  }

  connect(): void {

    this.client = new Client({

      brokerURL: `${this.notificationWsUrl}`,

      connectHeaders: {
        Authorization: `Bearer ${this.authService.getToken()}`
      },

      reconnectDelay: 0,


      onConnect: () => {

        console.log('✅ Connected');

        this.connected = true;

        this.client.subscribe(
          `/topic/notification/${this.userId}`,
          message => {
            const notificationDto: NotificationDto = JSON.parse(message.body);

            this.notificationSubject.next(notificationDto);
            this.incrementUnReadCount();
          }
        );
      },


      onStompError: frame => {
        console.log("STOMP ERROR", frame);
      },

      onWebSocketError: event => {
        console.log("WS ERROR", event);
      },
      onWebSocketClose: event => {

        console.log("WS CLOSED", event);
      }
    })

    this.client.activate();
  }

}
