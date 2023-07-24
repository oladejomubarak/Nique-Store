package oladejo.mubarak.niquestore.service;

import java.util.List;

public interface UserService{
    User findByEmail(String email);
    User register(UserDto userDto);
    String login(LoginRequest loginRequest);
    List<User> getAllUsers();
}
