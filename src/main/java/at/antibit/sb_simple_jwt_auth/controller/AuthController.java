package at.antibit.sb_simple_jwt_auth.controller;

import at.antibit.sb_simple_jwt_auth.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthRequest req) {
        String message = service.register(req.username, req.password);
        return ResponseEntity.ok(Map.of(
                "message", message
        ));
    }

    @PostMapping("/register-admin")
    public ResponseEntity registerAdmin(@RequestBody AuthRequest req) {
        String  message = service.registerAdmin(req.username, req.password);
        return  ResponseEntity.ok(Map.of(
                "message", message
        ));
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest req) {
        return service.login(req.username, req.password);
    }

    @GetMapping("/users")
    public List<UserDto> listAllUsers() {
        return this.service.getUsers();
    }

    @Secured("ADMIN_ROLE")
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) {
        this.service.deleteUserById(id);
    }

    @Data
    static class AuthRequest {
        public String username;
        public String password;
    }

    @Data
    public static class UserDto {
        public final Long id;
        public final String username;
        public final String role;
    }
}
