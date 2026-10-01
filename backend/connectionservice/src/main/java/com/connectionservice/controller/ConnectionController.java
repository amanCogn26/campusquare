package com.connectionservice.controller;

import com.connectionservice.dto.ConnectionDto;
import com.connectionservice.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/request/{receiverId}")
    public ResponseEntity<ConnectionDto>sendConnectionRequest(
            @RequestHeader("X-User-Id") Long senderId,
            @PathVariable Long receiverId
    ){

        log.info("send connection request controller is called");
        return ResponseEntity.ok(connectionService.sendConnectionRequest(senderId,receiverId));
    }

    @PutMapping("/accept/{connectionId}")
    public ResponseEntity<ConnectionDto>acceptConnectionRequest(
            @PathVariable Long connectionId
    ){
        log.info("accept connection request controller is called");
        return ResponseEntity.ok(connectionService.acceptConnectionRequest(connectionId));
    }

    @PutMapping("/reject/{connectionId}")
    public ResponseEntity<ConnectionDto>rejectConnectionRequest(
            @PathVariable Long connectionId
    ){
        log.info("reject connection request controller is called");
        return ResponseEntity.ok(connectionService.rejectConnectionRequest(connectionId));
    }

    @DeleteMapping("/{connectionId}")
    public ResponseEntity<Void>removeConnection(
            @PathVariable Long connectionId
    ){
        log.info("remove connection controller is called");
        connectionService.removeConnection(connectionId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ConnectionDto>> getAllAcceptedConnection(
            @RequestHeader("X-User-Id") Long userId
    ){
        log.info("get all acccepted connection controller is called");
        return ResponseEntity.ok(connectionService.getAllAcceptedConnection(userId,userId));
    }

    @GetMapping("network")
    public ResponseEntity<List<ConnectionDto>> getAllConnectionByUserId(
            @RequestHeader("X-User-Id") Long userId
    ){
        log.info("get all connection by current user id controller is called");
        return ResponseEntity.ok(connectionService.getAllConnectionByUserId(userId));
    }

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<Boolean>areConnected(@PathVariable Long senderId,
                                               @PathVariable Long receiverId){
        return ResponseEntity.ok(connectionService.areConnected(senderId,receiverId));
    }

}
