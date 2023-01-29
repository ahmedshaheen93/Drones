package com.shaheen.drones.model;

public enum DroneModel {
  LIGHTWEIGHT("Lightweight"), MIDDLEWEIGHT("Middleweight"), CRUISERWEIGHT("Cruiserweight"), HEAVYWEIGHT("Heavyweight");
  private final String value;

  DroneModel(String value) {
    this.value = value;
  }

  public static DroneModel getByString(String value) {
    for (DroneModel status : DroneModel.values()) {
      if (status.value.equalsIgnoreCase(value)) {
        return status;
      }
    }
    return null;
  }
}
