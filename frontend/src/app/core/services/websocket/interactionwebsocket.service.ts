import { inject, Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { AuthService } from '../auth.service';
import { environment } from '../../../../environment/environment.development';
import { CommentInteractionEvent, LikeInteractionEvent } from '../../models/post/post-interaction-event';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InteractionwebsocketService {

  private client!: Client;
  private authService = inject(AuthService);
  private readonly interactionWsUrl = `${environment.interactionWsUrl}`;

  private connected = false;

  private likeSubject =
    new Subject<LikeInteractionEvent>();

  likeSub$ =
    this.likeSubject.asObservable();

  private commentSubject = new Subject<CommentInteractionEvent>();

  commentSub$ = this.commentSubject.asObservable();

  connect(): void {

    this.client = new Client({

      brokerURL: `${this.interactionWsUrl}`,

      connectHeaders: {
        Authorization: `Bearer ${this.authService.getToken()}`
      },

      reconnectDelay: 0,

      onConnect: () => {

        console.log('✅ Connected');

        this.connected = true;

        this.client.subscribe(
          '/topic/post/like',
          message => {
            const event: LikeInteractionEvent = JSON.parse(message.body);

            this.likeSubject.next(event);
          }
        );
        this.client.subscribe(
          '/topic/post/comment',
          message => {
            const event: CommentInteractionEvent = JSON.parse(message.body);

            console.log(event);

            this.commentSubject.next(event);
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

    });

    this.client.activate();
  }

  getWebSocketConneted(): boolean {
    return this.connected;
  }

}
