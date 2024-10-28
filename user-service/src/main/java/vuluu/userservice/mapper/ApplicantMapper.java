package vuluu.userservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import vuluu.userservice.dto.request.CreateAccountApplicantRequestDTO;
import vuluu.userservice.entity.Applicant;
import vuluu.userservice.service.AddressService;

@Mapper(componentModel = ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface ApplicantMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "skill", ignore = true)
  Applicant toApplicant(CreateAccountApplicantRequestDTO requestDTO,
      @Context AddressService addressService);

  @AfterMapping
  default void mapAddress(@MappingTarget CreateAccountApplicantRequestDTO requestDTO,
      @Context AddressService addressService) {
    addressService.saveListAddress(requestDTO.getAddress());
  }
}
