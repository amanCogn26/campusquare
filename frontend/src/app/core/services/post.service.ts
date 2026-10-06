import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment.development';
import { PostRequest } from '../models/post/post-request';
import { Posts } from '../models/post/posts';

@Injectable({
  providedIn: 'root',
})
export class PostService {

  constructor(private http: HttpClient, private router: Router) { }

  private readonly apiUrl = environment.apiUrl;

  getFeed(): Observable<Posts[]> {
    return this.http.get<Posts[]>(`${this.apiUrl}/post/feed`);
  }

  createPost(postRequest: PostRequest): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/post`, postRequest);
  }

  getAllPostByUserId(): Observable<Posts[]> {
    return this.http.get<Posts[]>(`${this.apiUrl}/post/user`, {});
  }
  
  getAllPost(): Observable<Posts[]> {
    return this.http.get<Posts[]>(`${this.apiUrl}/post`, {});
  }

  deletePost(postId:number):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/post/${postId}`,{});
  }
}
