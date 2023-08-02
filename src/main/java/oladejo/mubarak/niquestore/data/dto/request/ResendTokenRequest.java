package oladejo.mubarak.niquestore.data.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResendTokenRequest {
    @NonNull
    private String email;
}
