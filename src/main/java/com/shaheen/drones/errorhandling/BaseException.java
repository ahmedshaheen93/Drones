package com.shaheen.drones.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
@Slf4j
public abstract class BaseException extends RuntimeException {

  protected BaseException(String message) {
    super(message);
    log.error(message);
  }


  protected abstract HttpStatus getHttpStatus();
}
