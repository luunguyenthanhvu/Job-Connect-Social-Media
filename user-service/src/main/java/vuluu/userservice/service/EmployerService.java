package vuluu.userservice.service;

import java.util.HashSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.dto.request.CreateAccountEmployerRequestDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.entity.Role;
import vuluu.userservice.enums.ERole;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.mapper.EmployerMapper;
import vuluu.userservice.repository.ApplicantRepository;
import vuluu.userservice.repository.EmployerRepository;
import vuluu.userservice.repository.RoleRepository;
import vuluu.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerService {

  EmployerRepository employerRepository;
  AddressService addressService;
  EmployerMapper employerMapper;
  UserRepository userRepository;
  ApplicantRepository applicantRepository;
  RoleRepository roleRepository;

  @Transactional
  public MessageResponseDTO createEmployerAccount(CreateAccountEmployerRequestDTO requestDTO) {
    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // If user not present throw error user not exist
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    // If user type exists throw error user existed
    if (employerRepository.existsById(userId) || applicantRepository.existsById(userId)) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    // map from dto to entity
    var employer = employerMapper.toEmployer(requestDTO, addressService);
    employer.setId(userId);

    // update role for Employer
    HashSet<Role> roles = new HashSet<>();
    roleRepository.findByRoleId(ERole.EMPLOYER).ifPresent(roles::add);
    user.setRoles(roles);

    // save updated role
    userRepository.save(user);

    // save new employer
    employerRepository.save(employer);

    return MessageResponseDTO.builder().message("Employer create successfully").build();
  }
}
