export interface Posts {
  postId: number;
  postImageUrl: string | null;
  description: string | null;
  userId: number;
  collegeId: number;
  visibility: Visibility;
  createdAt: Date;
  updatedAt: Date;
  likeCount: number;
  commentCount: number;
  publicId: string | null;
  name: string;
  profileImageUrl: string;
  likeByCurrentUser: boolean;
}

export enum Visibility {
  PUBLIC,
  PRIVATE,
  COLLEGE_ONLY
}