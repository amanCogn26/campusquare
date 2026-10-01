package com.userservice.exception;

import com.userservice.dto.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
          ResourceNotFoundException ex) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(
                    LocalDateTime.now(),
                    404,
                    ex.getMessage()));
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
          DuplicateResourceException ex) {

    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(
                    LocalDateTime.now(),
                    409,
                    ex.getMessage()));
  }

//  @ExceptionHandler(BadRequestException.class)
//  public ResponseEntity<ErrorResponse> handleBadRequest(
//          BadRequestException ex) {
//
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//            .body(new ErrorResponse(
//                    LocalDateTime.now(),
//                    400,
//                    ex.getMessage()));
//  }
//
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorized(
          UnauthorizedException ex) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(
                    LocalDateTime.now(),
                    401,
                    ex.getMessage()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDenied(
          AccessDeniedException ex) {

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Access Denied");
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(
          Exception ex) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(
                    LocalDateTime.now(),
                    500,
                    "Internal Server Error"));
  }

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ErrorResponse> handleFeignNotFound(FeignException ex) {
    return ResponseEntity.status(ex.status())
            .body(new ErrorResponse(
                    LocalDateTime.now(),
                    404,
                    "College service returned 404"));
  }
}
