package com.chatservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "connectionservice")
public interface ConnectionClient {

    @GetMapping("/connection/{senderId}/{receiverId}")
    boolean areConnected(@PathVariable Long senderId,@PathVariable Long receiverId);
}
