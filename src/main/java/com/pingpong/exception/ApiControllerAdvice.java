package com.pingpong.exception;

import com.pingpong.exception.situation.*;
import com.pingpong.global.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
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
    public ApiResponse<?> handleInvalidApiRequestException(InvalidApiRequestException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ApiResponse<?> handleInvalidParameterException(InvalidParameterException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(InvalidUserException.class)
    protected ApiResponse<?> handleInvalidUserException(
            InvalidUserException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(FullRoomException.class)
    protected ApiResponse<?> handleFullRoomException(
            FullRoomException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(MultipleRoomJoinException.class)
    protected ApiResponse<?> handleMultipleRoomJoinException(
            MultipleRoomJoinException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(InvalidRoomException.class)
    protected ApiResponse<?> handleInvalidRoomException(
            InvalidRoomException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(InvalidUserRoomException.class)
    protected ApiResponse<?> handleInvalidUserRoomException(
            InvalidUserRoomException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(UnfilledRoomException.class)
    protected ApiResponse<?> handleUnfilledRoomException(
            UnfilledRoomException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(FullOppositeTeamException.class)
    protected ApiResponse<?> handleFullOppositeTeamException(
            FullOppositeTeamException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    @ExceptionHandler(InvalidPageException.class)
    protected ApiResponse<?> handleInvalidPageException(
            InvalidPageException e) {
        log.error(e.getMessage());
        return ApiResponse.invalid();
    }


    //server error 500
    @ExceptionHandler(Exception.class)
    protected ApiResponse<?> handleException(Exception e) {
        log.error(e.getMessage());
        return ApiResponse.error();
    }


    @ExceptionHandler(NullPointerException.class)
    protected ApiResponse<?> handleNullPointerException(NullPointerException e) {
        log.error(e.getMessage());
        return ApiResponse.error();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ApiResponse.error();
    }


}
