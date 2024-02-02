package com.pingpong.global.error;

import com.pingpong.domain.room.exception.InvalidRoomException;
import com.pingpong.domain.user.exception.InvalidUserException;
import com.pingpong.domain.userroom.exception.*;
import com.pingpong.global.payload.ApiResponse;
import com.pingpong.global.payload.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    //api error 201
    @ExceptionHandler(InvalidApiRequestException.class)
    public ResponseEntity<?> handleInvalidApiRequestException(InvalidApiRequestException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleInvalidParameterException(InvalidParameterException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(InvalidUserException.class)
    protected ResponseEntity<?> handleInvalidUserException(
            InvalidUserException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(FullRoomException.class)
    protected ResponseEntity<?> handleFullRoomException(
            FullRoomException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(MultipleRoomJoinException.class)
    protected ResponseEntity<?> handleMultipleRoomJoinException(
            MultipleRoomJoinException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(InvalidRoomException.class)
    protected ResponseEntity<?> handleInvalidRoomException(
            InvalidRoomException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(InvalidUserRoomException.class)
    protected ResponseEntity<?> handleInvalidUserRoomException(
            InvalidUserRoomException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(UnfilledRoomException.class)
    protected ResponseEntity<?> handleUnfilledRoomException(
            UnfilledRoomException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(FullOppositeTeamException.class)
    protected ResponseEntity<?> handleFullOppositeTeamException(
            FullOppositeTeamException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.INVALID);
        return ResponseEntity.ok(apiResponse);
    }



    //server error 500
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.SERVER_ERROR);
        return ResponseEntity.ok(apiResponse);
    }


    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.SERVER_ERROR);
        return ResponseEntity.internalServerError().body(apiResponse);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.SERVER_ERROR);
        return ResponseEntity.internalServerError().body(apiResponse);
    }


}
