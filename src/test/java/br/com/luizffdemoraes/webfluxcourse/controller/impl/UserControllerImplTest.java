package br.com.luizffdemoraes.webfluxcourse.controller.impl;

import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.mapper.UserMapper;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import br.com.luizffdemoraes.webfluxcourse.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static br.com.luizffdemoraes.webfluxcourse.factory.Factory.*;
import static br.com.luizffdemoraes.webfluxcourse.factory.Factory.getBuildUserRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static reactor.core.publisher.Mono.just;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    private static final String BASE_URI = "/users";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private MongoClient mongoClient;

    @Test
    @DisplayName("Test endpoint save with success")
    void testSaveWithSuccess() {

        when(service.save(any(UserRequest.class))).thenReturn(just(getBuildUser()));

        webTestClient.post().uri(BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(getBuildUserRequest()))
                .exchange()
                .expectStatus().isCreated();

        verify(service).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save with bad request")
    void testSaveWithBadRequest() {
        String error = "Validation error";
        String message = "Error on validation attributes";
        String fieldName = "name";
        String errorMessage = "field cannot have blank spaces at the beginning or at end";

        webTestClient.post().uri(BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(getBuildUserRequestInvalid()))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo(BASE_URI)
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo(error)
                .jsonPath("$.message").isEqualTo(message)
                .jsonPath("$.errors[0].fieldName").isEqualTo(fieldName)
                .jsonPath("$.errors[0].message").isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test find by id endpoint with success")
    void testFindByIdWithSuccess() {

        when(service.findById(anyString())).thenReturn(getBuildMonoUser());
        when(mapper.toResponse(any(User.class))).thenReturn(getBuildUserResponse());

        webTestClient.get().uri(BASE_URI + "/" + getIDTest())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(getIDTest())
                .jsonPath("$.name").isEqualTo(getNAMETest())
                .jsonPath("$.email").isEqualTo(getEMAILTest())
                .jsonPath("$.password").isEqualTo(getPASSWORDTest());

        verify(service).findById(anyString());
        verify(mapper).toResponse(any(User.class));

    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}