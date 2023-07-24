package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.dto.request.LoginRequest;
import oladejo.mubarak.niquestore.data.dto.request.UserDto;
import oladejo.mubarak.niquestore.data.model.AppUser;

import java.util.List;

public interface CustomerService {
    AppUser findByEmail(String email);
    AppUser register(UserDto userDto);
    String login(LoginRequest loginRequest);
    List<AppUser> getAllUsers();
}
