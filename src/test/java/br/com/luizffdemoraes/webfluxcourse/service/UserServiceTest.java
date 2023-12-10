package br.com.luizffdemoraes.webfluxcourse.service;

import br.com.luizffdemoraes.webfluxcourse.controller.exceptions.ObjectNotFoundException;
import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.mapper.UserMapper;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import br.com.luizffdemoraes.webfluxcourse.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static br.com.luizffdemoraes.webfluxcourse.factory.Factory.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Test
    void testSave() {
        Mockito.when(mapper.toEntity(ArgumentMatchers.any(UserRequest.class))).thenReturn(getBuildUser());
        Mockito.when(repository.save(ArgumentMatchers.any(User.class))).thenReturn(getBuildMonoUser());

        Mono<User> result = service.save(getBuildUserRequest());

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testFindById() {
        String id = "123";
        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(getBuildMonoUser());

        Mono<User> result = service.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class
                        && Objects.equals(user.getId(), id))
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).findById(ArgumentMatchers.anyString());
    }

    @Test
    void testFindAll() {
        Mockito.when(repository.findAll()).thenReturn(Flux.just(getBuildUser()));

        Flux<User> result = service.findAll();

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Mockito.when(mapper.toEntity(ArgumentMatchers.any(UserRequest.class), ArgumentMatchers.any(User.class))).thenReturn(getBuildUser());
        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(getBuildMonoUser());
        Mockito.when(repository.save(ArgumentMatchers.any(User.class))).thenReturn(getBuildMonoUser());

        Mono<User> result = service.updateUser("123", getBuildUserRequest());

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testDelete() {
        Mockito.when(repository.findAndRemove(ArgumentMatchers.anyString())).thenReturn(getBuildMonoUser());

        Mono<User> result = service.deleteUser("123");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).findAndRemove(ArgumentMatchers.anyString());
    }

    @Test
    void testHandleNotFound() {
        String message = "Object not found. Id: 123, Type: User";
        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        ObjectNotFoundException exception = Assertions.assertThrows(ObjectNotFoundException.class, () -> service.findById("123").block());

        Assertions.assertTrue(exception.getMessage().contains(message));
    }
}