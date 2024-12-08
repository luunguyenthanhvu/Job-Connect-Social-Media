package vuluu.userservice.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vuluu.userservice.dto.request.CreateAccountApplicantRequestDTO;
import vuluu.userservice.entity.Applicant;
import vuluu.userservice.service.AddressService;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T16:25:01+0700",
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
        applicant.setUserEmail( requestDTO.getUserEmail() );
        applicant.setDob( requestDTO.getDob() );
        applicant.setPosition( requestDTO.getPosition() );
        applicant.setGender( requestDTO.getGender() );
        applicant.setObjective( requestDTO.getObjective() );
        applicant.setSkills( requestDTO.getSkills() );

        mapAddress( applicant, requestDTO, addressService );

        return applicant;
    }
}
