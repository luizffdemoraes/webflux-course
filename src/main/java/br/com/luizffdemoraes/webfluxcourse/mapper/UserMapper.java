package br.com.luizffdemoraes.webfluxcourse.mapper;

import br.com.luizffdemoraes.webfluxcourse.entity.User;
import br.com.luizffdemoraes.webfluxcourse.model.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest request);
}
