import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { PostService } from '../../core/services/post.service';
import { consumerPollProducersForChange } from '@angular/core/primitives/signals';
import { Post } from "../../features/posts/post/post";
import { Posts } from '../../core/models/post/posts';
import { CommonModule } from '@angular/common';
import { InvokeFunctionExpr } from '@angular/compiler';

@Component({
  selector: 'app-admin-posts',
  imports: [CommonModule, Post],
  templateUrl: './admin-posts.html',
  styleUrl: './admin-posts.css',
})
export class AdminPosts implements OnInit {

  private postService = inject(PostService);
  private cdr =inject(ChangeDetectorRef);

  posts: Posts[] = [];

  ngOnInit(): void {

    this.postService.getAllPost().subscribe({
      next: (response) => {
        this.posts = response;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
