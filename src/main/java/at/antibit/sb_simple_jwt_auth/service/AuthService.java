package at.antibit.sb_simple_jwt_auth.service;

import at.antibit.sb_simple_jwt_auth.model.User;
import at.antibit.sb_simple_jwt_auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String register(String username, String password) {
        checkUserInput(username, password);
        Optional<User> userOpt = repo.findByUsername(username);
        if(userOpt.isPresent())
            throw new RuntimeException("Username '"+username+"' already exists");

        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .role("USER_ROLE")
                .build();
        repo.save(user);
        log.info("User '"+username+"' registered successfully");
        return "User '" + username + "' registered";
    }

    public String registerAdmin(String username, String password) {
        checkUserInput(username, password);
        Optional<User> userOpt = repo.findByUsername(username);
        if(userOpt.isPresent())
            throw new RuntimeException("Username '"+username+"' already exists");

        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .role("ADMIN_ROLE")
                .build();
        repo.save(user);
        log.info("Admin '"+username+"' registered successfully");
        return "Admin '" + username + "' registered";
    }

    public String login(String username, String password) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User '" + username + "' not found"));

        if (!encoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwt.generateToken(username, user.getRole());
    }

    private void checkUserInput(String username, String password) {
        if (username == null)
            throw new RuntimeException("No username provided");
        if (username.isEmpty())
            throw new RuntimeException("Please provide a valid username");
        if (password == null)
            throw new RuntimeException("No password provided");
        if (password.isEmpty())
            throw new RuntimeException("Please provide a valid password");
    }
}
