package oladejo.mubarak.niquestore.data.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
}
