package br.com.luizffdemoraes.webfluxcourse.service;

import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.mapper.UserMapper;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import br.com.luizffdemoraes.webfluxcourse.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

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
        Mockito.when(mapper.toEntity(ArgumentMatchers.any(UserRequest.class))).thenReturn(User.builder().build());
        Mockito.when(repository.save(ArgumentMatchers.any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = service.save(UserRequest.builder().name("Luiz").email("luiz@gmail.com").password("123").build());

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testFindById() {
        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(User.builder().id("1234").build()));

        Mono<User> result = service.findById("123");
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class
                        && Objects.equals(user.getId(), "1234"))
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).findById(ArgumentMatchers.anyString());
    }
}