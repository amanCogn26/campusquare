import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Post } from "../../features/posts/post/post";
import { PostService } from '../../core/services/post.service';
import { CreatePost } from "../../features/posts/create-post/create-post";
// import { WebSocketService } from '../../core/services/websocket/web-socket-service';
import { AuthService } from '../../core/services/auth.service';
import { Posts } from '../../core/models/post/posts';
import { every } from 'rxjs';
import { InteractionwebsocketService } from '../../core/services/websocket/interactionwebsocket.service';

@Component({
  selector: 'app-right-sidebar',
  imports: [Post, CommonModule, CreatePost],
  standalone: true,
  templateUrl: './right-sidebar.html',
  styleUrl: './right-sidebar.css',
})
export class RightSidebar implements OnInit {

  private postService = inject(PostService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  private interactionwebsocket = inject(InteractionwebsocketService);

  posts: Posts[] = [];

  ngOnInit(): void {

    this.postService.getFeed().subscribe({
      next: (response) => {
        this.posts = response;
        this.cdr.detectChanges();
      }
    });

    this.interactionwebsocket.likeSub$
      .subscribe(event => {

        const post = this.posts.find(
          p => p.postId === event.postId
        );

        if (!post) {
          console.log('POST NOT FOUND');
          return;
        }

        post.likeCount = event.likeCount;

        if (event.userId === this.authService.getUserId()) {
          post.likeByCurrentUser = event.action === 'LIKE';
        }

        this.cdr.detectChanges();
      });
  }

}
