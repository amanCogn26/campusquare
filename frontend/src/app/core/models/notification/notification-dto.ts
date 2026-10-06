export interface NotificationDto {
  id: number;
  actorId: number;
  actorName: string;
  actorProfileImage: string | null;
  receiverId: number;
  message: string;
  type: string;
  isRead: boolean;
  createdAt: string;
}