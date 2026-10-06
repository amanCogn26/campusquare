import { CommentResponseDto } from "../interaction/comment-response-dto";

export interface LikeInteractionEvent {
  postId: number;
  userId: number;
  action: string;
  likeCount: number;
}

export interface CommentInteractionEvent{
  commentResponseDto:CommentResponseDto,
  action:string,
  commentCount:number,
}