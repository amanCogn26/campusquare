import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { PostService } from '../../core/services/post.service';
import { Post } from "../posts/post/post";
import { LoginResponse } from '../../core/models/auth/login-response';
import { Posts } from '../../core/models/post/posts';
import { RouterLink } from "@angular/router";
import { InteractionwebsocketService } from '../../core/services/websocket/interactionwebsocket.service';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, Post, RouterLink],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {

  private postService = inject(PostService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);
  private interactionwebsocket=inject(InteractionwebsocketService);

  posts: Posts[] = [];
  user!: LoginResponse | null;

  ngOnInit(): void {
    
    this.user = this.authService.getUser();

    this.postService.getAllPostByUserId()
      .subscribe({
        next: (response) => {
          this.posts = response;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.log(err);
        }
      });

    this.interactionwebsocket.likeSub$.subscribe(
      event=>{
        const post = this.posts.find(
          p => p.postId === event.postId
        );

        if (!post) {
          return;
        }

        post.likeCount = event.likeCount;

        if (event.userId === this.authService.getUserId()) {
          post.likeByCurrentUser = event.action === 'LIKE';
        }

        this.cdr.detectChanges();
      }
    )
  }
}
