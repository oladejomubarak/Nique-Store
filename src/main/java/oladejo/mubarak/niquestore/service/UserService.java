package oladejo.mubarak.niquestore.service;

import jakarta.mail.MessagingException;
import oladejo.mubarak.niquestore.data.dto.request.*;
import oladejo.mubarak.niquestore.data.model.AppUser;

public interface UserService {
    AppUser findByEmail(String email);
    String register(UserDto userDto) throws MessagingException;
    String login(LoginRequest loginRequest);
    String confirmToken(ConfirmationTokenRequest confirmationTokenRequest);
    String resendToken (ResendTokenRequest resendTokenRequest) throws MessagingException;
    String changePassword(ChangePasswordRequest changePasswordRequest);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    String deleteUserByEmail(String email);
    String deleteAllTokens();
}
