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
  @Mapping(target = "educations", ignore = true)
  @Mapping(target = "workExperiences", ignore = true)
  @Mapping(target = "firstname", source = "requestDTO.firstname")
  @Mapping(target = "lastname", source = "requestDTO.lastname")
  @Mapping(target = "dob", source = "requestDTO.dob")
  @Mapping(target = "objective", source = "requestDTO.objective")
  @Mapping(target = "skills", source = "requestDTO.skills")
  Applicant toApplicant(CreateAccountApplicantRequestDTO requestDTO,
      @Context AddressService addressService);

  @AfterMapping
  default void mapAddress(@MappingTarget CreateAccountApplicantRequestDTO requestDTO,
      @Context AddressService addressService) {
    addressService.saveListAddress(requestDTO.getAddress());
  }
}

