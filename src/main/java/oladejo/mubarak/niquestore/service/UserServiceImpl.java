package oladejo.mubarak.niquestore.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.config.email.EmailService;
import oladejo.mubarak.niquestore.config.security.JwtService;
import oladejo.mubarak.niquestore.data.dto.request.*;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Cart;
import oladejo.mubarak.niquestore.data.model.ConfirmationToken;
import oladejo.mubarak.niquestore.data.model.Role;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.repository.ConfirmationTokenRepository;
import oladejo.mubarak.niquestore.repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final SecuredUserService securedUserService;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final CartService cartService;

    @Override
    public AppUser findByEmail(String email){
        return userRepo.findAppUserByEmailIgnoreCase(email).orElseThrow(()-> new NiqueStoreException("user not found"));
    }

    @Override
    public AppUser findByPhoneNumber(String phone_number) {
        return null;
    }

    @Override
    public AppUser findByEmailOrPhoneNumber(String email, String phoneNumber) {
        return null;
    }

    @Override
    public String register(UserDto userDto) throws MessagingException {
        boolean foundUser = userRepo.existsByEmailIgnoreCaseOrPhoneNumber(userDto.getEmail(), userDto.getPhoneNumber());
        if(foundUser){throw new NiqueStoreException("email taken");}
//        else {
            AppUser user = new AppUser();
            Set<Role> rolesSet = new HashSet<>();
            rolesSet.add(Role.CUSTOMER);
            user.setEmail(userDto.getEmail());
            user.setRole(rolesSet);
            user.setFirstname(user.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            //user.setPassword(hashPassword(userDto.getPassword()));
            user.setPassword(passwordEncoder.encode(userDto.getConfirmPassword()));
            if (!userDto.getPassword().equals(userDto.getConfirmPassword()))
                throw new NiqueStoreException("passwords do not match");
            Cart cart = new Cart();
            user.setCart(cart);
            cartService.saveCart(cart);
            userRepo.save(user);

            String token = generateToken();
            emailService.send(userDto.getEmail(), buildEmail(userDto.getFirstname(),
                    token));

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(5),
                    user
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            return token;
//        }
    }
    @Override
    public String login(LoginRequest loginRequest){
        AppUser foundUser = findByEmail(loginRequest.getEmail());
        if(Objects.equals(foundUser.isEnabled(), false)) {throw new NiqueStoreException("You have not been verified");}
        if(!passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword())){
            throw new NiqueStoreException("Incorrect password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetails userDetails = securedUserService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        return "Bearer "+ token;
    }
    @Override
    public String confirmToken(ConfirmationTokenRequest confirmationTokenRequest) {
        AppUser foundUser = findByEmail(confirmationTokenRequest.getEmail());
        ConfirmationToken foundToken = confirmationTokenService.getConfirmationToken(confirmationTokenRequest.getToken())
                .orElseThrow(()-> new NiqueStoreException("such token does not exist"));
        if(foundToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new NiqueStoreException("Token has expired");
        }
        if (foundToken.getConfirmedAt() != null){
            throw new NiqueStoreException("Token has been used");
        }
        confirmationTokenService.setConfirmedAt(confirmationTokenRequest.getToken());

        foundUser.setEnabled(true);
        userRepo.save(foundUser);
        return "you are successfully verified";
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String resendToken (ResendTokenRequest resendTokenRequest) throws MessagingException {
        AppUser foundUser = findByEmail(resendTokenRequest.getEmail());
        if (foundUser.isEnabled()){ throw new NiqueStoreException("You are already verified, proceed to login");}
        else {
            String token = generateToken();
            emailService.send(resendTokenRequest.getEmail(), buildEmail(foundUser.getFirstname(), token));

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(5),
                    foundUser
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
        }
        return "token has been resent successfully";
    }
    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        AppUser foundUser = findByEmail(changePasswordRequest.getEmail());
        if(!BCrypt.checkpw(changePasswordRequest.getOldPassword(), foundUser.getPassword()))
            throw new NiqueStoreException("wrong old password");
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword()))
            throw new NiqueStoreException("Passwords do not match");
        foundUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepo.save(foundUser);
        return "Password changed successfully";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        AppUser foundUser = findByEmail(forgotPasswordRequest.getEmail());
        String token = generateToken();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                foundUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        emailService.send(foundUser.getEmail(), buildForgotPasswordEmail(foundUser.getFirstname(), token));
        return token;
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        AppUser foundUser = findByEmail(resetPasswordRequest.getEmail());
        ConfirmationToken foundToken = confirmationTokenService.getConfirmationToken(resetPasswordRequest.getToken())
                .orElseThrow(()-> new NiqueStoreException("such token does not exist"));
        if(foundToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new NiqueStoreException("Token has expired");
        }
        if (foundToken.getConfirmedAt() != null){
            throw new NiqueStoreException("Token has been used");
        }
        confirmationTokenService.setConfirmedAt(resetPasswordRequest.getToken());
        if(!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword())) {throw
                new NiqueStoreException("passwords do not match");
        }
        foundUser.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepo.save(foundUser);
        return "Password reset successfully";
    }
    @Override
    public String deleteUserByEmail(String email) {
        AppUser foundUser = findByEmail(email);
        userRepo.delete(foundUser);
        return "user deleted";
    }

    @Override
    public String deleteAllTokens() {
        confirmationTokenRepository.deleteAll();
        return "all tokens deleted";
    }

    @Override
    public void saveUser(AppUser appUser) {
        userRepo.save(appUser);
    }

    private String generateToken(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomNumber = secureRandom.nextInt(0, 9);
            stringBuilder.append(randomNumber);
        }
        return stringBuilder.toString();
    }

    private String buildEmail(String name, String token) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please copy the below token to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n The token will expire in 5 minutes time. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
    private String buildForgotPasswordEmail (String lastName, String token){
        return "Here's the link to reset your password"
                + "                                      "
                + "                                        "
                + "<p>Hello \"" + lastName + "\",</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + token + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p> " +
                "<p>Token expires in 5 minutes</p>";
    }
}
