export interface ConnectionDto {
  connectionId: number,
  userId: number,
  name: string,
  username: string,
  profileImage: string,
  status: string,
  connectedAt: Date,
  requestReceived:boolean,
}

export enum ConnectionStatus {
  ACCEPTED = 'ACCEPTED',
  PENDING = 'PENDING',
  REJECTED = 'REJECTED'
}