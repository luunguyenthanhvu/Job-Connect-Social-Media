package vuluu.userservice.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vuluu.userservice.dto.request.CreateAccountEmployerRequestDTO;
import vuluu.userservice.entity.Employer;
import vuluu.userservice.service.AddressService;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-24T17:39:49+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class EmployerMapperImpl implements EmployerMapper {

    @Override
    public Employer toEmployer(CreateAccountEmployerRequestDTO requestDTO, AddressService addressService) {
        if ( requestDTO == null ) {
            return null;
        }

        Employer employer = new Employer();

        employer.setCountry( requestDTO.getCountry() );
        employer.setIndustry( requestDTO.getIndustry() );

        mapAddress( employer, requestDTO, addressService );

        return employer;
    }
}
