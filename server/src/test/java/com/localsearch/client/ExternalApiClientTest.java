package com.localsearch.client;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
public class ExternalApiClientTest {

  @Autowired private ExternalApiClient client;

  public static Stream<Arguments> provideData() {
    return Stream.of(
        Arguments.of(
            "GXvPAor1ifNfpF0U5PTG0w",
            new ExternalApiClient.PlacesApiResponse(
                "Casa Ferlin",
                "Stampfenbachstrasse 38, 8006 Zürich",
                new ExternalApiClient.OpeningHours(
                    Map.of(
                        DayOfWeek.MONDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "14:00"),
                            new ExternalApiClient.TimeSlot("18:30", "22:00")),
                        DayOfWeek.TUESDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "14:00"),
                            new ExternalApiClient.TimeSlot("18:30", "22:00")),
                        DayOfWeek.WEDNESDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "14:00"),
                            new ExternalApiClient.TimeSlot("18:30", "22:00")),
                        DayOfWeek.THURSDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "14:00"),
                            new ExternalApiClient.TimeSlot("18:30", "22:00")),
                        DayOfWeek.FRIDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "14:00"),
                            new ExternalApiClient.TimeSlot("18:30", "22:00"))),
                    true,
                    false))),
        Arguments.of(
            "ohGSnJtMIC5nPfYRi_HTAg",
            new ExternalApiClient.PlacesApiResponse(
                "Le Café du Marché",
                "Rue de Conthey 17, 1950 Sion",
                new ExternalApiClient.OpeningHours(
                    Map.of(
                        DayOfWeek.TUESDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "15:00"),
                            new ExternalApiClient.TimeSlot("18:30", "00:00")),
                        DayOfWeek.WEDNESDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "15:00"),
                            new ExternalApiClient.TimeSlot("18:30", "00:00")),
                        DayOfWeek.THURSDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "15:00"),
                            new ExternalApiClient.TimeSlot("18:30", "00:00")),
                        DayOfWeek.FRIDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "15:00"),
                            new ExternalApiClient.TimeSlot("18:30", "00:00")),
                        DayOfWeek.SATURDAY.name().toLowerCase(),
                        List.of(new ExternalApiClient.TimeSlot("18:00", "00:00")),
                        DayOfWeek.SUNDAY.name().toLowerCase(),
                        List.of(new ExternalApiClient.TimeSlot("11:30", "15:00"))),
                    null,
                        null))));
  }

  @DisplayName("Given an ID, When service is called, Then a place is returned")
  @ParameterizedTest
  @MethodSource("provideData")
  void givenId_whenServiceIsCalled_thenPlaceIsReturned(String id, ExternalApiClient.PlacesApiResponse expected) {
    createStub(id);

    var response = client.get(id);

    StepVerifier.create(response).expectNext(expected).verifyComplete();
  }

  private void createStub(String id) {
    stubFor(
        get(urlEqualTo("/" + id))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBodyFile(id + ".json")));
  }

  @DisplayName("Given an invalid ID, When service is called, Then null is returned")
  @Test
  void givenInvalidId_whenServiceIsCalled_thenNullIsReturned() {
    String invalidId = "invalidId";
    stubFor(get(urlEqualTo("/" + invalidId)).willReturn(aResponse().withStatus(404)));

    var response = client.get(invalidId);

    StepVerifier.create(response).expectError().verify();
  }
}
