import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { InteractionService } from '../../../core/services/interaction.service';
import { RouterLink } from "@angular/router";
import { FormControl, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { CommentResponseDto } from '../../../core/models/interaction/comment-response-dto';
import { Posts } from '../../../core/models/post/posts';
import { PostService } from '../../../core/services/post.service';
import { AuthService } from '../../../core/services/auth.service';
import { InteractionwebsocketService } from '../../../core/services/websocket/interactionwebsocket.service';

@Component({
  selector: 'app-post',
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './post.html',
  styleUrl: './post.css',
})
export class Post implements OnChanges, OnInit {

  isPostView: boolean = false;
  commentResponseData: CommentResponseDto[] = [];
  commentFormData!: FormGroup

  private interactionService = inject(InteractionService)
  private authService = inject(AuthService)
  private postService = inject(PostService)
  private cdr = inject(ChangeDetectorRef)
  private  interactionwebsocket= inject(InteractionwebsocketService);
  authUserId=this.authService.getUserId();

  @Input() post!: Posts;

  ngOnChanges(changes: SimpleChanges): void {
  }

  ngOnInit(): void {
    
    this.commentFormData = new FormGroup({
      comment: new FormControl(),
    })

    this.interactionwebsocket.commentSub$.subscribe(event => {

      if (!this.post) {
        return;
      }

      if (event.action === "COMMENT_CREATED" && this.post.postId == event.commentResponseDto.postId) {
        this.commentResponseData.unshift(event.commentResponseDto);
        this.post.commentCount = event.commentCount;
        this.cdr.detectChanges();
      }

      if (event.action === "COMMENT_DELETED") {

        this.commentResponseData =this.commentResponseData.filter(
          comment =>
            comment.commentId !==
            event.commentResponseDto.commentId
        );
        this.post.commentCount = event.commentCount;
        this.cdr.detectChanges();
      }
    })
  }

  getAuthUserId(){
    return this.authService.getUserId;
  }

  like(postId: number | undefined): void {

    if (!postId) return;
    this.interactionService.likeIncrement(postId).subscribe({
      error: (err) => {
        console.log(err);
      }
    })
  }

  viewPost(post: Posts) {
    this.isPostView = !this.isPostView;

    if (!this.isPostView) {
      return;
    }

    this.interactionService.getCommentsByPostId(post.postId).subscribe({
      next: (response) => {
        this.commentResponseData = response.sort(
          (a, b) =>
            new Date(b.createdAt).getTime() -
            new Date(a.createdAt).getTime()
        );
        console.log(this.commentResponseData);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  formSubmit(postId: number): void {
    this.interactionService.addComment(this.commentFormData.value.comment, postId).subscribe({
      next: (response) => {
        this.commentFormData.reset();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  deleteComment(commentId: number) {
    this.interactionService.deleteComment(commentId).subscribe({
      next: (response) => {
        console.log(response)
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  deletePost(postId:number){
    this.postService.deletePost(postId).subscribe({
      next: (response) => {
        console.log(response)
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}