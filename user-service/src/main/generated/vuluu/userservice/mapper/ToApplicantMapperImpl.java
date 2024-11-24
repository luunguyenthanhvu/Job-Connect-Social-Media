package vuluu.userservice.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vuluu.userservice.dto.request.CreateAccountApplicantRequestDTO;
import vuluu.userservice.entity.Applicant;
import vuluu.userservice.service.AddressService;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-24T17:39:49+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class ToApplicantMapperImpl implements ToApplicantMapper {

    @Override
    public Applicant toApplicant(CreateAccountApplicantRequestDTO requestDTO, AddressService addressService) {
        if ( requestDTO == null ) {
            return null;
        }

        Applicant applicant = new Applicant();

        applicant.setFirstname( requestDTO.getFirstname() );
        applicant.setLastname( requestDTO.getLastname() );
        applicant.setDob( requestDTO.getDob() );
        applicant.setObjective( requestDTO.getObjective() );
        applicant.setSkills( requestDTO.getSkills() );
        applicant.setGender( requestDTO.getGender() );

        mapAddress( applicant, requestDTO, addressService );

        return applicant;
    }
}
