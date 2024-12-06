package vuluu.userservice.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.response.RoleResponseDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.entity.Role;
import vuluu.userservice.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-06T13:05:30+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(CreateAccountRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( requestDTO.getUsername() );
        user.setPassword( requestDTO.getPassword() );
        user.setEmail( requestDTO.getEmail() );
        user.setPhoneNumber( requestDTO.getPhoneNumber() );

        return user;
    }

    @Override
    public UserResponseDTO toUserResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( user.getId() );
        userResponseDTO.setUsername( user.getUsername() );
        userResponseDTO.setRoles( roleSetToRoleResponseDTOSet( user.getRoles() ) );
        userResponseDTO.setEmail( user.getEmail() );
        userResponseDTO.setPhoneNumber( user.getPhoneNumber() );
        userResponseDTO.setVerified( user.isVerified() );
        userResponseDTO.setDescription( user.getDescription() );

        return userResponseDTO;
    }

    @Override
    public RoleResponseDTO toRoleResponseDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();

        if ( role.getRoleName() != null ) {
            roleResponseDTO.setRoleName( role.getRoleName().name() );
        }
        roleResponseDTO.setDescription( role.getDescription() );

        return roleResponseDTO;
    }

    protected Set<RoleResponseDTO> roleSetToRoleResponseDTOSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponseDTO> set1 = new LinkedHashSet<RoleResponseDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( toRoleResponseDTO( role ) );
        }

        return set1;
    }
}
