package com.shaheen.drones.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaheen.drones.DronesApplication;
import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.api.model.DroneState;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = DronesApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DronesControllerIT {
  private static final String GET_DRONE_WITH_ID_ENDPOINT = "/drones/{id}";
  private static final String REGISTER_DRONE_ENDPOINT = "/drones";

  @Autowired
  private ObjectMapper objectMapper;
  @Value(value = "${local.server.port}")
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
  void registerDrone_happy() {
    ResponseEntity<DroneResponse> response = registerDrone("88888");
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isNotNull().isPositive();
  }


  @Test
  @Order(2)
  void getDroneInfoFullResponse() throws JsonProcessingException {
    ResponseEntity<DroneResponse> droneResponseEntity = registerDrone("123654");
    int id = droneResponseEntity.getBody().getId();
    String endpoint = "http://localhost:" + port + GET_DRONE_WITH_ID_ENDPOINT;
    ResponseEntity<DroneResponse> response = this.restTemplate.getForEntity(endpoint, DroneResponse.class, id);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).descriptionText();
    String responseBody = objectMapper.writeValueAsString(response.getBody());
    assertThat(responseBody).isEqualTo("{\"id\":%s,\"serialNumber\":\"123654\",\"model\":\"Heavyweight\",\"weightLimit\":500.0,\"batteryCapacity\":100,\"state\":\"IDLE\"}",
        response.getBody().getId());
  }

  @Test
  @Order(3)
  void getDroneInfoWithSpecificFields() throws JsonProcessingException {
    ResponseEntity<DroneResponse> droneResponseEntity = registerDrone("123658");
    int id = droneResponseEntity.getBody().getId();

    String endpoint = "http://localhost:" + port + GET_DRONE_WITH_ID_ENDPOINT + "?fields=batteryCapacity";
    ResponseEntity<DroneResponse> response = this.restTemplate.getForEntity(endpoint, DroneResponse.class, id);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).descriptionText();
    String responseBody = objectMapper.writeValueAsString(response.getBody());
    assertThat(responseBody).isEqualTo("{\"batteryCapacity\":100}");
  }

  private ResponseEntity<DroneResponse> registerDrone(String serialNumber) {
    DroneAddRequest droneAddRequest = new DroneAddRequest()
        .serialNumber(serialNumber)
        .state(DroneState.IDLE)
        .weightLimit(new BigDecimal(500))
        .model(DroneAddRequest.ModelEnum.HEAVYWEIGHT)
        .batteryCapacity(100);
    String endpoint = "http://localhost:" + port + REGISTER_DRONE_ENDPOINT;
    return restTemplate.postForEntity(endpoint, droneAddRequest, DroneResponse.class);
  }
}
