package oladejo.mubarak.niquestore.data.model;

import lombok.Data;

import java.util.Set;

@Data
public class AppUser {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private Set<Role> role;
}
