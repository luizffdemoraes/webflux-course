package br.com.luizffdemoraes.webfluxcourse.controller;

import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import br.com.luizffdemoraes.webfluxcourse.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "user-api")
public interface UserController {

    @PostMapping
    @Operation(summary = "User registration endpoint.", method = "POST")
    ResponseEntity<Mono<Void>> save(@Valid @RequestBody UserRequest request);

    @GetMapping(value = "/{id}")
    @Operation(summary = "User query endpoint.", method = "GET")
    ResponseEntity<Mono<UserResponse>> find(@Valid @PathVariable String id);

    @GetMapping
    @Operation(summary = "Query endpoint for all users.", method = "GET")
    ResponseEntity<Flux<UserResponse>> findAll();

    @PatchMapping(value = "/{id}")
    @Operation(summary = "User data update endpoint.", method = "PATCH")
    ResponseEntity<Mono<UserResponse>> update(@PathVariable String id, @RequestBody UserRequest request);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "User deletion endpoint.", method = "DELETE")
    ResponseEntity<Mono<Void>> delete(@PathVariable String id);
}
