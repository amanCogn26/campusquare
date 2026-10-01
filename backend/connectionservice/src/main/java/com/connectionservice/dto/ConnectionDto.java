package com.connectionservice.dto;

import com.connectionservice.enums.ConnectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDto {

    private Long connectionId;

    private Long userId;
    private String name;
    private String username;
    private String profileImage;

    private ConnectionStatus status;
    private Date connectedAt;

    private Boolean requestReceived;
}
