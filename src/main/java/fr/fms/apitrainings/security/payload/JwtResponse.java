package fr.fms.apitrainings.security.payload;


import fr.fms.apitrainings.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private Users user;
//    private Collection<GrantedAuthority> authorities;
}
