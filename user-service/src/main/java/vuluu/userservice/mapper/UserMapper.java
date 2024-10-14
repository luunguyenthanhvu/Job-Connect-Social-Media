package vuluu.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.response.RoleResponseDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.entity.Role;
import vuluu.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "verified", ignore = true)
  @Mapping(target = "description", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "img", ignore = true)
  @Mapping(target = "roles", ignore = true)
  User toUser(CreateAccountRequestDTO requestDTO);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "roles", source = "roles")
  UserResponseDTO toUserResponseDTO(User user);

  @Mapping(target = "permissionResponseDTOS", ignore = true)
    // Ignore if it doesn't exist in Role
  RoleResponseDTO toRoleResponseDTO(Role role);
}

