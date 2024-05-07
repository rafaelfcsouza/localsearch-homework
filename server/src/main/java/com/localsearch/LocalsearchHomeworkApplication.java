package com.localsearch;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class LocalsearchHomeworkApplication {

  public static void main(String[] args) {
    SpringApplication.run(LocalsearchHomeworkApplication.class, args);
  }

  @Bean
  public WebClient placesApiWebClient(@Value("${places.api.url}") String baseUrl) {
    return WebClient.builder().baseUrl(baseUrl).build();
  }

  @Bean
  RouterFunction<ServerResponse> getEmployeeByIdRoute(Handlers handlers) {
    return route(GET("/{id}"), handlers::handleRequest);
  }
}
