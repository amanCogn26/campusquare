import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environment/environment.development';
import { Observable } from 'rxjs';
import { ChatMessageResponseDto } from '../models/ChatMessage/chat-message-response-dto';

@Injectable({
  providedIn: 'root',
})
export class ChatService {

  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}`;

  getConversation(receiverId: number):Observable<ChatMessageResponseDto[]> {
    return this.http.get<ChatMessageResponseDto[]>(`${this.apiUrl}/chat/${receiverId}`,{})
  }

}
