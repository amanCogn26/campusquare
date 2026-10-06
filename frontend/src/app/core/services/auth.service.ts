import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environment/environment.development';
import { Observable } from 'rxjs';
import { LoginResponse } from '../models/auth/login-response';
import { RegisterResponse } from '../models/auth/register-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private http = inject(HttpClient);
  private router = inject(Router);

  loginResponseData: LoginResponse | null = null;

  private readonly apiUrl = environment.apiUrl;

  constructor() {
    const user = localStorage.getItem('loginResponse');
    if (user) {
      this.loginResponseData = JSON.parse(user);
    }
  }

  login(loginFormData: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, loginFormData);
  }

  register(registerFormData:any):Observable<RegisterResponse>{
    return this.http.post<RegisterResponse>(`${this.apiUrl}/auth/register`,registerFormData);
  }

  saveUser(user: LoginResponse) {
    this.loginResponseData = user;
    localStorage.setItem('loginResponse', JSON.stringify(user));
  }

  getUser():LoginResponse | null{
    return this.loginResponseData;
  }

  getToken(): string | null {
    return this.loginResponseData?.token ?? null;
  }

  getUserRole(): string | null {
    return this.loginResponseData?.role ?? null;
  }

  getUserId(): number | null {
    return this.loginResponseData?.id ?? null;
  }

  getCollegeId(): number | null {
    return this.loginResponseData?.collegeId ?? null;
  }

  logout(){
    this.loginResponseData =null;
    localStorage.removeItem('loginResponse');
    this.router.navigate(['/login']);
  }
}
