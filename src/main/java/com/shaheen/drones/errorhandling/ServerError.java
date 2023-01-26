package com.shaheen.drones.errorhandling;

public abstract class ServerError extends BaseException {
  protected ServerError(String message) {
    super(message);
  }
}
