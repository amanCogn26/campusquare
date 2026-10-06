import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environment/environment.development';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { NotificationDto } from '../models/notification/notification-dto';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {

  private readonly apiUrl = `${environment.apiUrl}`;
  private http = inject(HttpClient);

  getAllNotification(): Observable<NotificationDto[]> {
    return this.http.get<NotificationDto[]>(`${this.apiUrl}/notification`,{});
  }
  
  markAllAsRead(): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/notification/mark-all-read`,{});
  }
}
