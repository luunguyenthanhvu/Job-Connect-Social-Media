package vuluu.userservice.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.userservice.dto.request.CreateAddressRequestDTO;
import vuluu.userservice.dto.response.ApiResponse;
import vuluu.userservice.dto.response.ListAddressResponseDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.service.AddressService;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressController {

  AddressService addressService;

  /**
   * For employer we return a list address. For applicant we return one address
   *
   * @return List<ListAddressResponseDTO>
   */
  @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER')")
  @GetMapping("/get-address")
  public ApiResponse<List<ListAddressResponseDTO>> getUserAddress() {
    return ApiResponse.<List<ListAddressResponseDTO>>builder()
        .result(addressService.getUserAddress())
        .build();
  }

  /**
   * Create address for user(employer/ applicant)
   *
   * @param requestDTO
   * @return MessageResponseDTO
   */
  @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER')")
  @PostMapping("/add-address")
  public ApiResponse<MessageResponseDTO> createNewAddress(
      @RequestBody CreateAddressRequestDTO requestDTO) {
    return ApiResponse.<MessageResponseDTO>builder()
        .result(addressService.saveAddress(requestDTO))
        .build();
  }
}
