package vuluu.userservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.dto.request.CreateAddressRequestDTO;
import vuluu.userservice.dto.response.ListAddressResponseDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
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
  @CacheEvict(value = "userAddressCache", key = "'userAddressCache:' + #userId")
  public void saveListAddress(String[] listAddress) {
    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    var addresses = Arrays.stream(listAddress)
        .map(a -> Address.builder().user(user).addressDescription(a).build()).toList();

    addressRepository.saveAll(addresses);
  }

  @Transactional
  @CacheEvict(value = "userAddressCache", key = "'userAddressCache:' + #userId")
  public MessageResponseDTO saveAddress(CreateAddressRequestDTO requestDTO) {
    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    addressRepository.save(
        Address.builder().user(user).addressDescription(requestDTO.getAddress()).build());
    return MessageResponseDTO.builder().message("Address created successfully.").build();
  }

  @Cacheable(value = "userAddressCache", key = "'userAddressCache:' + #userId", unless = "#result == null")
  public List<ListAddressResponseDTO> getUserAddress() {
    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    var addressList = addressRepository.findAllByUser(user);
    return addressList.stream().map(address ->
        ListAddressResponseDTO
            .builder()
            .addressId(address.getId())
            .addressDescription(address.getAddressDescription())
            .build()
    ).collect(Collectors.toList());
  }
}
