package vuluu.userservice.service;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.entity.Address;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.repository.AddressRepository;
import vuluu.userservice.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {

  AddressRepository addressRepository;
  UserRepository userRepository;

  @Transactional
  public void saveListAddress(String[] listAddress) {
    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    var addresses = Arrays.stream(listAddress)
        .map(a -> Address
            .builder()
            .user(user)
            .addressDescription(a)
            .build())
        .toList();

    addressRepository.saveAll(addresses);
  }
}
