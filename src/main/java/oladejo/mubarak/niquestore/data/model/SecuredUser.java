package oladejo.mubarak.niquestore.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import oladejo.mubarak.niquestore.data.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class SecuredUser implements UserDetails {
    private final AppUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user
                .getRole()
                .stream()
                .map(roles -> new SimpleGrantedAuthority(roles.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
