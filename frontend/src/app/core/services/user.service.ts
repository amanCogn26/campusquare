import { inject, Injectable, OnInit } from '@angular/core';
import { environment } from '../../../environment/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../models/user/user-dto';


@Injectable({
  providedIn: 'root',
})
export class UserService{

  private readonly apiUrl = `${environment.apiUrl}`;
  private http=inject(HttpClient);

  getAllUsersByCollegeId():Observable<UserDto[]>{
    console.log("called");
    return this.http.get<UserDto[]>(`${this.apiUrl}/user/collegeid`,{});
  }

  getAllUser():Observable<UserDto[]>{
    return this.http.get<UserDto[]>(`${this.apiUrl}/user/alluser`,{});
  }

  deleteUser(userId:number):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/user/delete/${userId}`,{})
  }

}
