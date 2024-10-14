package vuluu.userservice.service.impl;

import java.util.HashSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.entity.Role;
import vuluu.userservice.entity.User;
import vuluu.userservice.enums.ERole;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.mapper.UserMapper;
import vuluu.userservice.repository.RoleRepository;
import vuluu.userservice.repository.UserRepository;
import vuluu.userservice.service.templates.IUserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {

  UserRepository userRepository;
  RoleRepository roleRepository;
  PasswordEncoder passwordEncoder;
  UserMapper userMapper;

  @Transactional
  public UserResponseDTO createUser(CreateAccountRequestDTO requestDTO) {
    if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    User user = userMapper.toUser(requestDTO);
    user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

    HashSet<Role> roles = new HashSet<>();
    roleRepository.findByRoleId(ERole.USER).ifPresent(roles::add);

    user.setRoles(roles);
    user = userRepository.save(user);

    return userMapper.toUserResponseDTO(user);
  }

}
