package at.antibit.sb_simple_jwt_auth.model.dto;

import at.antibit.sb_simple_jwt_auth.model.entity.User;

public record UserDto(long id, String username, String role) {
    public UserDto(User user) {
        this(user.getId(), user.getUsername(), user.getRole());
    }
}
