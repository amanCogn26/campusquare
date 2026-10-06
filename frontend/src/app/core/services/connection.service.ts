import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environment/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConnectionDto } from '../models/connection/connection-dto';

@Injectable({
  providedIn: 'root',
})
export class ConnectionService {

  private readonly apiUrl = `${environment.apiUrl}`;
  private http = inject(HttpClient);

  sendConnectionRequest(receiverId: number): Observable<ConnectionDto> {
    return this.http.post<ConnectionDto>(`${this.apiUrl}/connection/request/${receiverId}`, {});
  }

  acceptConnectionRequest(connectionId: number): Observable<ConnectionDto> {
    return this.http.put<ConnectionDto>(`${this.apiUrl}/connection/accept/${connectionId}`, {});
  }

  rejectConnectionRequest(connectionId: number): Observable<ConnectionDto> {
    return this.http.put<ConnectionDto>(`${this.apiUrl}/connection/reject/${connectionId}`, {});
  }

  getAllAcceptedConnection(): Observable<ConnectionDto[]> {
    return this.http.get<ConnectionDto[]>(`${this.apiUrl}/connection`, {});
  }
  
  
  getAllConnectionByUserId(): Observable<ConnectionDto[]> {
    return this.http.get<ConnectionDto[]>(`${this.apiUrl}/connection/network`, {});
  }

  removeConnection(connectionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/connection/${connectionId}`, {})
  }

}
