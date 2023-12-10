package br.com.luizffdemoraes.webfluxcourse.factory;

import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import reactor.core.publisher.Mono;

public class Factory {

    public static User getBuildUser() {
        return User.builder().build();
    }

    public static Mono<User> getBuildMonoUser() {
        return Mono.just(User.builder().id("123").build());
    }

    public static UserRequest getBuildUserRequest() {
        return UserRequest.builder().name("Luiz").email("luiz@gmail.com").password("123").build();
    }
}
