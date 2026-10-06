import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment.development';
import { CommentResponseDto } from '../models/interaction/comment-response-dto';
import { CommentRequestDto } from '../models/interaction/comment-request-dto';

@Injectable({
  providedIn: 'root',
})
export class InteractionService {

  private readonly apiUrl = environment.apiUrl;
  private http = inject(HttpClient);

  commentResponseDtoList!: CommentResponseDto[];
  commentResponseDto!: CommentResponseDto;
  commentRequestData!: CommentRequestDto;

  likeIncrement(postId: number | undefined): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/like/${postId}`, {});
  }

  getCommentsByPostId(postId: number): Observable<CommentResponseDto[]> {

    console.log("postId:", postId);
    return this.http.get<CommentResponseDto[]>(`${this.apiUrl}/comment/${postId}`, {});
  }

  addComment(comment: string, postId: number): Observable<CommentResponseDto> {

    const req: CommentRequestDto = {
      commentText: comment,
      postId,
    }
    return this.http.post<CommentResponseDto>(`${this.apiUrl}/comment`, req);
  }

  deleteComment(commentId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/comment/${commentId}`,{});
  }
}
