package com.connectionservice.service;

import com.connectionservice.dto.ConnectionDto;

import java.util.List;

public interface ConnectionService {

    List<ConnectionDto>getAllAcceptedConnection(Long userId,Long receiverId);

    List<ConnectionDto>getAllConnectionByUserId(Long userId);

    ConnectionDto sendConnectionRequest(Long senderId, Long receiverId);

    ConnectionDto acceptConnectionRequest(Long connectionId);

    ConnectionDto rejectConnectionRequest(Long connectionId);

    void removeConnection(Long connectionId);

    boolean areConnected(Long senderId,Long receiverId);

}