export interface ChatMessageResponseDto {
  id: number;
  senderId: number;
  receiverId: number;
  content: string;
  messageType: MessageType;
  messageStatus: MessageStatus;
  sentAt: string; // ISO date string from backend
}

export enum MessageType {
  TEXT = 'TEXT',
  IMAGE = 'IMAGE',
  FILE = 'FILE'
}

export enum MessageStatus {
  SENT = 'SENT',
  DELIVERED = 'DELIVERED',
  READ = 'READ'
}