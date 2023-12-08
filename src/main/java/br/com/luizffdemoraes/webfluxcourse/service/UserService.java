package br.com.luizffdemoraes.webfluxcourse.service;


import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.mapper.UserMapper;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import br.com.luizffdemoraes.webfluxcourse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository repository;
    private UserMapper mapper;

    public Mono<User> save(final UserRequest request) {
        return repository.save(mapper.toEntity(request));
    }
}
