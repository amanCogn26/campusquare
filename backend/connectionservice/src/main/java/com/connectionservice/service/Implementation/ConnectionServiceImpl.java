package com.connectionservice.service.Implementation;

import com.connectionservice.dto.ConnectionDto;
import com.connectionservice.dto.ConnectionRequestCreatedEvent;
import com.connectionservice.dto.LoginResponse;
import com.connectionservice.entity.Connection;
import com.connectionservice.enums.ConnectionStatus;
import com.connectionservice.exception.DuplicateResourceException;
import com.connectionservice.exception.ResourceNotFoundException;
import com.connectionservice.feignClient.UserClient;
import com.connectionservice.repository.ConnectionRepository;
import com.connectionservice.service.ConnectionEventProducer;
import com.connectionservice.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserClient userClient;
    private final ConnectionEventProducer connectionEventProducer;

    @Override
    public List<ConnectionDto> getAllAcceptedConnection(Long userId,Long receiverId){

        List<Connection> connections = connectionRepository.findAllBySenderIdOrReceiverId(userId,receiverId);

        return connections.stream()
                .filter(connection -> connection.getStatus().equals(ConnectionStatus.ACCEPTED))
                .map(connection -> {

                    Long connectedUserId = connection.getSenderId().equals(userId)
                            ? connection.getReceiverId() :connection.getSenderId();

                    LoginResponse user = userClient.findUserById(connectedUserId);

                            return ConnectionDto.builder()
                                    .connectionId(connection.getId())
                                    .userId(connectedUserId)
                                    .name(user.getName())
                                    .profileImage(user.getProfileImage())
                                    .username(user.getUsername())
                                    .status(connection.getStatus())
                                    .connectedAt(connection.getConnectedAt())
                                    .build();
                        }
                )
                .toList();
    }

    @Override
    public List<ConnectionDto> getAllConnectionByUserId(Long userId){

        List<Connection> connections = connectionRepository.findAllBySenderIdOrReceiverId(userId,userId);

        return connections.stream()
                .map(connection -> {

                    Long id = connection.getReceiverId().equals(userId) ?
                            connection.getSenderId() : connection.getReceiverId();

                    boolean userIdIsReceiverId = connection.getReceiverId().equals(userId);

                    LoginResponse user = userClient.findUserById(id);

                            return ConnectionDto.builder()
                                    .connectionId(connection.getId())
                                    .userId(user.getId())
                                    .name(user.getName())
                                    .profileImage(user.getProfileImage())
                                    .username(user.getUsername())
                                    .status(connection.getStatus())
                                    .requestReceived(userIdIsReceiverId)
                                    .connectedAt(connection.getConnectedAt())
                                    .build();
                        }
                )
                .toList();
    }

    @Override
    public ConnectionDto sendConnectionRequest(Long senderId,Long receiverId){

        if(senderId.equals(receiverId) ){
            throw new IllegalArgumentException(
                    "Cannot connect with yourself"
            );
        }

        Connection connection = Connection.builder()
                    .senderId(senderId)
                    .receiverId(receiverId)
                    .status(ConnectionStatus.PENDING)
                    .build();

        Optional<Connection> existingConnection =
                connectionRepository.findConnectionBetweenUsers(
                        senderId,
                        receiverId
                );

        if (existingConnection.isPresent()) {

            Connection connection1 = existingConnection.get();

            if (connection1.getStatus() == ConnectionStatus.PENDING) {
                throw new DuplicateResourceException(
                        "Connection request already pending"
                );
            }

            if (connection1.getStatus() == ConnectionStatus.ACCEPTED) {
                throw new DuplicateResourceException(
                        "Users are already connected"
                );
            }

        }

        connection = connectionRepository.save(connection);

        LoginResponse receiverUser = userClient.findUserById(receiverId);

        LoginResponse senderUser = userClient.findUserById(senderId);

        ConnectionRequestCreatedEvent event = ConnectionRequestCreatedEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .senderName(senderUser.getName())
                .build();

        connectionEventProducer.publish("connection-request-created",event);

        return mapToDto(connection,receiverUser,null);
    }

    @Override
    public ConnectionDto acceptConnectionRequest(Long connectionId){

        Connection connection = connectionRepository.findById(connectionId).orElseThrow(
                ()->new ResourceNotFoundException("Connection Request does not found")
        );

        connection.setStatus(ConnectionStatus.ACCEPTED);
        connection.setConnectedAt(new Date());

        connection = connectionRepository.save(connection);

        LoginResponse userSender = userClient.findUserById(connection.getSenderId());
        LoginResponse userReceiver = userClient.findUserById(connection.getReceiverId());

        ConnectionRequestCreatedEvent event =ConnectionRequestCreatedEvent.builder()
                .receiverId(connection.getSenderId())
                .senderId(connection.getReceiverId())
                .senderName(userReceiver.getName())
                .build();

        connectionEventProducer.publish("connection-request-accept",event);

        return ConnectionDto.builder()
                .connectionId(connection.getId())
                .userId(connection.getSenderId()) // <-- sender
                .name(userSender.getName())
                .profileImage(userSender.getProfileImage())
                .username(userSender.getUsername())
                .status(connection.getStatus())
                .build();
    }

    @Override
    public ConnectionDto rejectConnectionRequest(Long connectionId){

        Connection connection = connectionRepository.findById(connectionId).orElseThrow(
                ()->new ResourceNotFoundException("Connection Request does not found")
        );

        connection.setStatus(ConnectionStatus.REJECTED);

        connection = connectionRepository.save(connection);

        connectionRepository.deleteById(connectionId);

        LoginResponse user = userClient.findUserById(connection.getSenderId());

        return ConnectionDto.builder()
                .connectionId(connection.getId())
                .userId(connection.getSenderId()) // <-- sender
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .username(user.getUsername())
                .status(connection.getStatus())
                .build();
    }

    @Override
    public void removeConnection(Long connectionId){

        connectionRepository.findById(connectionId).orElseThrow(
                ()->new ResourceNotFoundException("Connection Request does not found")
        );

        connectionRepository.deleteById(connectionId);
    }

    @Override
    public boolean areConnected(Long senderId,Long receiverId){
        return connectionRepository.areConnected(senderId,receiverId);
    }

    public ConnectionDto mapToDto(Connection connection, LoginResponse user,
                                  Date connectedAt){

        return ConnectionDto.builder()
                .connectionId(connection.getId())
                .userId(connection.getReceiverId())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .username(user.getUsername())
                .status(connection.getStatus())
                .connectedAt(connectedAt)
                .build();
    }



}
