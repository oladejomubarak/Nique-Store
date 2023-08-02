package oladejo.mubarak.niquestore.data.dto.request;

import lombok.Data;

@Data
public class ConfirmationTokenRequest {
    private String token;
    private String email;
}
