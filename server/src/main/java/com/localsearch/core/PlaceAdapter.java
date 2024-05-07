package com.localsearch.core;

import com.localsearch.client.ExternalApiClient;
import java.time.DayOfWeek;
import java.util.*;

public class PlaceAdapter {

  public static Place fromApi(ExternalApiClient.PlacesApiResponse placesApiResponse) {
    Map<String, List<ExternalApiClient.TimeSlot>> apiDays = placesApiResponse.openingHours().days();

    // Group days by timeslots
    Map<List<Place.TimeSlot>, Set<DayOfWeek>> groupedDays = new HashMap<>();
    for (Map.Entry<String, List<ExternalApiClient.TimeSlot>> entry : apiDays.entrySet()) {
      DayOfWeek dayOfWeek = DayOfWeek.valueOf(entry.getKey().toUpperCase());
      List<ExternalApiClient.TimeSlot> apiTimeSlots = entry.getValue();
      List<Place.TimeSlot> timeslots =
          apiTimeSlots.stream()
              .map(timeslot -> new Place.TimeSlot(timeslot.start(), timeslot.end()))
              .toList();
      groupedDays.computeIfAbsent(timeslots, k -> new TreeSet<>()).add(dayOfWeek);
    }

    List<Place.OpeningHours> openingHours =
        groupedDays.entrySet().stream()
            .map((entry) -> new Place.OpeningHours(entry.getValue(), entry.getKey()))
            .sorted()
            .toList();

    return new Place(placesApiResponse.name(), placesApiResponse.address(), openingHours);
  }
}
