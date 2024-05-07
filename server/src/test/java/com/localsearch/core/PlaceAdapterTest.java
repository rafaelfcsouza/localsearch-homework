package com.localsearch.core;

import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.*;

import com.localsearch.client.ExternalApiClient;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlaceAdapterTest {
  public static Stream<Arguments> provideData() {
    return Stream.of(
        Arguments.of(
            new ExternalApiClient.PlacesApiResponse(
                "Casa Ferlin",
                "Stampfenbachstrasse 38, 8006 Zürich",
                new ExternalApiClient.OpeningHours(
                    Map.of(
                        MONDAY.name().toLowerCase(),
                        List.of(
                            new ExternalApiClient.TimeSlot("11:30", "14:00"),
                            new ExternalApiClient.TimeSlot("18:30", "22:00")),
                        TUESDAY.name().toLowerCase(),
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
                    false)),
            new Place(
                "Casa Ferlin",
                "Stampfenbachstrasse 38, 8006 Zürich",
                List.of(
                    new Place.OpeningHours(
                        Set.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY),
                        List.of(
                            new Place.TimeSlot("11:30", "14:00"),
                            new Place.TimeSlot("18:30", "22:00")))))),
        Arguments.of(
            new ExternalApiClient.PlacesApiResponse(
                "Le Café du Marché",
                "Rue de Conthey 17, 1950 Sion",
                new ExternalApiClient.OpeningHours(
                    Map.of(
                        TUESDAY.name().toLowerCase(),
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
                    null)),
            new Place(
                "Le Café du Marché",
                "Rue de Conthey 17, 1950 Sion",
                List.of(
                    new Place.OpeningHours(
                        new TreeSet<>(Set.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)),
                        List.of(
                            new Place.TimeSlot("11:30", "15:00"),
                            new Place.TimeSlot("18:30", "00:00"))),
                    new Place.OpeningHours(
                        Set.of(SATURDAY), List.of(new Place.TimeSlot("18:00", "00:00"))),
                    new Place.OpeningHours(
                        Set.of(SUNDAY), List.of(new Place.TimeSlot("11:30", "15:00")))))));
  }

  @ParameterizedTest
  @MethodSource("provideData")
  public void fromApi(ExternalApiClient.PlacesApiResponse response, Place expected) {
    // When
    Place place = PlaceAdapter.fromApi(response);
    // Then
    assertEquals(expected, place);
  }
}
