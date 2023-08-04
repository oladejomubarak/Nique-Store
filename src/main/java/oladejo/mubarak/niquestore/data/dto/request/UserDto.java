package oladejo.mubarak.niquestore.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {
    @NotBlank(message = "This field can't be empty")
    private String email;
    @NotBlank(message = "This field can't be empty")
    private String password;
    @NotBlank(message = "This field can't be empty")
    private String confirmPassword;
    @NotBlank(message = "This field can't be empty")
    private String firstname;
    @NotBlank(message = "This field can't be empty")
    private String lastname;
    @NotBlank(message = "This field can't be empty")
    private String phoneNumber;
}
