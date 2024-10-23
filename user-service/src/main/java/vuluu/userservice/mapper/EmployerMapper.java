package vuluu.userservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import vuluu.userservice.dto.request.CreateAccountEmployerRequestDTO;
import vuluu.userservice.entity.Employer;
import vuluu.userservice.service.AddressService;

@Mapper(componentModel = ComponentModel.SPRING,
    builder = @Builder(disableBuilder = true))
public interface EmployerMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "verified", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "verifyCode", ignore = true)
  @Mapping(target = "addresses", ignore = true)
  @Mapping(target = "verificationSentDate", ignore = true)
  @Mapping(target = "username", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "phoneNumber", ignore = true)
  @Mapping(target = "description", source = "requestDTO.description")
  @Mapping(target = "website", source = "requestDTO.website")
  @Mapping(target = "country", source = "requestDTO.country")
  @Mapping(target = "industry", source = "requestDTO.industry")
  Employer toEmployer(CreateAccountEmployerRequestDTO requestDTO,
      @Context AddressService addressService);

  @AfterMapping
  default void mapAddress(@MappingTarget Employer employer,
      CreateAccountEmployerRequestDTO requestDTO, @Context AddressService addressService) {
    addressService.saveListAddress(requestDTO.getAddress());
  }
}
