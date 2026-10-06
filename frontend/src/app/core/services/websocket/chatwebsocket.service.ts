import { inject, Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { AuthService } from '../auth.service';
import { environment } from '../../../../environment/environment.development';
import { ChatMessageResponseDto } from '../../models/ChatMessage/chat-message-response-dto';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChatwebsocketService {

  private client!: Client;
  private authService = inject(AuthService);
  private userId = this.authService.getUserId();
  private readonly chatWsUrl = `${environment.chatWsUrl}`;

  private connected = false;

  private chatMessageSubject = new Subject<ChatMessageResponseDto>();

  chatMessageSub$ = this.chatMessageSubject.asObservable();

  publishChatMessage(message: any): void {

    if (!this.getWebSocketConneted()) {
      console.error('WebSocket not connected');
      return;
    }

    this.client.publish({
      destination: '/app/chat.send',
      body: JSON.stringify(message)
    });

  }

  connect(): void {

    this.client = new Client({

      brokerURL: `${this.chatWsUrl}`,

      connectHeaders: {
        Authorization: `Bearer ${this.authService.getToken()}`
      },

      reconnectDelay: 0,

      onConnect: () => {

        console.log('✅ Connected');

        this.connected = true;

        this.client.subscribe(
          `/topic/chat/${this.userId}`,
          message => {
            const event: ChatMessageResponseDto = JSON.parse(message.body);

            console.log(event);

            this.chatMessageSubject.next(event);
          }
        )
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

    });

    console.log("WS URL:", this.chatWsUrl);

    this.client.activate();
  }

  getWebSocketConneted(): boolean {
    return this.connected;
  }


}
