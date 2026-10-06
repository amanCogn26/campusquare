import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PostService } from '../../../core/services/post.service';

@Component({
  selector: 'app-create-post',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './create-post.html',
  styleUrl: './create-post.css',
})
export class CreatePost implements OnInit {

  private router = inject(Router)
  private postService = inject(PostService);

  addPost: boolean = false;
  postRequestData !: FormGroup;

  editPost() {
    this.addPost = !this.addPost;
  }

  ngOnInit(): void {
    this.postRequestData = new FormGroup({
      description: new FormControl(),
      visibility: new FormControl('PUBLIC'),
      postImageUrl: new FormControl()
    })
  }

  formSubmit(): void {
    this.postService.createPost(this.postRequestData.value).subscribe({
      next: (response) => {
        console.log(response);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
