package at.antibit.sb_simple_jwt_auth.service;

import at.antibit.sb_simple_jwt_auth.controller.AuthController;
import at.antibit.sb_simple_jwt_auth.model.User;
import at.antibit.sb_simple_jwt_auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String register(String username, String password) {
        checkUserInput(username, password);
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent())
            throw new RuntimeException("Username '"+username+"' already exists");

        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .role("USER_ROLE")
                .build();
        userRepository.save(user);
        log.info("User '"+username+"' registered successfully");
        return "User '" + username + "' registered";
    }

    public String registerAdmin(String username, String password) {
        checkUserInput(username, password);
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent())
            throw new RuntimeException("Username '"+username+"' already exists");

        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .role("ADMIN_ROLE")
                .build();
        userRepository.save(user);
        log.info("Admin '"+username+"' registered successfully");
        return "Admin '" + username + "' registered";
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User '" + username + "' not found"));

        if (!encoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        log.info("User '"+username+"' logged in successfully");
        return jwt.generateToken(username, user.getRole());
    }

    public List<AuthController.UserDto> getUsers() {
        return this.userRepository.findAll().stream()
            .map(u -> new AuthController.UserDto(u.getId(), u.getUsername(), u.getRole()))
            .toList();
    }

    public void deleteUserById(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with ID '" + userId + "' not found"));
        userRepository.deleteById(userId);
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
