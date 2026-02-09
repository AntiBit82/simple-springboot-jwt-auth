package at.antibit.sb_simple_jwt_auth.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class ApiService {
    public String getTestMessage() {
        return "Hello User!";
    }

    public String getAdminTestMessage() {
        return "Hello Admin!";
    }
}
