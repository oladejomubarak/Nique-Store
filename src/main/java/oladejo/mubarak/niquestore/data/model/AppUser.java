package oladejo.mubarak.niquestore.data.model;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document
public class AppUser {
    @Id
    private String id;
    //@Email
    @NotBlank(message = "This field can't be empty")
    private String email;
    @NotBlank(message = "This field can't be empty")
    private String firstname;
    @NotBlank(message = "This field can't be empty")
    private String lastname;
    @NotBlank(message = "This field can't be empty")
    private String password;
    @NotBlank(message = "This field can't be empty")
    private String phoneNumber;
    private boolean isEnabled = false;
    private Set<Role> role;
    @DBRef
    private Cart cart;
}
