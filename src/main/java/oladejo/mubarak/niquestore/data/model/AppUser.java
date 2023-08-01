package oladejo.mubarak.niquestore.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document
public class AppUser {
    @Id
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private boolean isEnabled = false;
    private Set<Role> role;
}
