package com.shaheen.drones.errorhandling;

public abstract class ClientException extends BaseException {
  protected ClientException(String message) {
    super(message);
  }
}
