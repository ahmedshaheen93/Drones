package com.shaheen.drones.errorhandling;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ClientException{
  public BadRequestException(String message) {
    super(message);
  }

  @Override
  protected HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
