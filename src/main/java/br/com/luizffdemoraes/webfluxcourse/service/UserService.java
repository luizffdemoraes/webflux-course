package br.com.luizffdemoraes.webfluxcourse.service;


import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.mapper.UserMapper;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import br.com.luizffdemoraes.webfluxcourse.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Mono<User> save(final UserRequest request) {
        return repository.save(mapper.toEntity(request));
    }
}
