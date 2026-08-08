package com.controladorescritorio.deskop.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //Generic codes
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Entity not found"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),

    //Organization
   ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Organization not found"),

    //User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),

    //Device
    DEVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Device not found"),
    DEVICE_CODE_ALREADY_EXISTS(HttpStatus.CONFLICT, "Device code already exists"),

    //AccessToken
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Access token not found"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access token expired"),
    ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Access token invalid"),
    ACCESS_TOKEN_ALREADY_USED(HttpStatus.CONFLICT, "Access token already exists"),

    //RemoteSession
    SESSION_ALREADY_ACTIVE(HttpStatus.CONFLICT, "Remote session already exists"),

    //Authentication
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }


}

