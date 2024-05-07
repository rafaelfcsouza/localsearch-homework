package com.localsearch;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.localsearch.client.ExternalApiClient;
import com.localsearch.core.Place;
import com.localsearch.core.PlaceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handlers {

  final ExternalApiClient externalApiClient;

  public Mono<ServerResponse> handleRequest(ServerRequest request) {
    return ok().contentType(MediaType.APPLICATION_JSON).body(externalApiClient
        .get(request.pathVariable("id"))
        .map(PlaceAdapter::fromApi), Place.class);
  }
}
