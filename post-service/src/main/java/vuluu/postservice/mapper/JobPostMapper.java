package vuluu.postservice.mapper;

import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.JobPostDetailResponseDTO;
import vuluu.postservice.dto.response.JobPostResponseDTO;
import vuluu.postservice.entity.JobPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    builder = @Builder(disableBuilder = true))
public interface JobPostMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "postedDate", ignore = true)
  @Mapping(target = "applications", ignore = true)
  JobPost toJobPost(JobPostRequestDTO requestDTO);

  JobPostResponseDTO toJobPostResponseDTO(JobPost jobPost);

  @Mapping(target = "applied", ignore = true)
  JobPostDetailResponseDTO toJobPostDetailResponseDTO(JobPost jobPost);

  List<JobPostResponseDTO> toListJobPostResponseDTO(List<JobPost> jobPostList);
}
