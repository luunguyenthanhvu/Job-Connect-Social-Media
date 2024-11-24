package vuluu.userservice.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING,
    builder = @Builder(disableBuilder = true))
public interface ApplicantMapper {

}
