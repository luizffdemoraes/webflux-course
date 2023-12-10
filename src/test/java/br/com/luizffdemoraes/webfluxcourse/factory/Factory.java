package br.com.luizffdemoraes.webfluxcourse.factory;

import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import reactor.core.publisher.Mono;


public class Factory {

    private static final String ID = "123456";
    private static final String NAME = "Valdir";
    private static final String EMAIL = "valdir@mail.com";
    private static final String PASSWORD = "123";

    public static User getBuildUser() {
        return User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }


    public static Mono<User> getBuildMonoUser() {
        return Mono.just(getBuildUser());
    }

    public static UserRequest getBuildUserRequest() {
        return UserRequest.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public static UserRequest getBuildUserRequestInvalid() {
        return UserRequest.builder()
                .name(NAME.concat(" "))
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public static String getIDTest(){
        return ID;
    }

    public static String getNAMETest(){
        return NAME;
    }
    public static String getEMAILTest(){
        return EMAIL;
    }
    public static String getPASSWORDTest(){
        return PASSWORD;
    }
}
