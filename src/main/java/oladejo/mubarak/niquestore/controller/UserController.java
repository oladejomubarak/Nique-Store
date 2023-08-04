package oladejo.mubarak.niquestore.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.LoginRequest;
import oladejo.mubarak.niquestore.data.dto.request.UserDto;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
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
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws MessagingException {
        try {
            return new ResponseEntity<>(userService.register(userDto), HttpStatus.CREATED);
        }catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            return ResponseEntity.ok(userService.login(loginRequest));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
