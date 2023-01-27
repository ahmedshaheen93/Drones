package com.shaheen.drones.errorhandling;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ClientException{
  public NotFoundException(String message) {
    super(message);
  }

  @Override
  protected HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
