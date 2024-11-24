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
  @Mapping(target = "user", ignore = true)
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
