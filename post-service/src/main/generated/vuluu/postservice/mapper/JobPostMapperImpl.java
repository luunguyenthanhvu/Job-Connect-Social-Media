package vuluu.postservice.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.JobPostDetailResponseDTO;
import vuluu.postservice.dto.response.JobPostResponseDTO;
import vuluu.postservice.entity.JobPost;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-09T00:44:58+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class JobPostMapperImpl implements JobPostMapper {

    @Override
    public JobPost toJobPost(JobPostRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        JobPost jobPost = new JobPost();

        jobPost.setTitle( requestDTO.getTitle() );
        jobPost.setJobDescription( requestDTO.getJobDescription() );
        jobPost.setJobExpertise( requestDTO.getJobExpertise() );
        jobPost.setJobWelfare( requestDTO.getJobWelfare() );
        jobPost.setAddressId( requestDTO.getAddressId() );
        jobPost.setEmploymentType( requestDTO.getEmploymentType() );
        jobPost.setNumberOfPositions( requestDTO.getNumberOfPositions() );
        jobPost.setExpirationDate( requestDTO.getExpirationDate() );

        return jobPost;
    }

    @Override
    public JobPostResponseDTO toJobPostResponseDTO(JobPost jobPost) {
        if ( jobPost == null ) {
            return null;
        }

        JobPostResponseDTO jobPostResponseDTO = new JobPostResponseDTO();

        jobPostResponseDTO.setTitle( jobPost.getTitle() );
        jobPostResponseDTO.setEmploymentType( jobPost.getEmploymentType() );
        jobPostResponseDTO.setNumberOfPositions( jobPost.getNumberOfPositions() );
        jobPostResponseDTO.setPostedDate( jobPost.getPostedDate() );
        jobPostResponseDTO.setExpirationDate( jobPost.getExpirationDate() );

        return jobPostResponseDTO;
    }

    @Override
    public JobPostDetailResponseDTO toJobPostDetailResponseDTO(JobPost jobPost) {
        if ( jobPost == null ) {
            return null;
        }

        JobPostDetailResponseDTO jobPostDetailResponseDTO = new JobPostDetailResponseDTO();

        jobPostDetailResponseDTO.setId( jobPost.getId() );
        jobPostDetailResponseDTO.setTitle( jobPost.getTitle() );
        jobPostDetailResponseDTO.setJobDescription( jobPost.getJobDescription() );
        jobPostDetailResponseDTO.setJobExpertise( jobPost.getJobExpertise() );
        jobPostDetailResponseDTO.setJobWelfare( jobPost.getJobWelfare() );
        jobPostDetailResponseDTO.setUserId( jobPost.getUserId() );
        jobPostDetailResponseDTO.setAddressId( jobPost.getAddressId() );
        jobPostDetailResponseDTO.setEmploymentType( jobPost.getEmploymentType() );
        jobPostDetailResponseDTO.setNumberOfPositions( jobPost.getNumberOfPositions() );
        jobPostDetailResponseDTO.setPostedDate( jobPost.getPostedDate() );
        jobPostDetailResponseDTO.setExpirationDate( jobPost.getExpirationDate() );

        return jobPostDetailResponseDTO;
    }

    @Override
    public List<JobPostResponseDTO> toListJobPostResponseDTO(List<JobPost> jobPostList) {
        if ( jobPostList == null ) {
            return null;
        }

        List<JobPostResponseDTO> list = new ArrayList<JobPostResponseDTO>( jobPostList.size() );
        for ( JobPost jobPost : jobPostList ) {
            list.add( toJobPostResponseDTO( jobPost ) );
        }

        return list;
    }
}
