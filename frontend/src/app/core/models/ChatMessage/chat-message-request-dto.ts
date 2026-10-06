import { MessageType } from "./chat-message-response-dto";

export interface ChatMessageRequestDto {
  receiverId: number;
  content: string;
  messageType: MessageType;
}