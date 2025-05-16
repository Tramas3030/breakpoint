package br.com.Tramas3030.breakpoint.modules.user.mapper;

import br.com.Tramas3030.breakpoint.modules.user.dto.CreateUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.dto.ResponseCreateUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.security.PasswordEncoderMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {PasswordEncoderMapper.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "vices", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    UserEntity toEntity(CreateUserDTO createUserDTO);

    ResponseCreateUserDTO toResponseDTO(UserEntity userEntity);

}
