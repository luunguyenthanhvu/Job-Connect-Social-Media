package vuluu.postservice.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.entity.JobPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    builder = @Builder(disableBuilder = true))
public interface JobPostMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "postedDate", ignore = true)
  @Mapping(target = "applications", ignore = true)
  JobPost toJobPost(JobPostRequestDTO requestDTO);
}
