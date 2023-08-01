package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.dto.request.LoginRequest;
import oladejo.mubarak.niquestore.data.dto.request.UserDto;
import oladejo.mubarak.niquestore.data.model.AppUser;

public interface UserService {
    AppUser findByEmail(String email);
    AppUser register(UserDto userDto);
    String login(LoginRequest loginRequest);
}
