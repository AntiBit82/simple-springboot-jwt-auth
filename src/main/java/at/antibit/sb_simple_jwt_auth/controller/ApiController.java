package at.antibit.sb_simple_jwt_auth.controller;

import at.antibit.sb_simple_jwt_auth.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/test/user")
    public String testUser() {
        return apiService.getTestMessage();
    }

    @Secured("ADMIN_ROLE")
    @GetMapping("/test/admin")
    public String testAdmin() {
        return apiService.getTestMessage();
    }
}
