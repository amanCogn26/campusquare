import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from './core/services/auth.service';
// import { WebSocketService } from './core/services/websocket/web-socket-service';
import { LoaderService } from './core/services/loader.service';
import { CommonModule } from '@angular/common';
import { InteractionwebsocketService } from './core/services/websocket/interactionwebsocket.service';
import { NotificationwebsocketService } from './core/services/websocket/notificationwebsocket.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  protected readonly title = signal('campusquare-ui');
  private auth = inject(AuthService);
  private router = inject(Router);
  private interactionwebsocket = inject(InteractionwebsocketService)
  private notificationwebsocket = inject(NotificationwebsocketService)
  private loaderService = inject(LoaderService);

  loading$ = this.loaderService.loadingSub$;

  ngOnInit(): void {

    if (this.auth.getToken() == null) {
      this.router.navigate(['/login']);
    }
    
    if (this.auth.getToken()) {
      this.notificationwebsocket.connect();
    }

    if (
      this.auth.getToken() &&
      !this.interactionwebsocket.getWebSocketConneted()
    ) {
      this.interactionwebsocket.connect();
    }
  }
}
