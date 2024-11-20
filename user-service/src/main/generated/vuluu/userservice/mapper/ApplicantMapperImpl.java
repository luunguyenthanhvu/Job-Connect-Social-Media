package vuluu.userservice.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vuluu.userservice.dto.request.CreateAccountApplicantRequestDTO;
import vuluu.userservice.entity.Applicant;
import vuluu.userservice.service.AddressService;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-18T17:48:11+0700",
    comments = "version: 1.6.1, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class ApplicantMapperImpl implements ApplicantMapper {

    @Override
    public Applicant toApplicant(CreateAccountApplicantRequestDTO requestDTO, AddressService addressService) {
        if ( requestDTO == null ) {
            return null;
        }

        Applicant applicant = new Applicant();

        applicant.setFirstname( requestDTO.getFirstname() );
        applicant.setLastname( requestDTO.getLastname() );
        applicant.setDob( requestDTO.getDob() );
        applicant.setSummary( requestDTO.getSummary() );
        applicant.setEducationList( requestDTO.getEducationList() );
        applicant.setWorkExperiences( requestDTO.getWorkExperiences() );
        applicant.setSkills( requestDTO.getSkills() );
        applicant.setCertifications( requestDTO.getCertifications() );

        return applicant;
    }
}
