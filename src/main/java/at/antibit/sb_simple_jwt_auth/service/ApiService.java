package at.antibit.sb_simple_jwt_auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ApiService {

    public String getTestMessage() { return "Principal: '" + SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal()
            + "', Roles: " + getRolesString();
    }

    private String getRolesString() {
        String[] auths = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        return Arrays.toString(auths);
    }
}
