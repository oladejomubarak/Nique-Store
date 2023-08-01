package oladejo.mubarak.niquestore.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document
public class ConfirmationToken {
    @Id
    private String id;

    @NonNull
    private String token;
    @NonNull
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    @NonNull
    private LocalDateTime expiredAt;

    @NonNull
    private AppUser user;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, AppUser user){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}
