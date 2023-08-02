package oladejo.mubarak.niquestore.service;

import jakarta.mail.MessagingException;
import oladejo.mubarak.niquestore.data.dto.request.LoginRequest;
import oladejo.mubarak.niquestore.data.dto.request.UserDto;
import oladejo.mubarak.niquestore.data.model.AppUser;

public interface UserService {
    AppUser findByEmail(String email);
    String register(UserDto userDto) throws MessagingException;
    String login(LoginRequest loginRequest);
    String registerUser(CreateAppUserRequest createAppUserRequest) throws MessagingException;

    String confirmToken(ConfirmationTokenRequest confirmationTokenRequest);
    String resendToken (ResendTokenRequest resendTokenRequest) throws MessagingException;
    String deleteAppUser(String email);

    String changePassword(ChangePasswordRequest changePasswordRequest);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    AppUser findUserByEmailIgnoreCase(String email);
    String deleteUserByEmail(String email);
    String deleteAllTokens();
}
