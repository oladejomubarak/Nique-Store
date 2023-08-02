package oladejo.mubarak.niquestore.data.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class ChangePasswordRequest {
    @NonNull
    private String email;
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;
    @NonNull
    private String confirmNewPassword;
}
