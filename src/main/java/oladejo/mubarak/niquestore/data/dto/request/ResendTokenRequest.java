package oladejo.mubarak.niquestore.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class ResendTokenRequest {
    @NotBlank
    private String email;
}
