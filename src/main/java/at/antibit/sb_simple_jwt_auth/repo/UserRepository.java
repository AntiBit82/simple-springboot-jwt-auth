package at.antibit.sb_simple_jwt_auth.repo;

import at.antibit.sb_simple_jwt_auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
