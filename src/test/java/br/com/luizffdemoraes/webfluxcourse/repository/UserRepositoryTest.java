package br.com.luizffdemoraes.webfluxcourse.repository;

import br.com.luizffdemoraes.webfluxcourse.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static br.com.luizffdemoraes.webfluxcourse.factory.Factory.getBuildUser;
import static br.com.luizffdemoraes.webfluxcourse.factory.Factory.getIDTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    void testSave() {

        when(userRepository.save(any())).thenReturn(Mono.just(getBuildUser()));

        Mono<User> savedUser = userRepository.save(getBuildUser());

        StepVerifier.create(savedUser)
                .expectNextMatches(saved -> saved.getId().equals(getIDTest()))
                .verifyComplete();
    }


    @Test
    void testFindById() {

        when(userRepository.findById(getIDTest())).thenReturn(Mono.just(getBuildUser()));

        Mono<User> foundUser = userRepository.findById(getIDTest());

        StepVerifier.create(foundUser)
                .expectNextMatches(user -> user.getId().equals(getIDTest()))
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Flux.just(getBuildUser(), getBuildUser()));

        Flux<User> allUsers = userRepository.findAll();

        StepVerifier.create(allUsers)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindAndRemove() {

        when(userRepository.findAndRemove(getIDTest())).thenReturn(Mono.just(getBuildUser()));

        Mono<User> removedUser = userRepository.findAndRemove(getIDTest());

        StepVerifier.create(removedUser)
                .expectNextMatches(user -> user.getId().equals(getIDTest()))
                .verifyComplete();
    }
}