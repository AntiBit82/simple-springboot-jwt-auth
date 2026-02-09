package at.antibit.sb_simple_jwt_auth.controller;

import at.antibit.sb_simple_jwt_auth.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/hello")
    public String hello() {
        return "Hello authenticated user";
    }

    @Secured("ADMIN_ROLE")
    @GetMapping("/admin")
    public String adminOnly() {
        return "Hello Admin";
    }

    @Data
    public static class AuthRequest {
        public String username;
        public String password;
    }
}
