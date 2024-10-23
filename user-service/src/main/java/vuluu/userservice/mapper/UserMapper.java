package vuluu.userservice.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.response.RoleResponseDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.entity.Role;
import vuluu.userservice.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    builder = @Builder(disableBuilder = true))
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "verified", ignore = true)
  @Mapping(target = "description", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "verifyCode", ignore = true)
  @Mapping(target = "addresses", ignore = true)
  @Mapping(target = "verificationSentDate", ignore = true)
  User toUser(CreateAccountRequestDTO requestDTO);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "roles", source = "roles")
  UserResponseDTO toUserResponseDTO(User user);

  @Mapping(target = "permissionResponseDTOS", ignore = true)
  RoleResponseDTO toRoleResponseDTO(Role role);
}

