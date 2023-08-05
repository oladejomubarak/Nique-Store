package oladejo.mubarak.niquestore.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.*;
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
        }catch (NiqueStoreException | MessagingException e){
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
    @PatchMapping("confirm-password")
    public ResponseEntity<?> confirmPassword(@RequestBody ConfirmationTokenRequest confirmationTokenRequest){
        try{
            return ResponseEntity.ok(userService.confirmToken(confirmationTokenRequest));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("resend-token")
    public ResponseEntity<?> resendToken(@RequestBody ResendTokenRequest resendTokenRequest){
        try{
            return ResponseEntity.ok(userService.resendToken(resendTokenRequest));
        } catch (NiqueStoreException | MessagingException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        try{
            return ResponseEntity.ok(userService.changePassword(changePasswordRequest));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("forgot-pssword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        try{
            return ResponseEntity.ok(userService.forgotPassword(forgotPasswordRequest));
        } catch (NiqueStoreException | MessagingException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        try{
            return ResponseEntity.ok(userService.resetPassword(resetPasswordRequest));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
