import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ConnectionDto } from '../../core/models/connection/connection-dto';
import { CommonModule } from '@angular/common';
import { ConnectionService } from '../../core/services/connection.service';
import { ChatService } from '../../core/services/chat.service';
import { ChatMessageResponseDto } from '../../core/models/ChatMessage/chat-message-response-dto';
import { FormsModule } from '@angular/forms';
import { ChatwebsocketService } from '../../core/services/websocket/chatwebsocket.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-chat',
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.html',
  styleUrl: './chat.css',
})
export class Chat implements OnInit {

  private connectionService = inject(ConnectionService);
  private authService = inject(AuthService);
  private chatwebsocket = inject(ChatwebsocketService);
  private chatService = inject(ChatService);
  private cdr = inject(ChangeDetectorRef)
  private router = inject(Router)
  private route = inject(ActivatedRoute)

  connections: ConnectionDto[] = [];
  selectedConnection!: ConnectionDto;
  messages: ChatMessageResponseDto[] = [];
  textMessage: string = '';

  currentUserId = this.authService.getUserId();

  ngOnInit(): void {

    this.chatwebsocket.connect();

    this.chatwebsocket.chatMessageSub$
      .subscribe(message => {

        // console.log("WS MESSAGE RECEIVED", message);

        if (!this.selectedConnection) {
          return;
        }

        const selectedUserId =
          this.selectedConnection.userId;

        const belongsToCurrentConversation =

          (
            message.senderId === this.currentUserId &&
            message.receiverId === selectedUserId
          )

          ||

          (
            message.senderId === selectedUserId &&
            message.receiverId === this.currentUserId
          );

        if (belongsToCurrentConversation) {

          this.messages = [...this.messages, message];
          this.cdr.detectChanges();
          this.scrollToBottom();
        }

      });

    this.connectionService.getAllAcceptedConnection()
      .subscribe({

        next: response => {

          this.connections = response;

          this.route.paramMap.subscribe(params => {

            const userId =
              Number(params.get('userId'));

            if (!userId) {
              return;
            }

            const connection =
              this.connections.find(
                c => c.userId === userId
              );

            if (!connection) {
              return;
            }

            this.selectedConnection = connection;

            this.chatService
              .getConversation(userId)
              .subscribe({
                next: messages => {
                  this.messages = messages;
                  this.scrollToBottom();
                  this.cdr.detectChanges();
                }
              });
          });
        }
      });

  }


  selectConnection(connection: ConnectionDto) {

    this.router.navigate([
      '/chat',
      connection.userId
    ]);

    this.selectedConnection = connection;

    this.chatService.getConversation(connection.userId).subscribe({
      next: (response) => {
        this.messages = response;
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  sendMessage() {

    if (!this.selectedConnection) {
      console.log("No connection selected");
      return;
    }

    if (!this.textMessage.trim()) {
      return;
    }

    const content = this.textMessage;

    const request = {
      receiverId: this.selectedConnection.userId,
      content,
      messageType: 'TEXT'
    };

    this.chatwebsocket.publishChatMessage(request);

    this.textMessage = ''
  }

  scrollToBottom() {
    setTimeout(() => {
      const chatBox = document.getElementById('chat-box');

      if (chatBox) {
        chatBox.scrollTop = chatBox.scrollHeight;
      }
    });
  }

}
