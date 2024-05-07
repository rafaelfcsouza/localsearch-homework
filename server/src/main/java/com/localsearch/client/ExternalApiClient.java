package com.localsearch.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExternalApiClient {

  private final WebClient placesApiWebClient;

  public Mono<PlacesApiResponse> get(String id) {
    return placesApiWebClient
        .get()
        .uri("/{id}", id)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .retrieve()
        .bodyToMono(PlacesApiResponse.class);
  }

  public record PlacesApiResponse(
      @JsonProperty("displayed_what") String name,
      @JsonProperty("displayed_where") String address,
      @JsonProperty("opening_hours") OpeningHours openingHours) {}

  public record OpeningHours(
      Map<String, List<TimeSlot>> days,
      @JsonProperty("closed_on_holidays") Boolean closedOnHolidays,
      @JsonProperty("open_by_arrangement") Boolean openByArrangement) {}

  public record TimeSlot(String start, String end) {}
}
