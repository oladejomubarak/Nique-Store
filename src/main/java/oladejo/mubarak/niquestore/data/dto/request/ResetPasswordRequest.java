package oladejo.mubarak.niquestore.data.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResetPasswordRequest {
    @NonNull
    private String email;
    @NonNull
    private String newPassword;
    @NonNull
    private String confirmNewPassword;
    @NonNull
    private String token;
}
