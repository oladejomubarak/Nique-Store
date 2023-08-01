package oladejo.mubarak.niquestore.controller;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.LoginRequest;
import oladejo.mubarak.niquestore.data.dto.request.UserDto;
import oladejo.mubarak.niquestore.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.register(userDto), HttpStatus.CREATED);
    }
    @GetMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }
}
